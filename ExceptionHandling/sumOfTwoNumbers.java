import java.util.Scanner;

public class sumOfTwoNumbers {
    public static void main(String[] args) {
        String num1 = 5;
        String num2 = 10;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the 1st number:"); 
        num1 = sc.nextLine();
        System.out.println("Enter the 2nd number:"); 
        num2= sc.nextLine();
        int sum ;

        try{
         num1 =Integer.parseInt(num1);
         num2 = Integer.parseInt(num2);
        sum= num1 +num2;
        System.out.println("The sum = " +sum);
    } catch(NumberFormatException nex){
        System.out.println("Incorrect input(s). + please provide only numbers");
        System.out.println("Error:::"+ nex);
    }
    }   
}
