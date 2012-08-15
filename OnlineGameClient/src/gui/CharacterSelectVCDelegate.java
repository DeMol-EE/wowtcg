/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.util.ArrayList;
import model.CharacterInfo;

/**
 *
 * @author Warkst
 */
public interface CharacterSelectVCDelegate {
    public void willEnterWorld();
    public void didEnterWorld(CharacterInfo characterEnteringWorld, ArrayList<String> welcomeMessages);
}
