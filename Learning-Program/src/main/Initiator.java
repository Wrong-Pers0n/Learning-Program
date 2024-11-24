package main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfugue.player.Player;


public class Initiator extends JFrame {
    public Initiator() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);   
        setVisible(true);
        
        MainPanel panel = new MainPanel();
        add(panel); // Add the GamePanel to the frame
        
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new Initiator());
        
    }
}
