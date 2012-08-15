/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.HashMap;
import model.CharacterInfo;

/**
 *
 * @author Warkst
 */
public interface LogInVCDelegate {
    public void loginSuccess(HashMap<String, CharacterInfo> characters, String loggedInClientName);
}
