/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardLocationFrame;

/**
 *
 * @author Warkst
 */
public class CardLocation extends Card {

    public CardLocation(String name, int nr, char rarity, String set, String rules) {
	super("template.png", name, "Location", "", "", "", "", "", "", " ", 0, "", 0, nr, rarity, set, rules, "", "");
    }

    public CardLocation(Card cloneMe) {
	super(cloneMe);
    }

    @Override
    public void showCard() {
	new CardLocationFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardLocationFrame f = new CardLocationFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
    
}
