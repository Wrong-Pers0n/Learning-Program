package main;

import java.util.concurrent.ThreadLocalRandom;
import org.jfugue.player.Player;

@SuppressWarnings("ALL")
public class Chords {

    MainPanel panel;
    int currentNoteChange;
    int previousRandomInt = -1;
    int scale = 5;
    String mode = "major";
    String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};

    String[] chordNames = {"D7", "D65", "D43", "D2", "VII7", "pmVII7", "pm53", "pm53 (uz II)", "T53", "T63", "T64", "t53", "t63", "t64", "S53", "S63", "S64", "s53", "s63", "s64", "D53", "D63", "D64"};
    int[] totalChordNotes = {4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    boolean[] hasAlternateResolution = {true, true, true, true};

    int[] chordBaseNote =      {7, 11,2, 5, 11,11,11,2, 0, 4, 7, 0, 3, 7, };
    int[] chordNotesToSecond = {4, 3, 3, 2, 3, 3, 3, 3, 4, 3, 5, 3, 4, 5, };
    int[] chordNotesToThird =  {3, 3, 2, 4, 3, 3, 3, 3, 3, 5, 4, 4, 5, 3, };
    int[] chordNotesToForth = {3, 2, 4, 3, 4, 3};


    String[] chordResolutionNames = {};
    int[] chordResolutionBaseNote = {};
    int[] chordResolutionNotesToSecond = {};
    int[] chordResolutionNotesToThird = {};

    String[] altChordResolutionNames = {};
    int[] altChordResolutionBaseNote = {};
    int[] altChordResolutionNotesToSecond = {};
    int[] altChordResolutionNotesToThird = {};

    public Chords(MainPanel panel, int currentNoteChange, String mode, Player player) {
        this.panel = panel;
        this.currentNoteChange = currentNoteChange;
        this.mode = mode;

        String played = cauculateChordNotes(chooseChord());
        System.out.println(played);
        player.play(played);
    }

    private Object[] chooseChord() {

        // Choose random index aka chord (makes sure it isnt the same as last time if possible)
        int randomIndex = ThreadLocalRandom.current().nextInt(chordNames.length)-1;
        if(randomIndex == previousRandomInt) {
            randomIndex = ThreadLocalRandom.current().nextInt(chordNames.length)-1;
            if(randomIndex == previousRandomInt) {
                randomIndex = ThreadLocalRandom.current().nextInt(chordNames.length)-1;
                if(randomIndex == previousRandomInt) { randomIndex = 0; }
            }
        } previousRandomInt = randomIndex;

        // sets the notes to ints so its easier to see whats going on
        int baseNote = chordBaseNote[randomIndex];
        int secondNote = chordNotesToSecond[randomIndex];
        int thirdNote = chordNotesToThird[randomIndex];
        int forthNote = chordNotesToForth[randomIndex];
        int totalChordNotes = this.totalChordNotes[randomIndex];

        return new Object[] {baseNote, secondNote, thirdNote, forthNote, totalChordNotes};
    }

    public String cauculateChordNotes(Object[] chord) {
        // Sets all the needed int parameters
        int baseNote = (int) chord[0] - currentNoteChange;
        int addNotesForSecond = (int) chord[1];
        int addNotesForThird = (int) chord[2];
        int addNotesForForth = (int) chord[3];
        int totalChordNotes = (int) chord[4];

        int[] allChordNotes;
        String[] allChordNoteNames = {null, null, null, null};
        int[] chordRespectiveScales = {5,5,5,5};

        // adds notes in an array so i can use them easily in a for loop
        if(totalChordNotes == 3) {
            allChordNotes = new int[]{baseNote, addNotesForSecond, addNotesForThird};
        } else {
            allChordNotes = new int[]{baseNote, addNotesForSecond, addNotesForThird, addNotesForForth};
        }

        // The for loop loops through the respective chords and cauculates the notes
        for (int i = 0; i < allChordNotes.length; i++) {
            int currentNote = allChordNotes[i];

            if(currentNote >= notes.length-1) {
                chordRespectiveScales[i] += 1;
                currentNote -= notes.length;
            } else if(currentNote < 0) {
                chordRespectiveScales[i] -= 1;
                currentNote += notes.length;
            }

            // Makes sure the first note dosent go through the note analyser
            if(i == 0) { allChordNoteNames[0] = notes[currentNote]+chordRespectiveScales[0]+"w"; }
            // Cauculates the notes for the other chords
            else {

                int totalNotesFromBase = 0;
                int newNote = 0;

                // Cauculates total notes from base, by looping through all the previous ones and adding them together
                for(int e = i; e > 0; e--) {
                    totalNotesFromBase += allChordNotes[e];
                    newNote = allChordNotes[0] + totalNotesFromBase;
                    if(newNote >= notes.length) {
                        chordRespectiveScales[i] += 1;
                        newNote -= notes.length;
                    }


                    allChordNoteNames[i] = notes[newNote]+chordRespectiveScales[i]+"w";
                }

            }

        }

        String allChordNotesToReturn = new String(allChordNoteNames[0]);
        for(int c = 1; c < allChordNoteNames.length; c++) {
            allChordNotesToReturn = allChordNotesToReturn.concat("+"+allChordNoteNames[c]);
        }


        return allChordNotesToReturn;
    }


}
