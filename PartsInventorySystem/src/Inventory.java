//inventory class will manage the list of parts and handle operations 
//like adding, removing, and searching parts
import java.io.*;
import java.util.*;

public final class Inventory {
    //create a list to store parts
        private List<Part> loadedPartsList; //parts loaded from file
        private List<Part> tempPartsList; //parts added during runtime/current session
        private final String FILE_NAME = "E:\\College Prog\\Java_Final_Group_Project\\RoboticsParts_Inventory_System\\PartsInventorySystem\\src\\inventory_data.txt";

    //creating a constructor
    public Inventory(){
        loadedPartsList = new ArrayList<>();
        tempPartsList = new ArrayList<>();
        loadFromFile();
    }

    //Adding a new part to temp list
    public void addPart(Part part){
        tempPartsList.add(part);
    }

    //View all parts in the file
    public void viewAllParts(){
        if (loadedPartsList.isEmpty()){
            System.out.println("No parts in inventory.");
        } else {
            for (Part part : loadedPartsList){
                System.out.println(part);
                System.out.println("------------------------------------");
            }
        }
    }

    //Search parts by name or part number in loadedPartsList
    public Part searchPart(String query){
        for (Part part : loadedPartsList){
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

    //updating the quantity in loadedPartsList
    public void updateQuantity(String partNumber, int newQuantity){
        Part part = searchPart(partNumber);
        if (part != null){
            part.setQuantity(newQuantity);
            System.out.println("Quantity updated for " + part.getName());

            

        }else{
            System.out.println("Part not found.");
        }
    }

    //deleting from loadedPartsList
    public void deletePart(String partNumber){
        Part part = searchPart(partNumber);
        if (part != null){
            loadedPartsList.remove(part);
            System.out.println("Part deleted." + part.getName());
            saveToFile();
        }else{
            System.out.println("Part not found.");
        }
    }

    //this allows previewing the list of things to be added to the main txt file
    //preview from tempPartsList
    public void preview() {
        if (tempPartsList.isEmpty()){
            System.out.println("No parts to be added.");
        } else {
            int id = 0;
            for (Part part : tempPartsList){
                System.out.println(id + ". " + part);
                System.out.println("------------------------------------");
                id++;
            }
        }
    }
    // removing parts in the preview function
    public void deleteprev(int num){
        //fixed bug, indexOf(Num) to remoeve(num) num is not a part object
        if(num >= 0 && num < tempPartsList.size()){
            Part removed = tempPartsList.remove(num);
            System.out.println("Part: " + removed.getName() + "\n has been deleted successfully!");
    } else {
        System.out.println("Invalid Index.");
    }
}
    // editing parts in the preview function
    //FIXED: used set function from Parts instead of creating "New"
    public void editPart(int num){
        if(num >= 0 && num <tempPartsList.size()){
            Part p = tempPartsList.get(num);
            Scanner input = new Scanner(System.in);

            System.out.print("Enter new Name (current: " + p.getName() + "): ");
            p.setName(input.nextLine());

            System.out.print("Enter new Part Number (current: " + p.getPartNumber() + "): ");
            p.setPartNumber(input.nextLine());

            int newQuantity;
            while (true) {
                System.out.print("Enter new Quantity (current: " + p.getQuantity() + "): ");
                if (input.hasNextInt()) {
                    newQuantity = input.nextInt();
                    input.nextLine(); // consume newline
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid integer for quantity.");
                    input.next(); 
                }
            }
            p.setQuantity(newQuantity);

            System.out.print("Enter new Price (current: " + p.getPrice() + "): ");
            int newPrice = input.nextInt();
            input.nextLine();
            p.setPrice(newPrice);

            System.out.print("Enter new Category (current: " + p.getCategory() + "): ");
            p.setCategory(input.nextLine());

            System.out.print("Enter new Description (current: " + p.getDescription() + "): ");
            p.setDescription(input.nextLine());

            System.out.println("Part updated successfully: " + p);
        } else {
            System.out.println("Invalid Index.");
        }
        
}

    //Saving inventory: merge loadedPartsList + tempPArtsList
    public void saveToFile(){
        /*Prints formatted representations of objects to 
        a text-output stream. This class implements all of the print methods found in PrintStream. */
        //Constructs a FileWriter given a file name, using the default charset
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, false))){
            //merge both lists
            List<Part> mergedList = new ArrayList<>();
            mergedList.addAll(loadedPartsList);
            mergedList.addAll(tempPartsList);

            for (Part part : mergedList){
                writer.println("====================================");
                writer.println("Part Number : " + part.getPartNumber());
                writer.println("Name        : " + part.getName());
                writer.println("Quantity    : " + part.getQuantity());
                writer.println("Price       : " + part.getPrice());
                writer.println("Category    : " + part.getCategory());
                writer.println("Description : " + part.getDescription());  writer.println("====================================\n");
            }
              System.out.println("Inventory saved Successfully!");
              //moving tempPartsList to loadedPartsList
              loadedPartsList.addAll(tempPartsList);
              tempPartsList.clear();  System.out.println("Inventory saved Successfully!.");
            }
         catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
    }
    }
    //load inv from file
    public void loadFromFile(){
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
                            //fixed bug, fixed proper constructor order
                            loadedPartsList.add(new Part(name, partNumber, quantity, price, category, description));
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
