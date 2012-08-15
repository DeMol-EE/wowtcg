/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Warkst
 */
public class Message implements Serializable {
    // msg type
    // gui update type
    // action type
    public static final int TEXT = 0;
    public static final int GUI = 1;
    public static final int MOVE_ACTION = 2;
    public static final int SNAPSHOT_ACTION = 3;
    
    private int type;
    private HashMap<String, Object> args;

    public Message(int type, HashMap<String, Object> args) {
	this.type = type;
	this.args = args;
    }
    
    public HashMap<String, Object> getArgs() {
	return args;
    }

    public int getType() {
	return type;
    }
}
