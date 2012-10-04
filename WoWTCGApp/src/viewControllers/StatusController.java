/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Robin jr
 */
public class StatusController extends Thread{

    private final LinkedBlockingQueue<String> messages;
    private final JLabel statusOutlet;
    private final int decrease = 15;
    
    public StatusController(JLabel statusOutlet){
	this.statusOutlet = statusOutlet;
	
	this.messages = new LinkedBlockingQueue<String>();
    }
    
    /**
     * Create a new StatusController object and start its Thread. This object should be accessed through the enqueueMessage
     * method, which will enqueue a message for display on the given JLabel. The message will remain visible for at least 1500 ms.
     * 
     * @param statusOutlet The JLabel to be used for displaying the messages.
     * @return Returns a started Thread ready to handle enqueued messages.
     */
    public static StatusController createAndStart(JLabel statusOutlet){
	StatusController sc = new StatusController(statusOutlet);
	sc.start();
	return sc;
    }
    
    @Override
    public void run() {
	try{
	    while(true){
		String message = messages.take();

		do{
		    try {
			SwingUtilities.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
				Color fore = statusOutlet.getForeground();
				int newAlpha = Math.max(0, fore.getAlpha()-decrease);
				statusOutlet.setForeground(new Color(fore.getRed(), fore.getGreen(), fore.getBlue(), newAlpha));
				try {
				    Thread.sleep(50);
				} catch (InterruptedException ex) {
				    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
				}
    //				System.out.println("Lowered alpha to "+newAlpha);
			    }
			});
		    } catch (InvocationTargetException ex) {
			Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		} while(statusOutlet.getForeground().getAlpha()>0);

		statusOutlet.setText(message);
    //		System.out.println("Set new status message");

		do{
		    try {
			SwingUtilities.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
				Color fore = statusOutlet.getForeground();
				int newAlpha = Math.min(255, fore.getAlpha()+decrease);
				statusOutlet.setForeground(new Color(fore.getRed(), fore.getGreen(), fore.getBlue(), newAlpha));
				try {
				    Thread.sleep(50);
				} catch (InterruptedException ex) {
				    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
				}
    //				System.out.println("Increased alpha to "+newAlpha);
			    }
			});
		    } catch (InvocationTargetException ex) {
			Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		} while(statusOutlet.getForeground().getAlpha()<255);
		
		
		// leave visible for at least 1.5 seconds
		Thread.sleep(1500);
	    }
	} catch (InterruptedException ex){
	    return;
	}
    }
    
    /**
     * Enqueue a message to be displayed. Will remain visible for at least 1500 ms.
     * 
     * @param m 
     */
    public void enqueueMessage(String m){
	try {
	    messages.put(m);
	} catch (InterruptedException ex) {
	    Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
