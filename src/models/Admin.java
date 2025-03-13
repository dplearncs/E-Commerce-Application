package models;

import services.ProductService;
import services.UserService;

import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class Admin extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Ensure compatibility

    public Admin(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, "Admin");
    }

    public void adminPage(Scanner scanner, UserService userService, ProductService productService) {
        System.out.println("\n=== Welcome Admin ===");
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Update Product");
            System.out.println("4. Get all products");
            System.out.println("5. Delete User");
            System.out.println("6. Get all User");
            System.out.println("7. Logout of profile");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }
            System.out.println();

            switch (choice) {
                case 1 -> productService.addProduct(scanner);
                case 2 -> productService.deleteProduct(scanner);
                case 3 -> productService.updateProduct(scanner);
                case 4 -> productService.getAllProduct();
                case 5 -> {
                    if (userService.deleteUser(scanner, this)) {
                        System.out.print("\nCreate a new account or login");
                        return;
                    }
                }
                case 6 -> userService.getAllUsers();
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select a valid option from the menu.");
            }
        }
    }
}
