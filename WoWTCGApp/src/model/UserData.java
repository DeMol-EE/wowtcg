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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Warkst
 */
public class UserData {

    private Model model;
    private String[] backgrounds;
    private String userBackground;
    private JFileChooser deckFileChooser;
    
    private String decksFolder;

    public UserData(Model model) {
	this.model = model;

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
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Error loading user file! "+ex+"\nUsing default settings...");
	    userBackground = backgrounds[10];
	    decksFolder = null;
	} catch (IOException ex) {
	    System.err.println("Problem reading from user file! "+ex+"\nUsing default settings...");
	    userBackground = backgrounds[10];
	    decksFolder = null;
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
	    out.flush();
	    out.close();
	} catch (FileNotFoundException ex) {
	    System.err.println("Error writing user data to file! Data is not saved!!\n"+ex);
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

    public Deck loadDeckFromFile(File deckFile) throws FileNotFoundException {
	try {
	    BufferedReader in = new BufferedReader(new FileReader(deckFile));

	    String hero = in.readLine();
	    String clazz = in.readLine();
	    ArrayList<Card> cards = new ArrayList<Card>();

	    String str;
	    while ((str = in.readLine()) != null) {
		int quantity = Integer.parseInt(str.substring(0, str.indexOf(";;")));
		str = str.substring(str.indexOf(";;") + 2);
		String type = str.substring(0, str.indexOf(";;"));
		str = str.substring(str.indexOf(";;") + 2);
		String name = str.substring(0);

		for (int i = 0; i < quantity; i++) {
		    Card aCard = model.generateCardByName(type, name);

		    cards.add(aCard);
		}
	    }

	    return new Deck(deckFile.getName(), hero, clazz, cards);
	} catch (IOException ex) {
	    throw new FileNotFoundException("IOException occurred while trying to read from file!");
	}
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

	    if (ext.equalsIgnoreCase("wtd")) {
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
