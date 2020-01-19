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
        
       
        
        
        
        
    }

}
