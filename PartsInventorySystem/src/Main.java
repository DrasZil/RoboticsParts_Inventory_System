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
                               2. View All Parts
                               3. Search Part
                               4. Update Quantity
                               5. Delete Part
                               6. Save and Exit
                            Choose an option: """);

            int choice = input.nextInt();
            input.nextLine();

            switch(choice){
                case 1 -> {
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter part Number:");
                    String partNumber = input.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter Category: ");
                    String category = input.nextLine();
                    System.out.print("Enter Description: ");
                    String description = input.nextLine();

                    inventory.addPart(new Part(name, partNumber, quantity, category, description));
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
                    inventory.saveToFile();
                    
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
