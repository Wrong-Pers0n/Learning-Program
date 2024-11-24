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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jfugue.player.Player;

public class MainPanel extends JPanel {
	
	String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	
	
				//     M2, L2, M3, L3, T4, m5/P4  T5  M6  L6  M7   L7   T8
	int[] intervals = {1,  2,  3,  4,  5,  6,     7,  8,  9,  10,  11,  12};
	
	public MainPanel() {
		testingMusic();
		
		
	}
	
	public void testingMusic() {
		new Thread(() -> {
			Player player = new Player();
			player.play("E");
			//player.play( cauculateNotes(4,8,5,"w") );	
			player.play( cauculateNotes(chooseInterval(), "w") );
	    }).start();
		
	}
	
	public String cauculateNotes(Object[] noteInfo, String noteLength) {
		
		int startingNote = (int) noteInfo[0];
	    int increaseBy = (int) noteInfo[1];
	    int scale = (int) noteInfo[2];
	    int startingScale = scale;
	    System.out.println(startingScale);

		int newNote = startingNote + increaseBy;

        if (newNote >= notes.length) {
        	newNote -= notes.length;
            scale++; 
        }
        String note = notes[newNote];
        String bottomNote = notes[startingNote];
     
        System.out.println(bottomNote+startingScale+noteLength+"+"+note+scale+noteLength);
        return bottomNote+startingScale+noteLength+"+"+note+scale+noteLength;
	}
	
	public Object[] chooseInterval() {
		
		int randomIndex = ThreadLocalRandom.current().nextInt(intervals.length);
		int Interval = intervals[randomIndex];
		
		int randomNote = ThreadLocalRandom.current().nextInt(1, 13);
		
		return new Object[] {Interval, randomNote, 5};
	}

}
