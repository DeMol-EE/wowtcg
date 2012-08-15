/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Warkst
 */
public class ImagePanelBean extends JPanel implements Serializable {    
    protected ImageIcon backgroundImage = ImageLoader.scaleImage(ImageLoader.createImageIconAtHomeLocation("NOIMG.GIF"), new Dimension(100,100));

    public ImagePanelBean() {
	setSize(new Dimension(1440, 880));
	setLayout(null);
    }
    
    public ImageIcon getBackgroundImage() {
	return backgroundImage;
    }

    public void setBackgroundImage(ImageIcon backgroundImage) {
//	System.out.println("Setting bg image ("+backgroundImage+") with size "+getSize());
	this.backgroundImage = ImageLoader.scaleImage(backgroundImage, getSize());
	this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g); // hmm
	g.drawImage(backgroundImage.getImage(), 0, 0, getBackground(), null);
    }   
}
