/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardArmor;

/**
 *
 * @author Warkst
 */
public class CardArmorFrame extends CardFrame {

    public CardArmorFrame(CardArmor card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	typesLabel.setText(card.getType() + " - " + card.getSubtype());
	restrictionsLabel.setText(card.getRestriction());
	tagsLabel.setText(card.getTags());
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);
	bottomRightLabel.setText(""+card.getHealth());
    }
}
