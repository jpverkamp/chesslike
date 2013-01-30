package com.jverkamp.chesslike.actor;

import java.awt.Point;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.tile.Tile;
import com.jverkamp.chesslike.world.World;

public class King extends Actor {
	/**
	 * Create a new player.
	 * @param world The world to create the player in.
	 * @param team The team to add the player to.
	 */
	public King(World world, int team) {
		super(world, new Glyph('@', TeamColor[team]));
		
		Team = team;
		
		Location = new Point();
		do {
			Location.x = World.Rand.nextInt(World.Height);
			Location.y = World.Rand.nextInt(World.Width);
		} while(!World.getTile(Location.x, Location.y).equals(Tile.FLOOR));
		
		System.out.println("Created " + getClass().getSimpleName() + " at " + Location.x + "/" + Location.y);
	}

	@Override
	public void input(KeyEvent event) {
		int code = event.getKeyCode();
		
		// Move the player

		// UP LEFT
		if (code == KeyEvent.VK_NUMPAD7)
			go(Location.x - 1, Location.y - 1);
		
		// UP
		else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_NUMPAD8)
			go(Location.x, Location.y - 1);
		
		// UP RIGHT
		else if (code == KeyEvent.VK_NUMPAD9)
			go(Location.x + 1, Location.y - 1);
		
		// LEFT
		else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_NUMPAD4)
			go(Location.x - 1, Location.y);
		
		// RIGHT
		else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_NUMPAD6)
			go(Location.x + 1, Location.y);
		
		// DOWN LEFT
		if (code == KeyEvent.VK_NUMPAD1)
			go(Location.x - 1, Location.y + 1);
		
		// DOWN
		else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_NUMPAD2)
			go(Location.x, Location.y + 1);
		
		// DOWN RIGHT
		else if (code == KeyEvent.VK_NUMPAD3)
			go(Location.x + 1, Location.y + 1);
		
		
		// TODO: Check if we want to update the viewport
	}

	/**
	 * Check if a move would be valid assuming it's empty.
	 * @param x The x of the location to move to.
	 * @param y The y of the location to move to.
	 * @return If we can move there.
	 */
	@Override
	public boolean validMove(int x, int y) {
		return World.getTile(x, y).IsWalkable && Math.abs(x - Location.x) <= 1 && Math.abs(y - Location.y) <= 1;
	}

	/**
	 * Check if a capture would be valid if there's something there to capture.
	 * @param x The x of the location to capture at.
	 * @param y The y of the location to capture at.
	 * @return If we can capture it.
	 */
	@Override
	public boolean validCapture(int x, int y) {
		return World.getTile(x, y).IsWalkable && Math.abs(x - Location.x) <= 1 && Math.abs(y - Location.y) <= 1;
	}
}
