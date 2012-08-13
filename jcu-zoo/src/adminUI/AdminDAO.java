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
        try {
            connect();
            query = "UPDATE CAGE SET CAGE_NAME=?, LATITUDE=?, LONGITUDE=?, TYPE_ID=?, HAS_HUMAN=?, HAS_ANIMAL=? WHERE CAGE_ID=?";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setString(1,cage.getCageName());
            preparedStatement.setFloat(2,cage.getLatitude());
            preparedStatement.setFloat(3, cage.getLongitude());
            preparedStatement.setInt(4,cage.getTypeId());
            preparedStatement.setBoolean(5, cage.hasHuman());
            preparedStatement.setBoolean(6, cage.hasAnimal());
            preparedStatement.setInt(7, cage.getCageId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
    }

    @Override
    public void addCage() {
        try {
            connect();
            query = "INSERT INTO CAGE(HAS_HUMAN,HAS_ANIMAL) VALUES (False,False)";        
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
        //throw new UnsupportedOperationException("Not supported yet.");
        ArrayList<Cage> cages = new ArrayList<Cage>();
         try {
            connect();
            query = "SELECT * FROM CAGE";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cages.add(new Cage(resultSet.getInt("CAGE_ID"),
                        (String) resultSet.getString("CAGE_NAME"),
                        (Float) resultSet.getFloat("LATITUDE"),
                        (Float) resultSet.getFloat("LONGITUDE"),
                        (Integer) resultSet.getInt("TYPE_ID"),
                        (Boolean) resultSet.getBoolean("HAS_HUMAN"),
                        (Boolean) resultSet.getBoolean("HAS_ANIMAL")));
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
         return cages;
    }
    public static void main(String[] args) {
        Configuration.loadConfigurationFromFile();
        IAdminDAO adminDAO1 = AdminDAO.getInstanceOf();
        System.out.println(adminDAO1.getNumOfCages());
        adminDAO1.addCage();
        adminDAO1.addCage();
        System.out.println(adminDAO.getNumOfCages());
        adminDAO1.removeLastCage();
        System.out.println(adminDAO1.getNumOfCages());
        System.out.println(adminDAO1.getCages().toString());
    }

}
