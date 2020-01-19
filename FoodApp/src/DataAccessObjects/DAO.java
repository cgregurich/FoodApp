/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessObjects;
import java.util.List;

/**
 *
 * @author colin
 * @param <T>
 */
public interface DAO<T> {
    
    T get(String name);
    List<T> getAll();
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
    
    
}
