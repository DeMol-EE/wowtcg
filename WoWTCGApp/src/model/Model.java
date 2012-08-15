/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 * Offers dynamic access to the files downloaded by the webcrawler.
 * Oh pointers, how I would love you right now...
 *
 * @author Warkst
 */
public class Model {
    private static final String CARD_DELIMITER = "::";
    private static final String FIELD_DELIMITER = ";;";
    
    private ArrayList<String> cardTypes = new ArrayList<String>();
    private ArrayList<String> classes = new ArrayList<String>();
    
    private HashMap<String, CardAlly> allies = new HashMap<String, CardAlly>();
    private HashMap<String, CardHero> heroes = new HashMap<String, CardHero>();
    private HashMap<String, CardQuest> quests = new HashMap<String, CardQuest>();
    private HashMap<String, CardAbility> abilities = new HashMap<String, CardAbility>();
    private HashMap<String, CardWeapon> weapons = new HashMap<String, CardWeapon>();
    private HashMap<String, CardArmor> armors = new HashMap<String, CardArmor>();
    private HashMap<String, CardArmorset> armorsets = new HashMap<String, CardArmorset>();
    private HashMap<String, CardItem> items = new HashMap<String, CardItem>();
    private HashMap<String, CardLocation> locations = new HashMap<String, CardLocation>();
    
    public Model(){
	// load all data from crawled files
	System.out.println("Starting the data model, loading all cards from the system...");
	
	readHeroCards();
	readAllyCards();
	readQuestCards();
	readAbilityCards();
	readWeaponCards();
	readArmorCards();
	readArmorsetCards();
	readItemCards();
	readLocationCards();
	
	System.out.println("Data model launched, all cards loaded!");
    }
    
    private void readHeroCards(){
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-hero.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String race = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String clazz = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String professions = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String talent = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String faction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int health = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardHero hero = new CardHero(name, race, clazz, faction, health, nr, set, rules, talent, professions);
		    heroes.put(hero.getName(), hero);
		}
	    }
	    in.close();
	    
	    classes.add("All");
	    // set class types
	    classes.add("Death Knight");
	    classes.add("Druid");
	    classes.add("Hunter");
	    classes.add("Mage");
	    classes.add("Paladin");
	    classes.add("Priest");
	    classes.add("Rogue");
	    classes.add("Shaman");
	    classes.add("Warlock");
	    classes.add("Warrior");
	    
	    System.out.println("\t...hero cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-hero.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-hero.txt");
	}
    }
    
    private void readAllyCards(){
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-ally.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String race = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String clazz = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String faction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int atk = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String atkType = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int health = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardAlly ally = new CardAlly(atkType, name, race, clazz, tags, faction, cost, atk, health, nr, rarity, set, rules);
		    allies.put(ally.getName(), ally);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...ally cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-ally.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-ally.txt");
	}
    }
    
    private void readQuestCards(){
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-quest.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String faction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardQuest quest = new CardQuest(name, faction, nr, rarity, set, rules);
		    quests.put(quest.getName(), quest);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...quest cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-quest.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-quest.txt");
	}
    }

    private void readAbilityCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-ability.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String supertype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String subtype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String talent = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String faction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String restriction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardAbility ability = new CardAbility(name, supertype, subtype, talent, faction, tags, restriction, cost, rules, nr, rarity, set);
		    abilities.put(ability.getName(), ability);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...ability cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-ability.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-ability.txt");
	}    
    }
    
    private void readWeaponCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-weapon.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String supertype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String subtype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String restriction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int strikeCost = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int atk = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String atktype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardWeapon weapon = new CardWeapon(name, supertype, subtype, tags, restriction, cost, strikeCost, rules, atk, atktype, nr, rarity, set);
		    weapons.put(weapon.getName(), weapon);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...weapon cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-weapon.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-weapon.txt");
	}    
    }
    
    private void readArmorCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-armor.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String subtype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String restriction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int def = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardArmor armor = new CardArmor(name, subtype, tags, restriction, cost, def, nr, rarity, set, rules);
		    armors.put(armor.getName(), armor);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...armor cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-armor.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-armor.txt");
	}    
    }
    
    private void readArmorsetCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-armorset.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String subtype = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String restriction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int def = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardArmorset armorset = new CardArmorset(name, subtype, tags, restriction, cost, def, nr, rarity, set, rules);
		    armorsets.put(armorset.getName(), armorset);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...armorset cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-armorset.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-armorset.txt");
	}    
    }
    
    private void readItemCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-item.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String tags = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String restriction = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String cost = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardItem item = new CardItem(name, tags, restriction, cost, nr, rarity, set, rules);
		    items.put(item.getName(), item);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...item cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-item.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-item.txt");
	}    
    }
    
    private void readLocationCards() {
	try {
	    BufferedReader in = new BufferedReader(new FileReader("Data_type-location.txt"));

	    String str;

	    while((str = in.readLine()) != null){
		// while there are cards
		while(str.contains(CARD_DELIMITER)){
		    // jump to first field
		    str = str.substring(str.indexOf(CARD_DELIMITER)+CARD_DELIMITER.length());
		    
		    // read fields
		    String name = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String rules = extract(str);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    int nr = Integer.parseInt(extract(str));
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    char rarity = extract(str).charAt(0);
		    str = str.substring(str.indexOf(FIELD_DELIMITER)+FIELD_DELIMITER.length());
		    String set = extract(str);
		    
		    CardLocation location = new CardLocation(name, nr, rarity, set, rules);
		    locations.put(location.getName(), location);
		}
	    }
	    in.close();
	    
	    System.out.println("\t...location cards loaded succesfully");
	    
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't find file: Data_type-location.txt");
	} catch (IOException ex){
	    System.err.println("IOException reading from file: Data_type-location.txt");
	}    
    }
    
    private String extract(String str){
	String result = str.substring(0,str.indexOf(FIELD_DELIMITER));
	return result;
    }
    
    public Card randomHero(){
	int random = new Random().nextInt(heroes.size());
	Object[] keys = heroes.keySet().toArray();
	Card hero = heroes.get((String)keys[random]);
	return hero;
//	return allies.get("Parren Shadowshot");
    }
    
    public Card randomAlly(){
	int random = new Random().nextInt(allies.size());
	Object[] keys = allies.keySet().toArray();
	Card ally = allies.get((String)keys[random]);
	return ally;
//	return allies.get("Parren Shadowshot");
    }
    
    public Card randomQuest(){
	int random = new Random().nextInt(quests.size());
	Object[] keys = quests.keySet().toArray();
	Card quest = quests.get((String)keys[random]);
	return quest;
//	return allies.get("Parren Shadowshot");
    }
    
    public String[] allTypes(){
	return new String[]{"All","Allies","Quests","Abilities","Weapons","Armors","Armorsets","Items","Locations"};
    }
    
    public String[] allCardsForTypeIndex(int index){
	switch(index){
	    case 0: return allCards();
	    case 1: return allAllies();
	    case 2: return allQuests();
	    case 3: return allAbilities();
	    case 4: return allWeapons();
	    case 5: return allArmors();
	    case 6: return allArmorsets();
	    case 7: return allItems();
	    case 8: return allLocations();
	    default: return allCards(); // in case of failure, return everything
	}
    }
    
    public String[] allAllies(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(allies.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allQuests(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(quests.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allAbilities(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(abilities.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allWeapons(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(weapons.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allArmors(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(armors.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allArmorsets(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(armorsets.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allItems(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(items.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allLocations(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(locations.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allCards(){
	
	List<String> allCards = new ArrayList<String>();
	
	//allCards.addAll(heroes.keySet()); // don't count the heroes as regular cards
	allCards.addAll(allies.keySet());
	allCards.addAll(quests.keySet());
	allCards.addAll(abilities.keySet());
	allCards.addAll(weapons.keySet());
	allCards.addAll(armors.keySet());
	allCards.addAll(armorsets.keySet());
	allCards.addAll(items.keySet());
	allCards.addAll(locations.keySet());
	
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] allHeroes(){
	List<String> allCards = new ArrayList<String>();
	
	allCards.addAll(heroes.keySet());
	Collections.sort(allCards);
	
	return allCards.toArray(new String[0]);
    }
    
    public String[] getClasses(){
	return classes.toArray(new String[0]);
    }
    
    public String[] getHeroesByClass(String clazz){
	List<String> heroesByClass = new ArrayList<String>();
	
	for (Entry<String, CardHero> entry : heroes.entrySet()) {
	    if(entry.getValue().getClazz().compareToIgnoreCase(clazz)==0){
		heroesByClass.add(entry.getKey());
	    }
	}
	
	Collections.sort(heroesByClass);
	
	return heroesByClass.toArray(new String[0]);
    }
    
    public Card getCardByName(String name){
	// use treemap
	
	TreeMap<String, Card> allCards = new TreeMap<String, Card>();
	allCards.putAll(heroes);
	allCards.putAll(allies);
	allCards.putAll(quests);
	allCards.putAll(abilities);
	allCards.putAll(weapons);
	allCards.putAll(armors);
	allCards.putAll(armorsets);
	allCards.putAll(items);
	allCards.putAll(locations);
	
	return allCards.get(name);
    }
    
    public Card generateCardByName(String type, String name){
	if (type.equalsIgnoreCase("hero")) {
	    return new CardHero(getCardByName(name));
	} else if (type.equalsIgnoreCase("ally")) {
	    return new CardAlly(getCardByName(name));
	} else if (type.equalsIgnoreCase("quest")) {
	    return new CardQuest(getCardByName(name));
	} else if (type.equalsIgnoreCase("ability")) {
	    return new CardAbility(getCardByName(name));
	} else if (type.equalsIgnoreCase("weapon")) {
	    return new CardWeapon(getCardByName(name));
	} else if (type.equalsIgnoreCase("armor")) {
	    return new CardArmor(getCardByName(name));
	} else if (type.equalsIgnoreCase("armorset")) {
	    return new CardArmorset(getCardByName(name));
	} else if (type.equalsIgnoreCase("item")) {
	    return new CardItem(getCardByName(name));
	} else if (type.equalsIgnoreCase("location")) {
	    return new CardLocation(getCardByName(name));
	} else {
	    return null;
	}
    }
}
