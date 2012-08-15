/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import model.Card;
import model.CardSnapshot;

/**
 * MAKE ABSTRACT AND ADD EXTENSIONS FOR EACH TYPE OF CARD THAT NEEDS ONE
 *
 * @author Warkst
 */
public class CardPanel extends JLayeredPane implements Serializable {
    
    private final String GUID;
    
    // reference to the model
    private Card card;
//    private int layerOffset;
    
    private JLabel exhaustedLabel;
    private JLabel countersLabel;
    private JLabel flipLabel;
    
//    public CardPanel(Card card, int layerOffset, int index){
    public CardPanel(Card card, int index){
	this(card, index, card.getName().concat(""+new Random().nextInt(999999)).concat(""+index));
    }
    
    public CardPanel(Card card, int index, String GUID){
	this.GUID = GUID;
	this.card = card;
//	this.layerOffset = layerOffset;
//	System.out.println("Made panel with layer offset: "+layerOffset);
	
	// ???
	card.setIconURL("template.png");
	
	this.setLayout(null);
	
	// not working?
//	this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLineBorder(Color.yellow,2)));
//	this.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
	
	// create the exhausted label
	exhaustedLabel = new JLabel("Exhausted");
	exhaustedLabel.setOpaque(true);
	exhaustedLabel.setBounds(5, 35, 50, 15);
	exhaustedLabel.setForeground(Color.WHITE);
	exhaustedLabel.setBackground(Color.GRAY);
	exhaustedLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 9));
	exhaustedLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	
	// create the counters label
	countersLabel = new JLabel(""+card.getCounters());
	countersLabel.setHorizontalAlignment(SwingConstants.CENTER);
//	countersLabel.setHorizontalTextPosition(SwingConstants.CENTER);
	countersLabel.setOpaque(true);
	countersLabel.setBounds(22, 10, 16, 16);
	countersLabel.setForeground(Color.WHITE);
	countersLabel.setBackground(Color.GRAY);
	countersLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
	countersLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
	
	// create the flip label
	flipLabel = new JLabel("F");
	flipLabel.setHorizontalAlignment(SwingConstants.CENTER);
	flipLabel.setOpaque(true);
	flipLabel.setBounds(38, 10, 16, 16);
	flipLabel.setForeground(Color.WHITE);
	flipLabel.setBackground(Color.GRAY);
	flipLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
	flipLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
	
	// add the card icon to the panel
	JLabel bg = new JLabel(card.getIcon());
	bg.setBounds(0, 0, 60, 60);
//	bg.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLineBorder(new Color(142, 107, 35),2)));
	bg.setBorder(BorderFactory.createRaisedBevelBorder());
	this.add(bg,JLayeredPane.DEFAULT_LAYER);
	
	// add the title as a label
	JLabel titleLabel = new JLabel(card.getName());
	titleLabel.setOpaque(true);
	titleLabel.setBounds(2, 43, 56, 15);
	titleLabel.setForeground(Color.BLACK);
	titleLabel.setBackground(Color.LIGHT_GRAY);
	titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 9));
	if(card.getType().compareToIgnoreCase("hero")!=0) titleLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	else titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	this.add(titleLabel,JLayeredPane.PALETTE_LAYER);
	
//	titleLabel.setToolTipText(card.getName());
    }
    
    public String getGUID(){
	return GUID;
    }
    
    public Card getCard(){
	return card;
    }
    
    public void ready(){
	if(card.isExhausted()){
	    card.ready();
	    this.remove(exhaustedLabel);
	}
	this.validate();
	this.repaint();
    }

    public void toggleExhaust() {
	if(card.isExhausted()) {
	    card.ready();
	    this.remove(exhaustedLabel);
	} else {
	    card.exhaust();
	    this.add(exhaustedLabel, JLayeredPane.POPUP_LAYER);
	}
	this.validate();
	this.repaint();
    }
    
    public void toggleFlip() {
	card.toggleFlipped();
	if(card.isFlipped()){
	    this.add(flipLabel, JLayeredPane.POPUP_LAYER);
	} else {
	    this.remove(flipLabel);
	}
	this.validate();
	this.repaint();
    }
    
    public boolean isExhausted() {
	return card.isExhausted();
    }
//    
//    // accessors & mutators
//    public int getLayerOffset() {
//	return layerOffset;
//    }
//    
//    public void toFront(int layer){
//	this.layerOffset = layer;
//    }
//    
//    public void toBack(int layer){
//	this.layerOffset = layer;
//    }
//    
//    public void toLayer(int layer){
//	this.layerOffset = layer;
//    }
//    
//    public void oneToFront(){
//	this.layerOffset++;
//    }
//    
//    public void oneToBack(){
//	this.layerOffset--;
//    }
    
    // hmm
    public String getCardName(){
	return card.getName();
    }
    
    public void showCard(){
	card.showCard();
    }
    
    public void showCard(Point location){
	card.showCard(location);
    }
    
    public String getCost(){
	return card.getCost();
    }
    
    public void setCounters(int counters){
	if(counters == 0) countersLabel.setVisible(false);
	else countersLabel.setVisible(true);
	card.setCounters(counters);
	this.remove(countersLabel);
	countersLabel.setText(""+card.getCounters());
	this.add(countersLabel, JLayeredPane.POPUP_LAYER);
	this.validate();
	this.repaint();
    }
    
    public int getCounters(){
	return card.getCounters();
    }
    
    // LOLOL override this
    public boolean isMyCard(){
	return true;
    }
    
    public CardSnapshot getSnapshot(){
	return card.takeSnapshot();
    }
    
    public void applySnapshot(CardSnapshot snapshot){
	// apply snapshot
	card.applySnapshot(snapshot);
	
	// propagate changes to panel
	if(card.isExhausted()) {
	    this.add(exhaustedLabel, JLayeredPane.POPUP_LAYER);
	} else {
	    this.remove(exhaustedLabel);
	}
	
	if(card.isFlipped()){
	    this.add(flipLabel, JLayeredPane.POPUP_LAYER);
	} else {
	    this.remove(flipLabel);
	}
	
	this.setCounters(card.getCounters());
	
	this.validate();
	this.repaint();
    }
}
