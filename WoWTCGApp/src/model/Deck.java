/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Warkst
 */
public class Deck {
    private final String name;
    
    private final String hero;
    private final String clazz;
    private final ArrayList<Card> cards;

    public Deck(String name, String hero, String clazz, ArrayList<Card> cards) {
	this.name = name;
	this.hero = hero;
	this.clazz = clazz;
	this.cards = cards;
    }
    
    public String getName(){
	return name;
    }

    public List<Card> getCards() {
	return cards;
    }

    public String getHero() {
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
	for (Card card : cards) {
	    cardList.add(card.getName());
	}
	return cardList.toArray(new String[0]);
    }
    
    public void shuffle(){
	Collections.shuffle(cards);
    }
    
    // pop the first card
    public Card drawCard(){
	if(cards.size()>0) return cards.remove(0);
	else return null;
    }
    
    public void putOnTop(Card card){
	cards.add(0, card);
    }
}
