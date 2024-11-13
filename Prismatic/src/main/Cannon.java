package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cannon {
	
	public BufferedImage cannon1;
	BufferedImage cannon = cannon1;
	
	Player player;
	GamePanel panel;

	int x;
	int y;
	int width;
	int heigth;
	
	Rectangle hitBox;
	
	public Cannon(int x, int y, int width, int heigth, GamePanel panel) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		this.panel = panel;
		
		hitBox = new Rectangle(x+width/4,y+heigth/4,width/2,heigth/2);
		setupCannon();
		
	}
	public void draw(Graphics2D gtd) {
		
		gtd.drawImage(cannon1,x,y,50,50,null);
		
		if(panel.debugMode == true) {
			gtd.setColor(Color.red);
			gtd.draw(hitBox);
		}
		
		
	}
	public void setupCannon() {
		cannon1 = setup("/tiles/cannon");
	}
	public BufferedImage setup(String imagePath) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));	
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	

	}
	
}
