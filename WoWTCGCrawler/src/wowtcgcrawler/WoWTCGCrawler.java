/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wowtcgcrawler;

import crawlers.Crawler;

/**
 *
 * @author Warkst
 */
public class WoWTCGCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
}
