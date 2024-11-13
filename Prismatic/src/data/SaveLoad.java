package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;
import main.Player;

public class SaveLoad {

    private GamePanel panel;
    private static final String SAVE_DIR = System.getProperty("user.home") + File.separator + "prismatic" + File.separator + "saves";

    public SaveLoad(GamePanel panel) {
        this.panel = panel;
        // Ensure the save directory exists
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    public void save(int file, Player player, boolean forceSave) {
        File saveFile = new File(SAVE_DIR, "save" + file + ".dat");
        Data data;
        
        try {
            // Check if the file exists
            if (saveFile.exists()) {
                // File exists, read the existing data
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    data = (Data) ois.readObject();  // Existing data
                }
            } else {
                // File does not exist, create a new Data object
                data = new Data();
            }

            boolean updated = false;

            // Update data based on game state
            if (data.sunLevel < panel.player.sunLevel) {
                data.sunLevel = panel.level;
                updated = true;
                System.out.println("Sun saved to " + saveFile.getName());
            }
            
            if (data.moonLevel < panel.player.moonLevel) {
                data.moonLevel = panel.level;
                updated = true;
                System.out.println("Moon saved to " + saveFile.getName());
            }

            if (data.eclipseLevel < panel.player.eclipseLevel) {
                data.eclipseLevel = panel.level;
                updated = true;
                System.out.println("Eclipse saved to " + saveFile.getName());
            }

            if (data.voidLevel < panel.player.voidLevel) {
                data.voidLevel = panel.level;
                updated = true;
                System.out.println("Void saved to " + saveFile.getName());
            }

            // If any updates were made, write back to the file
            if (updated) {
                data.fileBlank = false;
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                    oos.writeObject(data);  // Overwrite with the updated data
                }
                System.out.println("Save file "+ panel.currentFile + " updated.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void load(int file, Player player) {
        File saveFile = new File(SAVE_DIR, "save" + file + ".dat");

        try {
            // Check if the file exists
            if (saveFile.exists()) {
                // File exists, read the existing data
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    Data data = (Data) ois.readObject();
                    
                    player.sunLevel = data.sunLevel-1;
                    player.moonLevel = data.moonLevel-1;
                    player.eclipseLevel = data.eclipseLevel-1;
                    player.voidLevel = data.voidLevel-1;
                    
                    panel.gamestate = panel.levelState;
                }
            } else {
                // File does not exist, initialize with default values
                System.out.println("Save file not found, initializing default values.");
                Data data = new Data();  // Default initialization

                // Assign default values to player
                player.sunLevel = data.sunLevel;
                player.moonLevel = data.moonLevel;
                player.eclipseLevel = data.eclipseLevel;
                player.voidLevel = data.voidLevel;

                // Optionally handle the gamestate
                panel.gamestate = panel.levelState;
            
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load Failed!");
            e.printStackTrace();
            player.sunLevel = 1;
            player.moonLevel = 1;
            player.eclipseLevel = 1;
            player.voidLevel = 1;
        }
    }


    public void wipeSave(int file, Player player) {
        File saveFile = new File(SAVE_DIR, "save" + file + ".dat");

        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                Data data = new Data();

                data.sunLevel = 1;
                data.moonLevel = 1;
                data.eclipseLevel = 1;
                data.voidLevel = 1;
                data.fileBlank = true;
                
                // Write the file
                oos.writeObject(data);
            }
            // Makes sure the changes actually do end up working
            save(file, player, true);
        } catch (IOException e) {
            System.out.println("Wipe Failed!");
            e.printStackTrace();
        }
    }
}
