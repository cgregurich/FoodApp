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
    private String[] statsArrShort = {"name", "ss", "unit", "cals", "carbs",
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
        System.out.println("search - search for a food by keyword");
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
                
            case "search":
                this.search();
                break;
                
            case "update":
                this.update();
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
        String[] criteriaAndOrder = sortFoodsGetInput();
        
        if (criteriaAndOrder == null){
            return false;
        }
        
        List<FoodItem> sortedFoods = this.foodDb.sort(criteriaAndOrder);
        
        System.out.println("");
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
            
            if (userEnteredX(sortCriteria)){
                return null;
            }
            
            for (String stat : this.statsArrShort){
                if (sortCriteria.equals(stat)){
                    isCriteriaValid = true;
                    break;
                }
            }
            
            if (!isCriteriaValid){
                System.out.println("Invalid entry. Please try again.\n");
            }
            
        }
        
        //user input matches SQL file column names in all cases but ss
        if (sortCriteria.equals("ss")){
            sortCriteria = "servingsize";
        }
        
        
        String order = "";
        while (!(order.equals("asc") || order.equals("desc"))){
            System.out.print("Ascending or descending? (enter asc or desc): ");
            order = sc.nextLine().toLowerCase();
            if (userEnteredX(order)){
                return null;
            }
            
            if (!(order.equals("asc") || order.equals("desc"))){
                System.out.println("Invalid entry. Please try again.\n");
            }
        }
        
        
        
        String[] criteriaAndOrder = {sortCriteria, order};
        
        
        return criteriaAndOrder;
    }
    
    /*
    ADD NEW FOOD
    */
    public void addNewFood(){
        FoodItem newFood = addNewFoodGetInput();
        
        if (newFood == null){
            return;
        }
        
        
        if (foodDb.add(newFood)){
            System.out.println("\n" +newFood.getName()+ " has been added.\n");
        }
        else{
            System.out.println("\nERROR. " +newFood.getName()+ " has already been added.");
        }
    }
    
    
    /*
    ADD NEW FOOD GET INPUT
    //TODO: validate format of input (numbers, strings, etc)
    */
    public FoodItem addNewFoodGetInput(){
        FoodItem newFood;
        
        String[] inputsArr = new String[this.statsArr.length];
        
        for (int i = 0; i < this.statsArr.length; i++){
            System.out.print("Enter " +this.statsArr[i]+ ": ");
            String input = sc.nextLine();
            
            if (input.equalsIgnoreCase("name")){
                System.out.println("Input can't be name.");
                i--;
                continue;
            }
            
            if (userEnteredX(input)){
                return null;
            }
            
            inputsArr[i] = input;
        }
        
        newFood = new FoodItem(inputsArr);
        
        
        return newFood;
    }
    
    /*
    DELETE FOOD BY NAME
    */
    public void deleteFoodByName(){
        String nameToBeDeleted = deleteFoodGetInput();
        
        if (userEnteredX(nameToBeDeleted)){
            return;
        }
        
        if (nameToBeDeleted.equalsIgnoreCase("name")){
            System.out.println("\nName to delete can't be name.");
            return;
        }
        
        if(this.foodDb.deleteByName(nameToBeDeleted)){
            System.out.println("\n" +nameToBeDeleted+ " was deleted.");
        }
        else{
            System.out.println("\n" +nameToBeDeleted+ " could not be found.");
        }
            
        
    }
    
    /*
    DELETE FOOD GET INPUT
    */
    public String deleteFoodGetInput(){
        System.out.print("Enter name of food to delete: ");
        String name = sc.nextLine().toLowerCase();
        
        return name;
    }
    
    /*
    SEARCH
    */
    public void search(){
        String keyword = getKeywordFromUser();
        
        if (userEnteredX(keyword)){
            return;
        }
        
        List<FoodItem> foodsThatContainKeyword = this.foodDb.searchByKeyword(keyword);
        
        if (foodsThatContainKeyword.isEmpty()){
            System.out.println("\nNo results found for \"" +keyword+ "\".");
            return;
        }
        else{
            System.out.println("");
            this.foodDb.printAllFoodsFormatted(foodsThatContainKeyword);
        }
        
        
    }
    
    /*
    SEARCH GET INPUT
    */
    public String getKeywordFromUser(){
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        
        return keyword;
    }
    
    /*
    UPDATE
    */
    public void update(){
        String[] inputsArr = updateGetInput();
        if (inputsArr == null){
            return;
        }
        
        String column = inputsArr[0];
        String newCell = inputsArr[1];
        String foodName = inputsArr[2];
        
        if (this.foodDb.updateFood(column, newCell, foodName)){
            System.out.println(foodName+ " has been updated.");
        }
        
        else{
            System.out.println("Something went wrong.");
        }
    }
    
    /*
    UPDATE GET INPUT
    */
    public String[] updateGetInput(){
        
        String foodName = updateGetNameToUpdate();
        if (userEnteredX(foodName)){
            return null;
        }
        
        String statChoice = updateGetStatChoice();
        if (userEnteredX(statChoice)){
            return null;
        }
        
        
        String newStat = updateGetNewStat(statChoice);
        if (userEnteredX(newStat)){
            return null;
        }
        
        String[] inputsArr = {statChoice, newStat, foodName};
        return inputsArr;
    }
    
    /*
    UPDATE GET NAME TO UPDATE
    */
    public String updateGetNameToUpdate(){
        boolean foodExists = false;
        String foodName = "";
        
        while (!foodExists){
            System.out.print("Enter name of food to update: ");
            foodName = sc.nextLine();
            
            if (userEnteredX(foodName)){
                return "x";
            }
            
            foodExists = this.foodDb.foodAlreadyExists(foodName);
            
            if (!foodExists){
                System.out.println("\nERROR: " +foodName+ " doesn't exist!\n");
            }
        }
        System.out.println("returning foodName: " +foodName); //testing
        return foodName;
    }
    
    /*
    UPDATE GET STAT CHOICE
    */
    public String updateGetStatChoice(){
        String statChoice = "";
        boolean isStatChoiceValid = false;
        
        while (!isStatChoiceValid){
            System.out.println("\nWhat would you like to update?");
            for (String stat : statsArrShort){
                System.out.print(stat+ " | ");
            }
            
            System.out.print("\n\nEnter which: ");
            statChoice = sc.nextLine().toLowerCase();
            
            if (userEnteredX(statChoice)){
                return "x";
            }
            
            for (String stat : statsArrShort){
                if (stat.equals(statChoice)){
                    isStatChoiceValid = true;
                    break;
                }
            }
            if (!isStatChoiceValid){
                System.out.println("\nInvalid input. Please try again.");
            }
        }
        
        return statChoice;
    }
    
    /*
    UPDATE GET NEW STAT
    TODO: validate format of input (number vs string, etc)
    */
    public String updateGetNewStat(String statChoice){
        System.out.print("Enter new " +statChoice+ ": ");
        String newStat = sc.nextLine();
        return newStat;
    }
    
    /*
    USER ENTERED X
    */
    public boolean userEnteredX(String input){
         return input.equalsIgnoreCase("x");
    }
}
