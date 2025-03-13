package models;

import java.io.Serial;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected String firstName;
    protected String lastName;
    protected String username;
    protected String passwordHash;
    protected String userID;
    protected String role;

    public User(String firstName, String lastName, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.userID = UUID.randomUUID().toString();
        this.role = role;
    }

    public boolean authenticate(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return role + ": " + username + " (ID: " + userID + ") " + ": " + hashPassword(passwordHash);
    }
}
