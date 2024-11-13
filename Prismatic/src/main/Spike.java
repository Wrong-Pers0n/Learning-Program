package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spike {
	
	public BufferedImage spike1;
	public BufferedImage spike2;	
	public BufferedImage spike3;
	public BufferedImage spike4;
	
	Player player;

	int x;
	int y;
	int width;
	int heigth;
	int rotation;
	
	Rectangle hitBox;
	
	public Spike(int x, int y, int width, int heigth, int rotation) {
		
		
		this.width = width;
		this.heigth = heigth;
		this.x = x;
		this.y = y-heigth/2;
		this.rotation = rotation;
		
		
		
		switch (rotation) {
        case 1: // Pointing Up
            hitBox = new Rectangle(x, y, width, heigth/2);
            break;

        case 2: // Pointing Left
            hitBox = new Rectangle(x + width / 2, y - heigth, width / 4, heigth*2);
            break;

        case 3: // Pointing Down
            hitBox = new Rectangle(x, y - heigth/2, width, heigth/2);
            break;

        case 4: // Pointing Right
            hitBox = new Rectangle(x + width / 4, y - heigth, width / 4, heigth*2);
            break;

        default:
        	System.err.println("Spike rotation not equal to 1-4");
            break;
    }
		
		setupCannon();
		
	}
	public void draw(Graphics2D gtd) {
		
		if(player.panel.debugMode == true) {
		gtd.setColor(Color.red);
		gtd.draw(hitBox);
		}
		
		switch(rotation) {
		case 1:
			gtd.drawImage(spike1,x,y-10,40,40,null);
			break;
		case 2:
			gtd.drawImage(spike2,x,y-10,40,40,null);
			break;
		case 3:
			gtd.drawImage(spike3,x,y-10,40,40,null);
			break;
		case 4:
			gtd.drawImage(spike4,x,y-10,40,40,null);
			break;
		default:
			System.out.println("Spike rotation does note exist");
			break;
		}
		
		
		
	}
	public void setupCannon() {
		spike1 = setup("/tiles/Spike_Up");
		spike2 = setup("/tiles/Spike_Right");
		spike3 = setup("/tiles/Spike_Down");
		spike4 = setup("/tiles/Spike_Left");
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
