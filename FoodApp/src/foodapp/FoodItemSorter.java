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
import static foodapp.Stat.*;
import java.util.List;
import java.util.ArrayList;



public class FoodItemSorter {
    
    /*
    SORT STRING ARRAY
    */
    public String[] sortStringArray(String[] strArr, boolean isAscending){
        String temp;
        int size = strArr.length;
        
        if (isAscending){
            for (int i = 0; i < size; i++){
                for (int j = i + 1; j < size; j++){
                    if (strArr[i].compareTo(strArr[j]) > 0){
                        temp = strArr[i];
                        strArr[i] = strArr[j];
                        strArr[j] = temp;
                    }
                }
            }
        }
        
        else{ //descending
            for (int i = 0; i < size; i++){
                for (int j = i + 1; j < size; j++){
                    if (strArr[i].compareTo(strArr[j]) < 0){
                        temp = strArr[i];
                        strArr[i] = strArr[j];
                        strArr[j] = temp;
                    }
                }
            }
        }
            
       
        return strArr;
    }
    
    /*
    SORT INT ARRAY
    */
    public int[] sortIntArray(int[] intArr, boolean isAscending){
        int temp;
        int size = intArr.length;
        
        if(isAscending){
            for (int i = 0; i < size; i++){
                for (int j = i + 1; j < size; j++){
                    if (intArr[i] > intArr[j]){
                        temp = intArr[i];
                        intArr[i] = intArr[j];
                        intArr[j] = temp;
                    }
                }
            }
        }
        
        else{ //descending
            for (int i = 0; i < size; i++){
                for (int j = i + 1; j < size; j++){
                    if (intArr[i] < intArr[j]){
                        temp = intArr[i];
                        intArr[i] = intArr[j];
                        intArr[j] = temp;
                    }
                }
            }
        }
            
        return intArr;
    }
    
    /*
    HOMEBASE FOR SORTING
    SORT BY STAT
    */
    public List<FoodItem> sortByStat(List<FoodItem> foodItemsList, Stat stat, boolean isAscending){
        List<FoodItem> sortedList;
        
        
        //if stat is String based instead of number based
        if (stat == NAME || stat == UNIT){
            //firstArr exists just for cleanlieness of code
            String[] firstArr = populateArrByStat(foodItemsList, stat);
            String[] sortedArr = sortStringArray(firstArr, isAscending);

            sortedList = new ArrayList<>();

            for (String currentStat : sortedArr){
                for (FoodItem food : foodItemsList){
                    if (food.getStat(stat).equals(currentStat)){
                        sortedList.add(food);
                        foodItemsList.remove(food);
                        break;
                    }
                }
            }
        }
        
        else{ //stat is something of int value
            String[] strArr = populateArrByStat(foodItemsList, stat);
            int[] firstArr = convertToIntArray(strArr);
            int[] sortedArr = sortIntArray(firstArr, isAscending);

            sortedList = new ArrayList<>();

            for (int currentStat : sortedArr){
                for (FoodItem food : foodItemsList){
                    int foodStat = Integer.parseInt(food.getStat(stat));
                    
                    if (foodStat == currentStat){
                        sortedList.add(food);
                        foodItemsList.remove(food);
                        break;
                    }

                }
            }
        }
            
        return sortedList;
    }
    
    /*
    CONVERT STR ARRAY TO INT ARRAY
    */
    
    public int[] convertToIntArray(String[] strArr){
        int[] intArr = new int[strArr.length];
        
        try{
            for (int i = 0; i < strArr.length; i++){
                intArr[i] = Integer.parseInt(strArr[i]);
            }
        }catch (Exception e){
            return null;
        }    
     
        return intArr;
        
    }
    
    /*
    POPULATE ARR BY STAT PARAM
    */
    
    public String[] populateArrByStat(List<FoodItem> foodItemsList, Stat stat){
        int size = foodItemsList.size();
        String[] statArr = new String[size];
        
        for (int i = 0; i < size; i++){
            statArr[i] = foodItemsList.get(i).getStat(stat);
        }
        
        return statArr;
    }
    
    
    
}
