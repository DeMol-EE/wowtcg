/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardLocation;

/**
 *
 * @author Warkst
 */
public class CardLocationFrame extends CardFrame {

    public CardLocationFrame(CardLocation card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText("");
	typesLabel.setText(card.getType());
	restrictionsLabel.setText("");
	tagsLabel.setText("");
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);	
	bottomRightLabel.setVisible(false); // this value is not in the db but is on the card
    }
}
