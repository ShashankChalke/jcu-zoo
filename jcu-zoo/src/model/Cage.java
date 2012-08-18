/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

import database.admin.AdminDAO;
import database.admin.IAdminDAO;
import database.cage.CageDAO;
import database.cage.ICageDAO;

/**
 *
 * @author Panda
 */
public class Cage {
    static IAdminDAO adminDAO = AdminDAO.getInstanceOf();
    static ICageDAO cageDAO = CageDAO.getInstanceOf();
    private int cageId;
    private String cageName;
    private Float latitude;
    private Float longitude;
    private String cageType;
    private boolean hasHuman;
    private boolean hasAnimal;
    private String exhibitName;
    private String exhibitDesc;
    public static ArrayList<Cage> getCages(){
    	return adminDAO.getCages();
    }
    public static void setNumOfCages(int numCages){
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
    /*public static void addCage(){
    	dao.addCage();
    }*/
    public static void removeCage(Cage cage){
    	adminDAO.removeCage(cage);
    }
    public static void updateCage(Cage cage){
    	adminDAO.updateCage(cage);
    }
    public static int getNumOfCages(){
    	return adminDAO.getNumOfCages();
    }
    public static Cage getCage(int cageId){
    	return adminDAO.getCage(cageId);
    }
    private static void addCages(int numCages){
        for (int i = numCages; i > 0; i--){
            adminDAO.addCage();
        }
    }
    private static void removeCages(int numCages){
        for (int i = numCages; i > 0; i--){
            adminDAO.removeLastCage();
        }
    }
    public Cage(int cageId, String cageName, Float latitude, Float longitude, String cageType, boolean hasHuman, boolean hasAnimal, String exhibitName, String exhibitDesc){
        this.cageId = cageId;
        this.cageName = cageName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cageType = cageType;
        this.hasHuman = hasHuman;
        this.hasAnimal = hasAnimal;
        this.setExhibitName(exhibitName);
        this.setExhibitDesc(exhibitDesc);
    }
    public int getCageId(){
        return cageId;
    }
    public String getCageName() {
        return cageName;
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
    public int getNumOfGates(){
    	return adminDAO.getNumOfGates(this);
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

    public String getCageType() {
        return this.cageType;
    }

    public void setCageType(String cageType) {
        this.cageType = cageType;
    }
    
    
    public String getExhibitName() {
		return exhibitName;
	}
	public void setExhibitName(String exhibitName) {
		this.exhibitName = exhibitName;
	}
	public static void main(String[] args) {
        
    }

    public void addGate() {
        adminDAO.addGateToCage(this);
    }

    public void removeGate() {
        adminDAO.removeGate(this);
    }
    
    @Override
    public String toString(){
        return Integer.toString(this.cageId) + 
                ", " + this.cageName + 
                ", Lat: " + Float.toString(this.latitude) +
                ", Long: " + Float.toString(this.longitude) +
                ", TypeID: " + this.cageType +
                ", hasAnimal: " + Boolean.toString(hasAnimal) +
                ", hasHuman: " + Boolean.toString(hasHuman);
    }
	public String getExhibitDesc() {
		return exhibitDesc;
	}
	public void setExhibitDesc(String exhibitDesc) {
		this.exhibitDesc = exhibitDesc;
	}
	public ArrayList<Gate> getGates(){
		return cageDAO.getGates(this);
	}
    
    
}
