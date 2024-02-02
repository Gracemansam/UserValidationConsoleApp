package com.samuelAdetoye;


import java.util.Scanner;



import static com.samuelAdetoye.ConcurrentInputValidator.validateConcurrently;
import static com.samuelAdetoye.UserValidatorImpl.validateInputs;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Brilloconnetz User Validation App!");
        System.out.println("");

        // Get user input
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your date of birth: (in this pattern: dd-MM-yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.println("");

        System.out.println("************************ VALIDATING INPUTS ************************");
        System.out.println("");

        //A. perform these validations

        validateInputs(username, email, password, dateOfBirth);
        System.out.println("");

        System.out.println("************************ VALIDATING INPUTS CONCURRENTLY ************************");
        System.out.println("");

        //B. Perform these validations concurrently

        validateConcurrently(username, email, password, dateOfBirth);

    }
}
