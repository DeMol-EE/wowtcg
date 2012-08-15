/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import model.CardAlly;

/**
 * Fix TAGS
 *
 * @author Warkst
 */
public class CardAllyFrame extends CardFrame {

    public CardAllyFrame(CardAlly card) {
	super(card);
    }
    
    @Override
    protected void setFields(){
	nameLabel.setText(card.getName());
	costLabel.setText(""+card.getCost());
	typesLabel.setText(card.getType()+" - "+card.getRace()+" "+card.getClazz());//+" -- "+card.getTags());
	restrictionsLabel.setText("");
	tagsLabel.setText(card.getTags()); //"" incase no tags -> no problem
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setText(card.getAtk()+" ["+card.getAtktype()+"]");
	bottomRightLabel.setText(""+card.getHealth());
    }
}
