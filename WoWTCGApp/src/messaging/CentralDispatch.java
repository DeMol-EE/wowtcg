/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Robin jr
 */
public class CentralDispatch extends Thread{
    private boolean stopped = false;

    private final LinkedBlockingQueue<Notification> notifications = new LinkedBlockingQueue<Notification>();
    
    @Override
    public void run() {
	while(!stopped){
	    try {
		Notification n = notifications.take();
		
		// do something with the notification
		
	    } catch (InterruptedException ex) {
		System.out.println("CD::Shutting down Central Dispatch...");
		return;
	    }
	}
    }
    
    public void shutdown(){
	stopped = true;
    }
}
