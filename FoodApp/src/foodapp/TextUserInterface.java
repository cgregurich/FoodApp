/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodapp;
import java.util.Scanner;

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
        
        System.out.println("\nCommand: ");
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
            System.out.println("DISPLAYING FOODS");
            foodFileDAO.printFile();
        }
        
        else{ //no food items in file
            System.out.println("NO FOODS TO DISPLAY");
            System.out.println("Add foods with command \"add\"");
        }
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
        
        boolean wasDeleted = this.foodFileDAO.delete(new FoodItem(name.toLowerCase()));
        
        if (wasDeleted){
            System.out.println(name+ " was deleted.");
            return true;
        }
        
        else{
            System.out.println(name+ " does not exist.");
            return false;
        }
        
        
    }
}
