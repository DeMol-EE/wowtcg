/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardAbility;

/**
 *
 * @author Warkst
 */
public class CardAbilityFrame extends CardFrame {

    public CardAbilityFrame(CardAbility card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	String typesString = "";
	if(card.getRace().length()>0) typesString = card.getRace()+" "+card.getType();
	else typesString = card.getType();
	if(card.getTalent().length()>0) typesString.concat(" - "+card.getTalent());
	typesLabel.setText(typesString);
	restrictionsLabel.setText(card.getRestriction());
	if(card.getTags().length()>0) tagsLabel.setText(card.getTags());
	tagsLabel.setText("");
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);
	bottomRightLabel.setVisible(false);
    }
    
}
