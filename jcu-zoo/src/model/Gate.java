/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.cage.CageDAO;
import database.cage.ICageDAO;

/**
 *
 * @author Panda
 */
public class Gate {
	static ICageDAO cageDAO = CageDAO.getInstanceOf();
    int gateId;
    boolean isLocked;
    boolean isClosed;
    public Gate(int gateId,boolean isClosed, boolean isLocked){
        this.gateId = gateId;
        this.isClosed = isClosed;
        this.isLocked = isLocked;
    }
    public void update(){
    	cageDAO.updateGate(this);
    }
    public static Gate getGate(int gateId){
    	return cageDAO.getGate(gateId); 
    }
    public int getGateId(){
        return this.gateId;
    }
    public void open(){
        this.isClosed = false;
    }
    public void close(){
        this.isClosed = true;
    }
    public void lock(){
        this.isLocked = true;
    }
    public void unlock(){
        this.isLocked = false;
    }
    
    public boolean isLocked(){
        return this.isLocked;
    }
    public boolean isClosed(){
        return this.isClosed;
    }
    @Override
    public String toString(){
        return Integer.toString(gateId) + ", isClosed: " + Boolean.toString(isClosed) + ", isLocked: " + Boolean.toString(isLocked);
    }
}
