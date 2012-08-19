/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.admin.AdminDAO;
import database.Configuration;
import database.admin.IAdminDAO;

/**
 *
 * @author Panda
 */
public class Zoo {
    IAdminDAO adminDAO = AdminDAO.getInstanceOf();
    //private ArrayList<Cage> cages  = new ArrayList<Cage>();
    public Zoo(){
        
    }
    void setNumOfCages(int numCages){
        // check that the num of cages is between min 1 and max 100
        if (numCages > 0 && numCages <= 100){
            int diff = numCages - adminDAO.getNumOfCages();
            if (diff > 0){
                addCages(diff);
            } else if (diff < 0){
                removeCages(0 - diff);
            } else {
                // THROW EXCEPTION
            }
        } else {
            // THROW EXCEPTION
        }
        
    }
    void addCages(int numCages){
        for (int i = numCages; i > 0; i--){
            adminDAO.addCage();
        }
    }
    void removeCages(int numCages){
        for (int i = numCages; i > 0; i--){
            adminDAO.removeLastCage();
        }
    }
    public static void main(String[] args) {
        //Test for get num of cages
        Configuration.loadConfigurationFromFile();
        Zoo zoo = new Zoo();
        IAdminDAO adminDAO = AdminDAO.getInstanceOf();
        System.out.println(adminDAO.getNumOfCages());
        zoo.setNumOfCages(3);
        System.out.println(adminDAO.getNumOfCages());
        zoo.setNumOfCages(2);
        System.out.println(adminDAO.getNumOfCages());                
    }
}
