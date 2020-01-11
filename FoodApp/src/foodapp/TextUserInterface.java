/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodapp;
import static foodapp.Stat.*;
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
    private FoodItemSorter sorter = new FoodItemSorter();
    
    
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
    USER MENU COMMAND
    */
    public String userMenuCommand(){
        
        System.out.print("\nCommand: ");
        String userCommand = sc.nextLine().toLowerCase();
        System.out.println("");
        
        switch (userCommand){
            
            case "view":
                this.view();
                break;
                
            case "sort":
                this.sortFoods();
                break;
                
            case "add":
                this.addFood();
                break;

            case "del":
                this.deleteFood();
                break;
                
            case "search":
                this.search();
                break;
                
            case "update":
                this.updateInputFoodToUpdate();
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
    DISPLAY MENU
    */
    public void displayMenu(){
        System.out.println("COMMANDS");
        System.out.println("\n(enter x at any point to return home)\n");
        System.out.println("view - view all foods");
        System.out.println("sort - sort foods by chosen criteria");
        System.out.println("add - add a new food");
        System.out.println("del - delete a food");
        System.out.println("search - search for a food by name");
        System.out.println("update - update the info of a food");
        System.out.println("help - display this menu");
        System.out.println("exit - exit the program");
    }
    
    
    /*
    VIEW
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
    SEARCH
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
        
        System.out.println("\n\nSEARCH RESULTS FOR " +searchedName+ "\n");
        
        if (!foundFoodItemsList.isEmpty()){
            this.foodFileDAO.printFormattedFromList(foundFoodItemsList);
            return true;
        }
        
        
        System.out.println("No results found");
        return false;
    }
    
    
    /*
    UPDATE GET INFO
    */
    public boolean updateInputFoodToUpdate(){
        
        List<FoodItem> foundFoodItemsList = new ArrayList<>();
        
        while (foundFoodItemsList.isEmpty()){
            System.out.print("Enter name of food to be updated: ");
            String foodNameToUpdate = sc.nextLine(); //no toLowerCase()???
            
            if (foodNameToUpdate.equalsIgnoreCase("x")){
                return false;
            }
            
            foundFoodItemsList = this.foodFileDAO.searchAllExact(foodNameToUpdate);
            
            if (foundFoodItemsList.isEmpty()){
                System.out.println("\n" +foodNameToUpdate+ " does not exist.\n");
                //return this method??
            }
            
            else if (foundFoodItemsList.size() == 1){
                return updateFood(foundFoodItemsList.get(0));
            }
            
            else{
                return updateMultipleResults(foundFoodItemsList);
            }
            
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
    UPDATE FOOD
    */
    public boolean updateFood(FoodItem food){
        
        //takes care of escape clause in updateGetChoice
        String choice = updateGetUserChoice();
        if (choice == null){
            return false;
        }
        
        //takes care of escape clause in updateGetNewStat
        String newStat = updateInputNewStat(choice);
        if (newStat == null){
            return false;
        }
        
        
        
        //prompts user for new stat based on choice
        switch(choice){
            case "name":
                food.setName(newStat);
                break;
                
            case "ss":
                food.setServingSize(newStat);
                break;
                
            case "unit":
                food.setUnit(newStat);
                break;
                
            case "cals":
                food.setCalories(newStat);
                break;
                
            case "carbs":
                food.setCarbs(newStat);
                break;
                
            case "fat":
                food.setFat(newStat);
                break;
                
            case "protein":
                food.setProtein(newStat);
                break;
                
            case "fiber":
                food.setFiber(newStat);
                break;
                
            case "sugar":
                food.setSugar(newStat);
                break;
                
        }
        
        this.foodFileDAO.save(); //writes new info to file

        return true;
    }
    
    
    /*
    UPDATE GET CHOICE
    */
    public String updateGetUserChoice(){
        //prompts for and validates choice input
        String choice = "";
        boolean isValidChoice = false;
        while (!isValidChoice){
            
            System.out.println("\nWhat needs to be updated?");
            System.out.println("name | ss | unit | cals | carbs | fat | protein | fiber | sugar");
            System.out.print("Enter choice: ");
            choice = sc.nextLine().toLowerCase();
            
            if (choice.equalsIgnoreCase("x")){
                return null;
            }
            
            String[] choicesArr = {"name", "ss", "unit", "cals", "carbs",
            "fat", "protein", "fiber", "sugar"};
            
            for (String curChoice : choicesArr){
                if (choice.equalsIgnoreCase(curChoice)){
                    isValidChoice = true;
                    break;
                }
            }
            
            if (isValidChoice){
                break;
            }
            else{
                System.out.println("\nInvalid input. Please try again.");
            }
        }
        return choice;
    }
    
    /*
    UPDATE GET NEW STAT
    */
    public String updateInputNewStat(String choice){
        
        String newStat;
        
        //outputted choice is not same as inputted choice 
        if (choice.equalsIgnoreCase("ss")){
            System.out.print("\nEnter new serving size: ");
            newStat = sc.nextLine();
        }
        
        //outputted choice is not same as inputted choice
        else if(choice.equalsIgnoreCase("cals")){
            System.out.print("\nEnter new calories: ");
            newStat = sc.nextLine();
        }
        
        else{
            System.out.print("\nEnter new " +choice+ ": ");
            newStat = sc.nextLine();
        }
            
        
        if (newStat.equals("x")){
            return null;
        }
        
        return newStat;
    }
    
    /*
    UPDATE MULTIPLE RESULTS
    */
    public boolean updateMultipleResults(List<FoodItem> foundFoodItemsList){
        
        //all elements will have save name, doesn't matter where name comes from
        String name = foundFoodItemsList.get(0).getName();
        
        
        System.out.println("\nMULTIPLE FOODS WITH NAME" +name+ "FOUND\n");
        this.foodFileDAO.printFormattedFromList(foundFoodItemsList);
        System.out.println("");
        
        //set to -1 since -1 will never be a valid input for user to enter
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
    
    
    /*
    ADD FOOD
    */
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
    DELETE FOOD
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
    DELETE MULTIPLE FOODS
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
            
            
            switch(choice){
                case "x":
                    return false;
                    
                case "y":
                    return deleteMultipleFoodsDeleteAll(foundFoodItemsList);
                    
                case "n":
                    return deleteMultipleFoodsChoose(foundFoodItemsList);
                    
                default:
                    System.out.println("Invalid input. Please try again.\n");
            }
        }
        return false;
    }
    
    /*
    DELETE MULTIPLE FOODS DELETE ALL
    */
    public boolean deleteMultipleFoodsDeleteAll(List<FoodItem> foundFoodItemsList){
        if (foundFoodItemsList == null){
            return false;
        }
        
        for (FoodItem f : foundFoodItemsList){
            this.foodFileDAO.delete(f);
            System.out.println(f.getName()+ " was deleted.");
        }
        return true;
    }
    
    /*
    DELETE MULTIPLE FOODS CHOOSE
    */
    public boolean deleteMultipleFoodsChoose(List<FoodItem> foundFoodItemsList){
        
        int whichToDelete = -1;
        String name = foundFoodItemsList.get(0).getName();
        
        while (whichToDelete < 1 || whichToDelete > foundFoodItemsList.size()){
            
            System.out.print("Delete which one? (enter number): ");
            String input = sc.nextLine();
            
            if (input.equalsIgnoreCase("x")){
                return false;
            }
            
           
            try{
                whichToDelete = Integer.parseInt(input);
            } catch(NumberFormatException e){
                System.out.println("Invalid input. Please try again.\n");
                continue;
            }
            
            if (whichToDelete > foundFoodItemsList.size() || whichToDelete < 1){
                System.out.println("\nInvalid input. Please try again.\n");
            }
            
            int indexOfFoodInDAO = this.foodFileDAO.indexOfExactFood(foundFoodItemsList.get(whichToDelete - 1));
            
            this.foodFileDAO.deleteByIndex(indexOfFoodInDAO);
            
            System.out.println(name+ " was deleted.");
            return true;
        }
        return false;                
    }
    
    /*
    SORT FOODS
    */
    public boolean sortFoods(){
        String choice = sortFoodsInputCriteria();
        
        if (choice == null){
            return false;
        }
        
        Stat stat = convertStringToStat(choice);
        this.foodFileDAO.sortFile(stat);
        //no save call in sortFile yet
        return true;
        
    }
    
    /*
    SORT FOODS INPUT CRITERIA
    */
    public String sortFoodsInputCriteria(){
        String[] criteriaOptions = {"name", "ss", "unit", "cals", "carbs", "fat",
            "protein", "fiber", "sugar"};
        
        boolean isChoiceValid = false;
        
        String choice = "";
        
        
        
        while (!isChoiceValid){
            System.out.println("Sort by what?");
            System.out.println("name | ss | cals | carbs | fat | protein | fiber | sugar");
            System.out.print("Enter choice: ");
            choice = sc.nextLine().toLowerCase();
            
            if (choice.equals("x")){
                return null;
            }
            
            for (String option : criteriaOptions){
                if (choice.equals(option)){
                    isChoiceValid = true;
                    break;
                }
            }
            if (!isChoiceValid){
                System.out.println("Invalid entry. Please try again.\n");
            }
            else{
                break;
            }
        }
        
        return choice;
    }
    
    /*
    CONVERT STRING TO STAT
    */
    public Stat convertStringToStat(String str){
        Stat stat;
        try{
            stat = Stat.valueOf(str.toUpperCase());
        }catch (Exception e){
            return null;
        }
            
        return stat;
    }
            
        
}
