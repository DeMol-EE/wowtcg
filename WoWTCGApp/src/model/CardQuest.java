/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardQuestFrame;

/**
 *
 * @author Warkst
 */
public class CardQuest extends Card {

    public CardQuest(String name, String faction, int nr, char rarity, String set, String rules) {
	super("template.png", name, "Quest", "", "", "", "", faction, "", " ", 0, "", 0, nr, rarity, set, rules, "", "");
    }
    
    public CardQuest(Card cloneMe) {
	super(cloneMe);
    }
    
    @Override
    public void showCard() {
	new CardQuestFrame(this).setVisible(true);
    }
    
    @Override
    public void showCard(Point location) {
	CardQuestFrame f = new CardQuestFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
}
