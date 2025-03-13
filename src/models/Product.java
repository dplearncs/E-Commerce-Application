package models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String productID;
    private String name;
    private double price;
    private int quantity;

    public Product( String name, double price, int quantity) {
        this.productID = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

//    Getters
    public String getId() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

//    Setters
    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Price: $" + price + " | Quantity: " + quantity + " | ID: " + productID;
    }
}
