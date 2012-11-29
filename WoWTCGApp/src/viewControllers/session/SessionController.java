/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SessionController.java
 *
 * Created on 15-aug-2012, 14:32:23
 */
package viewControllers.session;

import java.awt.event.ComponentEvent;
import viewControllers.menu.MainFrame;
import images.ImageLoader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import model.CardSnapshot;
import model.Deck;
import network.ConnectionProxy;
import network.MessageHandler;
import viewControllers.CardPanel;

/**
 *
 * @author Robin jr
 */
public class SessionController extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MessageHandler {  
    private final Deck deck;
    private final HandController handController;
    private final StatusController statusController;
    private final TableController tableController;
    
    private ArrayList<CardController> graveyard;
  
    /** Creates new form SessionController */
    public SessionController(MainFrame callback, Deck playerDeck) {
	this.parent = callback;
	this.deck = playerDeck;
	this.handController = new HandController(this);
	this.graveyard = new ArrayList<CardController>();
	
	
	// create menu
	this.sessionMenu = new JMenu("Session");
	this.sessionMenu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	this.sessionMenu.setBorderPainted(false);
	this.sessionMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sessionMenuMouseEntered(evt);
            }
	    @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sessionMenuMouseExited(evt);
            }
        });
	
	JMenuItem stop = new JMenuItem("Stop session...");
	stop.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		parent.removeMenu(sessionMenu);
		parent.stopSession();
	    }
	});
	this.sessionMenu.add(stop);
	
	parent.addMenu(sessionMenu);
	
	initComponents();
	
	
	playerOutlet.setVisible(false);
	consoleOutlet.setVisible(false);
	
	// add resize listener to layeredpane
	this.layeredOutlet.addComponentListener(new ComponentAdapter() {

	    @Override
	    public void componentResized(ComponentEvent e) {
		tablePanel.setSize(layeredOutlet.getWidth(), layeredOutlet.getHeight());
		playerOutlet.setBounds(0, layeredOutlet.getHeight()-playerOutlet.getHeight(), layeredOutlet.getWidth(), playerOutlet.getHeight());
		playerPullOutOutlet.setBounds((layeredOutlet.getWidth()-playerPullOutOutlet.getWidth())/2, layeredOutlet.getHeight()-playerPullOutOutlet.getHeight(), playerPullOutOutlet.getWidth(), playerPullOutOutlet.getHeight());
		consolePullOutOutlet.setBounds((layeredOutlet.getWidth()-consolePullOutOutlet.getWidth())/2, 0, consolePullOutOutlet.getWidth(), consolePullOutOutlet.getHeight());
		consoleOutlet.setSize(layeredOutlet.getWidth(), consoleOutlet.getHeight());
	    }
	    
	});
	
	this.statusController = StatusController.createAndStart(statusOutlet);
	
	// add the table controller
	this.tableController = new TableController(this);
	this.tablePanel.removeAll();
	this.tablePanel.add(tableController);
	this.tablePanel.repaint();
	
	this.handOutlet.removeAll();
	this.handOutlet.add(handController);
	this.handOutlet.repaint();
	
	styleComponents();
    }
    
    private void sessionMenuMouseEntered(MouseEvent e){
	this.sessionMenu.setBorderPainted(true);
    }
    
    private void sessionMenuMouseExited(MouseEvent e){
	this.sessionMenu.setBorderPainted(false);
    }
    
    private void styleComponents(){
	this.deckOutlet.setIcon(ImageLoader.scaleImage(ImageLoader.createImageIconAtHomeLocation("template.png"), this.deckOutlet.getPreferredSize()));
	this.resolvedCard.setIcon(ImageLoader.scaleImage(ImageLoader.createImageIconAtHomeLocation("template.png"), this.resolvedCard.getPreferredSize()));
	
	// set the hero
	this.tableController.setMyHero(deck.getHero());
    }
    
    public void updateHand(){
	this.handOutlet.repaint();
	this.handOutlet.validate();
	this.repaint();
	this.validate();
    }
    
    public void setStatusMessage(String message){
	statusController.enqueueMessage(message);
    }
    
    public void addCardToTable(CardController card){
	tableController.addMyCard(card);
    }
    
    public void addCardToResources(CardController card){
	tableController.addMyResource(card);
    }
    
    public void addCardToGraveyard(CardController card){
	graveyard.add(0, card);
    }
    
    public void resolve(CardController card){
//	resolvedCardOutlet.removeAll();
//	resolvedCardOutlet.add(card.scale(resolvedCard.getPreferredSize()));
//	resolvedCardOutlet.repaint();
//	resolvedCardOutlet.validate();
	
	tableController.resolve(card);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sideSplit = new javax.swing.JSplitPane();
        masterSplit = new javax.swing.JSplitPane();
        statusPanel = new javax.swing.JPanel();
        statusOutlet = new javax.swing.JLabel();
        mainSplit = new javax.swing.JSplitPane();
        tableOutlet = new javax.swing.JPanel();
        resolvedCardOutlet = new javax.swing.JPanel();
        resolvedCard = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        layeredOutlet = new javax.swing.JLayeredPane();
        tablePanel = new javax.swing.JPanel();
        playerOutlet = new javax.swing.JPanel();
        deckPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        deckOutlet = new javax.swing.JButton();
        handOutlet = new javax.swing.JPanel();
        dissmissHandOutlet = new javax.swing.JLabel();
        cardPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        graveyardOutlet = new javax.swing.JButton();
        removedFromTheGameOutlet = new javax.swing.JButton();
        playerPullOutOutlet = new javax.swing.JLabel();
        consolePullOutOutlet = new javax.swing.JLabel();
        consoleOutlet = new javax.swing.JPanel();
        consoleScroller = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        sideSplit.setBorder(null);
        sideSplit.setDividerLocation(500);
        sideSplit.setDividerSize(1);
        sideSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sideSplit.setResizeWeight(1.0);
        sideSplit.setEnabled(false);
        sideSplit.setFocusable(false);
        sideSplit.setMaximumSize(new java.awt.Dimension(150, 2147483647));
        sideSplit.setMinimumSize(new java.awt.Dimension(150, 0));
        sideSplit.setPreferredSize(new java.awt.Dimension(150, 800));
        sideSplit.setRequestFocusEnabled(false);

        masterSplit.setBorder(null);
        masterSplit.setDividerLocation(30);
        masterSplit.setDividerSize(0);
        masterSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        masterSplit.setEnabled(false);
        masterSplit.setFocusable(false);
        masterSplit.setMinimumSize(new java.awt.Dimension(1440, 800));
        masterSplit.setPreferredSize(new java.awt.Dimension(1440, 800));
        masterSplit.setRequestFocusEnabled(false);

        statusPanel.setMaximumSize(new java.awt.Dimension(1440, 30));
        statusPanel.setMinimumSize(new java.awt.Dimension(1440, 30));
        statusPanel.setPreferredSize(new java.awt.Dimension(1440, 30));
        statusPanel.setLayout(new java.awt.BorderLayout());

        statusOutlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusOutlet.setText("Status");
        statusOutlet.setFocusable(false);
        statusOutlet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        statusOutlet.setRequestFocusEnabled(false);
        statusPanel.add(statusOutlet, java.awt.BorderLayout.CENTER);

        masterSplit.setTopComponent(statusPanel);

        mainSplit.setBorder(null);
        mainSplit.setDividerLocation(570);
        mainSplit.setDividerSize(2);
        mainSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplit.setResizeWeight(1.0);
        mainSplit.setEnabled(false);
        mainSplit.setFocusable(false);
        mainSplit.setPreferredSize(new java.awt.Dimension(2, 770));
        mainSplit.setRequestFocusEnabled(false);

        tableOutlet.setBackground(new java.awt.Color(255, 255, 204));
        tableOutlet.setPreferredSize(new java.awt.Dimension(0, 570));
        tableOutlet.setLayout(new java.awt.BorderLayout());
        mainSplit.setLeftComponent(tableOutlet);

        masterSplit.setBottomComponent(mainSplit);

        resolvedCardOutlet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resolved card", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        resolvedCardOutlet.setMaximumSize(new java.awt.Dimension(142, 200));
        resolvedCardOutlet.setMinimumSize(new java.awt.Dimension(142, 200));
        resolvedCardOutlet.setPreferredSize(new java.awt.Dimension(142, 200));
        resolvedCardOutlet.setLayout(new java.awt.BorderLayout());

        resolvedCard.setPreferredSize(new java.awt.Dimension(130, 182));
        resolvedCardOutlet.add(resolvedCard, java.awt.BorderLayout.CENTER);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        resolvedCardOutlet.add(jButton1, java.awt.BorderLayout.PAGE_START);

        setMaximumSize(new java.awt.Dimension(1440, 800));
        setMinimumSize(new java.awt.Dimension(1440, 800));
        setPreferredSize(new java.awt.Dimension(1440, 800));
        setLayout(new java.awt.BorderLayout());

        tablePanel.setBackground(new java.awt.Color(204, 204, 0));
        tablePanel.setPreferredSize(new java.awt.Dimension(500, 500));
        tablePanel.setLayout(new java.awt.BorderLayout());
        tablePanel.setBounds(0, 0, 1440, 800);
        layeredOutlet.add(tablePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        playerOutlet.setMaximumSize(new java.awt.Dimension(32767, 220));
        playerOutlet.setMinimumSize(new java.awt.Dimension(2, 220));
        playerOutlet.setPreferredSize(new java.awt.Dimension(1000, 220));
        playerOutlet.setLayout(new java.awt.BorderLayout());

        deckPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        deckPanel.setEnabled(false);
        deckPanel.setFocusable(false);
        deckPanel.setMaximumSize(new java.awt.Dimension(120, 200));
        deckPanel.setMinimumSize(new java.awt.Dimension(120, 200));
        deckPanel.setPreferredSize(new java.awt.Dimension(120, 200));
        deckPanel.setRequestFocusEnabled(false);
        deckPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Deck", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel2.setPreferredSize(new java.awt.Dimension(140, 200));
        jPanel2.setLayout(new java.awt.BorderLayout());

        deckOutlet.setPreferredSize(new java.awt.Dimension(130, 182));
        deckOutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deckOutletActionPerformed(evt);
            }
        });
        jPanel2.add(deckOutlet, java.awt.BorderLayout.CENTER);

        deckPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        playerOutlet.add(deckPanel, java.awt.BorderLayout.LINE_START);

        handOutlet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        handOutlet.setMinimumSize(new java.awt.Dimension(800, 200));
        handOutlet.setPreferredSize(new java.awt.Dimension(800, 200));
        handOutlet.setLayout(new java.awt.BorderLayout());
        playerOutlet.add(handOutlet, java.awt.BorderLayout.CENTER);

        dissmissHandOutlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dissmissHandOutlet.setText("vvv");
        dissmissHandOutlet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dissmissHandOutlet.setFocusable(false);
        dissmissHandOutlet.setOpaque(true);
        dissmissHandOutlet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dissmissHandOutletMouseEntered(evt);
            }
        });
        playerOutlet.add(dissmissHandOutlet, java.awt.BorderLayout.PAGE_START);

        cardPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cardPanel.setMaximumSize(new java.awt.Dimension(120, 32767));
        cardPanel.setMinimumSize(new java.awt.Dimension(120, 146));
        cardPanel.setPreferredSize(new java.awt.Dimension(120, 200));
        cardPanel.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(120, 200));

        graveyardOutlet.setText("Gy");
        graveyardOutlet.setMaximumSize(new java.awt.Dimension(50, 50));
        graveyardOutlet.setMinimumSize(new java.awt.Dimension(50, 50));
        graveyardOutlet.setPreferredSize(new java.awt.Dimension(50, 50));
        graveyardOutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graveyardOutletActionPerformed(evt);
            }
        });
        jTabbedPane1.addTab("GY", graveyardOutlet);

        removedFromTheGameOutlet.setText("RftG");
        removedFromTheGameOutlet.setMaximumSize(new java.awt.Dimension(50, 50));
        removedFromTheGameOutlet.setMinimumSize(new java.awt.Dimension(50, 50));
        removedFromTheGameOutlet.setPreferredSize(new java.awt.Dimension(50, 50));
        removedFromTheGameOutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removedFromTheGameOutletActionPerformed(evt);
            }
        });
        jTabbedPane1.addTab("tab3", removedFromTheGameOutlet);

        cardPanel.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        playerOutlet.add(cardPanel, java.awt.BorderLayout.LINE_END);

        playerOutlet.setBounds(0, 620, 1440, 180);
        layeredOutlet.add(playerOutlet, javax.swing.JLayeredPane.MODAL_LAYER);

        playerPullOutOutlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerPullOutOutlet.setText("^^^");
        playerPullOutOutlet.setOpaque(true);
        playerPullOutOutlet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playerPullOutOutletMouseEntered(evt);
            }
        });
        playerPullOutOutlet.setBounds(760, 786, 30, 14);
        layeredOutlet.add(playerPullOutOutlet, javax.swing.JLayeredPane.PALETTE_LAYER);

        consolePullOutOutlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        consolePullOutOutlet.setText("vvv");
        consolePullOutOutlet.setOpaque(true);
        consolePullOutOutlet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                consolePullOutOutletMouseEntered(evt);
            }
        });
        consolePullOutOutlet.setBounds(700, 0, 40, 14);
        layeredOutlet.add(consolePullOutOutlet, javax.swing.JLayeredPane.PALETTE_LAYER);

        consoleOutlet.setBackground(new java.awt.Color(0, 153, 255));
        consoleOutlet.setMaximumSize(new java.awt.Dimension(32767, 200));
        consoleOutlet.setMinimumSize(new java.awt.Dimension(10, 200));
        consoleOutlet.setPreferredSize(new java.awt.Dimension(1000, 200));
        consoleOutlet.setLayout(new java.awt.BorderLayout());

        consoleScroller.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        consoleScroller.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        consoleScroller.setFocusable(false);

        console.setColumns(20);
        console.setLineWrap(true);
        console.setRows(1);
        console.setWrapStyleWord(true);
        console.setEnabled(false);
        console.setFocusable(false);
        consoleScroller.setViewportView(console);

        consoleOutlet.add(consoleScroller, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("^^^");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
        });
        consoleOutlet.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        consoleOutlet.setBounds(0, 0, 1440, 150);
        layeredOutlet.add(consoleOutlet, javax.swing.JLayeredPane.MODAL_LAYER);

        add(layeredOutlet, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void graveyardOutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graveyardOutletActionPerformed
	
    }//GEN-LAST:event_graveyardOutletActionPerformed

    /**
     * Draw a card when left clicking on the deck
     * 
     * @param evt 
     */
    private void deckOutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deckOutletActionPerformed
	drawCard(1);
    }//GEN-LAST:event_deckOutletActionPerformed

    private void drawCard(int amount) {
	for (int i = 0; i < amount; i++) {
	    CardController drawn = deck.drawCard();
	    if (drawn != null) {
		// add the preview mouse listener to the card upon drawing it and add it to the handcontroller
		handController.addCard(drawn);
	    } else {
		// DECK IS EMPTY
		JOptionPane.showMessageDialog(new JFrame(), "Your deck is empty!", "Can't draw card", JOptionPane.ERROR_MESSAGE);
		break;
	    }
	}
    }
    
    public void prepareAttachingOfCard(CardController toAttach){
	tableController.prepareAttachingOfCard(toAttach);
    }

    private void removedFromTheGameOutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removedFromTheGameOutletActionPerformed
	setStatusMessage(new Date().toString());
    }//GEN-LAST:event_removedFromTheGameOutletActionPerformed

    private void playerPullOutOutletMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerPullOutOutletMouseEntered
	playerOutlet.setVisible(true);
    }//GEN-LAST:event_playerPullOutOutletMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
	playerOutlet.setVisible(false);
	
    }//GEN-LAST:event_jButton1ActionPerformed

    private void consolePullOutOutletMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consolePullOutOutletMouseEntered
	consoleOutlet.setVisible(true);
    }//GEN-LAST:event_consolePullOutOutletMouseEntered

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
	consoleOutlet.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void dissmissHandOutletMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dissmissHandOutletMouseEntered
	playerOutlet.setVisible(false);
    }//GEN-LAST:event_dissmissHandOutletMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    private javax.swing.JTextArea console;
    private javax.swing.JPanel consoleOutlet;
    private javax.swing.JLabel consolePullOutOutlet;
    private javax.swing.JScrollPane consoleScroller;
    private javax.swing.JButton deckOutlet;
    private javax.swing.JPanel deckPanel;
    private javax.swing.JLabel dissmissHandOutlet;
    private javax.swing.JButton graveyardOutlet;
    private javax.swing.JPanel handOutlet;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLayeredPane layeredOutlet;
    private javax.swing.JSplitPane mainSplit;
    private javax.swing.JSplitPane masterSplit;
    private javax.swing.JPanel playerOutlet;
    private javax.swing.JLabel playerPullOutOutlet;
    private javax.swing.JButton removedFromTheGameOutlet;
    private javax.swing.JButton resolvedCard;
    private javax.swing.JPanel resolvedCardOutlet;
    private javax.swing.JSplitPane sideSplit;
    private javax.swing.JLabel statusOutlet;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JPanel tableOutlet;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
    
    private final JMenu sessionMenu;
    
    private MainFrame parent;
    
    private ArrayList<String> removedftgame;
    // drag from hand:
    private boolean draggingFromHand = false;
    private Point draggingFromHandLastLocation = null;
    private String draggingFromHandCardName = null;
    private CardPanel draggingFromHandCardPanel = null;
    private boolean draggingFromHandCardPanelShown = false;
    private boolean dragging = false;
    private CardPanel draggedCard;
    private static final int GRIDX = 20, GRIDY = 20;
    private List<CardPanel> cards = new ArrayList<CardPanel>();
    /**
     * Registered a mouse click
     * 
     * MAYBE: ADD SELECTED STATE TO PANELS AND ALLOW KEYBOARD INTERACTION?
     * 
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
	// determine the card that was clicked
	Component c = tableOutlet.findComponentAt(e.getX(), e.getY());
	if (!(c.getParent() instanceof CardPanel)) {
	    return;
	}
	c = c.getParent(); // change c to the panel
	final CardPanel clickedCard = (CardPanel) c;
	
//	System.out.println("Clicked on "+c.toString());

	final Point clickedLocation = e.getLocationOnScreen();

	// on right click, make a JPopupDialog
	if (e.getButton() == MouseEvent.BUTTON3) {
	    JPopupMenu popup = new JPopupMenu();

	    JMenuItem readItem = new JMenuItem("Read card...");
	    readItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    //
		    //System.out.println("STUB :: Read card clicked");
		    clickedCard.showCard(clickedLocation);
		}
	    });

	    popup.add(readItem);

	    popup.addSeparator();

	    JMenuItem menuItem = new JMenuItem("Exhaust");
	    if (clickedCard.isExhausted()) {
		menuItem.setText("Ready");
	    }
	    menuItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    //System.out.println("Clicked cardPanel: "+clickedCard.getName());
		    clickedCard.toggleExhaust();
		}
	    });
	    popup.add(menuItem);

	    JMenuItem flipItem = new JMenuItem("Flip");
	    flipItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    clickedCard.toggleFlip();
//		    if (state == ONLINE) {
//			connection.sendSnapshot(clickedCard.getGUID(), clickedCard.getSnapshot());
//		    }
		}
	    });
	    popup.add(flipItem);

	    popup.addSeparator();

	    JMenuItem countersItem = new JMenuItem("Set Counters...");
	    countersItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    boolean validInput = false;
		    int res = 0;
		    do {
			try {
			    String resString = JOptionPane.showInputDialog(new JFrame(), "Counters:", clickedCard.getCounters());

			    // close or cancel
			    if (resString == null) {
				break;
			    }

			    res = Integer.parseInt(resString);

			    //if(resString == JOptionPane.CANCEL_OPTION)

			    validInput = true;
			} catch (Exception ex) {
			    System.err.println("Caught an error: " + ex);
			    // learn to enter numbers faggots
			}

		    } while (!validInput);

		    if (validInput) {
			clickedCard.setCounters(res);
//			if (state == ONLINE) {
//			    connection.sendSnapshot(clickedCard.getGUID(), clickedCard.getSnapshot());
//			}
		    }
		}
	    });

//	    popup.add(countersItem);

	    JMenuItem addCounterItem = new JMenuItem("Add Counter");
	    addCounterItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    clickedCard.setCounters(clickedCard.getCounters() + 1);

//		    if (state == ONLINE) {
//			connection.sendSnapshot(clickedCard.getGUID(), clickedCard.getSnapshot());
//		    }
		}
	    });

	    popup.add(addCounterItem);

	    JMenuItem removeCounterItem = new JMenuItem("Remove Counter");
	    removeCounterItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    clickedCard.setCounters(clickedCard.getCounters() - 1);

//		    if (state == ONLINE) {
//			connection.sendSnapshot(clickedCard.getGUID(), clickedCard.getSnapshot());
//		    }
		}
	    });

	    popup.add(removeCounterItem);

	    popup.add(countersItem);

	    popup.addSeparator();

	    JMenuItem controlItem = new JMenuItem("Take Control!");
	    if (clickedCard.isMyCard()) {
		controlItem.setText("Give Control");
	    }
	    controlItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    System.out.println("STUB :: Switching control of card!");
		}
	    });

	    popup.add(controlItem);

	    popup.addSeparator();

	    JMenuItem toHandItem = new JMenuItem("To Hand");
	    toHandItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    //System.out.println("STUB :: Moving card to hand");
		    moveCardToHandFromPlay(clickedCard);
		}
	    });

	    popup.add(toHandItem);

	    JMenuItem graveyardItem = new JMenuItem("To Graveyard");
	    graveyardItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    //System.out.println("STUB :: Moving card to graveyard");
//		    moveCardToGraveyardFromPlay(clickedCard);
		}
	    });

	    popup.add(graveyardItem);

	    JMenuItem toDeckItem = new JMenuItem("To Top Of Deck");
	    toDeckItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    System.out.println("STUB :: Moving card to top of deck");
		}
	    });

	    popup.add(toDeckItem);

	    JMenuItem removeFromGameItem = new JMenuItem("Remove From Game");
	    removeFromGameItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    System.out.println("STUB :: Removing from the game");
		}
	    });

	    popup.add(removeFromGameItem);

	    popup.addSeparator();

	    // add to top, one to top, one to bot, to bot

	    JMenuItem toTopItem = new JMenuItem("Bring To Top");
	    toTopItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
//		    clickedCard.toFront(getTopLayer()+1);

		    // remove it and add it to the back = put on top
		    cards.remove(clickedCard);
		    cards.add(clickedCard);

//		    redrawCard(clickedCard);

		    redrawAllCards();
		}
	    });
	    popup.add(toTopItem);

	    JMenuItem oneUpItem = new JMenuItem("Move One Up");
	    oneUpItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    // remove it and add it to its previous place + 1
		    int index = cards.indexOf(clickedCard);
		    if (index < cards.size() - 1) {
			cards.remove(clickedCard);
			cards.add(index + 1, clickedCard);
		    }

		    redrawAllCards();
		}
	    });
	    popup.add(oneUpItem);

	    JMenuItem oneDownItem = new JMenuItem("Move One Down");
	    oneDownItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    // remove it and add it to its previous place - 1
		    int index = cards.indexOf(clickedCard);
		    if (index > 0) {
			cards.remove(clickedCard);
			cards.add(index - 1, clickedCard);
		    }

		    redrawAllCards();
		}
	    });
	    popup.add(oneDownItem);

	    JMenuItem toBackItem = new JMenuItem("Bring To Back");
	    toBackItem.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
//		    clickedCard.toFront(getTopLayer()+1);

		    // remove it and add it to the front = put in top
		    cards.remove(clickedCard);
		    cards.add(0, clickedCard);

//		    redrawCard(clickedCard);

		    redrawAllCards();
		}
	    });
	    popup.add(toBackItem);

	    popup.show(c, e.getX() - c.getX(), e.getY() - c.getY());
	} else if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) {
	    clickedCard.toggleExhaust();
//	    this.repaint();
	    System.out.println(" should exhaust ");
	    
	} else if (e.getButton() == MouseEvent.BUTTON1 && e.isControlDown()) {
	    
	    System.out.println("should delete");
	    
	    // ctrl+click -> card to graveyard
//	    moveCardToGraveyardFromPlay(clickedCard);
	} 
    }

    /**
     * MAKE SURE THIS ONLY WORKS FOR CARDS THAT I OWN
     * 
     * Maybe: dress up the dragged card (add border or just semi transparent color layer)
     * DOESNT WORK, AT LEAST NOT WITH BORDERS FOR SOME ODD REASON
     * 
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
	// only drag with left mouse button
//	if (e.getButton() == MouseEvent.BUTTON1 && !dragging && !e.isControlDown()) {
	if (e.getButton() == MouseEvent.BUTTON1 && !dragging && !e.isControlDown() && !e.isShiftDown()) {
	    
	    System.out.println("should start drag");
	    
	    if(e.isControlDown()) return;
	    
	    // reset drag and start
	    draggedCard = null;
	    dragging = true;

	    // find component that was clicked
	    Component c = tableOutlet.findComponentAt(e.getX(), e.getY());
	    if (!(c.getParent() instanceof CardPanel)) {
		return;
	    }
	    c = c.getParent(); // change c to the panel

	    // find location of click
	    Point compLoc = c.getLocation();

	    // find card and register it as drag card
	    draggedCard = (CardPanel) c;

//	    System.out.println("Clicked on card " + draggedCard.getCardName() + " on layer " + tableOutlet.getLayer(c));

	    // pick up card
	    draggedCard.setLocation(compLoc);
	    draggedCard.setBounds((int) compLoc.getX(), (int) compLoc.getY(), (int) c.getWidth(), (int) c.getHeight());

	    // remove card from its layer and add to drag layer
	    tableOutlet.remove(draggedCard);
	    tableOutlet.add(draggedCard, JLayeredPane.DRAG_LAYER);

	    // trigger redraw
	    tableOutlet.validate();

	    // set cursor to drag
	    tableOutlet.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}
    }

    /**
     * gets triggered after ctrl-c + alt-c
     * 
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
	dragging = false;
	if (e.getButton() == MouseEvent.BUTTON1) {
//	    if (!dragging || draggedCard == null) {
//		return;
//	    } else {
//		dragging = false;
//	    }
	    if(draggedCard == null){
		return;
	    }

	    // reset cursor
	    tableOutlet.setCursor(null);

	    // remove card from drag layer
	    tableOutlet.remove(draggedCard);

	    // add card to its previous layer
	    tableOutlet.add(draggedCard, new Integer(JLayeredPane.PALETTE_LAYER + cards.indexOf(draggedCard)));

//	    System.out.println("Released card " + draggedCard.getCardName() + " on layer " + tableOutlet.getLayer(draggedCard));

	    // broadcast this!!
	   
	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	
	// is called fast
	
	if (e.getButton() == MouseEvent.BUTTON1) {
	    if (draggedCard == null) {
		return;
	    }
	    if (!dragging) {
		dragging = true;
	    }

	    //	int x = e.getX()+dx;
	    int x = e.getX() - 10;
	    //	int y = e.getY()+dy;
	    int y = e.getY() - 10;

	    // snap to grid
	    x /= GRIDX;
	    x *= GRIDX;

	    y /= GRIDY;
	    y *= GRIDY;
	    
//	    if(x<0)x=0;
//	    if(x+draggedCard.getWidth()>mainPanel.getWidth())
//	    if(y<mainPanel.getLocationOnScreen().getY()) y = mainPanel.getY();
//	    if(y+draggedCard.getHeight()>=mainPanel.getHeight()+mainPanel.getY()) y = mainPanel.getHeight()+mainPanel.getY();

	    // redraw the card at its new location
	    draggedCard.setLocation(x, y);
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void startSession(ConnectionProxy conn) {
	// have connection
//	this.connection = conn;

	// put hero card into play
//	spawnCard(deck.getHero());
	
//	startGame(ONLINE);
    }


    private void resetLayout() {
	// empty hand

	// clear everything on the table
	tablePanel.removeAll();

	// add background again
    }

    private void redrawAllCards() {
	tablePanel.removeAll();

//	for (CardPanel cardPanel : myCards) {
//	    mainPanel.add(cardPanel, new Integer(JLayeredPane.PALETTE_LAYER+myCards.indexOf(cardPanel)));
//	}
	for (int i = 0; i < cards.size(); i++) {
	    tablePanel.add(cards.get(i), new Integer(JLayeredPane.PALETTE_LAYER + i));
	}

	tablePanel.validate();
    }

    private void spawnCard(String cardName) {
//	Card card = model.generateCardByName(model.getCardByName(cardName).getType(), cardName);
//
//	if (card != null) {
//	    CardPanel cardPanel = new CardPanel(card, cards.size());
//	    cardPanel.setBounds(40, 40, 60, 60);
//
//	    addToPlay(cardPanel);
//
////	    if (state == ONLINE) {
////		//send gui update
////		connection.sendGUIMessage(cardPanel);
////	    }
//	}
    }

    /**
     * Supposed to be called by receiveForeignCard
     * 
     * @param cardName
     * @param GUID 
     */
    private void spawnCardWithGUID(String cardName, String GUID) {
//	Card card = model.generateCardByName(model.getCardByName(cardName).getType(), cardName);
//
//	if (card != null) {
//	    CardPanel cardPanel = new CardPanel(card, cards.size(), GUID);
//	    cardPanel.setBounds(40, 40, 60, 60);
//
//	    addToPlay(cardPanel);
//
////	    if (state == ONLINE) {
////		connection.sendGUIMessage(cardPanel);
////	    }
//	}
    }

    private void addToPlay(CardPanel cardPanel) {
	System.out.println("Adding a card to play (" + cardPanel.getCardName() + ")!");

	cards.add(cardPanel);

//	int layer = new Integer(JLayeredPane.PALETTE_LAYER + cards.indexOf(cardPanel));

	tablePanel.add(cardPanel, new Integer(JLayeredPane.PALETTE_LAYER + cards.indexOf(cardPanel)));
	tablePanel.validate();
	tablePanel.repaint();
    }

    private void addToPlay(CardPanel cardPanel, Point loc) {
	cardPanel.setLocation(loc);
	addToPlay(cardPanel);
    }

    private void moveCardToHandFromPlay(CardPanel cardPanel) {
	tablePanel.remove(cardPanel);

	cardPanel.setVisible(false);
//	hand.add(cardPanel.getCard().getName());
//	leftList.setListData(hand.toArray(new String[0]));

	tablePanel.validate();
	tablePanel.repaint();
    }

    // Message Handler protocol
    @Override
    public void receiveTextMessage(String msg) {
//	addToChat("Stranger: " + msg);
    }

    @Override
    public void receiveForeignCard(CardPanel cardPanel) {
	addToPlay(cardPanel);
    }

    @Override
    public void moveCardWithGUIDToLocation(String GUID, Point location) {
	CardPanel moveMe = findCardPanelForGUID(GUID);
	if (moveMe != null) {
	    moveMe.setLocation(location);
	}
	tablePanel.validate();
    }

    @Override
    public void applySnapshotToCardWithGUID(String GUID, CardSnapshot snapshot) {
	CardPanel changeMe = findCardPanelForGUID(GUID);
	if (changeMe != null) {
	    changeMe.applySnapshot(snapshot);
	}
	tablePanel.validate();
    }
    
    
    private void rollDie(int sides) {
	int outcome = 1 + (int) (Math.random() * sides);
//	sendMessage("rolled a " + outcome + " with a " + sides + " sided die!");
    }

    private CardPanel findCardPanelForGUID(String GUID) {
	CardPanel ret = null;
	for (CardPanel cardPanel : cards) {
	    if (cardPanel.getGUID().compareTo(GUID) == 0) {
		ret = cardPanel;
		break;
	    }
	}
	return ret;
    }

    private boolean isMyCardPanel(String GUID) {
	boolean ret = false;
	for (CardPanel cardPanel : cards) {
	    if (cardPanel.getGUID().compareTo(GUID) == 0) {
		ret = true;
		break;
	    }
	}
	return ret;
    }
}
