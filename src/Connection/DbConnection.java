/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author RIDWAN
 */
public class DbConnection {
    
    private static Connection conn = null;
    private static String url = null;
    
    
    public Connection connect() throws SQLException {
        
        url = "jdbc:sqlite:db/smaarif.db";
        conn = DriverManager.getConnection(url);
        try {
            if (conn == null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return conn;
            }
}
