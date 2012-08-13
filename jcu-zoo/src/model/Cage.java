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
public class Cage {
    private ArrayList<Gate> gates = new ArrayList<Gate>();
    private int cageId;
    private String cageName;
    private int cageLocation;
    private int cageDimensions;
    private String cageType;
    private boolean hasHuman;
    private boolean hasAnimal;
    public void setNumOfDoors(int numGates){
        int diff = numGates - this.gates.size();
         if (diff > 0){
            addGates(diff);
        } else if (diff < 0){
            removeGates(0 - diff);
        }
    }
    
    public static void main(String[] args) {
        
    }

    private void addGates(int numGates) {
        for (int i = numGates; i > 0; i--){
            this.gates.add(new Gate());
        }
    }

    private void removeGates(int numGates) {
        for (int i = numGates; i > 0; i--){
            this.gates.add(new Gate());
        }
    }
    
    
}
