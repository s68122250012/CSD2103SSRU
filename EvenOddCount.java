import java.util.Scanner;

public class EvenOddCount {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[] number = new int[10];
        int even = 0;
        int odd = 0;

        for (int i = 0; i < 10; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            number[i] = sc.nextInt();

            if (number[i] % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }

        System.out.println("Even count = " + even);
        System.out.println("Odd count = " + odd);

        sc.close();
    }
}