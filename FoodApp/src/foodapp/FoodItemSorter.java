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
    
    //DELETE ALL THIS SHISH WHEN YOU KNOW THE NEW CLASS WORKS
    /*
    SORT STRING ARRAY
    IMPLEMENTED
    
    public String[] sortStringArrayAscending(String[] strArr){
        String temp;
        int size = strArr.length;
        
        for (int i = 0; i < size; i++){
            for (int j = i + 1; j < size; j++){
                if (strArr[i].compareTo(strArr[j]) > 0){
                    temp = strArr[i];
                    strArr[i] = strArr[j];
                    strArr[j] = temp;
                }
            }
        }
       
        return strArr;
    }
    */
    
    
    /*
    public List<FoodItem> sortByStatDescending(List<FoodItem> foodItemsList, Stat stat){
        List<FoodItem> sortedList;
        
        
        //EXTRACT METHOD
        if (stat == NAME || stat == UNIT){
            String[] firstArr = populateArrByStat(foodItemsList, stat);
            String[] sortedArr = sortStringArrayDescending(firstArr);
            
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
        
        //EXTRACT METHOD
        else{
            
        }
        
        
    }
    */
    
    
    /*
    HOMEBASE FOR SORTING
    SORT BY STAT
    
    public List<FoodItem> sortByStatAscending(List<FoodItem> foodItemsList, Stat stat){
        List<FoodItem> sortedList;
        
        
        //if stat is String based instead of number based
        if (stat == NAME || stat == UNIT){
            //firstArr exists just for cleanlieness of code
            String[] firstArr = populateArrByStat(foodItemsList, stat);
            String[] sortedArr = sortStringArray(firstArr);

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
            int[] sortedArr = sortIntArray(firstArr);

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
    */
    
    
    
    
    
    
    
    
    //BELOW HERE IS NEW CLASS
    
    /*
    SORT BY STAT
    */
    public List<FoodItem> sortByStat(List<FoodItem> foodItemsList, Stat stat, boolean isAscending){
        if (stat == NAME || stat == UNIT){
           if (isAscending){
               return sortListByStringStatAscending(foodItemsList, stat);
           } 
           else{
               return sortListByStringStatDescending(foodItemsList, stat);
           }
        }
        
        else if (stat == SS || stat == CALS || stat == CARBS || stat == FAT || 
                stat == PROTEIN || stat == FIBER || stat == SUGAR){
            if (isAscending){
                return sortListByIntStatAscending(foodItemsList, stat);
            }
            else{
                return sortListByIntStatDescending(foodItemsList, stat);
            }
        }
        return null;
    }
    
    /*
    SORT BY STRING STAT ASCENDING
    used to sort String columns: name and unit
    */
    public List<FoodItem> sortListByStringStatAscending(List<FoodItem> foodItemsList, Stat stat){
        
        String[] firstArr = populateArrWithStats(foodItemsList, stat);
        String[] sortedStatsArr = sortStringArrayAscending(firstArr);
        
        List<FoodItem> sortedList = populateListFromSortedStringStats(foodItemsList, stat, sortedStatsArr);
        
        return sortedList;
    }
    
    /*
    POPULATE LIST FROM SORTED STRING STATS
    */
    public List<FoodItem> populateListFromSortedStringStats(List<FoodItem> foodItemsList, Stat stat, String[] sortedStatsArr){
        List<FoodItem> sortedList = new ArrayList<>();
        
        for (String currentStat : sortedStatsArr){
            for (FoodItem food : foodItemsList){
                if (food.getStat(stat).equals(currentStat)){
                    sortedList.add(food);
                    foodItemsList.remove(food);
                    break;
                }
            }
        }
        return sortedList;
    }
    
    /*
    SORT LIST BY STIRNG STAT DESCENDING
    used to sort String columns: name and unit
    */
    public List<FoodItem> sortListByStringStatDescending(List<FoodItem> foodItemsList, Stat stat){
        String[] firstArr = populateArrWithStats(foodItemsList, stat);
        String[] sortedStatsArr = sortStringArrDescending(firstArr); 
        
        List<FoodItem> sortedList = populateListFromSortedStringStats(foodItemsList, stat, sortedStatsArr);
        return sortedList;
    }

    /*
    POPULATE LIST FROM SORTED INT STATS
    */
    public List<FoodItem> populateListFromSortedIntStats(List<FoodItem> foodItemsList, Stat stat, int[] sortedStatsArr){
        List<FoodItem> sortedList = new ArrayList<>();
        
        for (int currentStat : sortedStatsArr){
            for (FoodItem food : foodItemsList){
                int foodStat = Integer.parseInt(food.getStat(stat));
                if (foodStat == currentStat){
                    sortedList.add(food);
                    foodItemsList.remove(food);
                    break;
                }
            }
        }
        return sortedList;
    }
    
    /*
    SORT LIST BY INT STAT ASCENDING
    used to sort int columns: ss, cal, carbs, fat, protein, fiber, sugar
    */
    public List<FoodItem> sortListByIntStatAscending(List<FoodItem> foodItemsList, Stat stat){
        String[] firstArr = populateArrWithStats(foodItemsList, stat);
        int[] intArr = convertToIntArray(firstArr);
        int[] sortedStatsArr = this.sortIntArrayAscending(intArr);
        
        List<FoodItem> sortedList = populateListFromSortedIntStats(foodItemsList, stat, sortedStatsArr);
        return sortedList;
    }
    
    /*
    SORT LIST BY INT STAT DESCENDING
    used to sort int columns: ss, cal, carbs, fat, protein, fiber, sugar
    */
    public List<FoodItem> sortListByIntStatDescending(List<FoodItem> foodItemsList, Stat stat){
        String[] firstArr = populateArrWithStats(foodItemsList, stat);
        int[] intArr = convertToIntArray(firstArr);
        int[] sortedStatsArr = sortIntArrayDescending(intArr);
        
        List<FoodItem> sortedList = populateListFromSortedIntStats(foodItemsList, stat, sortedStatsArr);
        
        return sortedList;
    }
    
    /*
    SORT INT ARRAY ASCENDING
    */
    public int[] sortIntArrayAscending(int[] intArr){
        int temp;
        int size = intArr.length;
        
        for (int i = 0; i < size; i++){
            for (int j = i + 1; j < size; j++){
                if (intArr[i] > intArr[j]){
                    temp = intArr[i];
                    intArr[i] = intArr[j];
                    intArr[j] = temp;
                }
            }
        }
        return intArr;
    }
    
    
    /*
    SORT INT ARRAY DESCENDING
    */
    public int[] sortIntArrayDescending(int[] intArr){
        int temp;
        int size = intArr.length;
        
        for (int i = 0; i < size; i++){
            for (int j = i + 1; j < size; j++){
                if (intArr[i] < intArr[j]){
                    temp = intArr[i];
                    intArr[i] = intArr[j];
                    intArr[j] = temp;
                }
            }
        }
        return intArr;
    }
    
    
    
    
    
    /*
    SORT STRING ARR ASCENDING
    */
    public String[] sortStringArrayAscending(String[] strArr){
        String temp;
        
        int size = strArr.length;
        for (int i = 0; i < size; i++){
            for (int j = i+1; j < size; j++){
                if (strArr[j].compareTo(strArr[i]) < 0){
                    temp = strArr[i];
                    strArr[i] = strArr[j];
                    strArr[j] = temp;
                }
            }
        }
        return strArr;
    }
    
    
    
    /*
    SORT STRING ARR DESCENDING
    */
    public String[] sortStringArrDescending(String[] strArr){
        String temp;
        
        int size = strArr.length;
        for (int i = 0; i < size; i++){
            for (int j = i + 1; j < size; j++){
                if (strArr[j].compareTo(strArr[i]) > 0){
                    temp = strArr[i];
                    strArr[i] = strArr[j];
                    strArr[j] = temp;
                }
            }
        }
        return strArr;
    }
    
    /*
    POPULATE ARR BY STAT PARAM
    */
    public String[] populateArrWithStats(List<FoodItem> foodItemsList, Stat stat){
        int size = foodItemsList.size();
        String[] statArr = new String[size];
        
        for (int i = 0; i < size; i++){
            statArr[i] = foodItemsList.get(i).getStat(stat);
        }
        
        return statArr;
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
    
    
    
    
}
