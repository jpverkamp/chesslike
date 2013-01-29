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
		int code = event.getKeyCode();
		
		// Move the player
		
		// UP LEFT
		if (code == KeyEvent.VK_NUMPAD7)
			moveTo(Location.x - 1, Location.y - 1);
		
		// UP
		else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_NUMPAD8)
			moveTo(Location.x, Location.y - 1);
		
		// UP RIGHT
		else if (code == KeyEvent.VK_NUMPAD9)
			moveTo(Location.x + 1, Location.y - 1);
		
		// LEFT
		else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_NUMPAD4)
			moveTo(Location.x - 1, Location.y);
		
		// RIGHT
		else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_NUMPAD6)
			moveTo(Location.x + 1, Location.y);
		
		// DOWN LEFT
		if (code == KeyEvent.VK_NUMPAD1)
			moveTo(Location.x - 1, Location.y + 1);
		
		// DOWN
		else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_NUMPAD2)
			moveTo(Location.x, Location.y + 1);
		
		// DOWN RIGHT
		else if (code == KeyEvent.VK_NUMPAD3)
			moveTo(Location.x + 1, Location.y + 1);
		
		
		// TODO: Check if we want to update the viewport
	}
}
