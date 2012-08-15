/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import model.CharacterInfo;

/**
 *
 * @author Warkst
 */
public class LogInClient extends Thread {

    private Socket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LogInNetworkDelegate delegate;
    private Semaphore startSignal;
    private String username;
    private String password;

    public LogInClient(LogInNetworkDelegate delegate, Semaphore startSignal) throws UnknownHostException, IOException {
	this.delegate = delegate;
	this.startSignal = startSignal;

	// order of creating out/in MATTERS. Put it in reverse order as on the server...

	server = new Socket(Constants.SERVER_IP, Constants.LOGIN_PORT);
	out = new ObjectOutputStream(server.getOutputStream());
	in = new ObjectInputStream(server.getInputStream());
    }

    public LogInClient(LogInNetworkDelegate delegate, Semaphore startSignal, String host, int port) throws UnknownHostException, IOException {
	this.delegate = delegate;
	this.startSignal = startSignal;

	// order of creating out/in MATTERS. Put it in reverse order as on the server...
	server = new Socket(host, port);
	out = new ObjectOutputStream(server.getOutputStream());
	in = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public void run() {
	while (true) {
	    try {
		startSignal.acquire();
	    } catch (InterruptedException ex) {
		System.err.println("Interrupted while waiting for start signal:\n" + ex);
		return;
	    }

	    // when receiving the start signal, send the data
	    HashMap<String, Object> requestArgs = new HashMap<String, Object>();
	    requestArgs.put(Message.STR_USERNAME, this.username);
	    requestArgs.put(Message.STR_PASSWORD, this.password);
	    Message logInTry = new Message(Message.MSGTYPE_LOGIN_TRY, requestArgs);
	    try {
		out.writeObject(logInTry);
		out.flush();
	    } catch (IOException ex) {
		System.err.println("Connection to the server was lost!\n" + ex);
		delegate.connectionLost("A connection error occured! (code 1)");
		return;
	    }

	    // read the data
	    Message logInResult = null;
	    try {
		logInResult = (Message) in.readObject();
	    } catch (IOException ex) {
		System.err.println("Connection to the server was lost!\n" + ex);
		delegate.connectionLost("A connection error occured! (code 2)");
		return;
	    } catch (ClassNotFoundException ex) {
		System.err.println("Error parsing message to class \"Message\":\n" + ex);
		delegate.connectionLost("A connection error occured! (code 3)");
		return;
	    }

	    HashMap<String, Object> resultArgs = logInResult.getArguments();
	    boolean result = (Boolean) resultArgs.get(Message.STR_LOGIN_RESULT);
	    
	    // after receiving the data, alert the delegate
	    delegate.logInResult(logInResult);
	    if(result) {
		try {
		    in.close();
		    out.close();
		    server.close();
		} catch (IOException ex) {
		    System.err.println("Error closing the streams/socket after succesful log in:\n"+ex);
		} finally {
		    return;
		}
	    }
	}
    }

    public synchronized void setLogInCredentials(String username, String password) {
	this.username = username;
	this.password = password;
    }
}
