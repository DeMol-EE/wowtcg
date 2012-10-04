/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import images.ImageLoader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * This class extends JButton so it displays a specified image as a JToolTip when hovering the mouse over it.
 * 
 * @author Robin jr
 */
public class ImageToolTipButton extends JButton {

    private final ImageIcon coreImage;
    private final ImageIcon altImage;
    private final Dimension imageDimension;
    private boolean useAlt;

    public ImageToolTipButton(ImageIcon image, Dimension imageDimension) {
	super();
	this.coreImage = image;
	this.imageDimension = imageDimension;
	this.altImage = ImageLoader.scaleImage(ImageLoader.createImageIconAtHomeLocation("template.png"), imageDimension);
	this.useAlt = false;
	this.setToolTipText("asdf");
	this.setMinimumSize(new Dimension(0, 0));
	this.setBackground(Color.BLUE);
    }
    
    public void useAlt(boolean useAlt){
	this.useAlt = useAlt;
    }
    
    public void toggleUseAlt(){
	useAlt = !useAlt;
    }
    
    @Override
    public JToolTip createToolTip() {
	return new ImageToolTip();
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
	return new Point(getSize().width/2, -imageDimension.height);
    }

    private class ImageToolTip extends JToolTip {

	public ImageToolTip() {
	    setUI(new ImageToolTipUI());
	}

	private class ImageToolTipUI extends MetalToolTipUI {

	    @Override
	    public void paint(Graphics g, JComponent c) {
		g.drawImage(useAlt? altImage.getImage() : coreImage.getImage(), 0, 0, c);
	    }

	    @Override
	    public Dimension getPreferredSize(JComponent c) {
		return imageDimension;
	    }
	}
    }
}
