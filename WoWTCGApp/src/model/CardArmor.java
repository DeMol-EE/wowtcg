/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardArmorFrame;

/**
 *
 * @author Warkst
 */
public class CardArmor extends Card {
    
    public CardArmor(String imgPath, String name, String subtype, String tags, String restriction, String cost, int def, int nr, char rarity, String set, String rules) {
	super(imgPath, name, "Armor", subtype, "", "", tags, "", restriction, cost, 0, "", def, nr, rarity, set, rules, "", "");
    }

    public CardArmor(Card cloneMe) {
	super(cloneMe);
    }
    
    @Override
    public void showCard() {
	new CardArmorFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardArmorFrame f = new CardArmorFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
    
}
