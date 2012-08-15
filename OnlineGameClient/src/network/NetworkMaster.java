/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Warkst
 */
public class NetworkMaster implements ConnectionStarter{

    private CharacterConnectionClient characterConnectionClient; 
    private CharacterConnectionKeeper characterConnectionKeeper;

    public NetworkMaster() {
    }

    /**
     * Start a bidirectional connection with the server through setting up 2 parallel connection threads:
     * - uplink (MessageDispatcher)
     * - downlink (MessageGenerator)
     * This method will trigger a callback method on the specified UplinkHandler to monitor the connection (<code>registerUplinkConnection(UplinkDispatcher uplink)</code>)
     * 
     * @param setup The message to be sent to the server to set up the connection. <b>Must</b> contain client ip and port of the running downlink server
     * @param messageHandlerProvider The provider of message handlers that can handle incoming messages
     * @param downlinkPort The port of the running downlink server <i>ONLY FOR TESTING, RUNNING MULTIPLE CLIENTS ON 1 MACHINE</i>
     */

    public void startConnection(ConnectionMessage setup, MessageHandler handler, int downlinkPort) {
	this.characterConnectionKeeper = new CharacterConnectionKeeper();
	
	Semaphore synchronizer = new Semaphore(0);
	
	characterConnectionClient = new CharacterConnectionClient(synchronizer, setup, handler, characterConnectionKeeper, downlinkPort);
	characterConnectionClient.start();
	
	try {
	    synchronizer.acquire();
	} catch (InterruptedException ex) {
	    System.err.println("Interrupted while waiting for characterconnectionclient to start:\n"+ex);
	}
    }
    
    public void startConnection(ConnectionMessage setup, MessageHandlerProvider handlerProvider, String connectionTag, int downlinkPort){
	this.characterConnectionKeeper = new CharacterConnectionKeeper();
	
	Semaphore synchronizer = new Semaphore(0);
	
	characterConnectionClient = new CharacterConnectionClient(synchronizer, setup, handlerProvider, connectionTag, characterConnectionKeeper, downlinkPort);
	characterConnectionClient.start();
	
	try {
	    synchronizer.acquire();
	} catch (InterruptedException ex) {
	    System.err.println("Interrupted while waiting for characterconnectionclient to start:\n"+ex);
	}
    }

    public void shutdown(){
	if(characterConnectionKeeper!=null) characterConnectionKeeper.closeConnection();
    }
    
    public void dispatchMessage(Message msg){
	if(characterConnectionKeeper!=null) characterConnectionKeeper.dispatchMessage(msg);
    }
}
