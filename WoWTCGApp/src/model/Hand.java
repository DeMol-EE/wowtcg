/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import viewControllers.session.CardController;
import viewControllers.session.HandController;

/**
 *
 * @author Robin jr
 */
public class Hand {
    private final HandController controller;
    private final ArrayList<CardController> hand;
    
    public Hand(HandController controller){
	this.controller = controller;
	
	this.hand = new ArrayList<CardController>();
    }
    
    public void addCard(CardController c){
	hand.add(c);
	controller.update();
    }
    
    public void removeCard(CardController c){
	if(hand.contains(c)) hand.remove(c);
	controller.update();
    }
    
    public ArrayList<CardController> cards(){
	return hand;
    }
}
