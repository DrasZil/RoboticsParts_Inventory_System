//this Part class handles the details of the robotics parts

import java.io.Serializable; //allows saving/loading objects to/from files

public class Part implements Serializable {
    private final String name;
    private final String partNumber;
    private int quantity;
    private final String category;
    private final String description;

    //creating a constrctor
    //local parameters inside Part()
    //"this." Assigns parameter to the instance variables
    public Part(String name, String partNumber, int quantity, 
                String category, String description){
                    this.name = name;
                    this.partNumber = partNumber;
                    this.quantity = quantity;
                    this.category = category;
                    this.description = description;
                }

    //Get and set methods for each instance variable
    //Get will get the instance variable value from the user
    //Set will set the value of the instance variable from the user
    public String getName(){
        return name; }
    
    public String getPartNumber(){
        return partNumber;
    }

    public int getQuantity(){
        return quantity;
    }

    public int setQuantity(int quantity){
        return this.quantity = quantity;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    //override toString method
    @Override
    //toString for easy product display
    public String toString(){
        return String.format("Name: %s, Part Number: %s, Quantity: %d, Category: %s, Description: %s",
                             name, partNumber, quantity, category, description);
    }

}
