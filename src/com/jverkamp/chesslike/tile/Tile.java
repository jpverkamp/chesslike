package com.jverkamp.chesslike.tile;

import java.awt.Color;

import com.jverkamp.chesslike.Glyph;

/**
 * Represent various kinds of tiles.
 */
public class Tile {
	// Various tile properties.
	public Glyph Glyph;
	public boolean IsWalkable = false;
	
	// Defined tiles.
	public static Tile VOID = new Tile('*', Color.RED);
	public static Tile FLOOR = new Tile(' ', Color.BLACK);
	public static Tile WALL = new Tile('#', Color.GRAY);
	
	// Set static properties.
	static {
		FLOOR.IsWalkable = true;
	}
	
	/**
	 * Create a new tile.
	 * @param character The tile's character.
	 * @param color The tile's color.
	 */
	private Tile(char character, Color color) {
		Glyph = new Glyph(character, color);
	}
	
	/**
	 * Tiles are equivalent if they look the same.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tile && (((Tile) obj).Glyph.equals(Glyph));
	}
}
