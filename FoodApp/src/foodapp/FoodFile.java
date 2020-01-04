/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodapp;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.*;


/*
 *
 * @author colin
 */
public class FoodFile implements DAO<FoodItem> {
    final private String FILE_PATH_STR = "fooditems.txt";
    private Path foodItemsPath = null;
    private File foodItemsFile = null;
    private List<FoodItem> foodItemsList = null; //acts as proxy to the file
    final private String DELIMITER = "\t";
    
    
    public FoodFile(){
        this.foodItemsPath = Paths.get(FILE_PATH_STR);
        this.foodItemsFile = this.foodItemsPath.toFile();
        this.foodItemsList = this.getAll();
    }
    
    

    /*
    if foodItemsList is null (i.e. hasn't been touched), the file is read
    and each foodItem is added to the list
    */
    @Override
    public List<FoodItem> getAll() {
        if (this.foodItemsList != null){
            return this.foodItemsList;
        }
        
        this.foodItemsList = new ArrayList<>();
        
        //if the file already exists
        if (foodItemsFile.exists()){
            
            //creates a reader
            try (BufferedReader in = new BufferedReader(new FileReader(foodItemsFile))){
                //sets a String to the first line of file
                String line = in.readLine();
                
                //as long as not EOF
                while (line != null){
                    //line is split into arr at \t
                    String[] fields = line.split(DELIMITER);
                    //each element of arr is set to foodItem field
                    String name = fields[0];
                    String ss = fields[1];
                    String unit = fields[2];
                    String cals = fields[3];
                    String carbs = fields[4];
                    String fat = fields[5];
                    String protein = fields[6];
                    String fiber = fields[7];
                    String sugar = fields[8];
                    
                    //new foodItem created with these fields
                    FoodItem f = new FoodItem(name, ss, unit, cals, carbs, fat, protein, fiber, sugar);
                    //this foodItem then added to the list
                    this.foodItemsList.add(f);
                    
                    //next line is read, loops
                    line = in.readLine();
                }
                
            } catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
        
        //if file doesn't exist
        else{
            try{
                //creates file
                this.foodItemsPath = Files.createFile(Paths.get(FILE_PATH_STR));

            } catch (Exception e){
                System.out.println(e);
                return null;
            }
                
        }
        return this.foodItemsList;
    }

    
    /*
    adds a food item to foodItemsList
    then runs method save() to write new list to file
    */
    @Override
    public boolean add(FoodItem newFood) {
        if (this.foodItemsList != null){
            this.foodItemsList.add(newFood);
            this.save(); //writes new list to file
            return true;
        }
        
        return false;
            
        
    }
    
    
    /*
    if a foodItem with same name as param food exists in foodItemsList
    then that foodItem is removed from the list and method save() is called
    to update the file
    if not then returns false
    */
    @Override
    public boolean delete(FoodItem food) {
        int indexOfFood = this.exists(food);
        
        if (indexOfFood == -1){
            return false;
        }
        
        this.foodItemsList.remove(indexOfFood);
        this.save();
        return true;
    }
    
    /*
    removes the FoodItem at the param index in foodItemsList
    returns true if possible
    returns false if not
    */
    public boolean deleteByIndex(int index){
        if (this.foodItemsList.remove(index) != null){
            save();
            return true;
        }
        return false;
        
    }
    
    
    /*
    returns index of param food's value equivalent in foodItemsList if it exists
    if not, -1 is returned
    */
    public int indexOfExactFood(FoodItem food){
        
        for (FoodItem f : this.foodItemsList){
            
            //if food and f have same values, index of f is returned
            if (food.compareTo(f) == 0){
                return this.foodItemsList.indexOf(f);
            }
        }
        return -1;
    }
    
    
    
    /*
    checks whether a FoodItem with same name as param foodItem exists within
    the file / foodItemsList
    returns index of it if it does
    returns -1 if it does not
    */
    public int exists(FoodItem foodItem){
        for (FoodItem f : this.foodItemsList){
            if (f.getName().toLowerCase().equals(foodItem.getName().toLowerCase())){
                return this.foodItemsList.indexOf(f);
            }
        }
        //returns -1 if no food item shares the name
        return -1;
    }
   
    
    /*
    iterates through foodItemsList, if current FoodItem's name contains the
    param keyword, current FoodItem is added to return list
    list is returned
    */
    public List<FoodItem> searchAll(String keyword){
        ArrayList<FoodItem> foundFoodItemsList = new ArrayList<>();
        for (FoodItem f : this.foodItemsList){
            if (f.getName().toLowerCase().contains(keyword.toLowerCase())){
                foundFoodItemsList.add(f);
            }
        }
        return foundFoodItemsList;
    }
    
    /*
    iterates through foodItemsList, if current FoodItem's name equals the param
    keyword (not case sensitive), current FoodItem is added to return list
    list is returned
    */
    public List<FoodItem> searchAllExact(String name){
        ArrayList<FoodItem> foundFoodItemsList = new ArrayList<>();
        for (FoodItem f : this.foodItemsList){
            if (f.getName().toLowerCase().equals(name.toLowerCase())){
                foundFoodItemsList.add(f);
            }
        }
        return foundFoodItemsList;
    }
    
    
    /*
    returns a Food Item from this.foodItemsList based on index in foodItemsList
    */
    public FoodItem getFood(int indexOfFoodItem){
       return this.foodItemsList.get(indexOfFoodItem);
    }

    /*
    writes each foodItem in foodItemsList to the file
    works as an "update" to the file
    this method should be called every time a change to the list occurs
    */
    @Override
    public boolean save() {
        if (this.foodItemsList == null){
            return false;
        }
        
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.FILE_PATH_STR)))){
            for (FoodItem f : this.foodItemsList){
                out.print(f.getName());
                out.print(DELIMITER);
                out.print(f.getServingSize());
                out.print(DELIMITER);
                out.print(f.getUnit());
                out.print(DELIMITER);
                out.print(f.getCalories());
                out.print(DELIMITER);
                out.print(f.getCarbs());
                out.print(DELIMITER);
                out.print(f.getFat());
                out.print(DELIMITER);
                out.print(f.getProtein());
                out.print(DELIMITER);
                out.print(f.getFiber());
                out.print(DELIMITER);
                out.print(f.getSugar());
                out.println();
            }
            return true;
            
            
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    /*
    dang near useless method brthr
    but need to refactor some areas of code to use formatted instead of this
    */
    public void printFile(){
        for (FoodItem f : this.foodItemsList){
            System.out.println("Name: " +f.getName());
            System.out.println("Serving Size: " +f.getServingSize());
            System.out.println("Unit of measure: " +f.getUnit());
            System.out.println("Calories per " +f.getServingSize()+ " " +f.getUnit()+ ": " +f.getCalories());
            System.out.println("Carbs: " +f.getCarbs());
            System.out.println("Fat: " +f.getFat());
            System.out.println("Protein: " +f.getProtein());
            System.out.println("Fiber: " +f.getFiber());
            System.out.println("Sugar: " +f.getSugar());
            System.out.println("");
            
        }
    }
    
    public void printFileFormatted(){
        System.out.format("%-15s", "Name");
        System.out.format("%-14s", "Serving Size");
        System.out.format("%-6s", "Unit");
        System.out.format("%-10s", "Calories");
        System.out.format("%-7s", "Carbs");
        System.out.format("%-5s", "Fat");
        System.out.format("%-9s", "Protein");
        System.out.format("%-7s", "Fiber");
        System.out.format("%-7s%n", "Sugar");
        System.out.format("---------------------------------------------"
                + "-----------------------------------%n");
        
        for (FoodItem f : this.foodItemsList){
            System.out.format("%-15s", f.getName());
            System.out.format("%-14s", f.getServingSize());
            System.out.format("%-6s", f.getUnit());
            System.out.format("%-10s", f.getCalories());
            System.out.format("%-7s", f.getCarbs() + "g");
            System.out.format("%-5s", f.getFat() + "g");
            System.out.format("%-9s", f.getProtein() + "g");
            System.out.format("%-7s", f.getFiber() + "g");
            System.out.format("%-7s%n", f.getSugar() + "g");
        }
    }
    
}
