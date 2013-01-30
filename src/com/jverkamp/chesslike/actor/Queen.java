package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.tile.Tile;
import com.jverkamp.chesslike.world.World;

public class Queen extends Actor {
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Queen(World world, int team) {
		super(world, 'Q', team);
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
		
		// Self valid.
		if (x == Location.x && y == Location.y)
			return true;
		
		// Moving as a Rook.
		if (x == Location.x || y == Location.y) {
			// Check the path.
			if (x == Location.x) {
				for (int yi = Math.min(y, Location.y); yi <= Math.max(y, Location.y); yi++) {
					Tile t = World.getTile(x, yi);
					Actor a = World.getActorAt(x, yi);
					if (!t.IsWalkable || (a != this && a != null))
						return false;
				}
			} else {
				for (int xi = Math.min(x, Location.x); xi <= Math.max(x, Location.x); xi++) {
					Tile t = World.getTile(xi, y);
					Actor a = World.getActorAt(xi, y);
					if (!t.IsWalkable || (a != this && a != null))
						return false;
				}
			} 
			
			return true;
		}
		
		// Moving as a Bishop.
		else if (Math.abs(x - Location.x) == Math.abs(y - Location.y)) {
			// Check the path.
			int xd = x > Location.x ? 1 : -1;
			int yd = y > Location.y ? 1 : -1;
			for (int xi = Location.x, yi = Location.y; xi != x && yi != y; xi += xd, yi += yd) {
				Tile t = World.getTile(xi, yi);
				Actor a = World.getActorAt(xi, yi);
				if (!t.IsWalkable || (a != this && a != null))
					return false;
			}
			
			return true;
		}
		
		// Anything else fails
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
		
		// Team capture.
		Actor that = World.getActorAt(x, y);
		if (that == null || this.Team == that.Team)
			return false;
		
		// Moving as a Rook.
		if (x == Location.x || y == Location.y) {
			// Check the path.
			if (x == Location.x) {
				for (int yi = Math.min(y, Location.y) + 1; yi < Math.max(y, Location.y); yi++) {
					Tile t = World.getTile(x, yi);
					Actor a = World.getActorAt(x, yi);
					if (!t.IsWalkable || (a != this && a != null))
						return false;
				}
			} else {
				for (int xi = Math.min(x, Location.x) + 1; xi < Math.max(x, Location.x); xi++) {
					Tile t = World.getTile(xi, y);
					Actor a = World.getActorAt(xi, y);
					if (!t.IsWalkable || (a != this && a != null))
						return false;
				}
			} 
			
			return true;
		}
		
		// Moving as a Bishop.
		else if (Math.abs(x - Location.x) == Math.abs(y - Location.y)) {
			// Check the path.
			int xd = x > Location.x ? 1 : -1;
			int yd = y > Location.y ? 1 : -1;
			for (int xi = Location.x, yi = Location.y; xi != x && yi != y; xi += xd, yi += yd) {
				if (xi == x && yi == y)
					continue;
				
				Tile t = World.getTile(xi, yi);
				Actor a = World.getActorAt(xi, yi);
				if (!t.IsWalkable || (a != this && a != null))
					return false;
			}
			
			return true;
		}
		
		// Anything else fails
		return false;
	}
}
