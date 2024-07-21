package LibraryConsole;

import java.util.Scanner;

public class InputHandler {
    private static Scanner numberIn = new Scanner(System.in);
    /*
     * handels integer input of menu choices
     * makes sure that input is numeric, and in the currect
     * range!
     * - can be from 1 till max, doesnt have to get the min
     */
    public static int integerInputCheck(int min, int max, String menu) {
        boolean isInRange = false;
        int choice;
        do {
            System.out.printf(menu);
            while (!numberIn.hasNextInt()) {
                System.out.printf("invalid choice, choose a number between %d-%d\n", min, max);
                System.out.printf(menu);
                numberIn.next(); // this is important!
            }
            choice = numberIn.nextInt();
            isInRange = (choice >=min && choice <= max);
            if(!isInRange) {
                System.out.printf("invalid choice, choose a number between %d-%d\n", min, max);
            }
        } while (!isInRange);
        return choice;
    }
}
