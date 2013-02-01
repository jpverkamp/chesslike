package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.world.World;

public class Unicorn extends Actor {
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Unicorn(World world, int team) {
		super(world, 'U', team);
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
		
		// Cannot move onto anyone
		Actor a = World.getActorAt(x, y);
		if (a != this && a != null)
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Any multiple of 2/1 leaps
		int xdiff = Math.abs(x - Location.x);
		int ydiff = Math.abs(y - Location.y);
		if (!(xdiff == ydiff * 2 || xdiff * 2 == ydiff))
			return false;
		
		// Make sure we didn't hit anything
		int xd = (x > Location.x ? 1 : -1) * (xdiff > ydiff ? 2 : 1);
		int yd = (y > Location.y ? 1 : -1) * (xdiff > ydiff ? 1 : 2);
		for (int xi = Location.x, yi = Location.y; xi != x && yi != y; xi += xd, yi += yd) {
			Actor a2 = World.getActorAt(xi, yi);
			if (!World.getTile(xi, yi).IsWalkable || (a2 != this && a2 != null))
				return false;
		}
		
		// Passed all tests!
		return true;
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
		
		// Cannot move onto anyone
		Actor a = World.getActorAt(x, y);
		if (a != this && a.Team == Team)
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Any multiple of 2/1 leaps
		int xdiff = Math.abs(x - Location.x);
		int ydiff = Math.abs(y - Location.y);
		if (!(xdiff == ydiff * 2 || xdiff * 2 == ydiff))
			return false;
		
		// Make sure we didn't hit anything
		int xd = (x > Location.x ? 1 : -1) * (xdiff > ydiff ? 2 : 1);
		int yd = (y > Location.y ? 1 : -1) * (xdiff > ydiff ? 1 : 2);
		for (int xi = Location.x, yi = Location.y; xi != x && yi != y; xi += xd, yi += yd) {
			Actor a2 = World.getActorAt(xi, yi);
			if (!World.getTile(xi, yi).IsWalkable || (a2 != this && a2 != null))
				return false;
		}
		
		// Passed all tests!
		return true;
	}
}
