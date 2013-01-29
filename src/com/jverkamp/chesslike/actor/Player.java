package com.jverkamp.chesslike.actor;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.world.World;

public class Player extends Actor {
	/**
	 * Create a new player.
	 * @param world The world to create the player in.
	 * @param location The point to place the player at.
	 */
	public Player(World world, Point location) {
		super(world, new Glyph('@', Color.WHITE));
		Location = location;
	}

	@Override
	public void input(KeyEvent event) {
		// Move the player
		switch(event.getKeyCode()) {
		
		case KeyEvent.VK_W:
			if (World.getTile(Location.x, Location.y - 1).IsWalkable) Location.y -= 1;
			break;
			
		case KeyEvent.VK_A:
			if (World.getTile(Location.x - 1, Location.y).IsWalkable) Location.x -= 1;
			break;
			
		case KeyEvent.VK_S:
			if (World.getTile(Location.x, Location.y + 1).IsWalkable) Location.y += 1;
			break;
			
		case KeyEvent.VK_D:
			if (World.getTile(Location.x + 1, Location.y).IsWalkable) Location.x += 1;
			break;
		}
		
		System.out.println("player is at " + Location);
		
		// TODO: Check if we want to update the viewport
	}
}
