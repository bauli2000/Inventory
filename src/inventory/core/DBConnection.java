/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.core;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kind
 */
public class DBConnection {
    private static Connection read = null;
    private static Connection write = null;
    
    private DBConnection(){}
    
    public static Connection getReadConnection(){
        return read;
    }
    public static Connection getWriteConnection(){
        return write;
    }
    public static boolean setReadConnection(String userName, String password, String ip) {
        try {
            String readIP = ip;
            read = null;
            read = DriverManager.getConnection("jdbc:mysql://"+readIP,userName, password);
            System.out.println("Read Database Connection Success");
            return true;
        } catch (SQLException sqex) {
            System.out.println("SQLException: " + sqex.getMessage());
            System.out.println("SQLState: " + sqex.getSQLState());
        }
        return false;
    }
    /*
            
     */
    
    public static boolean setWriteConnection(String userName, String password, String ip) {
        try {
            String writeIP = ip;
            write = null;
            write = DriverManager.getConnection("jdbc:mysql://"+writeIP,userName, password);
            System.out.println("Write Database Connection Success");
            return true;
        } catch (SQLException sqex) {
            System.out.println("SQLException: " + sqex.getMessage());
            System.out.println("SQLState: " + sqex.getSQLState());
        }
        return false;
    }
    
    public static ResultSet updateQueryGetID(String sql){
        if(write != null){
            try {
                PreparedStatement ps = write.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                
                ps.executeUpdate();
                
                return ps.getGeneratedKeys();
            } catch (SQLException ex) {
                /*
                    todo 
                    * Handle Error
             
                */
                ex.printStackTrace();
                System.out.println("SQL Update Exception in Update Query");
                System.out.println(sql);
                return null;
            }
        }else{
            /*
             todo Error Message
             
             */
            System.out.println("Connection Error in Update Query");
            return null;
        }
    }
    
    public static void updateQuery(String sql){
        if(write != null){
            try {
                Statement s = write.createStatement();
                
                s.executeUpdate(sql);
            } catch (SQLException ex) {
                /*
                    todo 
                    * Handle Error
             
                */
                ex.printStackTrace();
                System.out.println("SQL Update Exception in Update Query");
                System.out.println(sql);
            }
        }else{
            /*
             todo Error Message
             
             */
            System.out.println("Connection Error in Update Query");
        }
    }
    
    public static ResultSet executeQuery(String sql){
        ResultSet rs = null;
        if(read != null){
            try {
                Statement s = null;
                s = read.createStatement();
                
                rs = s.executeQuery(sql);
            } catch (SQLException ex) {
                /*
                    todo 
                    * Handle Error
             
                */
                System.out.println("SQL Excute Exception in Excute Query");
                System.out.println(sql);
                return rs = null;
            }
        }else{
            /*
             todo Error Message
             
             */
            
            System.out.println("Connection Error in Excute Query");
            return rs = null;
        }
        return rs;
    }
}
