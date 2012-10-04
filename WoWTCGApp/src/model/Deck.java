/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import viewControllers.CardController;

/**
 *
 * @author Warkst
 */
public class Deck {
    private final String name;
    
    private final CardController hero;
    private final String clazz;
    private final ArrayList<CardController> cards;

    public Deck(String name, CardController hero, String clazz, ArrayList<CardController> cards) {
	this.name = name;
	this.hero = hero;
	this.clazz = clazz;
	this.cards = cards;
    }
    
    public String getName(){
	return name;
    }

    public List<CardController> getCards() {
	return cards;
    }

    public CardController getHero() {
	return hero;
    }
    
    public String getClazz() {
	return clazz;
    }
    
    public int getDeckSize(){
	return cards.size();
    }
    
    public String[] getCardList(){
	ArrayList<String> cardList = new ArrayList<String>();
	for (CardController card : cards) {
	    cardList.add(card.getCardName());
	}
	return cardList.toArray(new String[0]);
    }
    
    public void shuffle(){
	Collections.shuffle(cards);
    }
    
    // pop the first card
    public CardController drawCard(){
	if(cards.size()>0) return cards.remove(0);
	else return null;
    }
    
    // push on top
    public void putOnTop(CardController card){
	cards.add(0, card);
    }
}
