public class ExceptionTest {
    public static void main(String[] args) {
    try {
    int result = divide(10, 0);
    System.out.println("Result: " + result);
    } catch (ArithmeticException e) {
    System.out.println("Exception caught: " + e.getMessage());
    }
 }
    public static int divide(int a, int b) throws ArithmeticException {
    return a / b;
    }
   }
   /* OUTPUT:
    [Running] cd "c:\Users\HP\Desktop\JAVA-II\ExceptionHandling\" && javac ExceptionTest.java && java ExceptionTest
Exception caught: / by zero

[Done] exited with code=0 in 7.432 seconds
     */