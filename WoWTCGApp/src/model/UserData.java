/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Warkst
 */
public class UserData {

    private String[] backgrounds;
    private String userBackground;
    private JFileChooser deckFileChooser;
    
    private String dataPath = "";
    private String decksFolder;

    public UserData() {
//	System.out.println("STUB:: Starting the user model, loading decks and user preferences...");

	backgrounds = new String[11];
	for (int i = 0; i < 11; i++) {
	    backgrounds[i] = "wow_bg" + (i + 1) + ".jpg";
	}

	try {
	    // read user data
	    BufferedReader in = new BufferedReader(new FileReader("UserData.udf"));
	    
	    // read background
	    userBackground=in.readLine();
	    
	    // read decks url
	    decksFolder=in.readLine();
	    
	    // read data path
	    dataPath=in.readLine();
	    if(dataPath==null) dataPath = "";
	    if(dataPath.length()>0 && dataPath.charAt(dataPath.length()-1)!=File.separatorChar) dataPath+=File.separator;
	} catch (FileNotFoundException ex) {
	    System.err.println("Error loading user file! "+ex+"\nUsing default settings...");
	    userBackground = backgrounds[10];
	    decksFolder = null;
	    dataPath = "";
	} catch (IOException ex) {
	    System.err.println("Problem reading from user file! "+ex+"\nUsing default settings...");
	    userBackground = backgrounds[10];
	    decksFolder = null;
	    dataPath = "";
	}
	
	// initiate deckFileChooser
	this.deckFileChooser = new JFileChooser(decksFolder);
	
	// set filter for wow tcg deck files
	this.deckFileChooser.setFileFilter(new DeckFileFilter());
    }
    
    public void writeUserDataToFile(){
	try {
	    PrintWriter out = new PrintWriter("UserData.udf");
	    out.println(userBackground);
	    //decksFolder = deckFileChooser.getCurrentDirectory().getAbsolutePath();
	    if(decksFolder!=null)out.println(decksFolder);
	    if(dataPath!=null)out.println(dataPath);
	    out.flush();
	    out.close();
	} catch (FileNotFoundException ex) {
	    System.err.println("Error writing user data to file! Data is not saved!!\n"+ex);
	    
	    // TODO: Alert user
	}
    }
    
    public void setDecksFolder(File decksFolder){
	this.decksFolder = decksFolder.getAbsolutePath();
	
	this.deckFileChooser.setCurrentDirectory(decksFolder);
	
	writeUserDataToFile();
    }

    public String[] getBackgrounds() {
	return backgrounds;
    }

    public String getUserBackground() {
	return userBackground;
    }

    public void setUserBackground(String userBackground) {
	this.userBackground = userBackground;
	
	writeUserDataToFile();
    }

    public JFileChooser getFileChooser() {
	return deckFileChooser;
    }
    
    public String getDataPath(){
	return dataPath;
    }
    
    public String getImagePath(){
	return dataPath+File.separator+"images"+File.separator;
    }
    
    public void setDataPath(String dataPath){
	if(dataPath.charAt(dataPath.length()-1)!=File.separatorChar) dataPath+=File.separator;
	this.dataPath = dataPath;
	
	writeUserDataToFile();
    }

    private class DeckFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
	    if (f.isDirectory()) {
		return true;
	    }

	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');

	    if (i > 0 && i < s.length() - 1) {
		ext = s.substring(i + 1).toLowerCase();
	    }

	    if (ext!=null && ext.equalsIgnoreCase("wtd")) {
		return true;
	    } else {
		return false;
	    }
	}

	@Override
	public String getDescription() {
	    return "World of Warcraft TCG Decks";
	}
    }
}
