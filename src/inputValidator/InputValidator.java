package inputValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {

    public InputValidator(){

    }

    public ReturnValues validateNumberInput(Scanner scanner, String prompt){
        try{
            System.out.print(prompt);
            double number = scanner.nextDouble();
            scanner.nextLine(); // Consume the leftover newline
            return new ReturnValues(number, true);
        }catch (InputMismatchException e){
            System.out.println("\nInteger value only!");
            scanner.nextLine();
            return new ReturnValues(-1,false);
        }
    }

//  tryAgainOrExitMenu will return true or false if user wants to exit
    public boolean tryAgainOrExitMenu(Scanner scanner){
        while (true) {
            System.out.println("Enter a valid choice!!");
            System.out.println("1. Try Again");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            String stringInputChoice = scanner.nextLine().trim();

            if (stringInputChoice.equals("1")) {
                return false;  // Retry input
            } else if (stringInputChoice.equals("2")) {
                System.out.println("Exiting...");
                return true; // Exit
            } else {
                System.out.println("Invalid choice! Please enter '1' to try again or '2' to exit.");
            }
        }
    }

    public ReturnValues validateProductInput(String prompt, String errorPrompt,Scanner scanner) {
        while (true) {
            ReturnValues returnValues = validateNumberInput(scanner, prompt);
            if (returnValues.getReturningBoolean() && returnValues.getReturningNumber() >= 1) {
                return returnValues;
            }
            System.out.println(errorPrompt);
        }
    }

}
