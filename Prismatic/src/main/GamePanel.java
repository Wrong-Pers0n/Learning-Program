package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.FloatControl;

import data.Data;
import data.SaveLoad;
import tile.TileManager;


public class GamePanel extends javax.swing.JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8184305660442070766L;
	
	public ArrayList<Wall> walls = new ArrayList();
	public ArrayList<Cannon> cannons = new ArrayList();
	public ArrayList<Portal> portals = new ArrayList();
	public ArrayList<Particles> particles = new ArrayList();
	public ArrayList<Spike> spike = new ArrayList();
	
	
	
	public int tileSize = 40;
	
	public int loadFile;
	public int currentFile = 1;
	public int level = 1;

	public int maxWorldCol = 35;
	public int maxWorldRow = 20;
	
	public Player player = new Player(tileSize*8,tileSize*8,this);
	TileManager tileManager = new TileManager(this);
	SaveLoad saveload = new SaveLoad(this);
	Sound music = new Sound(this);
	Sound se = new Sound(this);
	static Random random = new Random();
	Portal portal = new Portal(-10,-10,40,40, this);
	Data data = new Data();
	
	Particles particle;
	public UI ui;
	Background background;
	Graphics2D gtd;
	Timer gameTimer;

	
	public boolean debugMode = false;
	public boolean advancedCommands = false;
	
	boolean spaceReleased = false;
	boolean shiftDown = false;
	
	public int gamemode;
	public double startX,startY;
	public volatile int gamestate;
	
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int levelState = 3;
	public final int loadState = 4;
	public final int settingsState = 5;
	public final int keybindState = 6;
	public final int settingsSoundState = 7;
	public final int victoryScreenState = 8;
	public final int inBetweenState = 9;

	int size;
	
	float masterVolume = 0;
	int code;
	
	public GamePanel() {
		
		playMusic(1, false, 2000);
		
		
		
		addMouseWheelListener(new MouseWheelListener() {
		    public void mouseWheelMoved(MouseWheelEvent e) {
		    	if(gamestate != playState) { return; }
		        // Adjust volume based on scroll direction
		        if (e.getWheelRotation() < 0) {
		            // Scroll up: Increase volume
		            masterVolume = Math.min(masterVolume + 5, 0);  // Cap at 0 (maximum)
		        } else {
		            // Scroll down: Decrease volume
		            masterVolume = Math.max(masterVolume - 5, -100);  // Cap at -40 (minimum)
		        }

		        // Apply the volume change to all clips
		        music.setMasterSound();
		    }
		});



		gamestate = 0;
	
		//player = new Player(tileSize*8,tileSize*8,this); // 400 = x, 300 = y, this = this gamepanel
		ui = new UI(0,0,this);
		background = new Background(this);
		saveload = new SaveLoad(this);
		data = new Data();
		
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {
			
				if(gamestate == playState) {
				player.set();
				}
				
				repaint();
			}
			
		}, 0, 17);
		
	}
	

	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D gtd = (Graphics2D) g;
		
		if(gamestate != inBetweenState) {
		background.drawBackground(gtd);
		} else {
			background.drawLoadingScreen(gtd);
		}
		
		if(gamestate == playState || gamestate == victoryScreenState || gamestate == pauseState) {
			player.draw(gtd);
			
		
		for(Portal portal: portals) portal.draw(gtd);
		for(Wall wall: walls) wall.draw(gtd);
		for(Cannon cannon: cannons) cannon.draw(gtd);
		for(Spike spike: spike) spike.draw(gtd);
		for(Particles particle: particles) particle.draw(gtd);

		}
		ui.drawUI(gtd);
		
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

	public void keyPressed(KeyEvent e) {
		spaceReleased = false;
		code = e.getKeyCode();
		if(code == KeyEvent.VK_SHIFT) { shiftDown = true; }
		
		if(e.getKeyChar() == 'a') {	player.keyLeft = true;	}
		if(e.getKeyChar() == 'd') {	player.keyRight = true;	}
		if(e.getKeyChar() == 's') {	player.keyDown = true;	}
		if(e.getKeyChar() == 'w') {	player.keyUp = true;	}
		if(code == KeyEvent.VK_SPACE) { player.keyUp = true; }
		if(code == KeyEvent.VK_Q && gamestate == playState) { saveload.wipeSave(currentFile, player); }
		
		
		if (gamestate == loadState) {

	    	 if (e.getKeyChar() == 'a') {
	             currentFile--;
	             if (currentFile < 1) {
	                 currentFile = 20;
	             }
	         } else if (e.getKeyChar() == 'd') {
	             currentFile++;
	             if (currentFile > 20) {
	                 currentFile = 1;
	             }
	            
	        }
		} else if (gamestate == settingsSoundState && ui.commandNum == 0) {

	    	 if (e.getKeyChar() == 'a' && masterVolume > -100) {
	             masterVolume--;
	             music.setMasterSound();
	             if(masterVolume == -100) { masterVolume = -250; }
	         } else if (e.getKeyChar() == 'd' && masterVolume < 0) {
	        	 if(masterVolume == -250) { masterVolume = -100; }
	        	 masterVolume++;
	        	 music.setMasterSound();
	        }
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
		
		
		code = e.getKeyCode();

		if(code == KeyEvent.VK_ESCAPE && gamestate == playState) { gamestate = pauseState; } 
		else if (code == KeyEvent.VK_ESCAPE && gamestate == pauseState) { gamestate = playState; } 
		
		if(code == KeyEvent.VK_SPACE) { player.keyUp = false; }
		
		if(code == KeyEvent.VK_SHIFT) { shiftDown = false; }
		
		if(advancedCommands) {
			if(code == KeyEvent.VK_D && shiftDown == true) { debugMode = !debugMode;}
		}
		
		if(code == KeyEvent.VK_A) {	player.keyLeft = false;	}
		if(code == KeyEvent.VK_D) {	player.keyRight = false;	}
		if(code == KeyEvent.VK_S) {	player.keyDown = false;	}
		if(code == KeyEvent.VK_W) {	player.keyUp = false;	}
		
		if(code == KeyEvent.VK_S && gamestate == levelState) {
			ui.commandNum++;
			if(ui.commandNum > 4) {
				ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_W && gamestate == levelState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 4;
			}
		}
		
		
		
		if (code == KeyEvent.VK_SPACE && gamestate != playState) {
			
		        
		    

		    // Handle titleState commands
		    if (gamestate == titleState) {
		        switch (ui.commandNum) {
		            case 0:
		                ui.commandNum = 0;
		                player.keyUp = false;
		                gamestate = levelState;
		                break;
		            case 1:
		                ui.commandNum = 0;
		                player.keyUp = false;
		                gamestate = loadState;
		                break;
		            case 2:
		                ui.commandNum = 0;
		                player.keyUp = false;
		                gamestate = settingsState;
		                break;
		            case 3:
		                System.exit(1);
		                break;
		        }
		    }

		    // Handle when game state is levelState
		    else if (gamestate == levelState) {
		        switch (ui.commandNum) {
		            case 0:
		                gamemode = 1;
		                level = player.sunLevel;
		                makeWalls();
		                break;
		            case 1:
		                gamemode = 2;
		                level = player.moonLevel;
		                makeWalls();
		                break;
		            case 2:
		                gamemode = 3;
		                level = player.eclipseLevel;
		                makeWalls();
		                break;
		            case 3:
		                gamemode = 4;
		                level = player.voidLevel;
		                makeWalls();
		                break;
		            case 4:
		            	ui.commandNum = 0;
				        gamestate = titleState;
				        break;
		        }
		    }
		    else if (gamestate == pauseState) {
		        switch (ui.commandNum) {
		            case 0:
		                gamestate = playState;
		                break;
		            case 1:
		                saveload.save(currentFile, player, true);
		                break;
		            case 2:
		            	ui.commandNum = 0;
		            	gamestate = titleState;
		                break;
		            case 3:
		                System.exit(0);
		                break;
		        }
		    }
		    else if (gamestate == settingsSoundState) {
		    	switch(ui.commandNum) {
		    		case 0:
		    			break;
		    		case 1:
		    			ui.commandNum = 0;
				        gamestate = settingsState;
		    			break;
		    	}
		    }
		    else if(gamestate == victoryScreenState) {
		    	switch(ui.commandNum) {
		    	case 0:
		    		ui.commandNum = 0;
		    		gamestate = titleState;
		    		break;
		    	case 1: 
		    		System.exit(0);
		    		break;
		    	}
		    }
		    else if (gamestate == loadState) {

		        if (code == KeyEvent.VK_SPACE) {
		            if (ui.commandNum == 0) {
		                gamestate = levelState;
		                saveload.load(currentFile, player);
		                System.out.println("Loaded file: " + currentFile);
		            } else if (ui.commandNum == 1) {
		                ui.commandNum = 0;
		                gamestate = titleState;
		            }
		        }
		    }
		    else if (gamestate == settingsState) {
		    	switch(ui.commandNum) {
		    	case 0:
		    		break;
		    	case 1:
			        ui.commandNum = 0;
			        gamestate = settingsSoundState;
		    		break;
		    	case 2: 
		    		
		    		break;
		    	case 3:
		    		advancedCommands = !advancedCommands;
		    		break;
		    	case 4:
			        ui.commandNum = 0;
			        gamestate = titleState;
		    		break;
		    	}
		    }
		    else if (gamestate == levelState && ui.commandNum == 4) {
		    	ui.commandNum = 0;
	    		gamestate = titleState;

		    }
		    
		}


		if(code == KeyEvent.VK_E && gamestate == playState) { makeWalls();}
		if(code == KeyEvent.VK_R && shiftDown == false) { saveload.save(currentFile, player, false); }
		if(code == KeyEvent.VK_R && shiftDown == true) { saveload.save(currentFile, player, true); System.out.println("force saved");}
		
		if(code == KeyEvent.VK_S && gamestate == titleState) {
			ui.commandNum++;
			if(ui.commandNum > 3) {
				ui.commandNum = 0;
			}
		} else if(code == KeyEvent.VK_S && gamestate == loadState) {
			ui.commandNum++;
			if(ui.commandNum > 1) {
				ui.commandNum = 0;
			}
		} else if(code == KeyEvent.VK_S && gamestate == settingsState) {
			ui.commandNum++;
			if(ui.commandNum > 4) {
				ui.commandNum = 0;
			}
		} else if(code == KeyEvent.VK_S && gamestate == settingsSoundState) {
			ui.commandNum++;
			if(ui.commandNum > 1) {
				ui.commandNum = 0;
			}
		} else if(code == KeyEvent.VK_S && gamestate == victoryScreenState) {
			ui.commandNum++;
			if(ui.commandNum > 1) {
				ui.commandNum = 0;
			}
		} else if(code == KeyEvent.VK_S && gamestate == pauseState) {
			ui.commandNum++;
			if(ui.commandNum > 3) {
				ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_W && gamestate == titleState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 3;
			}
		} else if(code == KeyEvent.VK_W && gamestate == loadState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 1;
			}
		} else if(code == KeyEvent.VK_W && gamestate == settingsState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 4;
			}
		} else if(code == KeyEvent.VK_W && gamestate == settingsSoundState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 1;
			}
		} else if(code == KeyEvent.VK_W && gamestate == victoryScreenState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 1;
			}
		} else if(code == KeyEvent.VK_W && gamestate == pauseState) {
			ui.commandNum--;
			if(ui.commandNum < 0) {
				ui.commandNum = 4;
			}
		}
		
	}

	public void playMusic(int i, boolean fade, int fadeInTime) {
		
		if(fade == false) {
			music.play(i);
		} else {
			fadeInMusic(fadeInTime, i);
		}
		music.loop(i);
		
	}
	public void stopMusic(boolean fade, int fadeOutTime, int i) {
		if(fade == true) {
			fadeOutMusic(2000, i);
		} else {
			music.stop(i);
		}
	}
	public void playSE(int i) {
		se.play(i);
	}	
	public void fadeInMusic(int fadeDuration, int clipIndex) {
	    new Thread(() -> {
	        try {
	            // Iterate over all clips and apply the fade-in effect
	            for (int i = 0; i < music.clips.length; i++) {
	                if (music.clips[i] != null && music.clips[i].isOpen()) {
	                    FloatControl gainControl = (FloatControl) music.clips[i].getControl(FloatControl.Type.MASTER_GAIN);
	                    
	                    float initialVolume = -80.0f;  // Start from silence
	                    gainControl.setValue(initialVolume);
	                    
	                    // Play the specified clip
	                    if (i == clipIndex) {
	                        music.play(i);
	                    }

	                    float fadeStep = Math.abs(initialVolume) / (fadeDuration / 50f);  // Gradual step increase
	                    
	                    while (initialVolume < 0.0f) {  // Gradually bring volume up to 0 dB
	                        initialVolume += fadeStep;
	                        gainControl.setValue(Math.min(initialVolume, 0.0f));  // Update volume
	                        Thread.sleep(50);  // Adjust for smoother fading
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
	}

	public void fadeOutMusic(int fadeDuration, int clipIndex) {
	    new Thread(() -> {
	        try {
	            // Iterate over all clips and apply the fade-out effect
	            for (int i = 0; i < music.clips.length; i++) {
	                if (music.clips[i] != null && music.clips[i].isOpen()) {
	                    FloatControl gainControl = (FloatControl) music.clips[i].getControl(FloatControl.Type.MASTER_GAIN);
	                    
	                    // Fade-out effect for the specified clip
	                    if (i == clipIndex) {
	                        float initialVolume = gainControl.getValue();  // Current volume level
	                        float fadeStep = initialVolume / (fadeDuration / 50f);  // Gradual step reduction
	                        
	                        while (initialVolume > -80.0f) {  // Usually, -80dB is considered silence
	                            initialVolume -= fadeStep;
	                            gainControl.setValue(Math.max(initialVolume, -80.0f));  // Update volume
	                            Thread.sleep(50);  // Adjust this sleep for smoother fading
	                        }
	                        music.stop(i);  // Stop the music when fully faded out
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
	}

	
	public void makeWalls() {
			
		gamestate = inBetweenState;
		randomInt();
		//player.fly = 1;
		tileManager.loadLevel(level, gamemode);
		level++;
		if(gamemode == 0) {
			player.sunLevel++;
		} else if(gamemode == 1) {
			player.moonLevel++;
		} else if(gamemode == 2) {
			player.eclipseLevel++;
		} else if(gamemode == 3) {
			player.voidLevel++;
		}
		player.resetLocation((int)(startX),(int)(startY));

		saveload.save(currentFile, player, false);	
	}
	
	public void randomInt() { 
		size = random.nextInt(20)+1; 
	}


}