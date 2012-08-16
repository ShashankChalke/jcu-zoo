/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.admin;

import java.util.ArrayList;
import model.Cage;
import model.Gate;

/**
 *
 * @author Panda
 */
public interface IAdminDAO {
    int getNumOfCages();
    void addCage();
    //void addCage(Cage cage);
    void removeLastCage();
    void removeCage(Cage cage); //remove a specific cage
    void updateCage(Cage cage);
    ArrayList<Cage> getCages();
    Cage getCage(int cageId); 
    //Gate createGate();
    Gate getGate(int gateId);
    void updateGate(Gate gate);
    void addGateToCage(Cage cage);
    void removeGate(Cage cage);
    Integer getNumOfGates(Cage cage);

}
