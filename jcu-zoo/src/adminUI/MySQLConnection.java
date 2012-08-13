/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminUI;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Panda
 */
public class MySQLConnection { 
    
     public static Connection connectDB() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://" + Configuration.hostname + "/" + Configuration.databaseName;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = (Connection) DriverManager.getConnection(url, Configuration.dbUser, Configuration.dbPassword);

            System.out.println("Database connection established");
            return connection;
        } catch (Exception ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     public static void main(String[] args) {
        Configuration.loadConfigurationFromFile();
        Connection conn = MySQLConnection.connectDB();
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     //private static void disconnectDB();
}
