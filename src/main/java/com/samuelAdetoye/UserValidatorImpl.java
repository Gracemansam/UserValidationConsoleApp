package com.samuelAdetoye;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class UserValidatorImpl {
    private User user = new User();

//    public UserValidatorImpl(User user) {
//        this.user = user;
//    }

    public static boolean validateInputs(String username, String email, String password, String dobInput) {

        boolean isValid = true;

        if (username == null) {
            System.out.println("Username: must not be null.");
            isValid = false;
        } else if (username.isEmpty() || username.length() < 4) {
            System.out.println("Username: must have a minimum of 4 characters.");
            isValid = false;
        }

        if (email == null) {
            System.out.println("Email: must not be null.");
            isValid = false;
        } else if (!isEmailValid(email)) {
            System.out.println("Email: must be a valid email address.");
            isValid = false;
        }

        if (password == null) {
            System.out.println("Password: must not be null.");
            isValid = false;
        } else if (!isPasswordValid(password)) {
            System.out.println("Password: must be a strong password with " +
                    "at least 1 upper case, 1 special characters: e.g., !@#$%^&*, 1 number, and " +
                    "a minimum of 8 characters.");
            isValid = false;
        }

        if (dobInput == null) {
            System.out.println("Date of Birth: must not be null.");
            isValid = false;
        } else if (!isDobValid(dobInput)) {
            System.out.println("Date of Birth: must be in the correct format and 16 years or greater.");
            isValid = false;
        }

        if (isValid) {
            System.out.println("All validations passed.");
        } else {
            System.out.println("Validation failed. Please check the errors above.");
        }

        return isValid;
    }
    public static boolean isUsernameValid(String username) {
        if (username == null) {
            return false;
        }

        if (username.length() >= 4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
           // System.out.println("Email: must not be null.");
            return false;
        }

        // Simple email validation using regex
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email.trim()); // Trim the email before matching

        if (matcher.matches()) {
            return true;
        } else {
          //  System.out.println("Email: must be in the correct format.");
            return false;
        }
    }


    public static boolean isPasswordValid(String password) {
        if (password == null) {
          //  System.out.println("Password: must not be null.");
            return false;
        }

        // Strong password validation using regex
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (password.matches(passwordRegex)) {
            return true;
        } else {
         //   System.out.println("Password: must be a strong password with at least 1 lower case, " +
            //        "1 upper case, 1 digit, and 1 special character (!@#$%^&*), and a minimum of 8 characters.");
            return false;
        }
    }

    public static boolean isDobValid(String dobInput) {
        if (dobInput == null || dobInput.isEmpty()) {
          //  System.out.println("Date of birth: must not be empty.");
            return false;
        }

        try {
            // Validate date format and age
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dob = LocalDate.parse(dobInput, formatter);
            LocalDate today = LocalDate.now();
            LocalDate minDob = today.minusYears(16);

            if (dob.isBefore(today) && dob.isBefore(minDob)) {
                return true;
            } else {
             //   System.out.println("Date of birth: must be at least 16 years ago.");
                return false;
            }
        } catch (DateTimeParseException e) {
            // Handle the error if not written in the right format
            //System.out.println("Date of birth: should be in the format dd-MM-yyyy.");
            return false;
        }
    }

}
