package tile;
	
	import java.awt.Graphics2D;
	import java.awt.image.BufferedImage;
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	
	import javax.imageio.ImageIO;
	
	import main.Cannon;
	import main.GamePanel;
	import main.Particles;
	import main.Portal;
	import main.Spike;
	import main.UtilityTool;
	import main.Wall;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.TimeUnit;
	
	public class TileManager {
	
		GamePanel panel;
		public Tile[] tile;
		public int mapTileNum[][];
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		public TileManager(GamePanel panel) {
			
			this.panel = panel;
			
			tile = new Tile[10];
			mapTileNum = new int[panel.maxWorldCol][panel.maxWorldRow];
			
			getTileImage();
			//loadMap("/maps/moon/moon_level_1.txt");
			
		}
		
		public void getTileImage() {
				setup(1, "normal_tile_middle");
				
	
		}
		public void setup(int index, String imageName) {
			
			UtilityTool uTool = new UtilityTool();
			
			try {
				tile[index] = new Tile();
				tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName+".png"));
				tile[index].image = uTool.scaleImage(tile[index].image,panel.tileSize,panel.tileSize);
				}catch(IOException e) {
					  System.err.println("Failed to load tile image: " + imageName);
				e.printStackTrace();
			}
		}
		
		public void loadLevel(int currentLevel, int gamemode) {
			panel.gamestate = panel.inBetweenState;
			new Thread(() -> {
				
		        if (gamemode == 1) {
		            loadMap("/maps/sun/sun_level_" + currentLevel + ".txt");
		        } else if (gamemode == 2) {
		            loadMap("/maps/moon/moon_level_" + currentLevel + ".txt");
		        } else if (gamemode == 3) {
		            loadMap("/maps/eclipse/eclipse_level_" + currentLevel + ".txt");
		        } else if (gamemode == 4) {
		            loadMap("/maps/void/void_level_" + currentLevel + ".txt");
		        }

		        try {
		            TimeUnit.MILLISECONDS.sleep(500);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }

		        panel.gamestate = panel.playState;
		    }).start();
			 
		}
		public void loadMap(String filePath) {
			
			InputStream is = getClass().getResourceAsStream(filePath);
	        if (is == null) {
	            System.err.println("Map file not found: " + filePath);
	            
	            final Logger logger = Logger.getLogger(TileManager.class.getName());        
	            logger.warning("There was no specified map for this level.");
	            //logger.info("This is a warning message");
	            //logger.severe("This is an error message");

	            panel.ui.commandNum = 0;
	            panel.gamestate = panel.victoryScreenState;
	            panel.level--;
	            
	            if(panel.gamemode == 0) {
	            	panel.player.sunLevel--;
	    		} else if(panel.gamemode == 1) {
	    			panel.player.moonLevel--;
	    		} else if(panel.gamemode == 2) {
	    			panel.player.eclipseLevel--;
	    		} else if(panel.gamemode == 3) {
	    			panel.player.voidLevel--;
	    		}
	            return;
	        }
	        
			
			try {

				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
				int col = 0;
				int row = 0;
				
				while(col < panel.maxWorldCol && row < panel.maxWorldRow) {
					
					String line = br.readLine();
					
					while(col < panel.maxWorldCol) {
						String numbers[] = line.split(" ");
						
						int num = Integer.parseInt(numbers[col]);
						
						mapTileNum[col][row] = num;
						col++;
					}
					if(col == panel.maxWorldCol) {
						col = 0;
						row++;
					}
				}
				br.close();
				
			}catch(Exception e) {
				
			}
			
			initializeGameObjects();
			
		}
		
		public void initializeGameObjects() {

			panel.walls.removeAll(panel.walls);
			panel.cannons.removeAll(panel.cannons);
			panel.portals.removeAll(panel.portals);
			panel.particles.removeAll(panel.particles);
			panel.spike.removeAll(panel.spike);
		    
			panel.player.doublejump = 2;
			panel.player.fly = 0;
			
		    int worldCol = 0;
		    int worldRow = 0;
	
		    while(worldCol < panel.maxWorldCol && worldRow < panel.maxWorldRow) {
		        int tileNum = mapTileNum[worldCol][worldRow];

	
		        int worldX = worldCol * panel.tileSize;
		        int worldY = worldRow * panel.tileSize;
	
		        switch (tileNum) {
		        case 0:
		        	break;
	            case 1: // Wall
	                panel.walls.add(new Wall(worldX, worldY, panel.tileSize, panel.tileSize));
	                break;
	            case 2: // Cannon
	                panel.cannons.add(new Cannon(worldX, worldY, panel.tileSize, panel.tileSize, panel));
	                break;
	            case 3: // Portal
	                panel.portals.add(new Portal(worldX, worldY, panel.tileSize, panel.tileSize,panel));
	                break;
	            case 4: // Particles
	                panel.particles.add(new Particles(worldX, worldY, panel.tileSize, panel.tileSize, 1, panel));
	                break;
	            case 5: // spawn for player
	            	panel.startX = worldX;
	            	panel.startY = worldY;
	            	break;
	            case 6: // slab
	            	panel.walls.add(new Wall(worldX, worldY+panel.tileSize/2, panel.tileSize, panel.tileSize/2));
	            	break;
	            case 7: // add for double jump. i was too lazy to make it check every level
	            	panel.player.doublejump = 1;
	            	panel.player.fly = 1;
	            	break;
	            case 8: // spikes up
	            	panel.spike.add(new Spike(worldX, worldY+panel.tileSize/2, panel.tileSize, panel.tileSize/2, 1));
	            	break;
	            case 9: // spikes on the left
	            	panel.spike.add(new Spike(worldX, worldY+panel.tileSize/2, panel.tileSize, panel.tileSize/2, 2));
	            	break;
	            case 10: // spikes down
	            	panel.spike.add(new Spike(worldX, worldY+panel.tileSize/2, panel.tileSize, panel.tileSize/2, 3));
	            	break;
	            case 11: // spikes on the right
	            	panel.spike.add(new Spike(worldX, worldY+panel.tileSize/2, panel.tileSize, panel.tileSize/2, 4));
	            	break;
	            default:
	            	System.out.println("tile dosent exist");
	                break;
	        }
	
		        worldCol++;
	
		        if (worldCol == panel.maxWorldCol) {
		            worldCol = 0;
		            worldRow++;
		        }
		    }
		    //panel.gamestate = panel.playState;
		}
	}