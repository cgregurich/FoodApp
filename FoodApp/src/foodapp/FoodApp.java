/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package foodapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author colin
 */



public class FoodApp extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Food App");
        
        GridPane grid = new GridPane();
        grid = menuItems(grid);
        Scene scene = new Scene(grid, 300, 100);
        grid.setAlignment(Pos.TOP_CENTER);
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public GridPane menuItems(GridPane grid){
        VBox box = new VBox(4);
        
        Button addButton = new Button("Add new food");
        //Button deleteButton = new Button ("Delete food");
        //Button updateButton = new Button("Update food");
        //Button searchButton = new Button("Search");
        
        box.getChildren().add(addButton);
        //box.getChildren().add(deleteButton);
        //box.getChildren().add(updateButton);
        //box.getChildren().add(searchButton);
        
        box.setAlignment(Pos.TOP_CENTER);
        
        grid.add(box, 0, 0);
        
        
        
        return grid;
        
        
        
        
    }
    

}
