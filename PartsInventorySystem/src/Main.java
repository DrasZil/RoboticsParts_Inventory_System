//Main class will provide the console based menu for user interaction

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //initialize the Inventory class to be called as inventory
        Inventory inventory = new Inventory();
        Scanner input = new Scanner(System.in);

        while(true){
            System.out.print("""
                               
                            Robotics Parts Inventory System 
                                1. Add Part
                               2. View Inventory
                               3. Search Part
                               4. Update Quantity
                               5. Delete Part
                               6  Preview
                               7. Save and Exit
                            Choose an option: """);

            int choice = input.nextInt();
            input.nextLine();

            switch(choice){
                case 1 -> {
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter part Number:");
                    String partNumber = input.nextLine();
                    int quantity;
                    while (true) { // loop until valid input
                    System.out.print("Enter Quantity: ");
                    if (input.hasNextInt()) {
                    quantity = input.nextInt();
                    input.nextLine();
                    break;
                    } else {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine();
                    }
                    }
                    System.out.print("Enter Price: ");
                    int price = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter Category: ");
                    String category = input.nextLine();
                    System.out.print("Enter Description: ");
                    String description = input.nextLine();

                    Part newPart= new Part(name, partNumber, quantity, price, category, description);
                    inventory.addPart(newPart);

                    System.out.println("---- ---- ----O");
                    System.out.print("Part added! ---- ");
                    System.out.println(newPart); 
                    System.out.println("---- ---- ----O");
                }
                case 2 -> inventory.viewAllParts();
                case 3 -> {
                    System.out.println("Enter the Name of part or part Number to search: ");
                    String query = input.nextLine();
                    Part part = inventory.searchPart(query);
                    if(part !=null){
                        System.out.println("Part found: " + part);
                    } else {
                        System.out.println("Part not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Part Number to update quantity:");
                    String updatePartNumber = input.nextLine();
                    System.out.print("Enter new Quantity:");
                    int newQuantity = input.nextInt();
                    inventory.updateQuantity(updatePartNumber, newQuantity);
                }
                case 5 -> {
                    System.out.print("Enter Part Number to delete:");
                    String deletePartNumber = input.nextLine();
                    inventory.deletePart(deletePartNumber);
                }
                case 6 -> {
                    System.out.println("This is a preview of the parts to be added to the Main Inventory");
                    System.out.println("---------------------------------");
                    System.out.println("Choose an Option to Proceed (1/2)");
                    System.out.println("---------------------------------");
                    System.out.println("Option 1: preview Parts  \nOption 2: Edit/Delete Part  \nOption 3: Exit");
                    System.out.println("Enter Option: ");
                    int res = input.nextInt();

                    switch(res) {
                    
                        case 1 -> {
                            System.out.println("===================================================");
                            inventory.preview();
                            System.out.println("===================================================");
                        }
                        case 2 -> {
                            System.out.print("Enter the Name of the part: ");
                            input.nextLine();           // consume leftover newline from previous nextInt() or nextDouble()
                            String pname = input.nextLine(); // now actually reads the user input
                            inventory.editPart(pname);
                            

                        }
                        case 3 -> {
                            System.out.println("insert exit");
                        }
                    }
                     



                }
                case 7 -> {
                    inventory.saveToFile(true);
                    
                    System.out.print("Do you want to continue? (Y/N): ");
                    String continueChoice = input.nextLine().trim().toUpperCase();
                    
                    if (continueChoice.equals("N")) {
                        System.out.println("Exiting...");
                        input.close();
                        return; // exit the program
                    }
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
