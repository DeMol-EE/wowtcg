package images;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class ImageLoader {

    /**
     * Static call to load an image into an imageIcon
     *
     * @param path Name of the image to load, inside images/sprites/
     * @return An ImageIcon object holding the desired image at given path
     *
     * @deprecated 
     */
    public static ImageIcon createImageIcon(String path) {
//	java.net.URL imgURL = ImageLoader.class.getResource("sprites/" + path);
//	if (imgURL != null) {
//	    return new ImageIcon(imgURL);
//	} else {
//	    System.err.println("IMAGELOADER: Couldn't find file: " + path);
//	    return new ImageIcon(ImageLoader.class.getResource("sprites/NOIMG.GIF"));
//	}
	System.out.println("TRYING TO LOAD IMAGE FROM PATH: "+path);
	File test = new File(path);
	if(test.exists()){
	    return new ImageIcon(path);
	} else {
	    System.err.println("---::: IMAGE LOADING FAILED: USING OLD LOADER :::---");
	    return createImageIconAtHomeLocation(path);
	    //return new ImageIcon(ImageLoader.class.getResource("sprites/NOIMG.GIF"));
	}
    }

    /**
     * Static call that allows for calling images at a different location than
     * images/sprites/ package.
     *
     * @param path Relative path of the desired image
     * @param baseClass Class whose folder should be used to set relative path
     * @return An ImageIcon object holding the desired image at given path
     */
    public static ImageIcon createImageIcon(String path, Class baseClass) {
	java.net.URL imgURL = baseClass.getResource(path);
	if (imgURL != null) {
	    return new ImageIcon(imgURL);
	} else {
	    System.err.println("IMAGELOADER: Couldn't find file: " + path);
	    return new ImageIcon(ImageLoader.class.getResource("sprites/NOIMG.GIF"));
	}
    }
    
    public static ImageIcon createImageIconAtHomeLocation(String path) {
	java.net.URL imgURL = ImageLoader.class.getResource(path);
	if (imgURL != null) {
	    return new ImageIcon(imgURL);
	} else {
	    System.err.println("IMAGELOADER: Couldn't find file: " + path);
	    return new ImageIcon(ImageLoader.class.getResource("NOIMG.GIF"));
	}
    }
    
    public static ImageIcon scaleImage(ImageIcon imgIcon, Dimension size){
	return new ImageIcon(imgIcon.getImage().getScaledInstance((int)(size.getWidth()), (int)(size.getHeight()), Image.SCALE_SMOOTH));
    }
}
