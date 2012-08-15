/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardArmorset;

/**
 *
 * @author Warkst
 */
public class CardArmorsetFrame extends CardFrame {

    public CardArmorsetFrame(CardArmorset card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	typesLabel.setText(card.getType() + " - " + card.getSubtype());
	restrictionsLabel.setText(card.getRestriction());
	tagsLabel.setText("");
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules()+"<br/>"+card.getTags());
	bottomLeftLabel.setVisible(false);
	bottomRightLabel.setText(""+card.getHealth());
    }
}
