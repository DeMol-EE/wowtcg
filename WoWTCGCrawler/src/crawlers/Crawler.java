/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Warkst
 */
public class Crawler implements Runnable {

    private static final String CARD_DELIMITER = "::";
    private static final String FIELD_DELIMITER = ";;";
    private static final String IMAGE_DELIMITER = "onmouseover=\"return overlib('', BACKGROUND, '";
    public static final int NONE = 0;
    public static final int CRAWL = 1;
    public static final int PARSE = 2;
    public static final int BOTH = 3;
    private String BASE_URL = "http://www.wowtcgdb.com/";
    private String SECTION_URL;
    private String EXTENSION = ".aspx";
    private String IMAGES = "images/";
    private String IMG_FORMAT = "png";
    private int type;
    private boolean recording = false;
    private boolean logging = true;

    public Crawler(String section, int type) {
	this.SECTION_URL = section;
	this.type = type;
    }

    public Crawler(String section, String baseURL, int type) {
	this.SECTION_URL = section;
	this.BASE_URL = baseURL;
	this.type = type;
    }

    @Override
    public void run() {
	if (type == CRAWL) {
	    downloadFromLink(BASE_URL + SECTION_URL + EXTENSION);
	} else if (type == PARSE) {
	    parseDataFromFile(SECTION_URL);
	} else if (type == BOTH) {
	    downloadFromLink(BASE_URL + SECTION_URL + EXTENSION);
	    parseDataFromFile(SECTION_URL);
	}
    }

    private void downloadFromLink(String link) {
	try {
	    System.out.println("Crawler of section \"" + SECTION_URL + "\" starting!");

	    File output = new File("Log_" + SECTION_URL + ".txt");
	    PrintWriter out = new PrintWriter(output);

	    URL url = new URL(link);
	    BufferedReader din = new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream())));

	    System.out.println("Downloaded page source for \""+ SECTION_URL+"\".");
	    
	    String s;
	    int i = 0;
	    int imgCount = 0;
	    while ((s = din.readLine()) != null) {
		// each tr of a card has background color #F7F7DE, so start reading from there
		if (s.contains("#F7F7DE")) {
		    if(!recording && logging) System.out.println("Turning ON recording for \""+SECTION_URL+"\".");
		    recording = true;
		}
		
		// stop recording at the end of the data table
		if (recording && s.contains("</table>")){
		    recording = false;
		    if(logging) System.out.println("Turning OFF recording for \""+SECTION_URL+"\".");
		}

		if (recording) {
		    if (s.contains("onmouseover=\"return overlib('', BACKGROUND, ")) {
			// download image
			String start = "BACKGROUND, '";
			String imgPath = s.substring(s.indexOf(start)+start.length(), s.indexOf("'", s.indexOf(start)+start.length()));
			URL imgURL = new URL(BASE_URL+"/"+imgPath);
			BufferedImage img = ImageIO.read(imgURL);
			String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1, imgPath.lastIndexOf("."));
			ImageIO.write(img, IMG_FORMAT, new File(IMAGES+imgName+"."+IMG_FORMAT));
			imgCount++;
			if(logging)System.out.println("Downloaded image \""+imgName+"\"!");
		    }
		    out.println(s);
		}

		if (logging) {
		    i++;
		    if (i % 25 == 0) {
			System.out.println("Scanned " + i + " lines (saved "+imgCount+" images)");
		    }
		}
	    }
	    out.flush();
	    out.close();

	    System.out.println("Crawler of section \"" + SECTION_URL + "\" finished!\n**************************************************");


	} catch (MalformedURLException ex) {
	    System.err.println("MalformedURLException: " + ex);
	    System.exit(1);
	} catch (IOException ex) {
	    System.err.println("IOException: " + ex);
	    System.exit(2);
	}
    }

    private void parseDataFromFile(String file) {
	try {
	    System.out.println("Parser of section \"" + SECTION_URL + "\" starting!");

	    int counter = 0;

	    // parse file
	    BufferedReader in = new BufferedReader(new FileReader("Log_" + SECTION_URL + ".txt"));

	    StringBuilder builder = new StringBuilder();
	    String str;

	    while ((str = in.readLine()) != null) {
		builder.append(str);
	    }

	    in.close();

//	    extractData(builder.toString());

	    String data = builder.toString();

	    PrintWriter out = new PrintWriter("Data_" + SECTION_URL + ".txt");

	    if (SECTION_URL.equalsIgnoreCase("type-hero")) {
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String race;
		    String clazz;
		    String professions;
		    String talent;
		    String faction;
		    String rules;
		    int health;
		    int nr;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    // ---------

		    // skip to link
		    data = data.substring(data.indexOf("<a"));
		    // read content
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);

		    // read race
		    // ---------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    race = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(race).append(FIELD_DELIMITER);

		    // read class
		    // ----------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    clazz = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(clazz).append(FIELD_DELIMITER);
		    
		    // read professions
		    // ----------------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    professions = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(professions).append(FIELD_DELIMITER);
		    
		    // read talent
		    // -----------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    talent = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(talent).append(FIELD_DELIMITER);

		    // read faction
		    // ------------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    faction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(faction).append(FIELD_DELIMITER);

		    // read rules
		    // ----------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read health
		    // -----------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    health = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(health).append(FIELD_DELIMITER);

		    // read nr
		    // -------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read set
		    // --------

		    // skip to font tag
		    data = data.substring(data.indexOf("<font", 1));
		    // read content
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

//		System.out.print("Card::"+cardBuilder);
		    //	    CardAlly ally = new CardAlly(rules, atkType, name, "Ally", "", race, clazz, tags, faction, "", cost, atk, health, nr, rarity, set);
		    //	    System.cardBuilder.appendln("");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}

	    } else if (SECTION_URL.equalsIgnoreCase("type-ally")) {
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String race;
		    String clazz;
		    String faction;
		    String tags;
		    String cost;
		    String rules;
		    int atk;
		    String atkType;
		    int health;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);

		    // read race
		    data = data.substring(data.indexOf("<font", 1));
		    race = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(race).append(FIELD_DELIMITER);

		    // read class
		    data = data.substring(data.indexOf("<font", 1));
		    clazz = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(clazz).append(FIELD_DELIMITER);

		    // read faction
		    data = data.substring(data.indexOf("<font", 1));
		    faction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(faction).append(FIELD_DELIMITER);

		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);

		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    try{
			cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    } catch(Exception e){
			cost = " ";
		    }
		    cardBuilder.append(cost).append(FIELD_DELIMITER);

		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read ATK
		    data = data.substring(data.indexOf("<font", 1));
		    atk = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(atk).append(FIELD_DELIMITER);

		    // read ATKType
		    data = data.substring(data.indexOf("<font", 1));
		    atkType = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(atkType).append(FIELD_DELIMITER);

		    // read health
		    data = data.substring(data.indexOf("<font", 1));
		    health = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(health).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

//		System.out.print("Card::"+cardBuilder);
		    //	    CardAlly ally = new CardAlly(rules, atkType, name, "Ally", "", race, clazz, tags, faction, "", cost, atk, health, nr, rarity, set);
		    //	    System.cardBuilder.appendln("");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-quest")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String faction;
		    String rules;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);

		    // read faction
		    data = data.substring(data.indexOf("<font", 1));
		    faction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(faction).append(FIELD_DELIMITER);

		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-ability")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
//		while (data.contains("<tr") && data.indexOf("</table>") > data.indexOf("<tr")) {
		while (data.contains("<tr")) {
		    String name;
		    String supertype;
		    String subtype;
		    String talent;
		    String faction;
		    String tags;
		    String restriction;
		    String cost;
		    String rules;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read supertype
		    data = data.substring(data.indexOf("<font", 1));
		    supertype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(supertype).append(FIELD_DELIMITER);
		    
		    // read subtype
		    data = data.substring(data.indexOf("<font", 1));
		    subtype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(subtype).append(FIELD_DELIMITER);
		    
		    // read talent
		    data = data.substring(data.indexOf("<font", 1));
		    talent = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(talent).append(FIELD_DELIMITER);
		    
		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);
		    
		    // read faction
		    data = data.substring(data.indexOf("<font", 1));
		    faction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(faction).append(FIELD_DELIMITER);

		    // read restriction
		    data = data.substring(data.indexOf("<font", 1));
		    restriction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(restriction).append(FIELD_DELIMITER);
		    
		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    try{
			cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    } catch(Exception e){
			cost = " ";
		    }
		    cardBuilder.append(cost).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-weapon")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String supertype; // go to race
		    String subtype;
		    String tags;
		    String restriction;
		    String cost;
		    int strikeCost; // go to health
		    String rules;
		    int atk;
		    String atktype;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read supertype
		    data = data.substring(data.indexOf("<font", 1));
		    supertype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(supertype).append(FIELD_DELIMITER);
		    
		    // read subtype
		    data = data.substring(data.indexOf("<font", 1));
		    subtype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(subtype).append(FIELD_DELIMITER);
		    
		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);
		    
		    // read restriction
		    data = data.substring(data.indexOf("<font", 1));
		    restriction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(restriction).append(FIELD_DELIMITER);
		    
		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(cost).append(FIELD_DELIMITER);
		    
		    // read strikecost
		    data = data.substring(data.indexOf("<font", 1));
		    strikeCost = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(strikeCost).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read atk
		    data = data.substring(data.indexOf("<font", 1));
		    atk = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(atk).append(FIELD_DELIMITER);
		    
		    // read atktype
		    data = data.substring(data.indexOf("<font", 1));
		    atktype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(atktype).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-armor")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String subtype;
		    String tags;
		    String restriction;
		    String cost;
		    String rules;
		    int def; // to go health
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read subtype
		    data = data.substring(data.indexOf("<font", 1));
		    subtype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(subtype).append(FIELD_DELIMITER);
		    
		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);
		    
		    // read restriction
		    data = data.substring(data.indexOf("<font", 1));
		    restriction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(restriction).append(FIELD_DELIMITER);
		    
		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(cost).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read atk
		    data = data.substring(data.indexOf("<font", 1));
		    def = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(def).append(FIELD_DELIMITER);
		    
		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-armorset")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String subtype;
		    String tags;
		    String restriction;
		    String cost;
		    String rules;
		    int def; // to go health
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read subtype
		    data = data.substring(data.indexOf("<font", 1));
		    subtype = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(subtype).append(FIELD_DELIMITER);
		    
		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);
		    
		    // read restriction
		    data = data.substring(data.indexOf("<font", 1));
		    restriction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(restriction).append(FIELD_DELIMITER);
		    
		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(cost).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read atk
		    data = data.substring(data.indexOf("<font", 1));
		    def = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(def).append(FIELD_DELIMITER);
		    
		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-item")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String tags;
		    String restriction;
		    String cost;
		    String rules;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read tags
		    data = data.substring(data.indexOf("<font", 1));
		    tags = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(tags).append(FIELD_DELIMITER);
		    
		    // read restriction
		    data = data.substring(data.indexOf("<font", 1));
		    restriction = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(restriction).append(FIELD_DELIMITER);
		    
		    // read cost
		    data = data.substring(data.indexOf("<font", 1));
		    cost = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(cost).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    } else if (SECTION_URL.equalsIgnoreCase("type-location")){
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("width=\"900\">") + 12);
//		data = data.substring(data.indexOf("/tr>") + 4);

		// every card is a row in the table
		while (data.contains("<tr")) {
		    String name;
		    String rules;
		    int nr;
		    char rarity;
		    String set;

		    StringBuilder cardBuilder = new StringBuilder();
		    cardBuilder.append(CARD_DELIMITER);

		    // skip image
		    data = data.substring(data.indexOf(IMAGE_DELIMITER)+IMAGE_DELIMITER.length());
		    String img = data.substring(data.indexOf("medium/")+7, data.indexOf(".jpg"));
		    data = data.substring(data.indexOf("/td>") + 4);
		    cardBuilder.append(img).append(FIELD_DELIMITER);

		    // read name
		    data = data.substring(data.indexOf("<a"));
		    name = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</a")));
		    cardBuilder.append(name).append(FIELD_DELIMITER);
		    
		    // read rules
		    data = data.substring(data.indexOf("<font", 1));
		    rules = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(rules).append(FIELD_DELIMITER);

		    // read nr
		    data = data.substring(data.indexOf("<font", 1));
		    nr = getIntFromString(cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))));
		    cardBuilder.append(nr).append(FIELD_DELIMITER);

		    // read rarity
		    data = data.substring(data.indexOf("<font", 1));
		    rarity = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font"))).charAt(0);
		    cardBuilder.append(rarity).append(FIELD_DELIMITER);

		    // read set
		    data = data.substring(data.indexOf("<font", 1));
		    set = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</font")));
		    cardBuilder.append(set).append(FIELD_DELIMITER).append("\n");

		    out.print(cardBuilder.toString());
		    out.flush();

		    counter++;

		    data = data.substring(data.indexOf("</tr>") + 5);
		}
	    }

	    out.close();
	    System.out.println("Parser of section \"" + SECTION_URL + "\" finished after parsing " + counter + " cards!");

	} catch (MalformedURLException ex) {
	    System.err.println("MalformedURLException: " + ex);
	    System.exit(1);
	} catch (IOException ex) {
	    System.err.println("IOException: " + ex);
	    System.exit(2);
	}
    }
    
    /**
     * Doesn't quite work
     * 
     * @param file
     * @param types
     * @param tags
     * @deprecated
     */
    @Deprecated
    private void parseDataFromFile(String file, ArrayList<Object> types, ArrayList<String> tags){
	try {
	    System.out.println("Parser of section \"" + SECTION_URL + "\" starting!");

	    int counter = 0;

	    // parse file
	    BufferedReader in = new BufferedReader(new FileReader("Log_" + SECTION_URL + ".txt"));

	    StringBuilder builder = new StringBuilder();
	    String str;

	    while ((str = in.readLine()) != null) {
		builder.append(str);
	    }

	    in.close();

	    String data = builder.toString();

	    PrintWriter out = new PrintWriter("Data_" + SECTION_URL + ".txt");
	
	    data = data.substring(data.indexOf("width=\"900\">") + 12);
	    data = data.substring(data.indexOf("width=\"900\">") + 12);
	    data = data.substring(data.indexOf("width=\"900\">") + 12);
	    data = data.substring(data.indexOf("/tr>") + 4);

	    // every card is a row in the table
	    while (data.contains("<tr") && data.indexOf("</table>") > data.indexOf("<tr")) {
		StringBuilder cardBuilder = new StringBuilder();
		cardBuilder.append(CARD_DELIMITER);
		
		for (int i = 0; i < types.size(); i++) {
		    Object o = types.get(i);
		    String tag = tags.get(i);
		    
		    if(tag.compareToIgnoreCase("a")!=0) data = data.substring(data.indexOf("<"+tag, 1));
		    else data = data.substring(data.indexOf("<"+tag));
		    String extracted = cleanUpString(data.substring(data.indexOf(">") + 1, data.indexOf("</"+tag)));
		    if(o instanceof Integer) cardBuilder.append(getIntFromString(extracted));
		    else if (o instanceof Character) cardBuilder.append(extracted.charAt(0));
		    else cardBuilder.append(extracted);
		    
		    cardBuilder.append(FIELD_DELIMITER);
		}
		
		out.print(cardBuilder.toString());
		out.flush();

		counter++;

		data = data.substring(data.indexOf("</tr>") + 5);
	    }
	
	    out.close();
	    System.out.println("Parser of section \"" + SECTION_URL + "\" finished after parsing " + counter + " cards!");

	} catch (MalformedURLException ex) {
	    System.err.println("MalformedURLException: " + ex);
	    System.exit(1);
	} catch (IOException ex) {
	    System.err.println("IOException: " + ex);
	    System.exit(2);
	}
    }

    private String cleanUpString(String input) {
	return input.replace("&nbsp;", "")
		.replaceAll("<img src=\"images/icon-exhaust.gif\">", "[exhaust]")
		.replaceAll("<img src=\"images/icon-exhaust.gif\"/>", "[exhaust]")
		.replaceAll("<img src=\"images/icon-arrow.gif\">", "->")
		.replaceAll("<img src=\"images/icon-arrow.gif\"/>", "->")
		.replaceAll("<br>", "<br/>");
    }

    private int getIntFromString(String input) {
	try {
	    int ret = Integer.parseInt(input);
	    return ret;
	} catch (Exception e) {
	    return 0;
	}
    }
}
