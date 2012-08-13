package adminUI;

/**
 *
 * @author Panda
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    public static String hostname;
    public static String databaseName;
    public static String dbUser;
    public static String dbPassword;

    public static void loadConfigurationFromCode() {
        System.out.println("Loading configuration settings from code...");
        
        Configuration.hostname = "localhost";
        Configuration.databaseName = "jcu-zoo";
        Configuration.dbUser = "test";
        Configuration.dbPassword = "test";
        System.out.println("Configuration settings loaded successfully.");
    }

   public static void loadConfigurationFromFile(){
       Properties prop = new Properties();
            try {
                prop.load(new FileInputStream("config.properties"));
                
                Configuration.hostname = prop.getProperty("hostname");
                Configuration.databaseName = prop.getProperty("database");
                Configuration.dbUser = prop.getProperty("dbuser");
                Configuration.dbPassword = prop.getProperty("dbpassword");
                System.out.println("Settings loaded from 'config.properties'");
            } catch (Exception ex) {
                System.out.println("Unable to read properties from 'config.properties'");
                loadConfigurationFromCode();
                writeConfiguration();
                
            }
        }
   
    public static void writeConfiguration() {
        System.out.println("Attempting to store current properties...");
        Properties prop = new Properties();

        try {
            //set the properties value for DatabaseWriter
            prop.setProperty("hostname", Configuration.hostname);
            prop.setProperty("database", Configuration.databaseName);
            prop.setProperty("dbuser", Configuration.dbUser);
            prop.setProperty("dbpassword", Configuration.dbPassword);

            //save properties to project root folder
            prop.store(new FileOutputStream("config.properties"), null);
            System.out.println("Successfully stored the configuration file.");
        } catch (IOException ex) {
            System.out.println("Failed to store the configuration file.");
        }
    }

    public static void main(String[] args) {
        Configuration config = new Configuration();
        Configuration.loadConfigurationFromCode();
        Configuration.writeConfiguration();
    }
}