/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import model.CharacterInfo;
import network.Message;
import network.NetworkMaster;

/**
 *
 * @author Warkst
 */
public class GUIMaster implements LogInVCDelegate, CharacterSelectVCDelegate, LoadingVCDelegate {
    private NetworkMaster networkMaster;
    private GameFrameController gameFrame;

    private String activeUser;
    private CharacterInfo activeCharacter;
    
    private LoadingViewController loadingViewController;

    public GUIMaster(NetworkMaster networkMaster){
	this.networkMaster = networkMaster;
    }
    
    public void displayLogInViewController(){
	final GUIMaster master = this;
	new LogInViewController(master).setVisible(true);
    }

    // delegate for loginvc
    public void loginSuccess(final HashMap<String, CharacterInfo> characters, String loggedInClientTag) {
	activeUser = loggedInClientTag;

	final GUIMaster master = this;
	final CharacterSelectViewController csvc = new CharacterSelectViewController(master, characters);
	
	// run the gameFrame in the EDT
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		// new frame with view controller
		gameFrame = new GameFrameController(master, csvc);
		gameFrame.setVisible(true);
	    }
	});
    }

    public NetworkMaster getNetworkMaster() {
	return networkMaster;
    }

    public CharacterInfo getActiveCharacter() {
	return activeCharacter;
    }

    public String getActiveUser() {
	return activeUser;
    }

    public GameFrameController getGameFrame() {
	return gameFrame;
    }
    
    public void showEnterWorldLoading(){
	
    }
    
    public void willEnterWorld(){
	loadingViewController = new LoadingViewController();
	gameFrame.setViewController(loadingViewController);
    }

    public void didEnterWorld(CharacterInfo characterEnteringWorld, ArrayList<String> welcomeMessages) {
	this.activeCharacter = characterEnteringWorld;
	AreaViewController wvc = new AreaViewController(this);
	for (String welcomeMessage : welcomeMessages) wvc.appendMessageToChat(welcomeMessage);
	gameFrame.setViewController(wvc);
	
	// send hi message (you are now in ..., all players are [...])
	Message welcome = new Message(Message.MSGTYPE_WHEREAMI);
	dispatchUplinkMessage(welcome);
    }

    public void shutdown(){
	// shut down the network connections
	networkMaster.shutdown();
	// hide the gui by disposing the frame
	gameFrame.shutdown();
    }

    public void dispatchUplinkMessage(Message uplinkMessage){
	networkMaster.dispatchMessage(uplinkMessage);
    }

    public void loadingProgress(int n) {
	loadingViewController.setProgress(n);
    }
}
