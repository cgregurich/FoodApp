/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodapp;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author colin
 */
public class TextUserInterface {
    private FoodFile foodFileDAO;
    private Scanner sc = new Scanner (System.in);
    
    
    public TextUserInterface(){
        foodFileDAO = new FoodFile();
    }
    
    
    /*
    homebase for the class
    displays the menu then calls userMenuCommand within a loop
    continues calling this until user enters "exit"
    */
    public void run(){
        displayMenu();
        
        String command = "";
        while (!command.equals("exit")){
            command = userMenuCommand();
        }
    }
    
    
    /*
    receives user input and calls the appropriate method based on input
    */
    public String userMenuCommand(){
        
        System.out.print("\nCommand: ");
        String userCommand = sc.nextLine().toLowerCase();
        
        switch (userCommand){
            case "add":
                this.addFood();
                break;

            case "del":
                this.deleteFood();
                break;

            case "view":
                this.view();
                break;
                
            case "search":
                this.search();
                break;

            case "help":
                System.out.println("");
                this.displayMenu();
                break;


            case "exit":
                System.out.println("\nGood bye.");
                break;

            default:
                System.out.println("Invalid command.");
                return this.userMenuCommand();
        }   

        return userCommand;
        
    }
    
    /*
    displays all commands available to the user
    */
    public void displayMenu(){
        System.out.println("COMMANDS\n");
        System.out.println("add - add a new food");
        System.out.println("del - delete a food");
        System.out.println("view - view all foods");
        System.out.println("search - search for a food by name");
        System.out.println("help - display this menu");
        System.out.println("exit - exit the program");
    }
    
    
    /*
    prints all food items in the list of foodFileDAO
    */
    public void view(){
        System.out.println("");
        
        //prints all foods
        if (!foodFileDAO.getAll().isEmpty()){
            System.out.println("DISPLAYING FOODS\n");
            foodFileDAO.printFile();
        }
        
        else{ //no food items in file
            System.out.println("NO FOODS TO DISPLAY");
            System.out.println("Add foods with command \"add\"");
        }
    }
    
    /*
    prompts user for food name
    prints all FoodItems that contain the user's input if there is one (or more)
    prints a message indicating the food doesn't exist if it doesn't
    */
    public boolean search(){
        //prompts user for keyword
        System.out.print("\nEnter keyword: ");
        String searchedName = sc.nextLine();
        
        
        FoodItem searchedFood = new FoodItem(searchedName);
        
        List<FoodItem> foundFoodItemsList = this.foodFileDAO.searchAll(searchedName);
        
        System.out.println("\nSEARCH RESULTS FOR " +searchedName+ "\n");
        
        //prints all items in foundFoodItemsList if the list is not null
        //aka if the food exists it prints all food items that contain the 
        //searched food name
        if (!foundFoodItemsList.isEmpty()){ //foods with keyword exist 
            for (FoodItem f : foundFoodItemsList){
                System.out.println(f);
                System.out.println("");
            }
            return true;
        }
        
        
        System.out.println("No results found");
        return false;
    }
    
    
    /*
    prompts the user for all foodItem info then creates a new foodItem with
    those stats, then adds that foodItem to foodFileDAO's foodItemsList/file
    */
    public boolean addFood(){
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Serving size: ");
        String ss = sc.nextLine();
        System.out.print("Unit of measure: ");
        String unit = sc.nextLine();
        System.out.print("Calories: ");
        String calories = sc.nextLine();
        System.out.print("Carbs: ");
        String carbs = sc.nextLine();
        System.out.print("Fat: ");
        String fat = sc.nextLine();
        System.out.print("Protein: ");
        String protein = sc.nextLine();
        System.out.print("Fiber: ");
        String fiber = sc.nextLine();
        System.out.print("Sugar: ");
        String sugar = sc.nextLine();
        FoodItem newFood = new FoodItem(name, ss, unit, calories, carbs, fat, 
                protein, fiber, sugar);
        return this.foodFileDAO.add(newFood);
    }
    
    
    /*
    prompts user for name of food
    if a food with this name exists in the file, then it is removed
    
    **need to add functionality to ask to delete all foods with this name
    
    */
    public boolean deleteFood(){
        System.out.print("Enter name of food to be deleted: ");
        String name = sc.nextLine();
        
        List<FoodItem> foundFoodItemsList = this.foodFileDAO.searchAllExact(name);
        
        //if there is more than 1 food item that has the same name as name
        boolean multipleResults = (foundFoodItemsList.size() > 1);
        
        if (multipleResults){
            return this.deleteMultipleFoods(foundFoodItemsList, name);
        }
        
        //below code will run if there is only one FoodItem with name
        boolean wasDeleted = this.foodFileDAO.delete(new FoodItem(name.toLowerCase()));
        
        if (wasDeleted){
            System.out.println("\n" +name+ " was deleted.");
            return true;
        }
        
        else{
            System.out.println("\n" +name+ " does not exist.");
            return false;
        }
        
        
    }
    
    
    /*
    method for deleting when there are multiple FoodItems with the name the 
    user enters
    prompts user to either delete all results or not
    if yes then all are deleted and true is returned
    if no then prompts user to enter which one to delete (using numbers)
    */
    public boolean deleteMultipleFoods(List<FoodItem> foundFoodItemsList, String name){
        //prints header if there are multiple results
        System.out.println("\nMULTIPLE FOODS WITH NAME " +name+ " FOUND\n");

        //prints all FoodItems in foundFoodItemsList
        for (FoodItem f : foundFoodItemsList){
            System.out.println(f);
            System.out.println("");
        }


        String choice = "";

        //validates user input for choice of deleting all or not
        while (!(choice.equals("y") || choice.equals("n"))){
            
            //prompt user
            System.out.print("Delete all results? (y or n): ");
            choice = sc.nextLine().toLowerCase();
            
            //if user enters y, deletes all foods
            if (choice.equals("y")){//delete all
                for (FoodItem f : foundFoodItemsList){
                    this.foodFileDAO.delete(f);
                    System.out.println(f.getName()+ " was deleted.");
                }
            }

            //if user enters n, then they only want to delete one
            else if (choice.equals("n")){ //delete one

                int whichToDelete = -1; //int for which food to delete
                
                //highest valid entry; based on frontend, not back end 
                int maxChoice = foundFoodItemsList.size();

                //gets user input for which to delete
                while (whichToDelete <= 0 || whichToDelete > maxChoice){
                    
                    System.out.print("Delete which one? (enter number): ");
                    whichToDelete = Integer.parseInt(sc.nextLine());  
                    
                    if (whichToDelete > maxChoice || whichToDelete < 1){ //input is invalid
                        System.out.println("Invalid input. Please try again.\n");
                    }
                }
                
                
                //int for the index of the food to delete in foodFileDAO's list
                int indexOfFoodInDAO = this.foodFileDAO.indexOfExactFood(foundFoodItemsList.get(whichToDelete - 1));
                
                //calls deleteByIndex and sets the return to a boolean
                boolean wasDeleted = this.foodFileDAO.deleteByIndex(indexOfFoodInDAO);
                
                //prints info to the user about what was deleted
                System.out.println(name+ " (" +whichToDelete+ ") was deleted." );
                return wasDeleted;
                
                
                
                
            }

            else{ //invalid choice; not y or n
                System.out.println("Invalid input. Please try again.\n");
            }
        }
        return false;
    }
            
        
}
