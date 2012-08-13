/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminUI;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cage;

/**
 *
 * @author Panda
 */
public class AdminDAO implements IAdminDAO{
    String query = null;
    Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private static AdminDAO adminDAO = null;
    private AdminDAO(){}
    public static AdminDAO getInstanceOf(){
        if (adminDAO == null){
            adminDAO = new AdminDAO();
        }
        return adminDAO;
    }
    private void connect(){
        try {
            conn = MySQLConnection.connectDB();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cleanUp(){        
                closeResultSet();
                closePreparedStatement();
                closeConnection();
    }
    private void closeResultSet(){
        try {
                if (resultSet != null){
                    resultSet.close();
                    resultSet = null;
                }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closePreparedStatement(){
        try {
                if (preparedStatement != null){
                    preparedStatement.close();
                    preparedStatement = null;
                }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closeConnection(){
        try {
            if (conn != null){
                conn.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Override
    public int getNumOfCages() {
        Integer numOfCages = null;
        try {
            connect();
            query = "SELECT COUNT(*) FROM CAGE";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                numOfCages = resultSet.getInt(1);
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        return numOfCages;
    }

    @Override
    public void removeCage(Cage cage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateCage(Cage cage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCage() {
        try {
            connect();
            query = "INSERT INTO CAGE() VALUES ()";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
    }

    @Override
    public void removeLastCage() {
        try {
            connect();
            query = "DELETE FROM CAGE ORDER BY CAGE_ID DESC LIMIT 1";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
    }

    @Override
    public ArrayList<Cage> getCages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void main(String[] args) {
        Configuration.loadConfigurationFromFile();
        IAdminDAO adminDAO1 = AdminDAO.getInstanceOf();
        System.out.println(adminDAO1.getNumOfCages());
        adminDAO1.addCage();
        System.out.println(adminDAO.getNumOfCages());
        adminDAO1.removeLastCage();
        System.out.println(adminDAO1.getNumOfCages());
    }

}
