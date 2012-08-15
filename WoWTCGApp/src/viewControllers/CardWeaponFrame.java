/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardWeapon;

/**
 *
 * @author Warkst
 */
public class CardWeaponFrame extends CardFrame {

    public CardWeaponFrame(CardWeapon card) {
	super(card);
    }
    
    @Override
    protected void setFields() {
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	StringBuilder typesBuilder = new StringBuilder();
	if(card.getRace().length()>0) typesBuilder.append(card.getRace()).append(" ");
	typesBuilder.append(card.getType()).append(" - ").append(card.getSubtype());
	typesLabel.setText(typesBuilder.toString());
	restrictionsLabel.setText("");
	tagsLabel.setText(card.getTags());
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules()+"<br/>"+card.getRestriction());
	bottomLeftLabel.setText(card.getAtk()+" ["+card.getAtktype()+"]");
	bottomRightLabel.setText(""+card.getHealth());
    }
}
