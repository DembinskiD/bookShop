package pl.biblioteka.biblioteka;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.biblioteka.biblioteka.products.Book;

import java.beans.Transient;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;


class BookOrderTest {
    private static List<Book> books = new ArrayList<>();
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static Random random = new Random();
    private BookOrder testBookOrder = new BookOrder.Builder()
            .setPrice(0)
            .setOrderedBooks(books)
            .build();


    @Transient
    public static void generateBooks() {

    }

    @Test
    void computePrice() {
        AtomicReference<Double> testSum = new AtomicReference<>((double) 0);

        testBookOrder.getOrderedBooks().forEach(a -> {
            testSum.updateAndGet(v -> (double) (v + a.getProductPrice()));
        });
        assertEquals(testSum.get(), testBookOrder.getPrice());
    }

    @Test
    void submit() {
        BookOrderArchive.INSTANCE.submit(testBookOrder);
        assertTrue(BookOrderArchive.INSTANCE.getArchive().contains(testBookOrder));
    }

    @Test
    void submitFromOrderSide() {
        testBookOrder.submit();
        assertTrue(BookOrderArchive.INSTANCE.getArchive().contains(testBookOrder));
    }
}