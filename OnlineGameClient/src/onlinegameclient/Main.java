/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onlinegameclient;

import gui.GUIMaster;
import network.NetworkMaster;

/**
 *
 * @author Warkst
 */
public class Main {

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
	final NetworkMaster networkMaster = new NetworkMaster();
        final GUIMaster guiMaster = new GUIMaster(networkMaster);
	
	// show log in screen
	guiMaster.displayLogInViewController();
    }
}
