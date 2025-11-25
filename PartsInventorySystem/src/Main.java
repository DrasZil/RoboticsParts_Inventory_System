//Main class will provide the console based menu for user interaction

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //initialize the Inventory class to be called as inventory
        Inventory inventory = new Inventory();
        Scanner input = new Scanner(System.in);

        while(true){
            ConsoleUtils.clearConsole();
            style.title("Welcome to the Robotics Parts Inventory System");

           System.out.println(
            style.YELLOW + "[1] " + style.CYAN + "Add Part\n" +
            style.YELLOW + "[2] " + style.CYAN + "View Inventory\n" +
            style.YELLOW + "[3] " + style.CYAN + "Search Part\n" +
            style.YELLOW + "[4] " + style.CYAN + "Update Quantity\n" +
            style.YELLOW + "[5] " + style.CYAN + "Delete Part\n" +
            style.YELLOW + "[6] " + style.CYAN + "Preview Parts\n" +
            style.YELLOW + "[7] " + style.CYAN + "Save and Exit" +
            style.RESET
            );



            style.enter("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch(choice){
                case 1 -> {
                    String name;
                    while (true) { 
                        style.enter("Enter Part Name: ");
                        name = input.nextLine();
                        if (!inventory.nameExists(name)) {
                     break;
                        } else {
                            style.warning("Name already exists. Please enter a different name.");
                        }
                    }

                    String partNumber;
                    while (true) { 
                        style.enter("Enter Part Number: ");
                        partNumber = input.nextLine();
                        if (!inventory.partNumberExists(partNumber)) {
                     break;
                        } else {
                            style.warning("Part Number already exists. Please enter a part number.");
                        }
                    }

                    int quantity;
                    while (true) { // loop until valid input
                    style.enter("Enter Quantity: ");
                    if (input.hasNextInt()) {
                    quantity = input.nextInt();
                    input.nextLine();
                    break;
                    } else {
                    style.error("Invalid input. Please enter a number.");
                    input.nextLine();
                        }
                    }

                    String currency;

                    while (true) {
                        style.enter("Enter Currency (PHP/USD): ");
                        currency = input.nextLine().trim().toUpperCase();
                        if (currency.equals("PHP") || currency.equals("USD")) {
                            break;
                        } else {
                            System.out.println("Invalid currency. Please enter either 'PHP' or 'USD'.");
                        }
                    }

                    double priceInput;
                    while (true) { 
                        style.enter("Enter Price: ");
                        if (input.hasNextDouble()) {
                            priceInput = input.nextDouble();
                            input.nextLine();
                            break;
                        } else {
                            style.error("Invalid input. Please enter a number.");
                            input.nextLine();
                        }  
                    }

                    double exchangeRate = 58.67; 
                    //UPDATE:
                    //this will store original Currency and original Price
                    //when I click USD, it will convert to PHP using exchange rate
                    //then It will show the converted price in the inventory and also its original USD price

                    //store original
                    String originalCurrency = currency;
                    double originalPrice = priceInput;
                    
                    //convert if USD
                    int price = (int) Math.round(
                        currency.equals("USD") ? priceInput * exchangeRate : priceInput
                    );

                    style.enter("Enter Category: ");
                    String category = input.nextLine();
                    style.enter("Enter Description: ");
                    String description = input.nextLine();
                    
                    //always save price in PHP
                    String finalCurrency = "PHP";

                    //added currency parameter
                    //added orginal Price and original Currency
                    Part newPart = new Part(name, partNumber, quantity, 
                    price, //already converted to PHP
                    finalCurrency, 
                    originalPrice, //USD value if USD was selected
                    originalCurrency, //USD or PHP
                    category, description);
                  
                    inventory.addPart(newPart);
                    
                    style.success("Part added successfully!");
                    System.out.println(newPart); 
                    style.line();
                    ConsoleUtils.pause(input);
                }
                case 2 -> {
                    inventory.viewAllParts();
                    ConsoleUtils.pause(input);
                }
                case 3 -> {
                    style.enter("Enter the Name of part or part Number to search: ");
                    String query = input.nextLine();
                    Part part = inventory.searchPart(query);
                    if(part !=null){
                        style.line();
                        style.success("Part found: " + part);
                    } else {
                        style.line();
                        style.error("Part not found.");
                    }
                    ConsoleUtils.pause(input);
                }
                case 4 -> {
                    style.enter("Enter Part Number to update quantity: ");
                    String updatePartNumber = input.nextLine();
                    style.enter("Enter new Quantity: ");
                    int newQuantity = input.nextInt();
                    inventory.updateQuantity(updatePartNumber, newQuantity);
                    ConsoleUtils.pause(input);
                }
                case 5 -> {
                    style.enter("Enter Part Number to delete: ");
                    String deletePartNumber = input.nextLine();
                    inventory.deletePart(deletePartNumber);
                    ConsoleUtils.pause(input);
                }
                case 6 -> {
                    style.title("This is a preview of the parts to be added to the Main Inventory");
                    System.out.println(
                        style.YELLOW + "[1] " + style.CYAN + "Preview Parts\n" +
                        style.YELLOW + "[2] " + style.CYAN + "Edit Part\n" +
                        style.YELLOW + "[3] " + style.CYAN + "Delete Part" +
                        style.RESET
                    );
                    style.enter("Enter your choice: ");

                    int res = input.nextInt();
                    input.nextLine();
                    ConsoleUtils.pause(input);

                    switch(res) {
                    
                        case 1 -> {
                            style.line();
                            inventory.preview();
                            style.line();
                            ConsoleUtils.pause(input);
                        }
                        case 2 -> {
                            inventory.preview();
                            style.line();
                            style.enter("Select number u want to edit: ");
                            input.nextLine();
                            int pnum = input.nextInt();
                     
                            inventory.editPart(pnum);
                            ConsoleUtils.pause(input);

                        }
                        case 3 -> {
                            inventory.preview();
                            style.line();
                            style.enter("Select number u want to remove: ");
                            input.nextLine();
                            int pnum = input.nextInt();
                            inventory.deleteprev(pnum);
                            ConsoleUtils.pause(input);
                        }   
                    }   
                    


                }
                case 7 -> {
                    inventory.saveToFile();
                    ConsoleUtils.pause(input);
                    style.enter("Do you want to continue? (Y/N): ");
                    String continueChoice = input.nextLine().trim().toUpperCase();
                    
                    if (continueChoice.equals("N")) {
                        System.out.println(style.YELLOW + "Exiting..." + style.RESET);
                        input.close();
                        return; // exit the program
                    }
                }
                default -> style.error("Invalid choice. Please try again.");
            }
        }
    }
}
