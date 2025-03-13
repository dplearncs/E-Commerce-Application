package main;

import services.ECommerceApp;

public class Main {
    public static void main(String[] args) {
       try {
            new ECommerceApp().start();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}