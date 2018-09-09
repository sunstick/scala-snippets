package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IterationOnSynchronizedCollections {
    // note that synchronized collection is different from concurrent collection
    public static final List<String> things = Collections.synchronizedList(new ArrayList<>());

    public static <T> void doSomething(T t) {

    }

    public static void obviousIterator() {
        // may throw ConcurrentModificationException
        for (String aThing : things) {
            doSomething(aThing);
        }
    }

    public static void hiddenIterator() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            things.add(String.valueOf(random.nextInt()));
        }
        // may throw ConcurrentModificationException, this indirectly calls things.toString and things is a collection
        System.out.println("Debug: added ten elements: " + things);
        // similar hidden iterator calls: equals, hashCode, containsAll, removeAll, retainAll
    }
}
