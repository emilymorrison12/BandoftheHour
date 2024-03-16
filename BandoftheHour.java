package Project1;

import java.util.Scanner;

public class BandoftheHour {
    public static final Scanner keyboard = new Scanner(System.in);
    public static final int ROWS_MAX = 10;
    public static final int POSITIONS_MAX = 8;

    public static void main(String[] args) {
        System.out.println("Welcome to the Band of the Hour");
        // Get number of rows and positions with validation
        int rows = getValidatedInput("Please enter number of rows: ", 1, ROWS_MAX);

        double[][] stands = new double[ROWS_MAX][]; // Use null for rows not in use

        for (int i = 0; i < rows; i++) {
            int positions = getValidatedInput(String.format("Please enter number of positions in row %c: ", 'A' + i), 1, POSITIONS_MAX);
            stands[i] = new double[positions]; // Initialize row with specified positions
        } // end of for loop
        // get menu option add, remove, print, or exit
        while (true) {
            System.out.println("(A)dd, (R)emove, (P)rint, (X)it: ");
            String choice = keyboard.nextLine().toUpperCase();
            switch (choice) {
                case "A":
                    addMusician(stands);
                    break;
                case "R":
                    removeMusician(stands);
                    break;
                case "P":
                    printAssignment(stands, rows);
                    break;
                case "X":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.err.println("Invalid option, try again.");
                    break;
            } // end of switch statement
        } // end of while loop
    } // end of main method

    // validate inputs of row and position quantities
    public static int getValidatedInput(String message, double min, double max) {
        int input;
        do {
            System.out.println(message);
            while (!keyboard.hasNextInt()) {
                System.err.println("Invalid input, please enter an integer:");
                keyboard.next();
            }
            input = keyboard.nextInt();
            keyboard.nextLine();
            if (input < min || input > max) {
                System.err.println("Error: Out of range, try again.");
            }
        } while (input < min || input > max);
        return input;
    } // end of getValidatedInput method
    // validate input of weights
    public static double getValidatedDouble(String message, double min, double max) {
        double input;
        do {
            System.out.println(message);
            while (!keyboard.hasNextDouble()) {
                System.err.println("Invalid input, please enter a number:");
                keyboard.next(); // Consume the invalid input
            }
            input = keyboard.nextDouble();
            keyboard.nextLine(); // Consume newline left-over
            if (input < min || input > max) {
                System.err.println("Error: Out of range, try again.");
            }
        } while (input < min || input > max);
        return input;
    } // end of getValidatedDouble method
    // get validated rows within range
    public static char getValidatedChar() {
        char input = '\0'; // Initialize `input` to the null character
        do {
            System.out.println("Please enter row letter (A-J): ");
            String line = keyboard.nextLine().toUpperCase();
            if (line.length() == 0) {
                System.err.println("Error: No input received.");
                continue;
            }
            input = line.charAt(0);
            if (input < 'A' || input > 'A' + ROWS_MAX - 1) {
                System.err.println("Error: Invalid row letter.");
            }
        } while (input < 'A' || input > 'A' + ROWS_MAX - 1);
        return input;
    } // end of getValidatedChar method

    public static void addMusician(double[][] stands) {
        char rowLetter = getValidatedChar();
        int row = rowLetter - 'A'; // Convert letter to row index

        if (row >= stands.length || stands[row] == null) {
            System.err.println("Error: Invalid row.");
            return;
        }

        int position = getValidatedInput("Please enter position number: ", 1, stands[row].length) - 1;
        double weight = getValidatedDouble("Please enter weight (45.0-200.0): ", 45.0, 200.0);

        if (stands[row][position] != 0.0) {
            System.err.println("Error: Position already occupied.");
            return;
        }

        // Correct total weight calculation
        double totalWeight = 0.0; // Reset total weight
        for (double posWeight : stands[row]) {
            totalWeight += posWeight;
        }

        if (totalWeight + weight > stands[row].length * 100) {
            System.err.println("Error: Adding musician would exceed weight limit.");
            return;
        }

        stands[row][position] = weight;
        System.out.println("Musician added.");
    } // end of addMusician method



    public static void removeMusician(double[][] stands) {
        char rowLetter = getValidatedChar();
        int row = rowLetter - 'A'; // Convert letter to row index

        // Check if row is initialized
        if (row >= stands.length || stands[row] == null) {
            System.err.println("Error: Invalid row.");
            return;
        }

        int position = getValidatedInput("Please enter position number: ", 1, stands[row].length) - 1;

        if (stands[row][position] == 0.0) {
            System.err.println("Error: Position is vacant.");
            return;
        }

        stands[row][position] = 0.0;
        System.out.println("Musician removed.");
    } // end of removeMusician method
    public static void printAssignment(double[][] stands, int rows) {
        System.out.println("Current assignment:");
        for (int i = 0; i < rows; i++) {
            if (stands[i] == null) continue; // Skip uninitialized rows

            char rowLetter = (char) ('A' + i);
            double totalWeight = 0.0;
            for (double weight : stands[i]) {
                totalWeight += weight;
            } // end of for loop
            double averageWeight = totalWeight / stands[i].length;

            System.out.printf("%c: ", rowLetter);
            for (double weight : stands[i]) {
                System.out.printf("%.1f ", weight);
            } // end of for loop
            System.out.printf(" [Total: %.1f, Avg: %.1f]\n", totalWeight, averageWeight);
        } // end of for loop
    } //end of printAssignment method



} // end of the BandoftheHour class