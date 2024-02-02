package com.samuelAdetoye;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.samuelAdetoye.JwtGenerator.generateJwt;
import static com.samuelAdetoye.JwtGenerator.verifyToken;

public class ConcurrentInputValidator {


    private static class ValidationTask extends RecursiveTask<Boolean> {
        private final List<Callable<Boolean>> tasks;

        public ValidationTask(List<Callable<Boolean>> tasks) {
            this.tasks = tasks;
        }

        @Override
        protected Boolean compute() {
            if (tasks.size() == 1) {
                try {
                    return tasks.get(0).call();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            int middle = tasks.size() / 2;
            List<ValidationTask> subtasks = new ArrayList<>();
            subtasks.add(new ValidationTask(tasks.subList(0, middle)));
            subtasks.add(new ValidationTask(tasks.subList(middle, tasks.size())));

            invokeAll(subtasks);

            return subtasks.stream().allMatch(ValidationTask::join); // Change this line

        }
    }

    public static void validateConcurrently(String username, String email, String password, String dateOfBirth) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        List<Callable<Boolean>> validationTasks = new ArrayList<>();
        validationTasks.add(() -> UserValidatorImpl.isUsernameValid(username));
        validationTasks.add(() -> UserValidatorImpl.isEmailValid(email));
        validationTasks.add(() -> UserValidatorImpl.isPasswordValid(password));
        validationTasks.add(() -> UserValidatorImpl.isDobValid(dateOfBirth));

        ValidationTask validationTask = new ValidationTask(validationTasks);
        boolean validationFailed = !forkJoinPool.invoke(validationTask); // Change this line
        forkJoinPool.shutdown();

        if (validationFailed) {
            // Print validation messages
            System.out.println("Validation failed:");
            printValidationResult(validationTasks.get(0), "Username");
            printValidationResult(validationTasks.get(1), "Email");
            printValidationResult(validationTasks.get(2), "Password");
            printValidationResult(validationTasks.get(3), "Date of Birth");
        } else {
            String generatedJwt = generateJwt(username);
            System.out.println("This is your JWT token: " + generatedJwt);
            System.out.println("");
            System.out.println( "************ VERIFICATION STATUS *********************");
            System.out.println("");
            System.out.println(verifyToken(generatedJwt));

           // System.out.println("All validations passed!");
        }
    }

    private static void printValidationResult(Callable<Boolean> task, String fieldName) {
        try {
            if (!task.call()) {
                System.out.println(fieldName + ": " + getErrorMessage(fieldName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getErrorMessage(String fieldName) {
        switch (fieldName) {
            case "Username":
                return "must not be empty and should have a minimum of 4 characters.";
            case "Email":
                return "must not be empty and should be a valid email address.";
            case "Password":
                return "must be a strong password with at least 1 upper case, " +
                        "1 special character (!@#$%^&*), 1 number, and a minimum of 8 characters.";
            case "Date of Birth":
                return "must not be empty and should be 16 years or greater.";
            default:
                return "Validation failed.";
        }
    }
}