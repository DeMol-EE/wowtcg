/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardWeaponFrame;

/**
 *
 * @author Warkst
 */
public class CardWeapon extends Card {

    public CardWeapon(String name, String supertype, String subtype, String tags, String restriction, String cost, int strikeCost, String rules, int atk, String atktype, int nr, char rarity, String set) {
	super("template.png", name, "Weapon", subtype, supertype, "", tags, "", restriction, cost, atk, atktype, strikeCost, nr, rarity, set, rules, "", "");
    }
    
    public CardWeapon(Card cloneMe) {
	super(cloneMe);
    }
    
    @Override
    public void showCard() {
	new CardWeaponFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardWeaponFrame f = new CardWeaponFrame(this);
	f.setLocation(location);
	f.setVisible(true);
    }
    
}
