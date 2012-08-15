/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardItem;

/**
 *
 * @author Warkst
 */
public class CardItemFrame extends CardFrame {

    public CardItemFrame(CardItem card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	typesLabel.setText(card.getType());
	restrictionsLabel.setText(card.getRestriction());
	tagsLabel.setText(card.getTags());
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);	
	bottomRightLabel.setVisible(false);
    }
}
