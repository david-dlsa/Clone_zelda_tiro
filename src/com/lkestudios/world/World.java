 package com.lkestudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.lkestudios.entities.*;
import com.lkestudios.graficos.Spritesheet;
import com.lkestudios.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		
		//MAPA POR LEITURA DO BITMAP
				try {
					BufferedImage map = ImageIO.read(getClass().getResource(path));
					int[] pixels = new int[map.getWidth() * map.getHeight()];
					WIDTH = map.getWidth();
					HEIGHT = map.getHeight();
					tiles = new Tile[map.getWidth() * map.getHeight()];
					map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
					
					for(int xx = 0; xx < map.getWidth(); xx++) {
						for(int yy = 0; yy < map.getHeight(); yy++) {
							int pixelAtual = pixels[xx + (yy * map.getWidth())];
							
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
							 if(pixelAtual == 0xFF000000) {
								//Floor(chão)
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
							}
							else if(pixelAtual == 0xFFFFFFFF) {
								//Parede
								tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
							}
							else if(pixelAtual == 0xFF0026FF) {
								//Player
								Game.player.setX(xx*16);
								Game.player.setY(yy*16);
							}
							else if(pixelAtual == 0xFFFF0000) {
								//Enemy
								Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN);
								Game.entities.add(en);
								Game.enemies.add(en);
							}
							else if(pixelAtual == 0xFF404040) {
								//Weapon
								Game.entities.add(new Weapon(xx*16,yy*16,16,16,Entity.WEAPON_EN));
							}
							else if(pixelAtual == 0xFFFF006E) {
								//Life Pack
								LifePack pack = new LifePack(xx*16,yy*16,16,16,Entity.LIFEPACK_EN);
								Game.entities.add(pack);
							}
							else if(pixelAtual == 0xFFFFD800) {
								//Bullet
								Game.entities.add(new Bullet(xx*16,yy*16,16,16,Entity.BULLET_EN));
							}
							
						}
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		/*
		//MAPA DE GERAÇAO ALTOMATICA
		Game.player.setX(0);
		Game.player.setY(0);
		WIDTH = 100;
		HEIGHT = 100;
		tiles = new Tile[WIDTH*HEIGHT];
		
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				tiles[xx + yy*WIDTH] = new WallTile(xx*16, yy*16,Tile.TILE_WALL);
			}
		}
		
		int dir = 0;
		int xx = 0;
		int yy =0;
		
		for(int i = 0; i < 200; i++) {
			tiles[xx + yy*WIDTH] = new FloorTile(xx*16, yy*16,Tile.TILE_FLOOR);
			if(dir == 0) {
				//direita

				if(xx < WIDTH) {
					xx++;
				}
			}
			else if(dir == 1) {
				//esquerda
				if(xx > 0) {
					xx--;
				}
			}
			else if(dir == 2) {
				//baixo
				if(yy < HEIGHT) {
					yy++;
				}
			}
			else if(dir == 3) {
				//cima
				if(yy > 0) {
					yy--;
				}
			}
			
			if(Game.rand.nextInt(100) < 30) {
				dir = Game.rand.nextInt(4);
			}
			
			
		}
		*/
	
	}
	
	public static void generateParticle(int amount, int x, int y) {
		for(int i = 0; i < amount; i++) {
			Game.entities.add(new Particle(x,y,1,1,null));
		}
	}
	
	public static boolean isFreeDynamic(int xnext,int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height - 1)/ TILE_SIZE;
		
		int x4 = (xnext + width - 1) / TILE_SIZE;
		int y4 = (ynext + height - 1)/ TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile || 
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile || 
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile || 
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}
	
	public static boolean isFree(int xnext,int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext /TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1)/TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1)/ TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile || 
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile || 
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile || 
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}
	
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.bullets.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/" + level);
		Game.minimapa = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.minimapaPixels = ((DataBufferInt)Game.minimapa.getRaster().getDataBuffer()).getData();	
		return;
	}
	
	public void render(Graphics g) {
		//">>" é uma forma diferente de representar o resultado da divisao( 4 pq é a msm coisa q div por 16)
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if( xx < 0 || yy < 0 || xx >= WIDTH || yy>= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static void renderMiniMap() {
		for(int i = 0; i < Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0xff0000;
				}
			}
		}
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		
		Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x0000ff;
	}
}
