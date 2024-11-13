package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Portal {
	
	public BufferedImage portal1;
	BufferedImage portal = portal1;
	
	Player player;
	//static GamePanel panel = new GamePanel();
	GamePanel panel;
	

	int x;
	int y;
	int width;
	int heigth;
	int animation = 0;
	int frame = 0;
	
	Rectangle hitBox;
	
	public Portal(int x, int y, int width, int heigth, GamePanel panel) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		this.panel = panel;
		
		hitBox = new Rectangle(x,y,width,heigth);
		setupPortal();
	}
	public void draw(Graphics2D gtd) {
		
		gtd.drawImage(portal1,x,y,width,heigth,null);
		
		animation ++;
		animation ++;
		if(animation >= 5) {
			if(frame >= 24) {
				frame = 0;
			}
			frame++;
			animation = 0;
		}
		
		if(panel.debugMode == true) {
			gtd.setColor(Color.red);
			gtd.draw(hitBox);
		}
	}
	
	public void level(GamePanel panel) {
		
		if(panel.player.hitbox.intersects(hitBox)) {
			System.out.println("hitbox touched");
			panel.makeWalls();
		}
	}
	
	public void setupPortal() {
		portal1 = setup("/tiles/portal1");
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
	public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return hitBox.x;
    }

    public int getY() {
        return hitBox.y;
    }
	
}
