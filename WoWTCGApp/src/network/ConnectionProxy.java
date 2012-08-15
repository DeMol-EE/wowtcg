/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.awt.Point;
import model.CardSnapshot;
import viewControllers.CardPanel;

/**
 *
 * @author Warkst
 */
public class ConnectionProxy {

    private final Connection connection;
    
    public ConnectionProxy(Connection connection) {
	this.connection = connection;
    }
    
    public void sendTextMessage(String content){
	connection.sendMessage(MessageFactory.createTextMessage(content));
    }
    
    public void sendGUIMessage(CardPanel cardPanel){
	connection.sendMessage(MessageFactory.createGUIMessage(cardPanel));
    }
    
    public void moveCardWithGUID(String cardGUID, Point location){
	connection.sendMessage(MessageFactory.createMoveCardMessage(cardGUID, location));
    }
    
    public void sendSnapshot(String cardGUID, CardSnapshot snapshot){
	connection.sendMessage(MessageFactory.createSnapshotMessage(cardGUID, snapshot));
    }
}
