/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminUI;

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

}
