/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.cage;

import java.util.ArrayList;
import model.Cage;
import model.Gate;

/**
 *
 * @author Panda
 */
public interface ICageDAO {
    //ArrayList<Cage> getCages();
    ArrayList<Gate> getGates(Cage cage);
    void updateGate(Gate gate);
    Gate getGate(int gateId);
}
