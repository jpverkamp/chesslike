package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.world.World;

public class Knight extends Actor {
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Knight(World world, int team) {
		super(world, 'k', team);
	}

	/**
	 * Check if a move would be valid assuming it's empty.
	 * @param x The x of the location to move to.
	 * @param y The y of the location to move to.
	 * @return If we can move there.
	 */
	@Override
	public boolean validMove(int x, int y) {
		// Not even possible.
		if (!World.getTile(x, y).IsWalkable)
			return false;
		
		// Actor there.
		Actor a = World.getActorAt(x, y);
		if (a != this && a != null)
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Any 2/1 leap
		if ((Math.abs(x - Location.x) == 2 && Math.abs(y - Location.y) == 1) ||
				(Math.abs(x - Location.x) == 1 && Math.abs(y - Location.y) == 2))
			return true;
		
		// Failed
		return false;
	}

	/**
	 * Check if a capture would be valid if there's something there to capture.
	 * @param x The x of the location to capture at.
	 * @param y The y of the location to capture at.
	 * @return If we can capture it.
	 */
	@Override
	public boolean validCapture(int x, int y) {
		// Not even possible.
		if (!World.getTile(x, y).IsWalkable)
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Any 2/1 leap
		if ((Math.abs(x - Location.x) == 2 && Math.abs(y - Location.y) == 1) ||
				(Math.abs(x - Location.x) == 1 && Math.abs(y - Location.y) == 2))
			return true;
		
		// Failed
		return false;
	}
}
