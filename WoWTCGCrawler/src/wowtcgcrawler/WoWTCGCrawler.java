/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wowtcgcrawler;

import crawlers.Crawler;

/**
 *
 * @author Robin jr
 */
public class WoWTCGCrawler {
    
    /**
     * Downloads and parses all the data.
     */
    public static void downloadAndParseAllData(){
	new Thread(new Crawler("type-hero",Crawler.BOTH)).start();
	new Thread(new Crawler("type-ally",Crawler.BOTH)).start();
	new Thread(new Crawler("type-quest",Crawler.BOTH)).start();
	new Thread(new Crawler("type-ability",Crawler.BOTH)).start();
	new Thread(new Crawler("type-weapon",Crawler.BOTH)).start();
	new Thread(new Crawler("type-armor",Crawler.BOTH)).start();
	new Thread(new Crawler("type-armorset",Crawler.BOTH)).start();
	new Thread(new Crawler("type-item",Crawler.BOTH)).start();
	new Thread(new Crawler("type-location",Crawler.BOTH)).start();
    }
    
    public static void parseAllData(){
	new Thread(new Crawler("type-hero",Crawler.PARSE)).start();
	new Thread(new Crawler("type-ally",Crawler.PARSE)).start();
	new Thread(new Crawler("type-quest",Crawler.PARSE)).start();
	new Thread(new Crawler("type-ability",Crawler.PARSE)).start();
	new Thread(new Crawler("type-weapon",Crawler.PARSE)).start();
	new Thread(new Crawler("type-armor",Crawler.PARSE)).start();
	new Thread(new Crawler("type-armorset",Crawler.PARSE)).start();
	new Thread(new Crawler("type-item",Crawler.PARSE)).start();
	new Thread(new Crawler("type-location",Crawler.PARSE)).start();
    }
    
    /**
     * Starts the crawler for the specified section with the specified action.
     * 
     * @param section The section to be crawled (type-xxx).
     * @param action The action to be performed, as defined in Crawler (NONE, DOWNLOAD, PARSE or BOTH).
     */
    public static void performOperationOnData(String section, int action){
	new Thread(new Crawler(section, action)).start();
    }
}
