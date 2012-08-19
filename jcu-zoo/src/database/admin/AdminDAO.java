/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.admin;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import database.Configuration;
import database.MySQLConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cage;
import model.Gate;

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
        this.query = null;
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
        try {
            connect();
            query = "DELETE FROM CAGE WHERE CAGE_ID=?";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cage.getCageId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }   
    }

    @Override
    public void updateCage(Cage cage) {
        try {
            connect();
            query = "UPDATE CAGE SET CAGE_NAME=?, LATITUDE=?, LONGITUDE=?, CAGE_TYPE=?, HAS_HUMAN=?, HAS_ANIMAL=?, EXHIBIT_NAME=?, EXHIBIT_DESCRIPTION=? WHERE CAGE_ID=?";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setString(1,cage.getCageName());
            preparedStatement.setFloat(2,cage.getLatitude());
            preparedStatement.setFloat(3, cage.getLongitude());
            preparedStatement.setString(4,cage.getCageType());
            preparedStatement.setBoolean(5, cage.hasHuman());
            preparedStatement.setBoolean(6, cage.hasAnimal());
            preparedStatement.setString(7,cage.getExhibitName());
            preparedStatement.setString(8,cage.getExhibitDesc());
            preparedStatement.setInt(9, cage.getCageId());
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
                        resultSet.getString("CAGE_NAME"),
                        resultSet.getFloat("LATITUDE"),
                        resultSet.getFloat("LONGITUDE"),
                        resultSet.getString("CAGE_TYPE"),
                        resultSet.getBoolean("HAS_HUMAN"),
                        resultSet.getBoolean("HAS_ANIMAL"),
                        resultSet.getString("EXHIBIT_NAME"),
                        resultSet.getString("EXHIBIT_DESCRIPTION")));
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
         return cages;
    }
    @Override
    public Cage getCage(int cageId) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Cage cage = null;
         try {
            connect();
            query = "SELECT * FROM CAGE WHERE CAGE_ID=?";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cageId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cage = new Cage(resultSet.getInt("CAGE_ID"),
                        resultSet.getString("CAGE_NAME"),
                        resultSet.getFloat("LATITUDE"),
                        resultSet.getFloat("LONGITUDE"),
                        resultSet.getString("CAGE_TYPE"),
                        resultSet.getBoolean("HAS_HUMAN"),
                        resultSet.getBoolean("HAS_ANIMAL"),
                        resultSet.getString("EXHIBIT_NAME"),
                        resultSet.getString("EXHIBIT_DESCRIPTION"));
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
         return cage;
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
        ArrayList<Cage> cages = adminDAO1.getCages(); 
        for (Cage c : cages){
        	System.out.println(c.toString());
        	System.out.println(adminDAO1.getCage(c.getCageId()).toString());
        	c.addGate();
        }
        
    }


    private Gate createGate() {
        int gateKey = -1;
        try {
            connect();
            query = "INSERT INTO GATE() VALUES ()";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                gateKey = resultSet.getInt(1);
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        System.out.println(gateKey);
        return getGate(gateKey);
    }

    @Override
    public void addGateToCage(Cage cage) {
    	Gate gate = createGate();
        try {
            connect();
            query = "INSERT INTO CAGE_GATES(CAGE_ID,GATE_ID) VALUES (?,?)";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cage.getCageId());
            preparedStatement.setInt(2, gate.getGateId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
    }

    @Override
    public Gate getGate(int gateId) {
        Gate gate = null;
        try {
            connect();
            query = "SELECT * FROM GATE WHERE GATE_ID=?";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1,gateId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                gate = new Gate(resultSet.getInt("GATE_ID"),resultSet.getBoolean("IS_CLOSED"),resultSet.getBoolean("IS_LOCKED"));
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        return gate;
    }

    @Override
    public void updateGate(Gate gate) {
        try {
            connect();
            query = "UPDATE GATE SET IS_CLOSED=?, IS_LOCKED=? WHERE GATE_ID=?";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setBoolean(1, gate.isClosed());
            preparedStatement.setBoolean(2, gate.isLocked());
            preparedStatement.setInt(3, gate.getGateId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
    }

    @Override
    public void removeGate(Cage cage) {
        try {
            connect();
            
            query = "DELETE FROM CAGE_GATES WHERE CAGE_ID=? LIMIT 1";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cage.getCageId());
            preparedStatement.executeUpdate();
            
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        removeUnusedGates();
    }
    
    private void removeUnusedGates() {
        try {
            connect();
            
            query = "DELETE FROM GATE WHERE GATE_ID NOT IN (SELECT GATE_ID FROM CAGE_GATES)";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
    }    
	@Override
	public Integer getNumOfGates(Cage cage) {
        Integer numOfGates = null;
        try {
            connect();
            query = "SELECT COUNT(*) FROM GATE, CAGE_GATES WHERE GATE.GATE_ID=CAGE_GATES.GATE_ID AND CAGE_GATES.CAGE_ID=?";        
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cage.getCageId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                numOfGates = resultSet.getInt(1);
            }
        } catch (Exception ex){
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        return numOfGates;
	}
	

}
