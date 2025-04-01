package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.jfugue.player.Player;


public class Initiator extends JFrame {
	
	private static JButton button;
    private static JButton revealButton;
    private JTextField revealTextField;
    private JTextField revealResTextField;
	
    public Initiator() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);   
        setVisible(true);
        
        
        MainPanel panel = new MainPanel();
        add(panel); // Add the GamePanel to the frame
        
        button = new JButton("Play interval");
		button.setBounds(10,80,80,25);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.buttonPressed();	
			}
			
		});
		panel.add(button);

        revealButton = new JButton("Reveal interval");
        revealButton.setBounds(10,80,80,25);
        revealButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.revealButtonPressed(revealTextField,revealResTextField);
            }

        });
        panel.add(revealButton);

        revealTextField = new JTextField("Press the reveal button to reveal the interval", 20);
        revealTextField.setEditable(false);
        panel.add(revealTextField);

        revealResTextField = new JTextField("Press the reveal button to reveal the interval", 20);
        revealResTextField.setEditable(false);
        panel.add(revealResTextField);
        
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new Initiator());
        
    }
}
