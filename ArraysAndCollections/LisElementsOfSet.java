package ArraysAndCollections;

import java.util.Iterator;
import java.util.Set;

public class LisElementsOfSet {
  public static void main(String[] args) {
    Set<Float> s1 = new TreeSet<>();
    s1.add(10.0f);
    s1.add(20.0f);
    s1.add(30.0f);
    s1.add(40.0f);
    s1.add(50.0f);
    
    System.out.println(s1);
    //Iteration
    for ( float i : s1){
        System.out.println(i);
    }
// iterator method
Iterator itr = s1.iterator();
System.out.println(itr.next());
while (itr.hasNext()) {
    System.out.println(itr.next());
    
}
    }
  }  

