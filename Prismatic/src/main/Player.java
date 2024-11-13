package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    static GamePanel panel;
    static Portal portal = new Portal(50 * 16, 50 * 16, 50, 50, null);

    public int x;
    public int y;
    int width;
    int height;
    public int doublejump = 2;
    public int fly = 1;
    public int sunLevel = 1;
    public int moonLevel = 1;
    public int eclipseLevel = 1;
    public int voidLevel = 1;
    public boolean transition = false;
    public int transframe = 1;
    public int transupdate = 0;

    private BufferedImage image;
    private static BufferedImage playerImage;
    private double xspeed;
    private double yspeed;
    public Rectangle hitbox;
    
    public boolean keyLeft;
    public boolean keyRight;
    public boolean keyUp;
    public boolean keyDown;
    
  


    public Player(int x, int y, GamePanel panel) {
        Player.panel = panel;
        this.x = x;
        this.y = y;
        width = panel.tileSize;
        height = panel.tileSize;
        hitbox = new Rectangle(this.x, this.y, width, height);
        setupPlayer();
    }

    public void resetLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set() {
    	
    	if(panel.gamestate == panel.inBetweenState) { return; }
        
    		updateSpeed();
    		updatePosition();
    		
    		handleCollisions();
            updateHitbox();
        
        updateCannons();
        updateSpikesAndPortals();
    }

    private void updateSpeed() {
        if (keyLeft && keyRight || !keyLeft && !keyRight) {
            xspeed *= 0.8;
        } else if (keyLeft) {
            xspeed--;
        } else if (keyRight) {
            xspeed++;
        }

        if (Math.abs(xspeed) < 0.75) {
            xspeed = 0;
        }
        int speedLimit = (int) ((doublejump == 1) ? panel.tileSize / 7.85 : panel.tileSize / 6.5);
        if (xspeed > 7) {
            xspeed = speedLimit;
        } else if (xspeed < -7) {
            xspeed = -speedLimit;
        }

        if (y >= 750) { resetLocation((int) panel.startX, (int) panel.startY);  xspeed = 0; yspeed = 0; }

        if (x <= -1) {
            xspeed = 1;
        } else if (x >= 1310) {
            xspeed = -1;
        }

        if (keyDown) { yspeed += 0.75; }
        else if (keyUp) { jump(); }

        yspeed += 0.5;
    }

    private void updatePosition() {
        x += xspeed;
        y += yspeed;
    }

    private void updateHitbox() {
        hitbox.setLocation(x, y);
    }

    private void handleCollisions() {
        // Horizontal collision
        hitbox.x += xspeed;
        for (Wall wall : panel.walls) {
            if (hitbox.intersects(wall.hitBox)) {
                hitbox.x -= xspeed;
                while (!wall.hitBox.intersects(hitbox)) {
                    hitbox.x += Math.signum(xspeed);
                }
                hitbox.x -= Math.signum(xspeed);
                xspeed = 0;
                x = hitbox.x;
            }
        }

        // Vertical collision
        hitbox.y += yspeed;
        for (Wall wall : panel.walls) {
            if (hitbox.intersects(wall.hitBox)) {
                hitbox.y -= yspeed;
                while (!wall.hitBox.intersects(hitbox)) {
                    hitbox.y += Math.signum(yspeed);
                }
                hitbox.y -= Math.signum(yspeed);
                yspeed = 0;
                y = hitbox.y;
                if (doublejump == 1) {
                    doublejump = 0;
                }
            }
        }
    }

    public void jump() {
        if (isOnGround()) {
            yspeed = panel.tileSize / -4.1;
            fly = 1;
        } else if (doublejump == 0) {
            yspeed = -10;
            doublejump++;
        }
    }

    private boolean isOnGround() {
        hitbox.y++;
        for (Wall wall : panel.walls) {
            if (wall.hitBox.intersects(hitbox)) {
                hitbox.y--;
                return true;
            }
        } 
        for (Cannon cannon : panel.cannons) {
            if (cannon.hitBox.intersects(hitbox) && fly == 1) {
                return true;
            }
        }
        hitbox.y--;
        return false;
    }


    public void updateCannons() {
        for (Cannon cannon : panel.cannons) {
            if (cannon.hitBox.intersects(hitbox) && fly == 1) {
                xspeed = 0;
                yspeed = 0;
                if (doublejump == 1) {
                    doublejump = 0;
                }
            }
        }
    }


    public void updateSpikesAndPortals() {
        for (Portal portal : panel.portals) {
            if (hitbox.intersects(portal.getHitBox())) {
                panel.makeWalls();
            }
        }
        for (Spike spike : panel.spike) {
            if (hitbox.intersects(spike.getHitBox())) {
                resetLocation((int) panel.startX, (int) panel.startY);
            }
        }
    }

    public void draw(Graphics2D gtd) {
        gtd.drawImage(image, x - 3, y - 20, width + 10, height + 30, null);

        if (panel.debugMode) {
            gtd.setColor(Color.red);
            gtd.draw(hitbox);
        }
    }

    private void setupPlayer() {
        if (playerImage == null) {
            playerImage = setup("/player/4");
        }
        image = playerImage;
    }

    private BufferedImage setup(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
