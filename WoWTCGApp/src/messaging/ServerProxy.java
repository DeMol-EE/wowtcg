/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import java.util.concurrent.LinkedBlockingQueue;
import network.Message;

/**
 * Class to serve as a proxy for the external server. Sends messages to the server under the hood but is externally
 * reachable through "normal" yet actual stubs for functions.
 *
 * @author Robin jr
 */
public class ServerProxy extends Thread {
    private boolean stopped = false;
    
    private final LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();

    @Override
    public void run() {
	while(!stopped){
	    try {
		Message m = messages.take();
		
		// send the message to the server
		
	    } catch (InterruptedException ex) {
		System.out.println("SP::Shutting down the server proxy...");
		return;
	    }
	}
    }
    
    public void shutdown(){
	stopped = true;
	// send interrupt to this object to force shut down?
    }
   
    /*
     * expose functionality through stubs
     */
    
    
}
