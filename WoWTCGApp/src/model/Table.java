/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import viewControllers.session.CardController;
import viewControllers.session.TableController;

/**
 *
 * @author Robin jr
 */
public class Table {
    private final TableController controller;
    
    // -- data --
    private CardController myHero;
    private final HashMap<String, CardController> myResourcesMap;
    private final ArrayList<CardController> myResources;
    private final HashMap<String, CardController> myCardsMap;
    private final ArrayList<CardController> myCards;
    
    private CardController enemyHero;
    private final HashMap<String, CardController> enemyResourcesMap;
    private final ArrayList<CardController> enemyResources;
    private final HashMap<String, CardController> enemyCardsMap;
    private final ArrayList<CardController> enemyCards;
    
    public Table(TableController controller){
	this.controller = controller;
	
	this.myCardsMap = new HashMap<String, CardController>();
	this.myCards = new ArrayList<CardController>();
	this.myResourcesMap = new HashMap<String, CardController>();
	this.myResources = new ArrayList<CardController>();
	
	this.enemyCardsMap = new HashMap<String, CardController>();
	this.enemyCards = new ArrayList<CardController>();
	this.enemyResourcesMap = new HashMap<String, CardController>();
	this.enemyResources = new ArrayList<CardController>();
    }
    
    public void addAttachListeners(final CardController toAttach){
	// remove and store all previous mouse listeners
	final MouseListener[] listeners = myHero.getMouseListeners();
	for (MouseListener mouseListener : listeners) {
	    myHero.removeMouseListener(mouseListener);
	}
	
	// add the attach listener
	myHero.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		myHero.attach(toAttach);
		
		// reattach the mouse listeners
		for (MouseListener mouseListener : listeners) {
		    myHero.addMouseListener(mouseListener);
		}
		
		// remove the attach listener
		myHero.removeMouseListener(this);
	    }
	    
	});
	
	for (final CardController cardController : myCards) {
	    final MouseListener[] cardListeners = cardController.getMouseListeners();
	    for (MouseListener mouseListener : cardListeners) {
		cardController.removeMouseListener(mouseListener);
	    }
	    
	    cardController.addMouseListener(new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
		    cardController.attach(toAttach);

		    for (MouseListener mouseListener : cardListeners) {
			cardController.addMouseListener(mouseListener);
		    }
		    
		    cardController.removeMouseListener(this);
		}

	    });
	}
	
	for (final CardController cardController : myResources) {
	    final MouseListener[] cardListeners = cardController.getMouseListeners();
	    for (MouseListener mouseListener : cardListeners) {
		cardController.removeMouseListener(mouseListener);
	    }
	    
	    cardController.addMouseListener(new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
		    cardController.attach(toAttach);

		    for (MouseListener mouseListener : cardListeners) {
			cardController.addMouseListener(mouseListener);
		    }
		    
		    cardController.removeMouseListener(this);
		}

	    });
	}
    }
    
    public void addMyResource(CardController myNewResource){
	myResourcesMap.put(myNewResource.GUID(), myNewResource);
	myResources.add(myNewResource);
	
	controller.updateMyResources();
    }
    
    public void addMyCard(CardController myNewCard){
	myCardsMap.put(myNewCard.GUID(), myNewCard);
	// add new card at the end
	myCards.add(myNewCard);
	
	controller.updateMyCards();
    }
    
    public void removeMyCard(String GUID){
	if(myCardsMap.containsKey(GUID) && myCards.contains(myCardsMap.get(GUID))) myCards.remove(myCardsMap.get(GUID));
	
	controller.updateMyCards();
    }
    
    public void removeMyCard(CardController toRemove){
	if(myCards.contains(toRemove)) myCards.remove(toRemove);

	controller.updateMyCards();
    }
    
    public void removeMyResource(String GUID){
	if(myResourcesMap.containsKey(GUID) && myResources.contains(myResourcesMap.get(GUID))) myResources.remove(myResourcesMap.get(GUID));
	
	controller.updateMyCards();
    }
    
    public void removeMyResource(CardController toRemove){
	if(myResources.contains(toRemove)) myResources.remove(toRemove);

	controller.updateMyCards();
    }

    public CardController getEnemyHero() {
	return enemyHero;
    }

    public void setEnemyHero(CardController enemyHero) {
	this.enemyHero = enemyHero;
	
	controller.updateEnemyHero();
    }

    public CardController getMyHero() {
	return myHero;
    }

    public void setMyHero(CardController myHero) {
	this.myHero = myHero;
	
	controller.updateMyHero();
    }

    public ArrayList<CardController> getEnemyCards() {
	return enemyCards;
    }

    public ArrayList<CardController> getEnemyResources() {
	return enemyResources;
    }

    public ArrayList<CardController> getMyCards() {
	return myCards;
    }

    public ArrayList<CardController> getMyResources() {
	return myResources;
    }
}
