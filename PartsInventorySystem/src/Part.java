//this Part class handles the details of the robotics parts

import java.io.Serializable; //allows saving/loading objects to/from files

public class Part implements Serializable {
    private String name;
    private String partNumber;
    private int quantity;
    private int price;
    private String category;
    private String description;

    //creating a constrctor
    //local parameters inside Part()
    //"this." Assigns parameter to the instance variables
    public Part(String name, String partNumber, int price, int quantity, 
                String category, String description){
                    this.name = name;
                    this.partNumber = partNumber;
                    this.quantity = quantity;
                    this.price = price;
                    this.category = category;
                    this.description = description;
                }

    //Get and set methods for each instance variable
    //Get will get the instance variable value from the user
    //Set will set the value of the instance variable from the user
    public String getName(){
        return name; 
    }
    public void setName(String Name){
        this.name = Name; 
    }
    public String getPartNumber(){
        return partNumber;
    }
    public String setPartNumber(String Partnumber){
        return this.partNumber = Partnumber;
    }
    public int getQuantity(){
        return quantity;
    }

    public int setQuantity(int quantity){
        return this.quantity = quantity;
    }

    public int getPrice(){
        return price;
    }

    public int setPrice(int price){
        return this.price = price;
    }

    public String getCategory(){
        return category;
    }
    public String setCategory(String Category){
        return this.category = Category;
    }
    public String getDescription(){
        return description;
    }
    public String setDescription(String Description){
        return this.description = Description;
    }

    //override toString method
    @Override
    //toString for easy product display
    public String toString(){
        return String.format("Name: %s, Part Number: %s, Quantity: %d, Price: %d, Category: %s, Description: %s",
                            name, partNumber, quantity, price, category, description);
    }

    
}
