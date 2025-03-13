package storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService<T> {
    private final String fileName;

    public FileService(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Saves a list of objects to the file
     * @param dataList The list of objects to be saved
     */
    public void saveData(List<T> dataList) {
        if (dataList == null) {
            System.out.println("Error: Attempted to save a null list.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(dataList);
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data to file '" + fileName + "': " + e.getMessage());
        }
    }

    /**
     * Loads a list of objects from the file
     * @return A list of objects retrieved from the file
     */
    @SuppressWarnings("unchecked")
    public List<T> loadData() {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) { // Check if file exists and is not empty
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error loading data from file '" + fileName + "': " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class mismatch while reading data from '" + fileName + "'.");
        }

        return new ArrayList<>();
    }

    public void deleteFile(String username){
        File file = new File(username+"OrderHistory.data");
        file.delete();
    }
}
