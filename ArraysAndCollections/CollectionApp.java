package ArraysAndCollections;

import java.util.Collection;
import java.util.HashSet;

public class CollectionApp {
    public static void main(String[] args) {
      //  Collection c1 = new HashSet();   a collection is a root most interface
      Set c1 = new HashSet();  // doesn't allow repetition and sorts( default asc), use <> to specify datatype
        c1.add(20);
        c1.add(40);
        c1.add("Tom");
        
        System.out.println(c1);

    }
}
