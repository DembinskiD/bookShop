package pl.biblioteka.biblioteka;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.biblioteka.biblioteka.products.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


class BookTest {
    static List<Productable> list = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        for (int i = 0; i < 20; i++) {
            list.add(ProductFabric.getProduct(ProductType.getRandom())
                .withGenre(Genre.getRandom())
                .withPublisher(PublisherList.getRandom()));
        }
    }

    @Test
    public void test() {
        list.forEach(item -> System.out.println(item.getClass() + " " + item.getId()));
        Assertions.assertTrue(true);
    }


    @Test
    void anotherTest() {
//        Function<Productable, Long> testFunction = Productable::getId;
        List<Long> longList = list.stream()
                .map(Productable::getId)
                .collect(Collectors.toList());

        Function<Long, Integer> intConsumer = Long::intValue;

            List<Integer> stringList = longList.stream()
                .map(intConsumer)
                .collect(Collectors.toList());

            stringList.forEach(System.out::print);
    }


    @Test
    void predicateTesting() {
        Predicate<Productable> productablePredicate = x -> x.getClass().equals(Book.class);


        List<Productable> collectedList = list.stream()
                .filter(productablePredicate.or(x -> x.getClass().equals(Press.class)))
                .collect(Collectors.toList());

        list.forEach(x -> System.out.printf("%d %s\n", x.getId(), x.getClass().getSimpleName()));

    }

    @Test
    void simpleToStringFormatterTest() {
        list.forEach(System.out::print);
    }




}