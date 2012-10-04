/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CardController.java
 *
 * Created on 21-aug-2012, 12:52:43
 */
package viewControllers;

import images.ImageLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.UUID;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import model.Card;

/**
 *
 * @author Robin jr
 */
public class CardController extends javax.swing.JPanel {

    public static final int CARD_ANCHOR_LEFT = 0;
    public static final int CARD_ANCHOR_BOTTOM = 1;
    
    private final ImageToolTipButton content;
    
    private final String GUID;
    private final Card card;
    
    private int CARD_ANCHOR = CARD_ANCHOR_LEFT;
    
    private MouseListener handMouseAdapter;
    private MouseListener tableMouseAdapter;
    private MouseListener resourceMouseAdapter;
    
    /** Creates new form CardController */
    public CardController(final Card card) {
	initComponents();
	
	this.GUID = card.getName() + new Date().toString() + generateNonce();
	this.card = card;
	
	CardController me = this;
	this.card.registerController(me);
	
	this.content = new ImageToolTipButton(card.getIcon(), new Dimension(220,308));
	this.content.setIcon(ImageLoader.scaleImage(card.getIcon(), this.contentPanel.getPreferredSize()));
	this.contentPanel.add(content, BorderLayout.CENTER);
	
	this.repaint();
	this.validate();
    }
    
    public CardController scale(Dimension newDimension){
	if(CARD_ANCHOR == CARD_ANCHOR_LEFT) setPreferredSize(new Dimension(newDimension.width + 15*card.attachedCards().size(), newDimension.height));
	else if (CARD_ANCHOR == CARD_ANCHOR_BOTTOM) setPreferredSize(new Dimension(newDimension.width, newDimension.height + 15*card.attachedCards().size()));
	return scaleIcon(newDimension);
    }
    
    public CardController scaleIcon(Dimension newDimension){
	if(CARD_ANCHOR == CARD_ANCHOR_LEFT) contentPanel.setBounds(0, 0, newDimension.width, newDimension.height);
	else if (CARD_ANCHOR == CARD_ANCHOR_BOTTOM) contentPanel.setBounds(0, getPreferredSize().height-newDimension.height, newDimension.width, newDimension.height);
	content.setIcon(ImageLoader.scaleImage(card.getIcon(), newDimension));
	repaint();
	
	// rescale all attachments too
	
	return this;
    }
    
    private String generateNonce(){
	return UUID.randomUUID().toString();
    }
    
    public void setCardAnchor(int anchor){
	this.CARD_ANCHOR = anchor;
    }
    
    public void setFlipped(boolean flipped){
	card.setFlipped(flipped);
	content.useAlt(flipped);
    }
    
    public void toggleFlipped(){
	card.toggleFlipped();
	content.toggleUseAlt();
    }
    
    public Card card(){
	return card;
    }
    
    public String getCardName(){
	return card.getName();
    }
    
    public String GUID(){
	return GUID;
    }
    
    public void setParent(CardController parent){
	card.setParent(parent);
    }
    
    public void attach(CardController toAttach){
	card.attachCard(toAttach);
    }
    
    public void removeAttachedCard(String GUID){
	card.removeAttachedCard(GUID);
    }
    
    public void updateAttachments(){
	
	System.out.println("CardController::Received attachment update from card model.");
	
	layeredContent.removeAll();
	
	Rectangle demBounds = contentPanel.getBounds();
	layeredContent.add(contentPanel, JLayeredPane.DRAG_LAYER);
	contentPanel.setBounds(demBounds);
	
	for (int i = 0; i < card.attachedCards().size(); i++) {
	    CardController attachment = card.attachedCards().get(i);
	    layeredContent.add(attachment, JLayeredPane.POPUP_LAYER - i);
	    if(CARD_ANCHOR == CARD_ANCHOR_LEFT) attachment.setBounds((i+1)*15, 0, attachment.getPreferredSize().width, attachment.getPreferredSize().height);
	    else if (CARD_ANCHOR == CARD_ANCHOR_BOTTOM) attachment.setBounds(0, getPreferredSize().height-attachment.getPreferredSize().height-(i+1)*15, attachment.getPreferredSize().width, attachment.getPreferredSize().height);
	}
	
	repaint();
	validate();
    }
    
    public void updateIcon(){
	scaleIcon(getPreferredSize());
    }
    
    public CardController addHandMouseListener(final HandController handController){
	final CardController me = this;
	handMouseAdapter = new MouseAdapter() {
	    @Override
	    public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
		    
		    JPopupMenu popup = new JPopupMenu();
		    
		    JMenu r = new JMenu("Play as Resource");
		    JMenuItem rfu = new JMenuItem("Face up");
		    rfu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    handController.playResource(me);
			}
		    });
		    JMenuItem rfd = new JMenuItem("Face down");
		    rfd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    handController.playResourceFlipped(me);
			}
		    });
		    r.add(rfu);
		    r.add(rfd);
		    
		    JMenuItem c = new JMenuItem("Play as Card");
		    c.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    handController.playCard(me);
			}
		    });
		    
		    JMenuItem i = new JMenuItem("Immediatly resolve");
		    i.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    handController.resolveCard(me);
			}
		    });
		    
		    JMenuItem a = new JMenuItem("Attach to...");
		    a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    handController.prepareAttachingOfCard(me);
			}
		    });
		    
		    popup.add(r);
		    popup.add(c);
		    popup.add(i);
		    popup.add(a);
		    popup.show(me, e.getX(), e.getY());
		    
//		    String[] options = {"Play as Resource...", "Play as Card", "Immediatly resolve", "Attach to..."};
//		    switch(JOptionPane.showOptionDialog(null, "How do you want \""+me.getCardName()+"\" to resolve?", "Resolve card", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0])){
//			case 0:
//			    System.out.println("Play as resource");
//			    
//			    String[] resourceOptions = {"Face up", "Face down"};
//			    switch(JOptionPane.showOptionDialog(null, "Do you want to play this card face up or face down?", "Play resource", 0, JOptionPane.QUESTION_MESSAGE, null, resourceOptions, resourceOptions[0])){
//				case 0:
//				    System.out.println("Play face up");
//				    handController.playResource(me);
//				    break;
//				case 1:
//				    System.out.println("Play face down");
//				    handController.playResourceFlipped(me);
//				    break;
//				default:
//			    }
//			    
//			    break;
//			case 1:
//			    System.out.println("Play as card");
//			    handController.playCard(me);
//			    break;
//			case 2:
//			    System.out.println("Resolve immediatly");
//			    handController.resolveCard(me);
//			    break;
//			case 3:
//			    System.out.println("Attach to...");
//			    break;
//			default:
//			    System.out.println("Default = do nothing");
//		    }
		} else if (e.getButton() == MouseEvent.BUTTON3){
		    
		}
	    }
	};
	
	content.addMouseListener(handMouseAdapter);
	
	return this;
    }
    
    public void removeHandMouseListener(){
	if(handMouseAdapter != null) content.removeMouseListener(handMouseAdapter);
	handMouseAdapter = null;
    }
    
    public CardController addTableMoudeListener(final TableController tableController){
	final CardController me = this;
	tableMouseAdapter = new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
		    if(e.isControlDown()){
			tableController.removeMyCardFromPlay(me);
		    }
		} else if (e.getButton() == MouseEvent.BUTTON3){
		    
		}
	    }
	};
	
	content.addMouseListener(tableMouseAdapter);
	
	return this;
    }
    
    public void removeTableMouseListener(){
	if(tableMouseAdapter != null) content.removeMouseListener(tableMouseAdapter);
	tableMouseAdapter = null;
    }
    
    public CardController addResourceMouseListener(final TableController tableController){
	final CardController me = this;
	resourceMouseAdapter = new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
		    if(e.isControlDown()){
			tableController.removeMyResourceFromPlay(me);
		    }
		} else if (e.getButton() == MouseEvent.BUTTON3){
		    
		}
	    }
	};
	
	content.addMouseListener(resourceMouseAdapter);
	
	return this;
    }
    
    public void removeResourceMouseListener(){
	if(resourceMouseAdapter != null) content.removeMouseListener(resourceMouseAdapter);
	resourceMouseAdapter = null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layeredContent = new javax.swing.JLayeredPane();
        contentPanel = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(2145789, 2154879));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(100, 140));
        setLayout(new java.awt.BorderLayout());

        contentPanel.setBackground(new java.awt.Color(255, 51, 51));
        contentPanel.setPreferredSize(new java.awt.Dimension(100, 140));
        contentPanel.setLayout(new java.awt.BorderLayout());
        contentPanel.setBounds(0, 0, 100, 140);
        layeredContent.add(contentPanel, javax.swing.JLayeredPane.DRAG_LAYER);

        add(layeredContent, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLayeredPane layeredContent;
    // End of variables declaration//GEN-END:variables
}
