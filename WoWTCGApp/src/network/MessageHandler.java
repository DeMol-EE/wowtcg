/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.awt.Point;
import model.CardSnapshot;
import viewControllers.CardPanel;

/**
 * Provides interface for the message dispatcher to call
 *
 * @author Warkst
 */
public interface MessageHandler {
    public void receiveTextMessage(String msg);
    public void receiveForeignCard(CardPanel panel);
    public void moveCardWithGUIDToLocation(String GUID, Point location);
    public void applySnapshotToCardWithGUID(String GUID, CardSnapshot snapshot);
}
