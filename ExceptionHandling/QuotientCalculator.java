import java.util.Scanner;
public class QuotientCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the first number (num1): ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter the second number (num2): ");
            double num2 = scanner.nextDouble();
            double quotient = num1 / num2;
            System.out.println("The quotient of " + num1 + " divided by " + num2 + " is: " + quotient);
        } catch (ArithmeticException aex) {
            System.out.println("Error: Cannot divide by zero.");
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred. Please make sure to enter valid numbers.");
        } 
        scanner.close();
    }
}