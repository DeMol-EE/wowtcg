package images;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;


// ADD CACHING?


public class ImageLoader {

    private static HashMap<String, ImageIcon> images = new HashMap<String, ImageIcon>();
    
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
    
    /**
     * Creates an ImageIcon with the image at the specified path of the specified type, if said image exists. If it doesn't,
     * a default image from the shipped and packed resources is loaded, as specified in the defaultPath.
     * This method uses caching to access images faster and to reduce IO to the drive directory containing the images.
     * 
     * @param path Full path of the image to be loaded.
     * @param type Extension of the image to be loaded.
     * @param defaultPath Path of the default image to be loaded if there is no image at "path".
     * @return Returns a new or cached ImageIcon of the image at the given path.
     */
    public static ImageIcon createImageIconWithDefaultAtHomeLocation(String path, String type, String defaultPath) {
	if(images.containsKey(path+"."+type)) return images.get(path+"."+type);
	
	File imgFile = new File(path+"."+type);
	if (imgFile.exists()) {
	    images.put(path+"."+type, new ImageIcon(imgFile.getPath()));
	    return images.get(path+"."+type);
	} else {
	    System.err.println("IMAGELOADER: Couldn't find file \""+path+"."+type+"\" at path: " + imgFile.getPath());
	    return new ImageIcon(ImageLoader.class.getResource(defaultPath));
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
    
    public static ImageIcon createImageIconAtHomeLocationWithDefault(String path, String defaultPath) {
	java.net.URL imgURL = ImageLoader.class.getResource(path);
	if (imgURL != null) {
	    return new ImageIcon(imgURL);
	} else {
	    System.err.println("IMAGELOADER: Couldn't find file: " + path);
	    return new ImageIcon(ImageLoader.class.getResource(defaultPath));
	}
    }
    
    public static ImageIcon scaleImage(ImageIcon imgIcon, Dimension size){
	return new ImageIcon(imgIcon.getImage().getScaledInstance((int)(size.getWidth()), (int)(size.getHeight()), Image.SCALE_SMOOTH));
    }
}
