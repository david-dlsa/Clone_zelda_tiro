package com.lkestudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lkestudios.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	
	//se por false, entraria na logica do fog of wars, e teria que chamar os respectivos comandos no player
	public boolean show = true;
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x,int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	
	public void render(Graphics g) {
		if(show) {
			g.drawImage(sprite,x  - Camera.x,y  - Camera.y,null);
		}
	}
}
