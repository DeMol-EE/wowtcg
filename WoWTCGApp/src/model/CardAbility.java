/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardAbilityFrame;

/**
 * Uses RACE field to store supertype!
 *
 * @author Warkst
 */
public class CardAbility extends Card {

    public CardAbility(String name, String supertype, String subtype, String talent, String faction, String tags, String restriction, String cost, String rules, int nr, char rarity, String set){
	super("template.png", name, "Ability", subtype, supertype, "", tags, faction, restriction, cost, 0, "", 0, nr, rarity, set, rules, talent, "");
    }
    
    public CardAbility(Card cloneMe) {
	super(cloneMe);
    }
    
    @Override
    public void showCard() {
	new CardAbilityFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardAbilityFrame f = new CardAbilityFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
    
}
