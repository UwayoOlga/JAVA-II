public class Test {
    public static void main(String[] args) {
    try {
    System.out.println(10 / 0);
    } finally {
    System.out.println("Inside finally block");
    }
}
} 
/* OUTPUT:
 * [Running] cd "c:\Users\HP\Desktop\JAVA-II\ExceptionHandling\" && javac Test.java && java Test
Inside finally block
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at Test.main(Test.java:4)

[Done] exited with code=1 in 1.708 seconds
 */