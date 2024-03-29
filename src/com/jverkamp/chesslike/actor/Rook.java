package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.tile.Tile;
import com.jverkamp.chesslike.world.World;

public class Rook extends Actor {
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Rook(World world, int team) {
		super(world, 'r', team);
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
		if (!World.getTile(x, y).IsWalkable || 
				!(x == Location.x || y == Location.y))
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Maximum range
		if (Math.abs(x - Location.x) > 4 || Math.abs(y - Location.y) > 4)		
			return false;
		
		// Check the path.
		int xd = x == Location.x ? 0 : x > Location.x ? 1 : -1;
		int yd = y == Location.y ? 0 : y > Location.y ? 1 : -1;
		for (int xi = Location.x + xd, yi = Location.y + yd; !(xi == x && yi == y); xi += xd, yi += yd) {
			Tile t = World.getTile(xi, yi);
			Actor a = World.getActorAt(xi, yi);
			if (!t.IsWalkable || a != null)
				return false;
		}
		
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
		if (!World.getTile(x, y).IsWalkable || 
				!(x == Location.x || y == Location.y))
			return false;
		
		// Team capture.
		Actor that = World.getActorAt(x, y);
		if (that == null || this.Team == that.Team)
			return false;
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Maximum range
		if (Math.abs(x - Location.x) > 4 || Math.abs(y - Location.y) > 4)		
			return false;
		
		// Check the path.
		int xd = x == Location.x ? 0 : x > Location.x ? 1 : -1;
		int yd = y == Location.y ? 0 : y > Location.y ? 1 : -1;
		for (int xi = Location.x + xd, yi = Location.y + yd; !(xi == x && yi == y); xi += xd, yi += yd) {
			if (xi == x && yi == y)
				continue;
			
			Tile t = World.getTile(xi, yi);
			Actor a = World.getActorAt(xi, yi);
			if (!t.IsWalkable || a != null)
				return false;
		}
		
		return true;
	}
}
