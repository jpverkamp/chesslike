package com.jverkamp.chesslike.world;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.actor.Actor;
import com.jverkamp.chesslike.actor.King;
import com.jverkamp.chesslike.tile.Tile;

public class World {
	public Random Rand = new Random();
	
	// The actual world contents	
	public int Width, Height;
	Tile[][] Tiles;
	List<Actor> Actors;
	
	// The current active actor
	Actor ActiveActor;
	
	// The slice the user can currently see
	Rectangle View;
	
	/**
	 * Create a new world
	 * @param width Number of columns
	 * @param height Number of rows
	 */
	public World(int width, int height) {
		View = new Rectangle(0, 0, width, height);
	
		Width = width;
		Height = height;
		Tiles = new Tile[Width][Height];
		
		Actors = new ArrayList<Actor>();
		
		// Randomly generate open and closed areas.
		for (int x = 0; x < Width; x++)
			for (int y = 0; y < Height; y++)
				Tiles[x][y] = (Rand.nextDouble() < 0.75 ? Tile.FLOOR : Tile.WALL);
		
		// Apply smoothing
		int x, y, walls;
		for (int i = 0; i < 1000000; i++) {
			x = Rand.nextInt(Width - 2) + 1;
			y = Rand.nextInt(Height - 2) + 1;
			walls = 0;
			for (Tile t : neighbors8(x, y))
				if (t.equals(Tile.WALL)) walls++;
			
			if (walls < 4) Tiles[x][y] = Tile.FLOOR;
			if (walls > 4) Tiles[x][y] = Tile.WALL;
		}
	}
		
	/**
	 * Iterate over the neighbors of a given tile.
	 * Note: The points are shuffled before being returned to avoid top left bias.
	 * 
	 * @param x The point's x to iterate around.
	 * @param y The point's y to iterate around.
	 * @return The resulting neighborhood.
	 */
	public Iterable<Tile> neighbors8(int x, int y) {
		List<Tile> neighbors = new ArrayList<Tile>();
		for (int xi = x - 1; xi <= x + 1; xi++)
			for (int yi = y - 1; yi <= y + 1; yi++)
				neighbors.add(getTile(x, y));
		
		Collections.shuffle(neighbors);
		
		return neighbors;
	}
	
	/**
	 * Add a player to the world at a random empty location.
	 */
	public void addPlayer() {
		Actor player = new King(this, 0);
		Actors.add(player);
		ActiveActor = player;
	}
	
	/**
	 * Get the tile at x,y. If it's not valid, return VOID.
	 * @param x The x to get.
	 * @param y The y to get.
	 * @return The tile.
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= Width || y < 0 || y >= Height)
			return Tile.VOID;
		else 
			return Tiles[x][y];
	}
	
	/**
	 * Get the actor at x,y. If there isn't one, return null.
	 * @param x The x to look at.
	 * @param y The y to look at.
	 * @return The actor.
	 */
	public Actor getActorAt(int x, int y) {
		for (Actor a : Actors) 
			if (a.Location.x == x && a.Location.y == y)
				return a;
		
		return null;
	}
	
	
	/**
	 * Set the size of the view into the world.
	 * @param width Width of the view
	 * @param height Height of the view
	 */
	public void setViewSize(int width, int height) {
		View.width = width;
		View.height = height;
	}
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	public void input(KeyEvent event) {
		for (Actor actor : Actors)
			actor.input(event);
	}
	
	/**
	 * Draw this screen.
	 * @param terminal The panel to draw to.
	 * @param region The region to draw to.
	 */
	public void draw(AsciiPanel terminal, Rectangle region) {
		if (View.width != region.width || View.height != region.height)
			throw new IllegalArgumentException("Region size doesn't match view size");
		
		// Draw tiles
		for (int xi = 0; xi < View.width; xi++) {
			for (int yi = 0; yi < View.height; yi++) {
				Tile t = getTile(View.x + xi, View.y + yi);
				Actor a = getActorAt(View.x + xi, View.y + yi);

				// TODO: factor this back out so it's more efficient
				// (or just don't add too many actors...)

				// No actor, draw the tile
				if (a == null) {
					terminal.write(
						t.Glyph.Character,
						region.x + xi,
						region.y + yi,
						t.Glyph.Color,
						ActiveActor.validMove(View.x + xi, View.y + yi) ? Color.DARK_GRAY : Color.BLACK
					);	
				} 
				
				// Actor exists, draw it
				else {
					terminal.write(
						a.Glyph.Character,
						region.x + xi,
						region.y + yi,
						ActiveActor.equals(a) ? Color.WHITE : a.Glyph.Color,
						ActiveActor.validCapture(View.x + xi, View.y + yi) ? Color.DARK_GRAY : Color.BLACK
					);
				}
			}
		}
	}
}
