package services;

import inputValidator.InputValidator;
import inputValidator.ReturnValues;
import models.Product;
import storage.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final List<Product> products;
    private final FileService<Product> fileService;
    private final InputValidator inputValidator = new InputValidator();

    public ProductService() {
        this.fileService = new FileService<>("productData.data");
        this.products = fileService.loadData();
    }

    public void addProduct(Scanner scanner){
        System.out.println("=== Add Product ===");
        while (true) {
            System.out.print("Enter Product Name: ");
            String productName = scanner.nextLine().trim();

            double productPrice = getValidatedInput("Enter Product Price: ", "Enter a valid price!", scanner);
            int productQuantity = (int) getValidatedInput("Enter Product Quantity: ", "Enter a valid quantity!", scanner);

            // Exit if user chooses not to retry
            if (productExist(productName, productPrice) == null) {
                products.add(new Product(productName, productPrice, productQuantity));
                System.out.println("Added product: " + productName + " | Price: " + productPrice);
                fileService.saveData(products);
            } else {
                System.out.println("Product already exists!");
                if (!inputValidator.tryAgainOrExitMenu(scanner)) {
                    continue; // Retry adding the product
                }
            }
            return;
        }
    }

    public void deleteProduct(Scanner scanner){
        System.out.println("=== Delete Product ===");
        while (true) {
            System.out.print("Enter Product Name: ");
            String productName = scanner.nextLine().trim();

            double productPrice = getValidatedInput("Enter Product Price: ", "Enter a valid price!", scanner);

            Product productToDelete = productExist(productName, productPrice);
            if (productToDelete != null) {
                products.remove(productToDelete);
                System.out.println("Deleted product: " + productName + " | Price: " + productPrice);
                fileService.saveData(products);
            } else {
                System.out.println("Invalid product name or price, or product does not exist!");
                if (!inputValidator.tryAgainOrExitMenu(scanner)) {
                    continue;
                }
            }
            return;
        }
    }

    public void updateProduct(Scanner scanner){
        System.out.println("=== Update Product ===");
        while (true) {
            System.out.print("Enter Product Name: ");
            String productName = scanner.nextLine().trim();

            double productPrice = getValidatedInput("Enter Product Price: ", "Enter a valid price!", scanner);

            Product productToUpdate = productExist(productName, productPrice);
            if (productToUpdate != null) {
                System.out.println("Updating product: " + productName + " | Price: " + productPrice);

                System.out.print("Enter New Product Name: ");
                productToUpdate.setName(scanner.nextLine().trim());

                ReturnValues newPriceValidation = inputValidator.validateProductInput("Enter New Product Price: ", "Enter a valid price!", scanner);
                productToUpdate.setPrice(newPriceValidation.getReturningNumber());

                ReturnValues newQuantityValidation = inputValidator.validateProductInput("Enter New Product Quantity: ", "Enter a valid quantity!", scanner);
                productToUpdate.setQuantity((int) newQuantityValidation.getReturningNumber());

                fileService.saveData(products);
            } else {
                System.out.println("Invalid product name or price, or product does not exist!");
                if (!inputValidator.tryAgainOrExitMenu(scanner)) {
                    continue;
                }
            }
            return;
        }
    }

    public Product orderProduct(Scanner scanner, String username) {
        System.out.println("=== Place an Order ===");
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine().trim();

        double productPrice = getValidatedInput("Enter Product Price: ", "Enter a valid price!", scanner);
        Product productToBuy = productExist(productName, productPrice);
        if (productToBuy != null) {
            int quantity = (int) getValidatedInput("Enter Quantity: ", "Enter a valid quantity!", scanner);

            while (quantity > productToBuy.getQuantity()) {
                System.out.println("Only " + productToBuy.getQuantity() + " available!");
                quantity = (int) getValidatedInput("Enter a lower quantity: ", "Enter a valid quantity!", scanner);
            }

            FileService fileServiceOrderHistory = new FileService(username + "OrderHistory.data");
            final List<Product> orderHistory = fileServiceOrderHistory.loadData();
            orderHistory.add(new Product(productToBuy.getName(), productToBuy.getPrice(), quantity));
            fileServiceOrderHistory.saveData(orderHistory);

            productToBuy.setQuantity(productToBuy.getQuantity() - quantity);
            if (productToBuy.getQuantity() == 0) {
                products.remove(productToBuy);
            }

            fileService.saveData(products);
            return productToBuy;
        } else {
            System.out.println("Product not found or incorrect price!");
            return null;
        }
    }

    public void loadOrderHistory(Scanner scanner, String username){
        final List<Product> orderHistory = new FileService(username + "OrderHistory.data").loadData();

        for(Product product : orderHistory){
            System.out.println(product.toString());
        }
    }

    public double getValidatedInput(String prompt, String errorMessage, Scanner scanner) {
        return inputValidator.validateProductInput(prompt, errorMessage, scanner)
                .getReturningNumber();
    }

    public Product productExist(String productName, double productPrice){
        for(Product product : products){
            if( (product.getName().equalsIgnoreCase(productName)) &&
                    (Double.compare(product.getPrice(), productPrice) == 0) ) {
                return product;
            }
        }
        return null;
    }

    public void getAllProduct() {
        System.out.println("\n=== List of All Products ===");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
}