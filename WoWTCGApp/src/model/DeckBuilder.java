/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;

/**
 *
 * @author Warkst
 */
public interface DeckBuilder {
    public void addToDeck(String card);
    public void removeFromDeck(String card);
    public int getDeckSize();
    
    public void setHero(String hero);
    public boolean hasHero();
    
    public void saveDeck(File file);
    
    public boolean validateDeck();
}
