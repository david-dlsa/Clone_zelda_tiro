package com.lkestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lkestudios.main.Game;
import com.lkestudios.world.Camera;
import com.lkestudios.world.World;

public class BulletShoot extends Entity{

	private double dx;
	private double dy;
	private double spd = 4;
	
	private int life = 30, curlife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy ) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		if(World.isFreeDynamic((int)(x+(dx*spd)),(int)(y+(dy*spd)),3,3)){
			x+=dx * spd;
			y+=dy * spd;
			curlife++;
		}
		else {
			Game.bullets.remove(this);
			World.generateParticle(10,(int)x,(int)y);
			return;
		}
		if(curlife == life) {
			Game.bullets.remove(this);
			return;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}
