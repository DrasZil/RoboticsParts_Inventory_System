//inventory class will manage the list of parts and handle operations 
//like adding, removing, and searching parts
import java.io.*;
import java.util.*;

public final class Inventory {
    //create a list to store parts
        private List<Part> partsList;
        private final String FILE_NAME = "../src/inventory_data.txt";

    //creating a constructor
    public Inventory(){
        partsList = new ArrayList<>();
        loadFromFile();
    }

    //Adding a part
    public void addPart(Part part){
        partsList.add(part);
        System.out.println("Part added: ");
    }

    //View all parts
    public void viewAllParts(){
        if (partsList.isEmpty()){
            System.out.println("No parts in inventory.");
        } else {
            for (Part part : partsList){
                System.out.println(part);
            }
        }
    }

    //Search parts by name or part number
    public Part searchPart(String query){
        for (Part part : partsList){
            //equalsIgnoreCase()
            /*Compares this String to another String, 
            ignoring case considerations. 
            Two strings are considered equal ignoring case 
            if they are of the same length and corresponding 
            Unicode code points in the two strings are equal 
            ignoring case. */
            if(part.getName().equalsIgnoreCase(query) 
                || part.getPartNumber().equalsIgnoreCase(query)){
                    return part;
                }
        }
        return null;
    }

    //updating the quantity of a part
    public void updateQuantity(String partNumber, int newQuantity){
        Part part = searchPart(partNumber);
        if (part != null){
            part.setQuantity(newQuantity);
            System.out.println("Quantity updated.");
        }else{
            System.out.println("Part not found.");
        }
    }

    //deleting a part
    public void deletePart(String partNumber){
        Part part = searchPart(partNumber);
        if (part != null){
            partsList.remove(part);
            System.out.println("Part deleted.");
        }else{
            System.out.println("Part not found.");
        }
    }

    //Saving to file
    public void saveToFile(){
        /*Prints formatted representations of objects to 
        a text-output stream. This class implements all of the print methods found in PrintStream. */
        //Constructs a FileWriter given a file name, using the default charset
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))){

            for (Part part: partsList){
                writer.println(part.getPartNumber() + ", " +
                               part.getName() + ", " + 
                               part.getQuantity() + ", " +
                               part.getCategory() + ", " +
                               part.getDescription()
                               );
                System.out.println("Inventory saved to file as text.");
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    //load from file
    public void loadFromFile(){
        partsList = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()){
            System.out.println("No existing inventory file found. Creating a new inventory file...");
            return;
        }

        try {
            // Ensure parent directories exist
            file.getParentFile().mkdirs();

            // Create a new empty file if it does not exist
            if (file.createNewFile()) {
                System.out.println("New inventory file created at: " + file.getAbsolutePath());
                return;
            }
        } catch (IOException e) {
            System.out.println("Error creating new inventory file: " + e.getMessage());
            return;
        }

        /*BufferedReader: Reads text from a character-input stream, 
        buffering characters so as to provide for the efficient reading 
        of characters, arrays, and lines. */
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null){
                String[] data = line.split(", ");
                if (data.length == 5){

                    String partNumber = data[0];
                    String name = data[1];
                    int quantity = Integer.parseInt(data[2]);
                    String category = data[3];
                    String description = data[4];

                    partsList.add(new Part(name, partNumber, quantity, category, description));
                }
            }
            System.out.println("Inventory loaded from file.");
        } catch (IOException e){
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }



    /*
parts: A list to store all parts.
addPart: Adds a new part to the list.
viewParts: Displays all parts in the inventory.
searchPart: Searches for a part by name or part number.
updateQuantity: Updates the quantity of a specific part.
deletePart: Removes a part from the inventory.
saveToFile: Saves the list of parts to a file using serialization.
loadFromFile: Loads the list of parts from a file. */
}
