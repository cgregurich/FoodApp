/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces;

import DataAccessObjects.FoodDB;
import foodapp.FoodItem;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author colin
 */
public class TextUserInterfaceDatabase {
    private Scanner sc = new Scanner(System.in);
    private FoodDB foodDb = new FoodDB();
    private String[] statsArr = {"name", "serving size", "unit", "calories", "carbs",
            "fat", "protein", "fiber", "sugar"};
    private String[] statsArrShort = {"name", "ss", "unit", "cal", "carbs",
            "fat", "protein", "fiber", "sugar"};
    
    
    public void run(){
        displayMenu();
        
        String command = "";
        while (!command.equals("exit")){
            command = userMenuCommand();
        }
    }
    
    /*
    DISPLAY MENU
    */
    public void displayMenu(){
        System.out.println("\n(enter x at any time to return home)\n");
        System.out.println("view - view all foods");
        System.out.println("sort - sort foods by chose criteria");
        System.out.println("add - add a new food");
        System.out.println("del - delete a food by name");
        System.out.println("search - search for a food by name");
        System.out.println("update - update the info of a food");
        System.out.println("help - display this menu");
        System.out.println("exit - exit the program");
    }
    
    /*
    USER MENU COMMAND
    */
    public String userMenuCommand(){
        System.out.print("\nCommand: ");
        String command = sc.nextLine().toLowerCase();
        System.out.println("");
        
        switch (command){
            case "view":
                this.view();
                break;
                
            case "sort":
                this.sortFoods();
                break;
                
            case "add":
                this.addNewFood();
                break;
                
            case "del":
                this.deleteFoodByName();
                break;
                
            case "help":
                displayMenu();
                break;
                
            case "exit":
                System.out.println("Good bye.");
                break;
                
        }
        
        
        return command;
    }
    
    /*
    VIEW
    */
    public void view(){
        foodDb.printAllFoodsFormatted(this.foodDb.getAll());
    }
    
    /*
    SORT FOODS
    */
    public boolean sortFoods(){
        String[] params = sortFoodsGetInput();
        if (params == null){
            //do something; escape clause
            System.out.println("it's null bruv");
            return false;
        }
        
        List<FoodItem> sortedFoods = this.foodDb.sort(params);
        
        this.foodDb.printAllFoodsFormatted(sortedFoods);
        
        return true;
    }
    
    /*
    SORT FOODS GET INPUT
    */
    public String[] sortFoodsGetInput(){
        System.out.println("Sort by what?");
        for (String stat : this.statsArrShort){
            System.out.print(stat+ " | ");
        }
        System.out.println("");
            
        
        
        String sortCriteria = "";
        boolean isCriteriaValid = false;
        
        while (!isCriteriaValid){
            System.out.print("Enter choice: ");
            sortCriteria = sc.nextLine().toLowerCase();
            
            //add escape clause
            
            for (String stat : this.statsArrShort){
                if (sortCriteria.equals(stat)){
                    System.out.println("sortCriteria: " +sortCriteria);
                    System.out.println("stat: " +stat);
                    isCriteriaValid = true;
                    break;
                }
            }
            
            if (!isCriteriaValid){
                System.out.println("Invalid entry. Please try again.\n");
            }
            
        }
        
        if (sortCriteria.equals("ss")){
            sortCriteria = "servingsize";
        }
        
        
        String order = "";
        while (!(order.equals("asc") || order.equals("desc"))){
            System.out.print("Ascending or descending? (asc or desc): ");
            order = sc.nextLine().toLowerCase();
            
            if (!(order.equals("asc") || order.equals("desc"))){
                System.out.println("Invalid entry. Please try again.\n");
            }
        }
        
        String[] returnArr = {sortCriteria, order};
        
        return returnArr;
    }
    
    /*
    ADD NEW FOOD
    */
    public void addNewFood(){
        FoodItem newFood = addNewFoodGetInput();
        if (foodDb.add(newFood)){
            System.out.println("\n" +newFood.getName()+ " has been added.\n");
        }
        else{
            System.out.println("\nERROR. " +newFood.getName()+ " has already been added.");
        }
    }
    
    
    /*
    ADD NEW FOOD GET INPUT
    */
    public FoodItem addNewFoodGetInput(){
        FoodItem newFood = new FoodItem("");
        
        String[] inputsArr = new String[this.statsArr.length];
        
        for (int i = 0; i < this.statsArr.length; i++){
            System.out.print("Enter " +this.statsArr[i]+ ": ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("x")){
                return null;
            }
            inputsArr[i] = sc.nextLine();
        }
        
        newFood.setStatsFromArray(inputsArr);
        
        return newFood;
    }
    
    /*
    DELETE FOOD BY NAME
    */
    public void deleteFoodByName(){
        String nameToBeDeleted = deleteFoodGetInput();
        
        if (nameToBeDeleted == null){
            userMenuCommand();
            return;
        }
        
        if(this.foodDb.deleteByName(nameToBeDeleted)){
            System.out.println("\n" +nameToBeDeleted+ " was deleted.");
        }
        else{
            System.out.println("\n" +nameToBeDeleted+ " could not be found.");
        }
            
        //TODO: output if a food can't be found??
        //output what food(s) were deleted
    }
    
    /*
    DELETE FOOD GET INPUT
    */
    public String deleteFoodGetInput(){
        System.out.print("Enter name of food to delete: ");
        String name = sc.nextLine().toLowerCase();
        if (userWantsToLeave(name)){
            return null;
        }
        return name;
    }
    
    /*
    USER WANTS TO LEAVE
    */
    public boolean userWantsToLeave(String input){
         return input.equalsIgnoreCase("x");
    }
}
