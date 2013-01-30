package com.jverkamp.chesslike.actor;

import java.awt.Color;
import java.awt.Point;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.tile.Tile;
import com.jverkamp.chesslike.world.World;

/**
 * Anything that can move about and potentially respond to user input.
 */
public abstract class Actor {
	World World;
	
	public Point Location;
	public Glyph Glyph;
	
	// 0 means player controlled. Teams have to be different to capture.
	public int Team = 0;
	static Color[] TeamColor = new Color[]{
		Color.RED,
		Color.BLUE,
		Color.YELLOW,
		Color.GREEN
	};
	
	/**
	 * Create a new actor.
	 * @param world The world to place the actor in.
	 */
	public Actor(World world, char glyph, int team) {
		World = world;
		Glyph = new Glyph(glyph, TeamColor[team]);
		Team = team;
		
		Location = new Point();
		do {
			Location.x = World.Rand.nextInt(World.Height);
			Location.y = World.Rand.nextInt(World.Width);
		} while(!World.getTile(Location.x, Location.y).equals(Tile.FLOOR));
	}
	
	/**
	 * Move the actor to the given location if it's walkable; otherwise, stay put.
	 * 
	 * @param x Location x
	 * @param y Location y
	 * @return If we can successfully move there.
	 */
	public boolean go(int x, int y) {
		// If the tile isn't walkable, don't move.
		if (!World.getTile(x, y).IsWalkable)
			return false;
		
		// Check if there's something there to capture.
		Actor that = World.getActorAt(x, y);
		if (this == that) that = null;
		
		// Empty, check if it's valid.
		if (that == null) {
			if (validMove(x, y)) {
				Location.x = x;
				Location.y = y;
				return true;
			} else {
				return false;
			}
		} 
		
		// Potential enemy, try to attack it.
		else {
			if (Team != that.Team && validCapture(x, y)) {
				// TODO: Deal with this logic
				// - remove the piece
				// - potentially take it over
				System.err.println(
					getClass().getSimpleName() + " @ " + x + "/" + y + 
					" is trying to capture " + 
					that.getClass().getSimpleName() + " @ " + x + "/" + y
				);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Test if a given piece can make a valid move to the given location.
	 * @param x The x to move to.
	 * @param y The y to move to.
	 * @return If the piece can be moved.
	 */
	public abstract boolean validMove(int x, int y);
	
	/**
	 * Test if a given piece can make a valid capture to the given location.
	 * @param x The x to capture at.
	 * @param y The y to capture at.
	 * @return If the piece can capture that location (assuming there is a piece there).
	 */
	public abstract boolean validCapture(int x, int y);

	/**
	 * Run the actor's AI.
	 */
	public abstract void AI();
}
