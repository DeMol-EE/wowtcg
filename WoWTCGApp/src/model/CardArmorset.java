/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardArmorsetFrame;

/**
 *
 * @author Warkst
 */
public class CardArmorset extends Card {

    public CardArmorset(String name, String subtype, String tags, String restriction, String cost, int def, int nr, char rarity, String set, String rules) {
	super("template.png", name, "Armor Set", subtype, "", "", tags, "", restriction, cost, 0, "", def, nr, rarity, set, rules, "", "");
    }

    public CardArmorset(Card cloneMe) {
	super(cloneMe);
    }

    @Override
    public void showCard() {
	new CardArmorsetFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardArmorsetFrame f = new CardArmorsetFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
    
}
