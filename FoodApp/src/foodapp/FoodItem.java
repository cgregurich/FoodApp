/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodapp;

/**
 *
 * @author colin
 */
public class FoodItem implements Comparable {
    private String name;
    private String servingSize;
    private String unit;
    private String calories;
    private String carbs;
    private String fat;
    private String protein;
    private String fiber;
    private String sugar;
    
    public FoodItem(){
        
    }
    
    public FoodItem(String name){
        this.name = name;
    }
    
    public FoodItem(String name, String ss, String unit, String cals, String carbs,
            String fat, String protein, String fiber, String sugar){
        this.name = name;
        this.servingSize = ss;
        this.unit = unit;
        this.calories = cals;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
        this.fiber = fiber;
        this.sugar = sugar;
    }
    
    /*
    returns a String of all info of FoodItem, each stat labelled and
    on a new line
    */
    @Override
    public String toString(){
        String foodStr = "Name: " +name+ "\nServing Size: " +servingSize+
                "\nUnit of measure: " +unit+ "\nCalories: " +calories+ "\nCarbs: "
                +carbs+ "\nFat: " +fat+ "\nProtein: " +protein+ "\nFiber: " +fiber+
                "\nSugar: " +sugar;
        return foodStr;
    }
    
    
    /*
    returns 0 if both FoodItems have the same values entirely
    returns -1 if there is a difference somewhere
    */
    @Override
    public int compareTo(Object o) {
        
        FoodItem f = (FoodItem) o;
        
        //if all attributes are the same
        if (f.getName().equals(this.getName()) && f.getCalories().equals(this.getCalories())
                && f.getServingSize().equals(this.getServingSize()) && f.getUnit().equals(this.getUnit())
                && f.getCarbs().equals(this.getCarbs()) && f.getFat().equals(this.getFat())
                && f.getProtein().equals(this.getProtein()) && f.getFiber().equals(this.getFiber())
                && f.getSugar().equals(this.getSugar())){
            return 0;
        }
        
        return -1;
        
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getServingSize(){
        return this.servingSize;
    }
    
    public String getUnit(){
        return this.unit;
    }
    
    public String getCalories(){
        return this.calories;
    }
    
    public String getCarbs(){
        return this.carbs;
    }
    
    public String getFat(){
        return this.fat;
    }
    
    public String getProtein(){
        return this.protein;
    }
    
    public String getFiber(){
        return this.fiber;
    }
    
    public String getSugar(){
        return this.sugar;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setServingSize(String servingSize){
        this.servingSize = servingSize;
    }
    
    public void setUnit(String unit){
        this.unit = unit;
    }
    
    public void setCalories(String calories){
        this.calories = calories;
    }
    
    public void setCarbs(String carbs){
        this.carbs = carbs;
    }
    
    public void setFat(String fat){
        this.fat = fat;
    }
    
    public void setProtein(String protein){
        this.protein = protein;
    }
    
    public void setFiber(String fiber){
        this.fiber = fiber;
    }
    
    public void setSugar(String sugar){
        this.sugar = sugar;
    }

}
