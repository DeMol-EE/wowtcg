/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardAllyFrame;

/**
 *
 * @author Warkst
 */
public class CardAlly extends Card {

    public CardAlly(String atkType, String name, String race, String clazz, String tags, String faction, String cost, int atk, int health, int nr, char rarity, String set, String rules) {
	super("template.png", name, "Ally", "", race, clazz, tags, faction, "", cost, atk, atkType, health, nr, rarity, set, rules, "", "");
    }

    public CardAlly(Card cloneMe) {
	super(cloneMe);
    }

    @Override
    public void showCard() {
	new CardAllyFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardAllyFrame f = new CardAllyFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
}
