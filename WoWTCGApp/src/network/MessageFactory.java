/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.awt.Point;
import java.util.HashMap;
import model.CardSnapshot;
import viewControllers.CardPanel;

/**
 *
 * @author Warkst
 */
public class MessageFactory {
    public static Message createTextMessage(String content){
	HashMap<String, Object> args = new HashMap<String, Object>();
	args.put("msg", content);
	return new Message(Message.TEXT, args);
    }
    
    public static Message createGUIMessage(CardPanel panel){
	HashMap<String, Object> args = new HashMap<String, Object>();
	args.put("panel", panel);
	return new Message(Message.GUI, args);
    }
    
    public static Message createMoveCardMessage(String cardGUID, Point location){
	HashMap<String, Object> args = new HashMap<String, Object>();
	args.put("GUID", cardGUID);
	args.put("location", location);
	return new Message(Message.MOVE_ACTION, args);
    }
    
    public static Message createSnapshotMessage(String cardGUID, CardSnapshot snapshot){
	HashMap<String, Object> args = new HashMap<String, Object>();
	args.put("GUID", cardGUID);
	args.put("snapshot", snapshot);
	return new Message(Message.SNAPSHOT_ACTION, args);
    }
}
