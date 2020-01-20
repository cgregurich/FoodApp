/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessObjects;

import DataAccessObjects.DAO;
import foodapp.FoodItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import foodapp.FoodItemSorter;
import foodapp.Stat;

/**
 *
 * @author colin
 */
public class FoodDB implements DAO<FoodItem> {
    private final String SQL_NAME = "FoodAppFoodItems";
    private final String TABLE_NAME = "FoodItems";
    private FoodItemSorter sorter = new FoodItemSorter();
    private static final String SQL = "select instance_id, %s from eam_measurement"
    + " where resource_id in (select RESOURCE_ID from eam_res_grp_res_map where"
    + " resource_group_id = ?) and DSN like ? order by 2";
    
    
    /*
    GET CONNECTION
    */
    public Connection getConnection(){
        String dbUrl = "jdbc:sqlite:FoodAppFoodItems.sqlite";
        
        try{
            Connection connection = DriverManager.getConnection(dbUrl);
            return connection;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }

    /*
    GET ALL
    */
    @Override
    public List<FoodItem> getAll() {
        String query = "SELECT * FROM " +TABLE_NAME;
        List<FoodItem> foodItems = new ArrayList<>();
        
        
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()){
            
            
            String[] statsArr = new String[9];
            
            while (rs.next()){
                for (int i = 0; i < statsArr.length; i++){
                    statsArr[i] = rs.getString(i+1);
                }
                FoodItem f = new FoodItem(statsArr);
                foodItems.add(f);
            }
            return foodItems;
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }

    /*
    ADD
    */
    @Override
    public boolean add(FoodItem newFood) {
        
        if (foodAlreadyExists(newFood.getName())){
            return false;
        }
        
        String query = "INSERT INTO " +TABLE_NAME
                + "(name, servingsize, unit, cals, carbs, fat, protein,"
                + "  fiber, sugar)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            
            List<String> newFoodStatsList = newFood.getStatsList();
            
            
            int statementIndex = 1;
            for (String stat : newFoodStatsList){
                ps.setString(statementIndex, stat);
                statementIndex++;
            }
            ps.executeUpdate();
            
            
            return true;
        } catch (SQLException e){
            //System.err.println(e);
            return false;
        }
    }
    
    /*
    FOOD ALREADY EXISTS
    */
    public boolean foodAlreadyExists(String name){
        List<FoodItem> foodItemsList = getListOfFoodsFromName(name);
        return !foodItemsList.isEmpty();
    }
    
    public List<FoodItem> getListOfFoodsFromName(String name){
        String query = "SELECT * FROM " +TABLE_NAME+ ""
                + " WHERE name = ?";
        
        try(Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            List<FoodItem> list = this.getListOfFoodsFromResultSet(rs);
            return list;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    /*
    GET LIST OF FOODS FROM RESULT SET
    */
    public List<FoodItem> getListOfFoodsFromResultSet(ResultSet rs){
        List<FoodItem> list = new ArrayList<>();
        
        try{
            FoodItem food;
            String[] stats = new String[9];
            while (rs.next()){
                stats[0] = rs.getString("name");
                stats[1] = rs.getString("servingsize");
                stats[2] = rs.getString("unit");
                stats[3] = rs.getString("cals");
                stats[4] = rs.getString("carbs");
                stats[5] = rs.getString("fat");
                stats[6] = rs.getString("protein");
                stats[7] = rs.getString("fiber");
                stats[8] = rs.getString("sugar");
                food = new FoodItem(stats);
                list.add(food);
            }
            
            return list;
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
   
    
    /*
    SORT
    */
    public List<FoodItem> sort(String[] params){
        String sortCriteria = params[0];
        String order = params[1].toUpperCase();
        
        String query = "SELECT * FROM " +TABLE_NAME;
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            
            ResultSet rs = ps.executeQuery();
            List<FoodItem> foodItemsList = getListOfFoodsFromResultSet(rs);
            
            if (foodItemsList == null){
                return null;
            }
            
            Stat sortStat = convertStringToStat(sortCriteria);
            
            if (sortStat == null){
                return null;
            }
            
            boolean isAscending = order.equals("ASC");
            
            List<FoodItem> sortedList = this.sorter.sortByStat(foodItemsList, sortStat, isAscending);
            return sortedList;
            
            
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
  
    
    /*
    CONVERT STRING TO STAT
    */
    public Stat convertStringToStat(String statStr){
        try{
            if (statStr.equals("servingsize")){
                statStr = "ss";
            }
            
            Stat stat = Stat.valueOf(statStr.toUpperCase());
            return stat;
        } catch (Exception e){
            System.err.println(e);
            return null;
        }
    }

    /*
    DELETE
    */
    @Override
    public boolean delete(FoodItem t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /*
    DELETE BY NAME
    */
    public boolean deleteByName(String name){
        if (name.equals("name")){
            return false;
        }
        
        String query = "DELETE FROM " +TABLE_NAME
                +" WHERE name = ?";
        
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
            
            List<FoodItem> foodItemsList = getListOfFoodsFromName(name);
            
            if (foodItemsList == null || foodItemsList.isEmpty()){
                return false;
            }
            
            ps.setString(1, name);
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }
    

    @Override
    public boolean update(FoodItem t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    public void printAllFoodsFormatted(List<FoodItem> foodItemsList){
        List<FoodItem> allFoodsList = this.getAll();
        
        System.out.format("%-15s", "Name");
        System.out.format("%-14s", "Serving Size");
        System.out.format("%-10s", "Calories");
        System.out.format("%-9s", "Carbs");
        System.out.format("%-9s", "Fat");
        System.out.format("%-9s", "Protein");
        System.out.format("%-7s", "Fiber");
        System.out.format("%-7s%n", "Sugar");
        System.out.format("-------------------------------------------"
                + "-----------------------------------%n");
        
        for (FoodItem f : foodItemsList){
            System.out.format("%-15s", f.getName());
            System.out.format("%-14s", f.getServingSize()+ " " +f.getUnit());
            System.out.format("%-10s", f.getCalories());
            System.out.format("%-9s", f.getCarbs());
            System.out.format("%-9s", f.getFat());
            System.out.format("%-9s", f.getProtein());
            System.out.format("%-7s", f.getFiber());
            System.out.format("%-7s%n", f.getSugar());
        }
    }

    @Override
    public FoodItem get(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
