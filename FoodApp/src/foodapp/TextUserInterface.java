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
        System.out.println("");
        
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
                
            case "update":
                this.updateGetInfo();
                break;
                
            case "help":
                System.out.println("");
                this.displayMenu();
                break;
                
            case "x":
                break;


            case "exit":
                System.out.println("Good bye.");
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
        System.out.println("COMMANDS");
        System.out.println("\n(enter x at any point to return home)\n");
        System.out.println("add - add a new food");
        System.out.println("del - delete a food");
        System.out.println("view - view all foods");
        System.out.println("search - search for a food by name");
        System.out.println("update - update the info of a food");
        System.out.println("help - display this menu");
        System.out.println("exit - exit the program");
    }
    
    
    /*
    prints all food items in the list of foodFileDAO
    */
    public void view(){
        
        List<FoodItem> foodItemsListDAO = this.foodFileDAO.getAll();
        
        //prints all foods
        if (!foodItemsListDAO.isEmpty()){
            System.out.println("DISPLAYING FOODS\n");
            foodFileDAO.printFormattedFromList(foodItemsListDAO);
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
        System.out.print("Enter keyword: ");
        String searchedName = sc.nextLine();
        
        
        if (searchedName.equalsIgnoreCase("x")){
            return false;
        }
        
        
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
    
    //deals with updating a food
    public boolean updateFood(FoodItem food){
        
        //prompts user for what stat to update
        System.out.println("\nWhat needs to be updated?");
        System.out.println("name | ss | unit | cals | carbs | fat | protein | fiber | sugar");
        System.out.print("Enter choice: ");
        String choice = sc.nextLine().toLowerCase();
        
        
        //prompts user for new stat based on choice
        switch(choice){
            case "name":
                System.out.print("Enter new name: ");
                food.setName(sc.nextLine());
                break;
                
            case "ss":
                System.out.print("Enter new serving size: ");
                food.setServingSize(sc.nextLine());
                break;
                
            case "unit":
                System.out.print("Enter new unit: ");
                food.setUnit(sc.nextLine());
                break;
                
            case "cals":
                System.out.print("Enter new calories: ");
                food.setCalories(sc.nextLine());
                break;
                
            case "carbs":
                System.out.print("Enter new carbs: ");
                food.setCarbs(sc.nextLine());
                break;
                
            case "fat":
                System.out.print("Enter new fat: ");
                food.setFat(sc.nextLine());
                break;
                
            case "protein":
                System.out.print("Enter new protein: ");
                food.setProtein(sc.nextLine());
                break;
                
            case "fiber":
                System.out.print("Enter new fiber: ");
                food.setFiber(sc.nextLine());
                break;
                
            case "sugar":
                System.out.print("Enter new sugar: ");
                food.setSugar(sc.nextLine());
                break;
                
            case "x":
                return false;
                
            default:
                System.out.println("\nInvalid input.\n");
                return updateFood(food);
        }
        
        this.foodFileDAO.save(); //writes new info to file

        return true;
    }
    
    
    
    public boolean updateGetInfo(){
        System.out.print("Enter name of food to be updated: ");
        String foodNameToUpdate = sc.nextLine().toLowerCase();
        
        if (foodNameToUpdate.equalsIgnoreCase("x")){
            return false;
        }
        
        List<FoodItem> foundFoodItemsList = this.foodFileDAO.searchAllExact(foodNameToUpdate);
        
        if (foundFoodItemsList.isEmpty()){
            System.out.println("\n" +foodNameToUpdate+ " does not exist.\n");
            return false;
        }
        
        //not multiple foods with name
        if (foundFoodItemsList.size() == 1){
            //sends only/first item in foundFoodItemsList to updateFood method
            return updateFood(foundFoodItemsList.get(0));
        }
        
        //otherwise multiple foods with name exist
        
        
        return updateMultipleResults(foundFoodItemsList); //index 0 as param for testing!!
    }
    
    
    
    /*
    method to be called if there are more than one result with the name entered
    by the user in the method updateGetInfo
    */
    public boolean updateMultipleResults(List<FoodItem> foundFoodItemsList){
        
        //all elements will have save name, doesn't matter where name comes from
        String name = foundFoodItemsList.get(0).getName();
        
        //displays all foods with name
        System.out.println("\nMULTIPLE FOODS WITH NAME" +name+ "FOUND\n");
        this.foodFileDAO.printFormattedFromList(foundFoodItemsList);
        System.out.println("");
        
        
        int whichToUpdate = -1;
        
        while (whichToUpdate < 1 || whichToUpdate > foundFoodItemsList.size()){
            System.out.print("Which " +name+ " do you want to update? (enter number): ");
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("x")){
                return false;
            }
            
            //handles non-number input
            try{
                whichToUpdate = Integer.parseInt(userInput);
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please try again.\n");
                continue;
            }
            
            
            if (whichToUpdate < 1 || whichToUpdate > foundFoodItemsList.size()){
                System.out.println("Invalid input. Please try again.\n");
            }
        }
        
        FoodItem foodItemToUpdate = foundFoodItemsList.get(whichToUpdate - 1);
        
        return this.updateFood(foodItemToUpdate);
        
    }
    
    
    
    public boolean addFood(){
        
        String[] prompts = {"Name", "Serving size", "Unit of measure", "Calories",
            "Carbs", "Fat", "Protein", "Fiber", "Sugar"};
        
        String[] inputsArr = new String[prompts.length];
        
        
        String input;
        int promptCounter = 0;
        while (promptCounter < 9){
            System.out.print(prompts[promptCounter]+ ": ");
            input = sc.nextLine();
            
            
            if (input.equalsIgnoreCase("x")){
                return false;
            }
            
            else{
                inputsArr[promptCounter] = input;
                promptCounter++;
            }
        }
        
        String name = inputsArr[0];
        String ss = inputsArr[1];
        String unit = inputsArr[2];
        String cals = inputsArr[3];
        String carbs = inputsArr[4];
        String fat = inputsArr[5];
        String protein = inputsArr[6];
        String fiber = inputsArr[7];
        String sugar = inputsArr[8];
        
        FoodItem newFood = new FoodItem(name, ss, unit, cals, carbs, fat, protein,
            fiber, sugar);
        
        boolean foodAdded = this.foodFileDAO.add(newFood);
        
        if (foodAdded){
            System.out.println("\n" +name+ " was added.\n");
            return true;
        }
        
        return false;
    }
    
    
    /*
    prompts user for name of food
    if a food with this name exists in the file, then it is removed
    
    **need to add functionality to ask to delete all foods with this name
    
    */
    public boolean deleteFood(){
        System.out.print("Enter name of food to be deleted: ");
        String name = sc.nextLine();
        
        if (name.equalsIgnoreCase("x")){
            return false;
        }
        
        List<FoodItem> foundFoodItemsList = this.foodFileDAO.searchAllExact(name);
        
        //if there is more than 1 food item that has the same name as name
        boolean multipleResults = (foundFoodItemsList.size() > 1);
        
        if (multipleResults){
            return this.deleteMultipleFoods(foundFoodItemsList);
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
    public boolean deleteMultipleFoods(List<FoodItem> foundFoodItemsList){
        String name = foundFoodItemsList.get(0).getName();
        
        //prints header if there are multiple results
        System.out.println("\nMULTIPLE FOODS WITH NAME " +name+ " FOUND\n");

        //prints all FoodItems in foundFoodItemsList
        this.foodFileDAO.printFormattedFromList(foundFoodItemsList);
        System.out.println("");


        String choice = "";

        //validates user input for choice of deleting all or not
        while (!(choice.equals("y") || choice.equals("n"))){
            
            //prompt user
            System.out.print("Delete all results? (y or n): ");
            choice = sc.nextLine().toLowerCase();
            
            
            if (choice.equalsIgnoreCase("x")){
                return false;
            }
            
            //if user enters y, deletes all foods
            else if (choice.equals("y")){//delete all
                for (FoodItem f : foundFoodItemsList){
                    this.foodFileDAO.delete(f);
                    System.out.println(f.getName()+ " was deleted.");
                }
            }

            //if user enters n, then they only want to delete one
            else if (choice.equals("n")){ //delete one

                int whichToDelete = -1; //int for which food to delete


                //gets user input for which to delete
                while (whichToDelete < 1 || whichToDelete > foundFoodItemsList.size()){
                    
                    System.out.print("Delete which one? (enter number): ");
                    
                    String input = sc.nextLine();
                    
                    if (input.equalsIgnoreCase("x")){
                        return false;
                    }
                    
                    //handles non-number input
                    try{
                        whichToDelete = Integer.parseInt(input);  
                    } catch (NumberFormatException e){
                        System.out.println("Invalid input. Please try again.\n");
                        continue;
                    }
                        
                    
                    if (whichToDelete > foundFoodItemsList.size() || whichToDelete < 1){ //input is invalid
                        System.out.println("\nInvalid input. Please try again.\n");
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
