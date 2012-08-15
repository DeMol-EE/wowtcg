/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConnectionFrame.java
 *
 * Created on Dec 14, 2011, 10:10:56 PM
 */
package viewControllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import javax.imageio.IIOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Deck;

/**
 *
 * @author Warkst
 */
public class ConnectionFrame extends javax.swing.JFrame {

    private final MainFrame delegate;
    private final Deck deck;
    
    /** Creates new form ConnectionFrame */
    public ConnectionFrame(MainFrame delegate, Deck deck) {
	this.delegate = delegate;
	this.deck = deck;
	
	initComponents();
	
	this.setLocationRelativeTo(null);
	
//	this.setLocation(delegate.getX()+400, delegate.getY()+200);
	
	try {
	    this.ipLabel.setText("You ip address: " + getRouterIP());
	} catch (Exception ex) {
	    // silent fail much
	    this.ipLabel.setText("visit www.whatismyip.com");
	}
	
	// change to name of deck?
	this.deckLabel.setText("Deck: "+deck.getName()+" ("+deck.getClazz()+")");
	
	this.portLabel.setText("Service will attempt to run on port 9856");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ipLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        connectButton = new javax.swing.JButton();
        hostButton = new javax.swing.JButton();
        deckLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ipLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ipLabel.setText("ip");

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        connectButton.setText("Connect to...");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        jPanel1.add(connectButton);

        hostButton.setText("Host");
        hostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostButtonActionPerformed(evt);
            }
        });
        jPanel1.add(hostButton);

        deckLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deckLabel.setText("deck");

        portLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        portLabel.setText("port");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, deckLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .add(ipLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, portLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(ipLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(portLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deckLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostButtonActionPerformed
	new ConnectingFrame(delegate, this).setVisible(true);
	this.setVisible(false);
    }//GEN-LAST:event_hostButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
	String result = JOptionPane.showInputDialog(new JFrame(), "Host ip: ");
	if (result != null) {
	    if (checkIp(result)) {
		// connect
		new ConnectingFrame(delegate, this, result).setVisible(true);
		this.setVisible(false);
	    } else {
		JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid IP address!", "Invalid IP", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }//GEN-LAST:event_connectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel deckLabel;
    private javax.swing.JButton hostButton;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel portLabel;
    // End of variables declaration//GEN-END:variables

    private boolean checkIp(String sip) {
	String[] parts = sip.split("\\.");
	for (String s : parts) {
	    try {
		int i = Integer.parseInt(s);
		if (i < 0 || i > 255) {
		    return false;
		}
	    } catch (Exception e) {
		// fakka joeeee
		return false;
	    }

	}
	return true;
    }
    
    private String getRouterIP() throws IOException {
	Process result;
	result = Runtime.getRuntime().exec("traceroute -m 2 www.google.com");
	BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream()));
	output.readLine(); // read first line
	String thisLine = output.readLine(); // read second line
	StringTokenizer st = new StringTokenizer(thisLine);
	st.nextToken();
	st.nextToken();
	String gateway = st.nextToken();
	
	if(!checkIp(gateway)) throw new IIOException("Invalid ip");
	
	return gateway;

    }
}