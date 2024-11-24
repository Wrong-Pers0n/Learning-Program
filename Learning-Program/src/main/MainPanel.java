package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jfugue.player.Player;

public class MainPanel extends JPanel {
	
	String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	
	public MainPanel() {
		testingMusic();
		
		
	}
	
	public void testingMusic() {
		new Thread(() -> {
			Player player = new Player();
			player.play("E");
			player.play(cauculateNotes(4,8,5,"w"));	
	    }).start();
		
	}
	
	public String cauculateNotes(int startingNote, int increaseBy, int scale, String noteLength) {

		int newNote = startingNote + increaseBy;

        if (newNote >= notes.length) {
        	newNote -= notes.length;
            scale++; 
        }
        String note = notes[newNote];
     
        return note+scale+noteLength;
	}

}
