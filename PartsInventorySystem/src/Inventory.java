//inventory class will manage the list of parts and handle operations 
//like adding, removing, and searching parts
import java.io.*;
import java.util.*;

public final class Inventory {
    //create a list to store parts
        private List<Part> partsList;
        private final String FILE_NAME = "E:\\College Prog\\Java_Final_Group_Project\\RoboticsParts_Inventory_System\\PartsInventorySystem\\src\\inventory_data.txt";

    //creating a constructor
    public Inventory(){
        partsList = new ArrayList<>();
        loadFromFile();
    }

    //Adding a part
    public void addPart(Part part){
        partsList.add(part);
    }

    //View all parts
    public void viewAllParts(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // do whatever you want with each line
            }

        } catch (IOException e) {
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
        Scanner sc = new Scanner(System.in);
        Part part = searchPart(partNumber);
        if (part != null){
            part.setQuantity(newQuantity);
            System.out.println("Quantity updated for " + part.getName());

            

        }else{
            System.out.println("Part not found.");
        }
    }

    //deleting a part
    public void deletePart(String partNumber){
        Part part = searchPart(partNumber);
        if (part != null){
            partsList.remove(part);
            System.out.println("Part deleted." + part.getName());
        }else{
            System.out.println("Part not found.");
        }
    }

    //this allows previewing the list of things to be added to the main txt file
    public void preview() {
        if (partsList.isEmpty()){
            System.out.println("No parts to be added.");
        } else {
            for (Part part : partsList){
                System.out.println(part);
                System.out.println("------------------------------------");
            }
        }
    }

    public void editPart(String name){
    boolean found = false;
    name = name.trim(); // remove accidental leading/trailing spaces

    for (Part part : partsList) {
        if (part != null && part.getName() != null && part.getName().equalsIgnoreCase(name)) {
            System.out.println("part found");
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("Part not found.");
    }

}

    //Saving to file1
    public void saveToFile(){
        /*Prints formatted representations of objects to 
        a text-output stream. This class implements all of the print methods found in PrintStream. */
        //Constructs a FileWriter given a file name, using the default charset
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))){

            if (partsList.isEmpty()){
                System.out.println("No parts in Inventory.");
            } else {
            for (Part part : partsList){
                writer.println("====================================");
                writer.println("Part Number : " + part.getPartNumber());
                writer.println("Name        : " + part.getName());
                writer.println("Quantity    : " + part.getQuantity());
                writer.println("Price       : " + part.getPrice());
                writer.println("Category    : " + part.getCategory());
                writer.println("Description : " + part.getDescription());
                writer.println("====================================\n");
            }
                System.out.println("Inventory saved Successfully!.");
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
        partsList.clear();
    }

    //load inv from file
    public void loadFromFile(){
        partsList = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()){
            System.out.println("No existing inventory file found. Creating a new inventory file...");
            return;
        }

        /*BufferedReader: Reads text from a character-input stream, 
        buffering characters so as to provide for the efficient reading 
        of characters, arrays, and lines. */
        //FileReader: opens the file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
           //create temporary variables to hold part data
           //collect data line by line
            String line;
            String partNumber = null, name = null, category = null, description = null;
            int price = 0;
            int quantity = 0;

            //br.readline: will read one line at a time
            //loop continues until readLine() returns null(end of file)
            //line.trim(): removes leading and trailing whitespace
            while ((line = br.readLine()) != null){
                line = line.trim();

                //check what each line starts with and extract data accordingly
                //line,startsWith(): check what kind of data the line contains
                //substring(): extracts the actual data by removing the label part
                //integer.parseInt(): converts string to integer

                /*line.startsWith("=====") identifies the separator line in your formatted file. 
                This means we finished reading one part. If all fields are not null, 
                we create a Part object and add it to partsList.
                Then we reset the temporary variables to be ready 
                 for the next part. */
                if(line.startsWith("Part Number : ")){
                    partNumber = line.substring("Part Number : ".length());
                } else if (line.startsWith("Name        : ")){
                    name = line.substring("Name        : ".length());
                } else if (line.startsWith("Quantity    : ")){
                    quantity = Integer.parseInt(line.substring("Quantity    : ".length()));
                } else if (line.startsWith("Price       : ")){
                    price = Integer.parseInt(line.substring("Price       : ".length()));
                } else if (line.startsWith("Category    : ")) {
                    category = line.substring("Category    : ".length());
                } else if (line.startsWith("Description : ")) {
                    description = line.substring("Description : ".length());
                } else if (line.startsWith("=====")) {
                    //end part of block, add to list if all fields exist
                    if (partNumber != null && name != null && category != null && description != null){
                            partsList.add(new Part(name, partNumber, quantity, price, category, description));
                            partNumber = name = category = description = null;
                            quantity = 0;
                            price = 0;
                        }
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
