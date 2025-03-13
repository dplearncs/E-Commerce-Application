package services;

import inputValidator.InputValidator;
import inputValidator.ReturnValues;

import java.util.Scanner;

public class ECommerceApp {
    UserService userService;
    ProductService productService;
    InputValidator inputValidator;
    ReturnValues returnValues;
    Scanner scanner;

    public ECommerceApp(){
        userService = new UserService();
        productService = new ProductService();
        inputValidator = new InputValidator();
        returnValues = new ReturnValues(-1,false);
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n=== Welcome to E-Commerce App ===");
        while(true){

            do {
                returnValues = inputValidator.validateNumberInput(scanner, """
                        1. Register User
                        2. Login
                        3. View all products
                        4. Exit
                        Enter your choice: \s""");
            } while (!returnValues.getReturningBoolean());

            switch ((int)returnValues.getReturningNumber()) {
                case 1:
                    userService.registerUser(scanner, inputValidator, returnValues);
                    break;
                case 2:
                    userService.loginUser(scanner, productService, inputValidator, returnValues);
                    break;
                case 3:
                    productService.getAllProduct();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("\nInvalid choice! Please enter a valid option");

            }
        }
    }
}