/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JPanel;
import network.MessageHandler;

/**
 *
 * @author Warkst
 */
public interface ViewController extends MessageHandler {
    public JPanel getView();
    public void electFirstResponder();
}
