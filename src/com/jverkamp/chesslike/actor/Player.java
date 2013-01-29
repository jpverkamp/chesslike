package com.jverkamp.chesslike.actor;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.world.World;

public class Player extends Actor {
	/**
	 * Create a new player.
	 * @param world The world to create the player in
	 * @param r Initial row
	 * @param c Initial column
	 */
	public Player(World world, int r, int c) {
		super(world, new Glyph('@', Color.WHITE));
		
		R = r; 
		C = c;
	}

	@Override
	public void input(KeyEvent event) {
		// Move the player
		switch(event.getKeyCode()) {
		
		case KeyEvent.VK_W:
			if (World.getTile(R - 1, C).IsWalkable) R -= 1;
			break;
			
		case KeyEvent.VK_A:
			if (World.getTile(R, C - 1).IsWalkable) C -= 1;
			break;
			
		case KeyEvent.VK_S:
			if (World.getTile(R + 1, C).IsWalkable) R += 1;
			break;
			
		case KeyEvent.VK_D:
			if (World.getTile(R, C + 1).IsWalkable) C += 1;
			break;
		}
		
		// TODO: Check if we want to update the viewport
	}
}
