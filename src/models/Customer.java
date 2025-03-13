package models;

import services.ProductService;

import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class Customer extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Ensure compatibility

    public Customer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, "Customer");
    }

    public void customerPage(Scanner scanner, ProductService productService){
        while(true)
        {
            System.out.println("\n=== Welcome Customer ===");
            System.out.println("1. View Product");
            System.out.println("2. Place Orders");
            System.out.println("3. View Orders");
            System.out.println("4. Exit");
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
                case 1 -> productService.getAllProduct();
                case 2 -> productService.orderProduct(scanner, this.getUsername());
                case 3 -> {
                    System.out.println("logic for viewing order history");
                    productService.loadOrderHistory(scanner, this.getUsername());
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}