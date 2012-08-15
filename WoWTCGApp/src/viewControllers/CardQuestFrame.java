/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardQuest;

/**
 *
 * @author Warkst
 */
public class CardQuestFrame extends CardFrame {

    public CardQuestFrame(CardQuest card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(card.getFaction());
	typesLabel.setText(card.getType());
	restrictionsLabel.setText("");
	tagsLabel.setText("");
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);	
	bottomRightLabel.setVisible(false);
    }
    
}
