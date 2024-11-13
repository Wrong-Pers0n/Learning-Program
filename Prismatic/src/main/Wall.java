package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall {

	int x;
	int y;
	int width;
	int heigth;
	
	Rectangle hitBox;
	
	public BufferedImage tilemiddle;
	BufferedImage cannon = tilemiddle;
	
	public Wall(int x, int y, int width, int heigth) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		
		hitBox = new Rectangle(x,y,width,heigth);
		
		setupTile();
		
	}
	public void draw(Graphics2D gtd) {
		
//		gtd.setColor(Color.LIGHT_GRAY);
//		gtd.drawRect(x, y, width, heigth);
//		gtd.setColor(Color.WHITE);
//		gtd.fillRect(x+1, y+1, width-1, heigth-1);
		
		gtd.drawImage(tilemiddle,x,y,width,heigth,null);
		
		
	}
	public void setupTile() {
		tilemiddle = setup("/tiles/normal_tile_middle");
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
