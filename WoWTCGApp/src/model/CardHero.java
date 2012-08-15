/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardHeroFrame;

/**
 *
 * @author Warkst
 */
public class CardHero extends Card {

    public CardHero(String name, String race, String clazz, String faction, int health, int nr, String set, String rules, String talent, String professions) {
	super("template.png", name, "Hero", "", race, clazz, "", faction, "", " ", 0, "", health, nr, 'C', set, rules, talent, professions);
    }

    public CardHero(Card cloneMe) {
	super(cloneMe);
    }

    @Override
    public void showCard() {
	new CardHeroFrame(this).setVisible(true);
    }
    
    @Override
    public void showCard(Point location) {
	CardHeroFrame f = new CardHeroFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
}
