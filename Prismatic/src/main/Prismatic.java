package main; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Prismatic {


	public static void main(String[] args) {

		
		MainFrame frame = new MainFrame();
		
		frame.setSize(1350,750);
	
		frame.setBackground(Color.LIGHT_GRAY);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth()/2 - frame.getSize().getWidth()/2), (int) (screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
	
		frame.setResizable(false);
		frame.setTitle("Prismatic Demo V0.4"); // Name of tab!
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	
	}
	

}
