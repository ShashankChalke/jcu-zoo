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
    private Float latitude;
    private Float longitude;
    private Integer typeId;
    private boolean hasHuman;
    private boolean hasAnimal;
    public Cage(int cageId, String cageName, Float latitude, Float longitude, Integer typeId, boolean hasHuman, boolean hasAnimal){
        this.cageId = cageId;
        this.cageName = cageName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeId = typeId;
        this.hasHuman = hasHuman;
        this.hasAnimal = hasAnimal;
    }
    public int getCageId(){
        return cageId;
    }
    public String getCageName() {
        return cageName;
    }

    public void setCageName(String cageName) {
        this.cageName = cageName;
    }

    public boolean hasAnimal() {
        return hasAnimal;
    }

    public void hasAnimal(boolean hasAnimal) {
        this.hasAnimal = hasAnimal;
    }

    public boolean hasHuman() {
        return hasHuman;
    }

    public void hasHuman(boolean hasHuman) {
        this.hasHuman = hasHuman;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
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
    
    @Override
    public String toString(){
        return Integer.toString(this.cageId) + 
                ", " + this.cageName + 
                ", Lat: " + Float.toString(this.latitude) +
                ", Long: " + Float.toString(this.longitude) +
                ", TypeID: " + Integer.toString(this.typeId) +
                ", hasAnimal: " + Boolean.toString(hasAnimal) +
                ", hasHuman: " + Boolean.toString(hasHuman);
    }
    
    
}
