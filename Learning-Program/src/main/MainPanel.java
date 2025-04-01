package main;

import java.util.concurrent.ThreadLocalRandom;
import org.jfugue.player.Player;
import javax.swing.*;



public class MainPanel extends JPanel {

	
	final String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};

	int previousRandomInt = 0;
	final String altResError = "Error: This resolution does not exist.";


	// Names
	// Base note in half notes
	// Half notes to top note
	final String[] intervalNames = {"l2", "m7", "pm5", "Pl4", "Pl2", "pm7"};
	final int[] intervalIncreaseNoteBy = {5, 7, 11, 4, 8, 11};
	final int[] intervalBaseNotes = {2, 10, 6, 6, 3, 9};

	final String[] resolutionNames = {"m3", "l6", "l3", "m6", "t4", "t5"};
	final int[] resolutionBaseNote = {3, 9, 4, 8, 5, 7};
	final int[] resolutionIncreaseNoteBy = {4, 7, 1, 3, 7, 1};


	final String[] alternateResolutionNames = {"m6", "l3", altResError, altResError, altResError, altResError};
	final int[] alternateResolutionBaseNotes = {4, 0, -1, -1, -1, -1};
	final int[] alternateResolutionDistanceToTopNote = {8, 4, -100, -100, -100, -100};

	int currentInterval;
	int currentNoteChange = ThreadLocalRandom.current().nextInt(notes.length-1);

	final String[] modes = {"major", "minor"};
	Chords chords;
	Player player = new Player();

	int scale = 0;

	
	public MainPanel() {
		testingMusic();
		String currentMode = modes[ThreadLocalRandom.current().nextInt(modes.length)];
		this.chords = new Chords(this,currentNoteChange,currentMode,player);
	}
	
	public void testingMusic() {
		
		
		
	}
	
	public String cauculateNotes(Object[] noteInfo, String noteLength) {

		
		int startingNote = (int) noteInfo[0] - currentNoteChange;
	    int increaseBy = (int) noteInfo[1];
	    int scale = (int) noteInfo[2];
	    
	    int startingScale = scale;

		if(startingNote < 0) {
			startingNote += notes.length-1;
		}

		int newNote = startingNote + increaseBy;

        if (newNote >= notes.length) {
        	newNote -= notes.length;
            scale++; 
        }

        if(startingNote >= notes.length) startingNote = 0;
        
        System.out.println(startingNote+" "+increaseBy+" "+startingScale+" "+newNote);
        
        String note = notes[newNote];
        String bottomNote = notes[startingNote];
        
     
        System.out.println("        Cauculated Notes: "+bottomNote+startingScale+noteLength+"+"+note+scale+noteLength);
        return bottomNote+startingScale+noteLength+"+"+note+scale+noteLength;
	}
	
	public String cauculateResolution(Object[] noteInfo, String noteLength) {
		int startingScale = 5;
		int scale = 5;
	    int resolutionBase = (int) noteInfo[3] - currentNoteChange;
	    int resolutionIncreaseBy = (int) noteInfo[4];

		if(resolutionBase < 0) {
			resolutionBase += notes.length-1;
		}

		System.out.println("Resolution base " + resolutionBase + "   Resolution increase by " + resolutionIncreaseBy + "   Scale " + scale);
	    


		int newResolutionNote = resolutionBase + resolutionIncreaseBy;


        if (newResolutionNote >= notes.length) {
        	newResolutionNote -= notes.length;
            scale++; 
        }
        if(resolutionBase >= notes.length) resolutionBase = 0;
        
        
        String resolutionNote = notes[newResolutionNote];
        String resolutionBottomNote = notes[resolutionBase];

		System.out.println(resolutionBottomNote+" "+resolutionNote +" ! " + scale);
		System.out.println();
        String fullResolution = resolutionBottomNote + startingScale + noteLength +"+"+ resolutionNote + scale + noteLength;;

        return fullResolution;
	}
	
	public Object[] chooseInterval() {
		
		int randomIndex = ThreadLocalRandom.current().nextInt(intervalBaseNotes.length);

		if(randomIndex == previousRandomInt) {
			randomIndex = ThreadLocalRandom.current().nextInt(intervalBaseNotes.length);
				if(randomIndex == previousRandomInt) {
					randomIndex = ThreadLocalRandom.current().nextInt(intervalBaseNotes.length);
						if(randomIndex == previousRandomInt) { randomIndex = 5; }
				}
		}
		previousRandomInt = randomIndex;

		//int baseNote = intervalBaseNotes[randomIndex];
		int baseNote = intervalBaseNotes[randomIndex];
		int Interval = intervalIncreaseNoteBy[randomIndex];
		int currentScale = 5;

		System.out.println(intervalNames[randomIndex]);
		
		currentInterval = randomIndex;

		
		return new Object[] {Interval, baseNote, currentScale, null, null};
	}
	
	public Object[] chooseResolution() {

		int resolutionBaseNote = this.resolutionBaseNote[currentInterval];
		int resolution = resolutionIncreaseNoteBy[currentInterval];

		System.out.println(resolutionNames[currentInterval]);
		
		return new Object[] {null, null, 5, resolution, resolutionBaseNote};
	}


	public void revealButtonPressed(JTextField revealField, JTextField resRevealField) {
		revealField.setText(intervalNames[currentInterval]);
		resRevealField.setText(resolutionNames[currentInterval]);
	}
	
	public void buttonPressed() {
		
		new Thread(() -> {
			player.play( cauculateNotes(chooseInterval(), "w") );
			player.play( cauculateResolution(chooseResolution(), "w") );
	    }).start();
		
		
	}
	


}
