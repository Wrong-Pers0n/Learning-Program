package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	GamePanel panel;
	
	public BufferedImage moon1,title1,sun1;

	BufferedImage moon = moon1;
	BufferedImage title = title1;
	BufferedImage sun = sun1;
	public Background(GamePanel panel) {
		this.panel = panel;
		setupBackground();
	}
	
	public void setupBackground() {
		moon1 = setup("/background/moon");
		title1 = setup("/background/title_background");
		sun1 = setup("/background/sun");
		
	}
	public void drawBackground(Graphics2D gtd) {
		
		if(panel.gamestate == panel.playState && panel.gamemode == 2 || panel.gamestate == panel.victoryScreenState && panel.gamemode == 2 || panel.gamestate == panel.inBetweenState) { gtd.drawImage(moon1,0,0,1366,768,null); }
		else if(panel.gamestate == panel.playState && panel.gamemode == 1 || panel.gamestate == panel.victoryScreenState && panel.gamemode == 1) { gtd.drawImage(sun1,0,0,1366,768,null); }
		else if(panel.gamestate == 9) System.out.println("inbetween");
		else{ gtd.drawImage(title1,0,0,1366,768,null);}

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

	public void drawLoadingScreen(Graphics2D gtd) {
		gtd.drawImage(title1,0,0,1366,768,null);
		
	}	
}

