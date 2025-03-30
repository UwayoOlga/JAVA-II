public class MultipleExceptions {
    public static void main(String[] args) {
        try { 
            String str = null;
            System.out.println(str.length());  
            
             int result = 10 / 0; 

            int[] arr = new int[5];
            
        } catch (NullPointerException e) {
            System.out.println("Null reference: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Math error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General exception: " + e.getMessage());
        }
    }
}