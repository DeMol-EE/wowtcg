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
public class CharacterConnectionClient extends ConnectionClient {

    public CharacterConnectionClient(Semaphore synchronizer, ConnectionMessage setup, MessageHandler messageHandler, DispatcherKeeper uplinkKeeper, int port) {
	super(synchronizer, setup, messageHandler, uplinkKeeper, port);
    }

    public CharacterConnectionClient(Semaphore synchronizer, ConnectionMessage setup, MessageHandlerProvider messageHandlerProvider, String handlerTag, DispatcherKeeper uplinkKeeper, int port) {
	super(synchronizer, setup, messageHandlerProvider, handlerTag, uplinkKeeper, port);
    }
    
}
