package ArraysAndCollections;

import java.lang.reflect.Array;
import java.util.Set;

public class setUnion {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        s1.add(50);
        s1.add(20);
        s1.add(40);
        s1.add(9); 
        Set<Integer> set2 = new HashSet<>(Array.asList(50, 9, 7, 11));
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println(union);
    }
}
