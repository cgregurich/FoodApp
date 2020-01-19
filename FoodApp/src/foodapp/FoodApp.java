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
import DataAccessObjects.FoodDB;
import java.util.List;
import java.util.ArrayList;
import static foodapp.Stat.*;
import UserInterfaces.TextUserInterface;
import UserInterfaces.TextUserInterfaceDatabase;



public class FoodApp {

    public static void main(String[] args) {
        TextUserInterfaceDatabase tUI = new TextUserInterfaceDatabase();
        
        //TextUserInterface tUI = new TextUserInterface();
        tUI.run();
        
       //added from laptop
        
        /*
        FoodDB foodDb = new FoodDB();
        FoodItem f = new FoodItem("test1name", "testss", "testunit", "testcalories",
                    "testcarbs", "testfat", "testprotein", "testfiber", "testsugar");
        foodDb.add(f);
        */
        
        
        
        
        /*
        FoodItemSorter sorter = new FoodItemSorter();
        
        List<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1"));
        foodList.add(new FoodItem("b2", "a2", "c2", "f2", "e2", "g2", "h2", "i2", "d2"));
        foodList.add(new FoodItem("c3", "d3", "g3", "b3", "a3", "e3", "i3", "f3", "h3"));
        foodList.add(new FoodItem("b2", "i4", "c4", "b4", "d4", "e4", "f4", "a4", "h4"));
        foodList.add(new FoodItem("b2", "c5", "a5", "i5", "h5", "f5", "g5", "d5", "e5"));
        
        
        foodList = sorter.sortByStat(foodList, SS);
        
        int counter = 1;
        for (FoodItem f : foodList){
            System.out.println("food #" +counter);
            counter++;
            System.out.println(f);
            System.out.println("");
        }
        */
        
        
        
        
    }

}
