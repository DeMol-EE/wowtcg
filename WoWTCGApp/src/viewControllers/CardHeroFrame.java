/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import java.awt.Color;
import javax.swing.BorderFactory;
import model.CardHero;

/**
 *
 * @author Warkst
 */
public class CardHeroFrame extends CardFrame {
    public CardHeroFrame(CardHero card) {
	super(card);
    }
    
    @Override
    protected void setFields(){
	nameLabel.setText(card.getName());
	costLabel.setText(card.getClazz());
	typesLabel.setText(card.getType()+" - "+card.getRace()+" "+card.getTalent()+" "+card.getClazz());
	tagsLabel.setText(card.getProfessions());
	restrictionsLabel.setText("");
	rnsLabel.setText(card.getRarity()+" #"+card.getNr()+" "+card.getSet());
	rulesArea.setText(card.getRules());
	bottomLeftLabel.setVisible(false);	
	bottomRightLabel.setText(""+card.getHealth());
    }
}
