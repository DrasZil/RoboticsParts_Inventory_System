//this Part class handles the details of the robotics parts

import java.io.Serializable; //allows saving/loading objects to/from files

public class Part implements Serializable {
    private String name;
    private String partNumber;
    private int quantity;
    private int price;
    private String currency;
    private double originalPrice;
    private String originalCurrency;
    private String category;
    private String description;

    //creating a constrctor
    //local parameters inside Part()
    //"this." Assigns parameter to the instance variables
    public Part(String name, String partNumber, int quantity, int price, 
                String currency, double originalPrice, String originalCurrency, 
                String category, String description){
                    //bg fixed, fixed proper order
                    this.name = name;
                    this.partNumber = partNumber;
                    this.quantity = quantity;

                    this.price = price;
                    //added currency, originalPrice, and originalCurrency parameters
                    this.currency = currency;
                    this.originalPrice = originalPrice;
                    this.originalCurrency = originalCurrency;

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

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public double getOriginalPrice(){
        return originalPrice;
    }

    public String getOriginalCurrency(){
        return originalCurrency;
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
        StringBuilder sb = new StringBuilder();

            sb.append("\n ---------------- PART DETAILS ---------------- \n");
            sb.append(String.format("Name        : %s\n", name));
            sb.append(String.format("Part Number : %s\n", partNumber));
            sb.append(String.format("Quantity    : %d\n", quantity));
            sb.append(String.format("Price       : %s %d\n", currency, price));
            sb.append(String.format("Category    : %s\n", category));
            sb.append(String.format("Description : %s\n", description));

            //show origial USD price only if it exists AND is USD
            if (originalCurrency != null && originalCurrency.equals("USD")){
                sb.append(String.format("Original    : %s %.2f\n", originalCurrency, originalPrice));
            }
            sb.append("------------------------------------------------\n");
            
        return sb.toString();
    }

    
}
