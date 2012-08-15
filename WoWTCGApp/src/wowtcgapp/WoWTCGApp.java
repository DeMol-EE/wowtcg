/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wowtcgapp;

import model.Model;
import model.UserData;
import viewControllers.LoadingScreen;
import viewControllers.MainFrame;

/**
 *
 * @author Warkst
 */
public class WoWTCGApp {

    static LoadingScreen loading;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
	// show load screen
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Mac OS X".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	
	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		loading = new LoadingScreen();
		loading.setLoadingLabel("Loading heroes...");
		loading.setVisible(true);
	    }
	});
	
	// start model
	final Model m = new Model();
	
	// some delay to show the pretty loading screen :3
	/*
	for (int i = 0; i < 100000; i++) {
	    System.out.println("i="+i);
	    if(i==7000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading allies...");
		    }
		});
	    } else if(i==17000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading quests...");
		    }
		});
	    } else if(i==25000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading abilities...");
		    }
		});
	    } else if(i==50000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading weapons...");
		    }
		});
	    } else if(i==60000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading armors...");
		    }
		});
	    } else if(i==70000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading armor sets...");
		    }
		});
	    } else if(i==80000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading items...");
		    }
		});
	    } else if(i==90000){
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			loading.setLoadingLabel("Loading locations...");
		    }
		});
	    }
	}
	//*/
	
	// load user data
	final UserData ud = new UserData(m);
	
	// start GUI
	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		MainFrame mf = new MainFrame(m, ud);
		mf.setVisible(true);
		mf.electFirstResponder();
		
		loading.dispose();
	    }
	});
    }
}
