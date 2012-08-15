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
public class MessageDispatcher {
    private final MessageHandler handler;

    public MessageDispatcher(MessageHandler handler) {
	this.handler = handler;
    }
    
    /**
     * Dispatch method
     */
    public void dispatchMessage(Message msg){
	int type = msg.getType();
	HashMap<String, Object> args = msg.getArgs();
	
	switch(type){
	    case Message.TEXT: 
		String content = (String)args.get("msg");
		handler.receiveTextMessage(content);
		break;
	    case Message.GUI:
		CardPanel panel = (CardPanel)args.get("panel");
		handler.receiveForeignCard(panel);
		break;
	    case Message.MOVE_ACTION:
		String GUID = (String)args.get("GUID");
		Point loc = (Point)args.get("location");
		handler.moveCardWithGUIDToLocation(GUID, loc);
		break;
	    case Message.SNAPSHOT_ACTION:
		String GUID2 = (String)args.get("GUID");
		CardSnapshot snap = (CardSnapshot)args.get("snapshot");
		handler.applySnapshotToCardWithGUID(GUID2, snap);
		break;
	}
    }
}
