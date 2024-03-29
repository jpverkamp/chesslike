package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.world.World;

public class King extends Actor {
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public King(World world, int team) {
		super(world, 'K', team);
	}

	/**
	 * Check if a move would be valid assuming it's empty.
	 * @param x The x of the location to move to.
	 * @param y The y of the location to move to.
	 * @return If we can move there.
	 */
	@Override
	public boolean validMove(int x, int y) {
		return (World.getTile(x, y).IsWalkable && 
				Math.abs(x - Location.x) <= 1 && 
				Math.abs(y - Location.y) <= 1);
	}

	/**
	 * Check if a capture would be valid if there's something there to capture.
	 * @param x The x of the location to capture at.
	 * @param y The y of the location to capture at.
	 * @return If we can capture it.
	 */
	@Override
	public boolean validCapture(int x, int y) {
		return (World.getTile(x, y).IsWalkable && 
				Math.abs(x - Location.x) <= 1 && 
				Math.abs(y - Location.y) <= 1);
	}
	
	/**
	 * The King doesn't move.
	 */
	@Override
	public void AI() {
	}
}
