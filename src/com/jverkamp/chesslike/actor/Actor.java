package com.jverkamp.chesslike.actor;

import java.awt.Point;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.world.World;

/**
 * Anything that can move about and potentially respond to user input.
 */
public abstract class Actor {
	World World;
	
	public Point Location;
	public Glyph Glyph;
	
	/**
	 * Create a new actor.
	 * @param world The world to place the actor in.
	 */
	public Actor(World world, Glyph glyph) {
		World = world;
		Glyph = glyph;
	}
	
	/**
	 * Move the actor to the given location if it's walkable; otherwise, stay put.
	 * 
	 * @param x Location x
	 * @param y Location y
	 */
	public void moveTo(int x, int y) {
		if (World.getTile(x, y).IsWalkable) {
			Location.x = x;
			Location.y = y;
		}
	}
	
	/**
	 * Handle input from the user.
	 * @param event The event to process.
	 */
	public abstract void input(KeyEvent event);
}
