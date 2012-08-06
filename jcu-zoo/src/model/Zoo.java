/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Panda
 */
public class Zoo {
    private ArrayList<Cage> cages  = new ArrayList<Cage>();
    public Zoo(){
        
    }
    void setNumOfCages(int numCages){
        // check that the num of cages is between min 1 and max 100
        if (numCages > 0 && numCages <= 100){
            int diff = numCages - this.cages.size();
            if (diff > 0){
                addCages(diff);
            } else if (diff < 0){
                removeCages(0 - diff);
            } else {
                // THROW EXCEPTION
            }
                    
            //this.cages.size(); if greater than existing number add cage else remove cages
        } else {
            // THROW EXCEPTION
        }
        
    }
    void addCages(int numCages){
        for (int i = numCages; i > 0; i--){
            this.cages.add(new Cage());
        }
    }
    void removeCages(int numCages){
        for (int i = numCages; i > 0; i--){
            this.cages.remove(cages.size()-1);
        }
    }
    ArrayList<Cage> getCages(){        
        return this.cages;
    }    
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        ArrayList<Cage> cages = zoo.getCages();
        System.out.println(cages.size());
        zoo.setNumOfCages(3);
        cages = zoo.getCages();
        System.out.println(cages.size());
        zoo.setNumOfCages(2);
        cages = zoo.getCages();
        System.out.println(cages.size());                
    }
}
