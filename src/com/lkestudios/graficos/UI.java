package com.lkestudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.lkestudios.entities.Player;
import com.lkestudios.main.Game;
import com.lkestudios.world.Camera;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(8 , 4, 70, 8);
		g.setColor(Color.GREEN);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.maxlife)*70), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,8));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxlife, 30 , 11);
		//Barra de vida em cima do jogador e seguindo ele
		//g.fillRect(Game.player.getX() - Camera.x - 15, Game.player.getY() - Camera.y - 8, (Player.life/Player.maxlife)*50, 5);
	}
}
