/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.cage;

import com.mysql.jdbc.Connection;

import com.mysql.jdbc.PreparedStatement;
import database.MySQLConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cage;
import model.Gate;

/**
 *
 * @author Panda
 */
public class CageDAO implements ICageDAO{
    String query = null;
    Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private static CageDAO cageDAO = null;
    private CageDAO(){}
    public static CageDAO getInstanceOf(){
        if (cageDAO == null){
            cageDAO = new CageDAO();
        }
        return cageDAO;
    }
    private void connect(){
        try {
            conn = MySQLConnection.connectDB();
        } catch (Exception ex) {
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closePreparedStatement(){
        try {
                if (preparedStatement != null){
                    preparedStatement.close();
                    preparedStatement = null;
                }
        } catch (Exception ex){
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closeConnection(){
        try {
            if (conn != null){
                conn.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }        
         return cages;
    }

    @Override
    public ArrayList<Gate> getGates(Cage cage) {
        ArrayList<Gate> gates = new ArrayList<Gate>();
        try {
            connect();
            query = "SELECT * FROM GATES WHERE CAGE_ID=?";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setInt(1, cage.getCageId());
            resultSet = preparedStatement.executeQuery();                        
            while (resultSet.next()){
                gates.add(new Gate(resultSet.getInt("CAGE_ID"),resultSet.getBoolean("IS_CLOSED"),resultSet.getBoolean("IS_LOCKED")));
            }
                    
        } catch (Exception ex){
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
        return gates;
    }

    @Override
    public void updateGate(Gate gate) {
        try {
            query = "UPDATE GATE SET IS_CLOSED=?, IS_LOCKED=? WHERE GATE_ID=?";
            preparedStatement = (PreparedStatement) conn.prepareStatement(query);
            preparedStatement.setBoolean(1, gate.isClosed());
            preparedStatement.setBoolean(2, gate.isLocked());
            preparedStatement.setInt(3,gate.getGateId());
            preparedStatement.executeUpdate();
            
        } catch (Exception ex){
            Logger.getLogger(CageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cleanUp();
        }
    }
    
}
