package com.jverkamp.chesslike.world;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.actor.Actor;
import com.jverkamp.chesslike.actor.Player;
import com.jverkamp.chesslike.tile.Tile;

public class World {
	Random Rand = new Random();
	
	// The actual world contents	
	int Width, Height;
	Tile[][] Tiles;
	List<Actor> Actors;
	
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
		Tiles = new Tile[Height][Width];
		
		Actors = new ArrayList<Actor>();
		
		// Randomly generate open and closed areas.
		for (int r = 0; r < Height; r++)
			for (int c = 0; c < Width; c++)
				Tiles[r][c] = (Rand.nextDouble() < 0.75 ? Tile.FLOOR : Tile.WALL);
		
		// Apply smoothing
		int r, c, walls;
		for (int i = 0; i < 1000000; i++) {
			r = Rand.nextInt(Height - 2) + 1;
			c = Rand.nextInt(Width - 2) + 1;
			walls = 0;
			for (Tile t : neighbors8(r, c))
				if (t.equals(Tile.WALL)) walls++;
			
			if (walls < 4) Tiles[r][c] = Tile.FLOOR;
			if (walls > 4) Tiles[r][c] = Tile.WALL;
		}
	}
		
	/**
	 * 
	 * @param r
	 * @param c
	 * @return
	 */
	public Iterable<Tile> neighbors8(int r, int c) {
		List<Tile> neighbors = new ArrayList<Tile>();
		for (int ri = r - 1; ri <= r + 1; ri++)
			for (int ci = c - 1; ci <= c + 1; ci++)
				if (ri >= 0 && ri < Height && ci >= 0 && ci < Width && !(ri == 0 && ci == 0))
					neighbors.add(Tiles[r][c]);
		
		Collections.shuffle(neighbors);
		
		return neighbors;
	}
	
	/**
	 * Add a player to the world at a random empty location.
	 */
	public void addPlayer() {
		
		int r, c;
		do {
			r = Rand.nextInt(Height);
			c = Rand.nextInt(Width);
		} while(getTile(r, c).equals(Tile.WALL));
			
		Actors.add(new Player(this, r, c));
	}
	
	/**
	 * Get the tile at r,c. If it's not valid, return VOID.
	 * @param r The row to get
	 * @param c The column to get.
	 * @return
	 */
	public Tile getTile(int r, int c) {
		if (r < 0 || r >= Height || c < 0 || c >= Width)
			return Tile.VOID;
		else 
			return Tiles[r][c];
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
		Tile t;
		for (int r = 0; r < View.height; r++) {
			for (int c = 0; c < View.width; c++) {
				t = getTile(View.y + c, View.x + r);
				terminal.write(
					t.Glyph.Character,
					region.x + c,
					region.y + r,
					t.Glyph.Color
				);
			}
		}
		
		// Overlay actors
		for (Actor a : Actors) {
			if (a.R >= View.y && a.R < View.y + View.height && a.C >= View.x && a.C < View.x + View.width) { 
				terminal.write(
					a.Glyph.Character,
					region.x + a.C - View.y,
					region.y + a.R - View.x,
					a.Glyph.Color
				);
			}
		}
	}
}
