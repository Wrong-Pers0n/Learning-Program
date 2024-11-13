package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

	GamePanel panel;
	Graphics2D gtd;
	
	public int screenWidth = 1366;
	public int screenHeigth = 768;
	public int commandNum = 0;
	int x,y,width,heigth;
	public String message = " ";
	public boolean autoSaveTrue = true;
	
	Font arial_40, arial_80B;
	Font arial_20;
	
	int alpha = 190; // 50% transparent of 254
	Color pauseScreenColor = new Color(0, 0, 0, alpha);
	
	public UI(int x, int y, GamePanel panel) {
		
		this.panel = panel;
		this.x = x;
		this.y = y;
		
		width = screenWidth;
		heigth = screenHeigth;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		arial_20 = new Font("Arial", Font.PLAIN, 20);
		
	}
	public void drawUI(Graphics2D gtd) {
		
	if(panel.gamestate == panel.titleState) {
		drawTitleScreen(gtd);
	}
	if(panel.gamestate == panel.levelState) {
		drawLevelScreen(gtd);
	}
	if(panel.gamestate == panel.loadState) {
		drawLoadScreen(gtd);
	}
	if(panel.gamestate == panel.settingsState) {
		drawSettingsScreen(gtd);
	}
	else if(panel.gamestate == panel.settingsSoundState) {
		drawSoundSettingsScreen(gtd);
	}
	else if(panel.gamestate == panel.victoryScreenState) {
		drawVictoryScreen(gtd);
	} else if(panel.gamestate == panel.pauseState) {
		drawPauseScreen(gtd);
	}
	
	}
	public void drawLevelScreen(Graphics2D gtd) {

		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "Prismatic";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*2.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "Sunlight";
		int xx = getXForCenteredText(text,gtd);
		int yy = y + 50*3;
		gtd.drawString(text, xx, yy);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		
		text = "Moonlight";
		int xxx = getXForCenteredText(text,gtd);
		int yyy = y + 50*4;
		gtd.drawString(text, xxx, yyy);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxx-50/2, yyy);
			gtd.setColor(Color.black);
		}
		
		text = "Eclipse";
		int xxxx = getXForCenteredText(text,gtd);
		int yyyy = y + 50*5;
		gtd.drawString(text, xxxx, yyyy);
		if(commandNum == 2) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxxx-50/2, yyyy);
			gtd.setColor(Color.black);
		}
		text = "Void";
		xxxx = getXForCenteredText(text,gtd);
		yyyy = y + 50*6;
		gtd.drawString(text, xxxx, yyyy);
		if(commandNum == 3) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxxx-50/2, yyyy);
			gtd.setColor(Color.black);
		}
		text = "Back";
		xx = getXForCenteredText(text,gtd);
		yy = (int) (y + 50*7.5);
		gtd.drawString(text, xx, yy);
		if(commandNum == 4) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
	}
	public void drawTitleScreen(Graphics2D gtd) {

		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "Prismatic";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*2.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "New Game";
		int xx = getXForCenteredText(text,gtd);
		int yy = y + 50*3;
		gtd.drawString(text, xx, yy);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		
		text = "Load Game";
		int xxx = getXForCenteredText(text,gtd);
		int yyy = y + 50*4;
		gtd.drawString(text, xxx, yyy);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxx-50/2, yyy);
			gtd.setColor(Color.black);
		}
		
		text = "Settings";
		int xxxx = getXForCenteredText(text,gtd);
		int yyyy = y + 50*5;
		gtd.drawString(text, xxxx, yyyy);
		if(commandNum == 2) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxxx-50/2, yyyy);
			gtd.setColor(Color.black);
		}
		
		text = "Quit";
		xxxx = getXForCenteredText(text,gtd);
		yyyy = y + 50*6;
		gtd.drawString(text, xxxx, yyyy);
		if(commandNum == 3) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xxxx-50/2, yyyy);
			gtd.setColor(Color.black);
		}
		
	}
	public void drawLoadScreen(Graphics2D gtd) {

		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "Prismatic";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*2.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "File " + panel.currentFile;
		int xx = getXForCenteredText(text,gtd);
		int yy = y + 50*3;
		gtd.drawString(text, xx, yy);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		text = "Back";
		xx = getXForCenteredText(text,gtd);
		yy = y + 50*4;
		gtd.drawString(text, xx, yy);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
	}
	public void drawSettingsScreen(Graphics2D gtd) {

		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "Prismatic";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*2.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "Key bindings";
		int xx = getXForCenteredText(text,gtd);
		int yy = y + 50*3;
		gtd.drawString(text, xx, yy);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		text = "Sound";
		xx = getXForCenteredText(text,gtd);
		yy = y + 50*4;
		gtd.drawString(text, xx, yy);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		text = "AutoSave " + autoSaveTrue;
		xx = getXForCenteredText(text,gtd);
		yy = y + 50*5;
		gtd.drawString(text, xx, yy);
		if(commandNum == 2) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		text = "Advanced Commands " + panel.advancedCommands;
		xx = getXForCenteredText(text,gtd);
		yy = y + 50*6;
		gtd.drawString(text, xx, yy);
		if(commandNum == 3) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}
		text = "Back";
		xx = getXForCenteredText(text,gtd);
		yy = y + 50*7;
		gtd.drawString(text, xx, yy);
		if(commandNum == 4) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", xx-50/2, yy);
			gtd.setColor(Color.black);
		}

	}	
	public void drawSoundSettingsScreen(Graphics2D gtd) {
		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "Prismatic";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*2.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		if(panel.masterVolume >= -100) { text = "Master Volume " + (int) (panel.masterVolume + 100); }
		else {text = "Master Volume 0"; }

		x = getXForCenteredText(text,gtd);
		y = y + 50*3;
		gtd.drawString(text, x, y);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.black);
		}
		text = "Back";
		x = getXForCenteredText(text,gtd);
		y = y + 50*4;
		gtd.drawString(text, x, y);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.black);
		}
	}
	public void drawVictoryScreen(Graphics2D gtd) {
		gtd.setColor(Color.black);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "VICTORY";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*4.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "Congrats. You have";

		x = getXForCenteredText(text,gtd);
		y = y + 50*3;
		gtd.drawString(text, x, y);
		
		text = "completed this gamemode.";

		x = getXForCenteredText(text,gtd);
		y = y + 50*1;
		gtd.drawString(text, x, y);

		text = "Back to title screen";
		x = getXForCenteredText(text,gtd);
		y = y + 50*2;
		gtd.drawString(text, x, y);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.black);
		}
		text = "Quit";
		x = getXForCenteredText(text,gtd);
		y = y + 50*1;
		gtd.drawString(text, x, y);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.black);
		}
		
	}
	public void drawPauseScreen(Graphics2D gtd) {
	
		gtd.setColor(pauseScreenColor);
		gtd.fillRect(0,0, screenWidth, heigth);
		
		gtd.setColor(Color.white);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,96F));
		String text = "GAME PAUSED";
		int x = getXForCenteredText(text,gtd);
		int y = (int) (50*4.5);
		
		gtd.drawString(text, x+5, y+5);
		
		gtd.setFont(gtd.getFont().deriveFont(Font.BOLD,48F));
		
		text = "Continue";

		x = getXForCenteredText(text,gtd);
		y = y + 50*3;
		gtd.drawString(text, x, y);
		if(commandNum == 0) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.white);
		}
		
		text = "Save";

		x = getXForCenteredText(text,gtd);
		y = y + 50*1;
		gtd.drawString(text, x, y);
		if(commandNum == 1) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.white);
		}

		text = "Back to title screen";
		x = getXForCenteredText(text,gtd);
		y = y + 50*1;
		gtd.drawString(text, x, y);
		if(commandNum == 2) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.white);
		}
		text = "Quit";
		x = getXForCenteredText(text,gtd);
		y = y + 50*1;
		gtd.drawString(text, x, y);
		if(commandNum == 3) {
			gtd.setColor(Color.LIGHT_GRAY);
			gtd.drawString(">", x-50/2, y);
			gtd.setColor(Color.white);
		}

	}

public int getXForCenteredText(String text, Graphics2D gtd) {
		this.gtd = gtd;
		int length = (int)gtd.getFontMetrics().getStringBounds(text, gtd).getWidth();
		int x = 1366/2 - length/2;
		return x;
	}
	
}
