package ArraysAndCollections;

import java.util.Scanner;

public class SumEvenArray {
    public static void main(String[] args) {
        int size;
        long sum = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of elements:");
        size = sc.nextInt();

        int[] els = new int[size];

        System.out.println("Enter " + size + " elements:");
        for (int i = 0; i < size; i++) {
            els[i] = sc.nextInt();
        }

        System.out.println("Entered elements:");
        for (int k = 0; k < size; k++) {
            System.out.print(els[k] + " ");
        }

        for (int num : els) {
            if (num % 2 == 0) {
                sum += num;
            }
        }

        System.out.println("\nSum of even numbers: " + sum);
    }
}
