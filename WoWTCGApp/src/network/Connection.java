/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import viewControllers.menu.MainFrame;

/**
 * Make this as a thread to allow it to block on send without making the UI hang
 *
 * @author Warkst
 */
public class Connection extends Thread {
    
    // serves as a message handler
    private final MainFrame delegate;
    private final Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ConnReader reader;
    
    private MessageDispatcher disp;
    
    private final boolean host;

    public Connection(MainFrame delegate, Socket client, boolean host) {
	this.delegate = delegate;
	this.client = client;
	this.host = host;
    }

    @Override
    public void run() {
	try{
	    if(host){
		out = new ObjectOutputStream(client.getOutputStream());
		in = new ObjectInputStream(client.getInputStream());
	    } else {
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
	    }
	    
	    // create am interpreter for the messages, with delegate as sink
	    disp = new MessageDispatcher(delegate);
	    
	    // start the connection listener
	    reader = new ConnReader(in, disp);
	    reader.start();
	} catch (Exception e){
	    JOptionPane.showMessageDialog(new JFrame(), "Problem obtaining streams, operation aborted!", "Error", JOptionPane.ERROR_MESSAGE);
	    return;
	}
    }
    
    public void sendMessage(Message msg){
	try {
	    out.writeObject(msg);
	    out.flush();
	} catch (IOException ex) {
	    JOptionPane.showMessageDialog(new JFrame(), "There was a problem with your connection. The session will be reset and you will go into offline mode!", "Connection interrupted", JOptionPane.ERROR_MESSAGE);
	    try {
		out.close();
	    } catch (IOException ex1) {
		System.err.println("Error occurred closing socket! "+ex);
	    }
	    reader.terminate();
	    System.err.println("Closing connection and go to offline mode!!");
//	    delegate.terminateSession();
	}
    }
    
    public void terminate(){
	try {
	    out.close();
	    client.close();
	} catch (IOException ex) {
	    System.err.println("Problem closing connection! "+ex);
	}
    }
    
    private class ConnReader extends Thread{
	private ObjectInputStream in;
	private MessageDispatcher disp;
	private boolean showMessage;

	public ConnReader(ObjectInputStream in, MessageDispatcher disp) {
	    this.showMessage = true;
	    this.in = in;
	    this.disp = disp;
	}
	
	@Override
	public void run() {
	    while(true){
		try {
		    Message m = (Message)in.readObject();
		    disp.dispatchMessage(m);
		} catch (IOException ex) {
		    System.err.println("Connection terminated");
		    if(showMessage) JOptionPane.showMessageDialog(new JFrame(), "Connection closed by foreign host, returning to offline mode!", "Connection closed", JOptionPane.ERROR_MESSAGE);
		    // close connection
//		    delegate.terminateSession();
		    break;
		} catch (ClassNotFoundException ex) {
		    break;
		}
	    }
	}
	
	public void terminate(){
	    try {
		showMessage = false;
		in.close();
		client.close();
	    } catch (IOException ex) {
		System.err.println("Error closing connection!");
		// suggest to restart the program to reset sockets
	    }
	}
    }
}
