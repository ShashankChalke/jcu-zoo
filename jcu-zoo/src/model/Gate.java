/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Panda
 */
public class Gate {
    int gateId;
    boolean isLocked;
    boolean isClosed;
    public Gate(int gateId,boolean isClosed, boolean isLocked){
        this.gateId = gateId;
        this.isClosed = isClosed;
        this.isLocked = isLocked;
    }
    public int getGateId(){
        return this.gateId;
    }
    void open(){
        this.isClosed = false;
    }
    void close(){
        this.isClosed = true;
    }
    void lock(){
        this.isLocked = true;
    }
    void unlock(){
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
