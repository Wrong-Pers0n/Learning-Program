package main;

import java.util.Scanner;
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

    String[] chordNames = {"D7", "D65", "D43", "D2", "VII7", "pmVII7", "pm53", "pm53(II)", "M53", "M63", "M64", "m53", "m63", "m64", "Pl53"};
    int[] totalChordNotes = {4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    boolean[] hasAlternateResolution = {true, true, true, true};

    // Some numbers are negative to push them into nicer scale placements. -5=7
    int[] chordBaseNote =      {-5, -1,2, 5, -1,-1,-1,2, 0, 4, -5, 0, 3, -5, -4};
    int[] chordNotesToSecond = {4, 3, 3, 2, 3, 3, 3, 3, 4, 3, 5, 3, 4, 5, 4};
    int[] chordNotesToThird =  {3, 3, 2, 4, 3, 3, 3, 3, 3, 5, 4, 4, 5, 3, 4};
    int[] chordNotesToForth =  {3, 2, 4, 3, 4, 3};


    String[] chordResolutionNames = {"T53", "T53", "T53", "T63", "T53", "T53", "T53", "T53"};
    int[] chordResolutionBaseNote = {0, 0, 0, 4, 0, 0, 0, 4};
    int[] chordResolutionSecondNote = {4, 4, 4, 7, 4, 4, 4, 7};
    int[] chordResolutionThirdNote = {-1, 7, 7, 12, 7, 7, -1, -1};
    int[] chordResolutionForthNote = {-1, -1, 12, -1, -1, -1, -1, -1};
    int[] numOfNotesInResolution = {2, 3, 4, 3, 3, 3, 2, 2};

    String[] altChordResolutionNames = {};
    int[] altChordResolutionBaseNote = {};
    int[] altChordResolutionNotesToSecond = {};
    int[] altChordResolutionNotesToThird = {};

    String lastChord = "Error: No chords played yet";
    boolean forever = true;
    int randomIndex = ThreadLocalRandom.current().nextInt(chordNames.length)-1;
    int totalAttempts = 0;
    int totalTriesToGetRight = 0;

    public Chords(MainPanel panel, int currentNoteChange, String mode, Player player) {
        this.panel = panel;
        this.currentNoteChange = currentNoteChange;
        this.mode = mode;





        Scanner scanner = new Scanner(System.in);
        while(forever) {
            System.out.println();
            System.out.print("Play chords?: (YES/NO) ");
            String choice = scanner.nextLine().trim().toLowerCase().toString();
            if(choice.equals("no")) forever = false;
            else if(choice.equals("stats")) {
                double identifiedAverage = 0;
                try {
                    identifiedAverage = (double) totalTriesToGetRight/totalAttempts;
                } catch(Exception e) {
                    System.out.println("No tries yet.");
                }

                System.out.printf("Average attempts per chord: %1$.2f. Total chords played: %2$d. Total tries to identify chord: %3$d.", identifiedAverage, totalAttempts, totalTriesToGetRight);
            }
            else {
                totalAttempts++;
                String played = cauculateChordNotes(chooseChord());
                player.play(played);

                if(randomIndex < 8) {
                    String playedResolution = cauculateChordResolutionNotes(cauculateChordResolution(randomIndex));
                    player.play(playedResolution);
                }


                System.out.print("Ready?: (YES/NO) ");
                if(scanner.nextLine().trim().toLowerCase().toString().equals("yes")) {
                        boolean guessedRight = false;
                        while (guessedRight == false) {
                            System.out.print("Guess the chord: ");
                            totalTriesToGetRight++;
                            if(scanner.nextLine().trim().toString().equals(lastChord)) {
                                System.out.println("You guessed right! It was indeed "+lastChord);
                                guessedRight = true;
                            } else {
                                    System.out.print("Do you want to give up?: (YES/NO)");
                                    if(scanner.nextLine().trim().toLowerCase().toString().equals("yes")) {
                                        System.out.println("It was " + lastChord);
                                        guessedRight = true;
                                    }
                            }
                        }
                } else {
                    boolean knowsOutcome = false;
                    while (knowsOutcome == false) {
                        System.out.print("Play again?: (YES/NO)");
                        if(scanner.nextLine().trim().toLowerCase().toString().equals("yes")) {
                            player.play(played);
                        } else {
                            knowsOutcome = true;
                        }
                    }
                }

            }

        }
        scanner.close();


    }

    private Object[] chooseChord() {

        // Choose random index aka chord (makes sure it isnt the same as last time if possible)
        randomIndex = ThreadLocalRandom.current().nextInt(0,chordNames.length);
        if(randomIndex == previousRandomInt) {
            randomIndex = ThreadLocalRandom.current().nextInt(0, chordNames.length);
            if(randomIndex == previousRandomInt) {
                randomIndex = ThreadLocalRandom.current().nextInt(0, chordNames.length);
                if(randomIndex == previousRandomInt) { randomIndex = 0; }
            }
        } previousRandomInt = randomIndex;

        if(randomIndex < 0) randomIndex = 0;

        lastChord = chordNames[randomIndex];

        // sets the notes to ints so its easier to see whats going on
        int baseNote = chordBaseNote[randomIndex];
        int secondNote = chordNotesToSecond[randomIndex];
        int thirdNote = chordNotesToThird[randomIndex];

        int forthNote = -1;
        try {
            forthNote = chordNotesToForth[randomIndex];
        } catch (Exception e) {
        }


        int totalChordNotes = this.totalChordNotes[randomIndex];

        if(forthNote != -1) {
            return new Object[] {baseNote, secondNote, thirdNote, forthNote, totalChordNotes};
        } else {
            return new Object[] {baseNote, secondNote, thirdNote, null, totalChordNotes};
        }



    }

    public String cauculateChordNotes(Object[] chord) {
        // Sets all the needed int parameters
        int baseNote = (int) chord[0] - currentNoteChange;
        int addNotesForSecond = (int) chord[1];
        int addNotesForThird = (int) chord[2];
        int addNotesForForth = -2;
        int totalChordNotes = (int) chord[4];

        if(totalChordNotes == 4) {
            addNotesForForth = (int) chord[3];
        }

        int[] allChordNotes = new int[totalChordNotes];
        String[] allChordNoteNames = {null, null, null};

        // You need to make a new one bc otherwise it dosent know its null. im too lazy to fix it in the for loop
        if(totalChordNotes == 4) {
            allChordNoteNames = new String[]{null, null, null, null};
        }


        int[] chordRespectiveScales = {5,5,5,5};

        // adds notes in an array so i can use them easily in a for loop
        if(totalChordNotes == 4) {
            allChordNotes = new int[]{baseNote, addNotesForSecond, addNotesForThird, addNotesForForth};
        } else {
            allChordNotes = new int[]{baseNote, addNotesForSecond, addNotesForThird};
        }

        // The for loop loops through the respective chords and cauculates the notes
        for (int i = 0; i < allChordNotes.length; i++) {
            int currentNote = allChordNotes[i];

            if(currentNote >= notes.length-1) {
                chordRespectiveScales[i] += 1;
                currentNote -= notes.length;
            }
            if(currentNote < 0) {
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
                    } else if(newNote < 0) {
                        chordRespectiveScales[i] -= 1;
                        newNote += notes.length;
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

    public String cauculateChordResolutionNotes(Object[] obj) {
        int baseNote = (int) obj[0]- currentNoteChange;
        int secondNote = (int) obj[1]- currentNoteChange;
        int thirdNote = (int) obj[2]- currentNoteChange;
        int forthNote = (int) obj[3]- currentNoteChange;
        int numOfNotes = (int) obj[4];
        int[] chordRespectiveScales = {5,5,5,5};

        if(baseNote > notes.length-1) { baseNote -= notes.length; chordRespectiveScales[0]++;}
        if(baseNote < 0) { baseNote += notes.length;chordRespectiveScales[0]--;}

        if(secondNote > notes.length-1) {secondNote -= notes.length;chordRespectiveScales[1]++;}
        if(secondNote < 0) {secondNote += notes.length;chordRespectiveScales[1]--;}

        if(thirdNote > notes.length-1) {thirdNote -= notes.length;chordRespectiveScales[2]++;}
        if(thirdNote < 0) {thirdNote += notes.length;chordRespectiveScales[2]--;}

        if(numOfNotes == 4) {
            if(forthNote > notes.length-1) {forthNote -= notes.length;chordRespectiveScales[3]++;}
            if(forthNote < 0) {forthNote += notes.length;chordRespectiveScales[3]--;}
        }


        String resolution = "";



        if(numOfNotes == 4 && thirdNote != -1 && forthNote !=-1) {
            resolution = notes[baseNote]+chordRespectiveScales[0]+"w+"+notes[secondNote]+chordRespectiveScales[1]+"w+"+notes[thirdNote]+chordRespectiveScales[2]+"w+"+notes[forthNote]+chordRespectiveScales[3]+"w";
        } else if(numOfNotes == 3 && thirdNote != -1) {
            resolution = notes[baseNote]+chordRespectiveScales[0]+"w+"+notes[secondNote]+chordRespectiveScales[1]+"w+"+notes[thirdNote]+chordRespectiveScales[2]+"w";
        } else if(numOfNotes == 2){
            resolution = notes[baseNote]+chordRespectiveScales[0]+"w+"+notes[secondNote]+chordRespectiveScales[1]+"w";
        } else {
            System.out.println("There's been an error. Num of notes is invalid.");
        }
        return resolution;
    }









    private Object[] cauculateChordResolution(int chordIndex) {

        int baseNote = chordResolutionBaseNote[chordIndex];
        int secondNote = chordResolutionSecondNote[chordIndex];
        int thirdNote = chordResolutionThirdNote[chordIndex];
        int fourthNote = chordResolutionForthNote[chordIndex];
        int numOfNotes = numOfNotesInResolution[chordIndex];

        return new Object[]{baseNote,secondNote,thirdNote, fourthNote, numOfNotes};
    }


}
