package services;

import inputValidator.InputValidator;
import inputValidator.ReturnValues;
import models.Admin;
import models.Customer;
import models.Product;
import models.User;
import storage.FileService;

import java.util.*;

public class UserService {
    private final String fileName = "userData.data";
    private final List<User> users;

    public UserService(){
        users = new FileService<User>(fileName).loadData();
    }

    public void registerUser(Scanner scanner, InputValidator inputValidator, ReturnValues returnValues) {
        System.out.println("\n=== User Registration ===");
        returnValues = inputValidator.validateNumberInput(scanner, """
                \n1. Customer
                2. Admin
                3. Exit
                Enter your choice:\s""");

        if(returnValues.getReturningBoolean()){
            if(returnValues.getReturningNumber() == 1 || returnValues.getReturningNumber() == 2){
                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine().trim();
                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine().trim();
                System.out.print("Enter username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();

//          Check if the username is already taken
                while(!checkUsernameAvailability(username))
                {
                    System.out.print("Username already exists! \nEnter a new username: ");
                    username = scanner.nextLine();
                }

                if(returnValues.getReturningNumber() == 1) {
                    users.add((new Customer(firstName, lastName, username, password)));
                    System.out.println("Customer created successfully!");
                } else {
                    users.add(new Admin(firstName, lastName, username, password));
                    System.out.println("Admin created successfully!");
                }
                new FileService<User>(fileName).saveData(users);
                getAllUsers();
            }
            else if(returnValues.getReturningNumber() == 3){
                System.out.println("Exiting...");
            }
            else{
                System.out.println("Not a valid choice!");
                scanner.nextLine(); // Clear buffer
                if(!inputValidator.tryAgainOrExitMenu(scanner)){
                    registerUser(scanner, inputValidator, returnValues);
                }
            }
        }
        else {
            if(!inputValidator.tryAgainOrExitMenu(scanner)){
                registerUser(scanner, inputValidator, returnValues);
            }
        }
    }

    public void loginUser(Scanner scanner, ProductService productService, InputValidator inputValidator, ReturnValues returnValues){
        System.out.println("\n=== User Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = authenticateUser(username, password);

        if(user != null){
            if (user instanceof Admin) {
                ((Admin) user).adminPage(scanner, this, productService);
            } else if (user instanceof Customer) {
                ((Customer) user).customerPage(scanner, productService);
            }
        }else{
            System.out.print("\nInvalid login information!");
            while(true){
                returnValues = inputValidator.validateNumberInput(scanner, """
                            \n1. Try Again
                            2. Register user
                            3. Exit
                            Enter you choice: \s""");


                if(returnValues.getReturningBoolean()){
                    if (returnValues.getReturningNumber() == 1
                        || returnValues.getReturningNumber() == 2
                        || returnValues.getReturningNumber() == 3) {
                        break; // Valid input, exit loop
                    } else {
                        System.out.println("Please enter a valid choice!.");
                    }
                }
            }
            switch ((int)returnValues.getReturningNumber()) {
                case 1:
                    loginUser(scanner, productService, inputValidator, returnValues);
                    break;
                case 2:
                    registerUser(scanner, inputValidator, returnValues);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public boolean deleteUser(Scanner scanner, Admin admin) {
        System.out.println("=== User Deletion ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        String inputYorN = "";

        boolean currentAdminUser = false;
        if(username.equals(admin.getUsername())){
            System.out.println("Are you sure you want to delete your account? y/n");

            while(true){
                inputYorN = scanner.nextLine();
                if(inputYorN.equalsIgnoreCase("y")){
                    inputYorN = "y";
                    currentAdminUser = true;
                    System.out.println("Deleting your account");
                    break;
                }
                else if(inputYorN.equalsIgnoreCase("n")){
                    getAllUsers();
                    deleteUser(scanner,admin);
                    break;
                }
                else {
                    System.out.println("Enter y or n");
                    deleteUser(scanner,admin);
                }
            }
        }

        if (!inputYorN.equalsIgnoreCase("n")) {
            int totalUsersBeforeDeletion = users.size();
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    new FileService<Product>(user.getUsername()+"OrderHistory.data").deleteFile(user.getUsername());
                    users.remove(user);
                    new FileService<User>(fileName).saveData(users);
                    break;
                }
            }

            if (totalUsersBeforeDeletion == users.size()) {
                System.out.println("You entered wrong user or User does not exist!");
                if (new InputValidator().tryAgainOrExitMenu(scanner)) {
                    return false;
                } else {
                    deleteUser(scanner, admin);
                }
            }
        }
        return currentAdminUser;
    }

//  Compares username and password with stored user data values
    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                return user;
            }
        }
        return null;
    }

//  Compares username with stored user data values
    private boolean checkUsernameAvailability(String username){
        for(User user : users){
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    public void getAllUsers(){
        System.out.println("=== List of all users ===");
        for(User user: users){
            System.out.println(user.toString());
        }
    }
}
