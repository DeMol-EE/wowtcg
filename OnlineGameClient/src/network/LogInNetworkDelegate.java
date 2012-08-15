/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;

/**
 *
 * @author Warkst
 */
public interface LogInNetworkDelegate {
    public void logInResult(Message result);
    public void connectionLost(String message);
}
