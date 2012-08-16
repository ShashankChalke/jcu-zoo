/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

import database.admin.AdminDAO;
import database.admin.IAdminDAO;

/**
 *
 * @author Panda
 */
public class Cage {
    static IAdminDAO dao = AdminDAO.getInstanceOf();
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
    	return dao.getCages();
    }
    public static int getNumOfCages(){
    	return dao.getNumOfCages();
    }
    public static Cage getCage(int cageId){
    	return dao.getCage(cageId);
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
    	return dao.getNumOfGates(this);
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
        dao.addGateToCage(this);
    }

    public void removeGate() {
        dao.removeGate(this);
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
    
    
}
