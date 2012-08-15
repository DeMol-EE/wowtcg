/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import images.ImageLoader;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Warkst
 */
public abstract class Card implements Serializable {
    private boolean exhausted;
    private boolean flipped;
    private int counters;
    
//    private ImageIcon icon;
    private String iconURL;
    
    private String name;
    private String type;
    private String subtype;
    private String race;
    private String clazz;
    private String tags;
    private String faction;
    private String restriction;
    private String cost;
    private int atk;
    private String atktype;
    private int health;
    private int nr;
    private char rarity;
    private String set;
    private String rules;
    private String talent;
    private String professions;

    public Card(String iconURL, String name, String type, String subtype, String race, String clazz, String tags, String faction, String restriction, String cost, int atk, String atktype, int health, int nr, char rarity, String set, String rules, String talent, String professions) {
	this.iconURL = iconURL;
	this.name = name;
	this.type = type;
	this.subtype = subtype;
	this.race = race;
	this.clazz = clazz;
	this.tags = tags;
	this.faction = faction;
	this.restriction = restriction;
	this.cost = cost;
	this.atk = atk;
	this.atktype = atktype;
	this.health = health;
	this.nr = nr;
	this.rarity = rarity;
	this.set = set;
	this.rules = rules;
	this.talent = talent;
	this.professions = professions;
	
	this.exhausted = false;
	this.flipped = false;
	this.counters = 0;
    }
    
    public Card(Card cloneMe){
	this.iconURL = cloneMe.getIconURL();
	this.name = cloneMe.getName();
	this.type = cloneMe.getType();
	this.subtype = cloneMe.getSubtype();
	this.race = cloneMe.getRace();
	this.clazz = cloneMe.getClazz();
	this.tags = cloneMe.getTags();
	this.faction = cloneMe.getFaction();
	this.restriction = cloneMe.getRestriction();
	this.cost = cloneMe.getCost();
	this.atk = cloneMe.getAtk();
	this.atktype = cloneMe.getAtktype();
	this.health = cloneMe.getHealth();
	this.nr = cloneMe.getNr();
	this.rarity = cloneMe.getRarity();
	this.set = cloneMe.getSet();
	this.rules = cloneMe.getRules();
	this.talent = cloneMe.getTalent();
	this.professions = cloneMe.getProfessions();
	
	this.exhausted = false;
	this.flipped = false;
	this.counters = 0;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    public ImageIcon getIcon(){
	ImageIcon icon = ImageLoader.createImageIconAtHomeLocation(getIconURL());
	return new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
    }

    public String getIconURL() {
	return iconURL;
    }

    public void setIconURL(String iconURL) {
	this.iconURL = iconURL;
    }

    public int getAtk() {
	return atk;
    }

    public void setAtk(int atk) {
	this.atk = atk;
    }

    public String getClazz() {
	return clazz;
    }

    public void setClazz(String clazz) {
	this.clazz = clazz;
    }

    public String getCost() {
	return cost;
    }

    public void setCost(String cost) {
	this.cost = cost;
    }

    public String getFaction() {
	return faction;
    }

    public void setFaction(String faction) {
	this.faction = faction;
    }

    public int getHealth() {
	return health;
    }

    public void setHealth(int health) {
	this.health = health;
    }

    public int getNr() {
	return nr;
    }

    public void setNr(int nr) {
	this.nr = nr;
    }

    public String getRace() {
	return race;
    }

    public void setRace(String race) {
	this.race = race;
    }

    public char getRarity() {
	return rarity;
    }

    public void setRarity(char rarity) {
	this.rarity = rarity;
    }

    public String getRestriction() {
	return restriction;
    }

    public void setRestriction(String restriction) {
	this.restriction = restriction;
    }

    public String getSet() {
	return set;
    }

    public void setSet(String set) {
	this.set = set;
    }

    public String getSubtype() {
	return subtype;
    }

    public void setSubtype(String subtype) {
	this.subtype = subtype;
    }

    public String getTags() {
	return tags;
    }

    public void setTags(String tags) {
	this.tags = tags;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }
    
    // logic
    public void exhaust(){
	this.exhausted = true;
    }
    
    public void ready(){
	this.exhausted = false;
    }
    
    public boolean isExhausted(){
	return exhausted;
    }
    
    public boolean isReady(){
	return !exhausted;
    }
    
    public void setCounters(int counters){
	this.counters = counters;
    }
    
    public int getCounters(){
	return counters;
    }

    public String getRules() {
	return rules;
    }

    public void setRules(String rules) {
	this.rules = rules;
    }

    public String getAtktype() {
	return atktype;
    }

    public void setAtktype(String atktype) {
	this.atktype = atktype;
    }

    public String getProfessions() {
	return professions;
    }

    public void setProfessions(String professions) {
	this.professions = professions;
    }

    public String getTalent() {
	return talent;
    }

    public void setTalent(String talent) {
	this.talent = talent;
    }

    public boolean isFlipped() {
	return flipped;
    }

    public void setFlipped(boolean flipped) {
	this.flipped = flipped;
    }
    
    public void toggleFlipped(){
	this.flipped = !this.flipped;
    }
    
    public CardSnapshot takeSnapshot(){
	return new CardSnapshot(exhausted, flipped, counters);
    }
    
    public void applySnapshot(CardSnapshot snapshot){
	this.exhausted = snapshot.isExhausted();
	this.flipped = snapshot.isFlipped();
	this.counters = snapshot.getCounters();
    }
    
    public abstract void showCard();
    
    public abstract void showCard(Point location);
}
