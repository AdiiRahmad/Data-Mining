/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RIDWAN
 */
public class ConnectionSQLite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();  
    }
 
    public static void connect(){
        
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:db\\jajaj.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been estabilished");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    
}
    
    

