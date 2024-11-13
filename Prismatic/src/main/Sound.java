package main;

import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    Clip[] clips = new Clip[30];  // Store all clips in an array
    URL soundURL[] = new URL[30];
    GamePanel panel;

    public Sound(GamePanel panel) {
    	this.panel = panel;
        // Load the sound files
        soundURL[0] = getClass().getResource("/sound/theme1.wav");
        soundURL[1] = getClass().getResource("/sound/TitleScreen.wav");
        soundURL[2] = getClass().getResource("/sound/titletheme1.wav");

        // Preload all clips
        loadClips();
    }

    // Method to preload all audio clips
    private void loadClips() {
        try {
            for (int i = 0; i < soundURL.length; i++) {
                if (soundURL[i] != null) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                    clips[i] = AudioSystem.getClip();
                    clips[i].open(ais);  // Load the audio file into the clip
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print any errors
        }
    }

    // Play a specific clip (set the clip by its index)
    public void play(int i) {
        if (clips[i] != null) {
            clips[i].start();
        }
    }

    // Loop a specific clip
    public void loop(int i) {
        if (clips[i] != null) {
            clips[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Stop a specific clip
    public void stop(int i) {
        if (clips[i] != null) {
            clips[i].stop();
        }
    }
    
	public void setMasterSound() {
        for (int i = 0; i < clips.length; i++) {
            if (clips[i] != null && clips[i].isOpen()) {
                try {
                    FloatControl gainControl = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(panel.masterVolume*1.5f/2.5f);
                } catch (IllegalArgumentException ex) {
                    // Handle cases where the clip does not support FloatControl (some clips may not support it)
                    System.out.println("Clip " + i + " does not support volume control.");
                }
            }
        }
	}
}
