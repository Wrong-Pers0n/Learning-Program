package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Particles {

    private boolean fadingIn = true;
    private boolean fadingOut = false;

    private int x;
    private int y;
    private int width;
    private int height;
    private int type;
    private boolean isAlive = false;
    
    int alpha = 190; // 50% transparent of 254
    Color pauseScreenColor = new Color(0, 0, 0, alpha);
    Random random = new Random();
    
    GamePanel panel;
    private final boolean forever = true;

    public Particles(int x, int y, int width, int height, int type, GamePanel panel) {
      
    	this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.panel = panel;
        setupParticle();
        
    }

    private void dustParticle() {

        
        new Thread(() -> {
        	while(forever) {
        		fadingIn = true;
        	isAlive = true;
            try {
      	  		TimeUnit.MILLISECONDS.sleep(600+random.nextInt(60));
      	  	} catch (InterruptedException e) {
      	  		e.printStackTrace();
      	  	}
            fadingIn = false;
           
      	  	try {
      	  		TimeUnit.MILLISECONDS.sleep(6000+random.nextInt(600));
      	  	} catch (InterruptedException e) {
      	  		e.printStackTrace();
      	  	}
      	  	fadingOut = true;
      	  	
      	  try {
    	  		TimeUnit.MILLISECONDS.sleep(600+random.nextInt(60));
    	  	} catch (InterruptedException e) {
    	  		e.printStackTrace();
    	  	}
      
      	  	this.x = random.nextInt(panel.ui.screenWidth);
      	  	this.y = random.nextInt(768);
      	  	
      	  	isAlive = false;
      	  	fadingOut = false;
      	  	try {
      	  		TimeUnit.MILLISECONDS.sleep(5940+random.nextInt(120));
    	  	} catch (InterruptedException e) {
    	  		e.printStackTrace();
    	  	}
      	  	isAlive = true;
        	}
        	
            
	    }).start();
        
    }

    public void draw(Graphics2D gtd) {
        if (type == 1) {
            drawDust(gtd);
        } else if (type == 2) {
            gtd.setColor(Color.LIGHT_GRAY);
            gtd.drawRect(x, y, width, height);
            gtd.setColor(Color.RED);
            gtd.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
    }

    private void drawDust(Graphics2D gtd) {
    	
    	if(isAlive){
            gtd.fillOval(x, type, width, height);
        } else if(!isAlive) {
        	gtd.setColor(Color.yellow);
            gtd.fillOval(x, type, width, height);
        }
        if (fadingIn) {
        	gtd.setColor(Color.red);
            gtd.fillOval(x, type, width, height);
        } else if (fadingOut) {
        	gtd.setColor(Color.blue);
            gtd.fillOval(x, type, width, height);
        } 
        
        updateFrame();
    }

    private void updateFrame() {

    }

    private void setupParticle() {
    	
    	if (type == 1) {
  	  		dustParticle();
  	  	}
    	
    	
    }

}
