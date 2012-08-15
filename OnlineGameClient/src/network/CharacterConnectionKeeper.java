/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Warkst
 */
public class CharacterConnectionKeeper implements DispatcherKeeper {
    private Dispatcher uplinkDispatcher;

    public void setDispatcher(Dispatcher dispatcher) {
	this.uplinkDispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
	return uplinkDispatcher;
    }

    public void dispatchMessage(Message message) {
	uplinkDispatcher.dispatchMessage(message);
    }

    public void closeConnection() {
	uplinkDispatcher.interrupt();
    }
}
