/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import viewControllers.CardItemFrame;

/**
 *
 * @author Warkst
 */
public class CardItem extends Card {

    public CardItem(String imgPath, String name, String tags, String restriction, String cost, int nr, char rarity, String set, String rules) {
	super(imgPath, name, "Item", "", "", "", tags, "", restriction, cost, 0, "", 0, nr, rarity, set, rules, "", "");
    }

    public CardItem(Card cloneMe) {
	super(cloneMe);
    }
    
    @Override
    public void showCard() {
	new CardItemFrame(this).setVisible(true);
    }

    @Override
    public void showCard(Point location) {
	CardItemFrame f = new CardItemFrame(this);
	f.setLocation(location);
	f.setVisible(true);
	f.electFirstResponder();
    }
    
}
