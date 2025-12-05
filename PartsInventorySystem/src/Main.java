//Main class will provide the console based menu for user interaction

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //initialize the Inventory class to be called as inventory
        Inventory inventory = new Inventory();
        Scanner input = new Scanner(System.in);
        int choice;

        while(true){
            //reduces clutter
            ConsoleUtils.clearConsole();

            style.mainTitle();
            style.mainMenu();
            style.enter("Enter your choice: ");

            // Input validation for the main menu choice
            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine();
            } else {

                style.error("Invalid choice. Please try again.");
                input.nextLine();
                continue;
            }


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
                // View Parts inside Inventory
                case 2 -> {
                    inventory.viewAllParts();
                    ConsoleUtils.pause(input);
                }
                // Search for Part
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
                // Quantity Update Option
                case 4 -> {
                    String updatePartNumber = "";
                    int newQuantity = -1;
                    
                    while (true) {
                        style.enter("Enter Part Number to update quantity: ");
                        
                        if (input.hasNextInt()) { // checks for integer input and find the part number
                            updatePartNumber = String.valueOf(input.nextInt());
                            input.nextLine();
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                            input.nextLine();
                        }
                    }

                    while (true) {
                        style.enter("Enter new Quantity: ");

                        if (input.hasNextInt()) { // checks for integer input and will update the new quantity
                            newQuantity = input.nextInt();
                            input.nextLine();
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                            input.nextLine();
                        }
                    }
                    
                    inventory.updateQuantity(updatePartNumber, newQuantity);
                    ConsoleUtils.pause(input);
                }
                // Delete Part
                case 5 -> {
                    style.enter("Enter Part Number to delete: ");
                    String deletePartNumber = input.nextLine();
                    inventory.deletePart(deletePartNumber);
                    ConsoleUtils.pause(input);
                }
                // Preview Menu
                case 6 -> {
                    int res;
                    boolean runSubMenu = true;

                    while (runSubMenu) {
                        style.subTitle();
                        style.subMenu();
                      
                       style.enter("Enter your choice: ");

                        if (input.hasNextInt()) {
                            res = input.nextInt();
                            input.nextLine();

                            switch(res) {
                                // Preview Case //
                                case 1 -> {
                                    style.line();
                                    inventory.preview();
                                    style.line();
                                    ConsoleUtils.pause(input);
                                }
                                // Edit Case //
                                case 2 -> {
                                    inventory.preview();
                                    style.line();
                                    style.enter("Select number u want to edit: ");

                                    if (input.hasNextInt()) { // to require an integer input
                                        int pnum = input.nextInt();
                                        input.nextLine();

                                        String confirmation;
                                        boolean confirmed = false;

                                        // Confirmation of changes 
                                        do {
                                            style.enter("You selected part " + pnum + " to edit. Do you want to proceed? (yes/no): ");
                                            confirmation = input.nextLine().trim().toLowerCase();

                                            switch (confirmation) {
                                                case "yes" -> confirmed = true;
                                                case "no" -> {
                                                    confirmed = false;
                                                    break;
                                                }
                                                default -> System.out.println("\n!! Invalid input. Please type 'yes' or 'no'.");
                                            }
                                        } while (!confirmation.equals("yes") && !confirmation.equals("no")); // Checks and requires for "yes" or "no" input

                                        if (confirmed) {
                                            inventory.editPart(pnum);
                                        } else {
                                            System.out.println("\n!! Edit cancelled. Returning to main options.");
                                        }
                                    } else {
                                        System.out.println("\n!! Invalid part number. Please enter a number.");
                                        input.nextLine();
                                    }
                                    ConsoleUtils.pause(input);
                                }

                                // Delete/Remove Part Case //
                                case 3 -> {
                                    inventory.preview();
                                    style.line();
                                    style.enter("Select number u want to remove: ");

                                    if (input.hasNextInt()) { // to require an integer input
                                        int pnum = input.nextInt();
                                        input.nextLine();

                                        String confirmation;
                                        boolean confirmed = false;

                                        // Confirmation of changes
                                        do {
                                            style.enter("You selected part " + pnum + " to remove. Do you want to proceed? (yes/no): ");
                                            confirmation = input.nextLine().trim().toLowerCase();

                                            switch (confirmation) {
                                                case "yes" -> confirmed = true;
                                                case "no" -> {
                                                    confirmed = false;
                                                    break;
                                                }
                                                default -> System.out.println("\n!! Invalid input. Please type 'yes' or 'no'.");
                                            }
                                        } while (!confirmation.equals("yes") && !confirmation.equals("no")); // Checks and requires for "yes" or "no" input

                                        if (confirmed) {
                                            inventory.deleteprev(pnum);
                                        } else {
                                            System.out.println("\n!! Removal cancelled. Returning to main options.");
                                        }
                                    } else {
                                        System.out.println("\n!! Invalid part number. Please enter a number.");
                                        input.nextLine();
                                    }
                                    ConsoleUtils.pause(input);
                                }
                                // Cancel Preview Case //
                                case 4 -> {
                                    System.out.println("\n>> Preview cancelled. Returning to main menu.");
                                    runSubMenu = false;
                                    ConsoleUtils.pause(input);
                                }
                                default -> {
                                    System.out.println("\n!! Invalid option: " + res + ". Returning to main menu.");
                                    ConsoleUtils.pause(input);
                                }
                            }
                        } else {
                            System.out.println("\n!! Invalid option !! \nPlease enter a number.");
                            input.nextLine();
                            ConsoleUtils.pause(input);
                        }
                    }
                }
                // Save & Exit //
                case 7 -> {
                    inventory.saveToFile();
                    ConsoleUtils.pause(input);
                    
                    String continueChoice;
                    
                    do {
                        style.enter("Do you want to continue? (Y/N): ");
                        continueChoice = input.nextLine().trim().toUpperCase();

                        if (!continueChoice.equals("Y") && !continueChoice.equals("N")) { // Checks and requires for "Y" or "N" input
                            System.out.println("Please enter Y to continue or N to exit:");
                        }

                    } while (!continueChoice.equals("Y") && !continueChoice.equals("N"));

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