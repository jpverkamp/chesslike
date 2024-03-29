package com.jverkamp.chesslike.tile;

import java.awt.Color;

import com.jverkamp.chesslike.Glyph;
import com.jverkamp.chesslike.Util;

/**
 * Represent various kinds of tiles.
 */
public class Tile {
	// Various tile properties.
	public Glyph Glyph;
	public boolean IsWalkable = false;
	
	// Error tiles.
	public static final Tile VOID = new Tile('*', Color.RED);
	
	// Walkable tiles
	public static final Tile FLOOR = new Tile(' ', Color.BLACK);
	public static final Tile GRASS_1 = new Tile('.', Color.BLACK);
	public static final Tile GRASS_2 = new Tile(',', Color.GREEN);
	public static final Tile MUD_1 = new Tile('.', new Color(139, 69, 19) /* BROWN */);
	public static final Tile MUD_2 = new Tile(',', new Color(139, 69, 19) /* BROWN */);
	public static final Tile PEW = new Tile((char) 186, new Color(139, 69, 19) /* BROWN */);
	public static final Tile THRONE = new Tile((char) 233, new Color(255, 215, 0) /* GOLD */);
	
	// Non-walkable tiles
	public static final Tile WALL = new Tile('#', Color.GRAY);
	public static final Tile TREE = new Tile((char) 6, Color.GREEN);
	public static final Tile WATER = new Tile((char) 178, Color.BLUE);
	public static final Tile MUSHROOM = new Tile((char) 5, new Color(139, 69, 19) /* BROWN */);
	public static final Tile FORGE = new Tile((char) 209, new Color(183, 65, 14) /* RUST */);
	public static final Tile LAVA = new Tile((char) 178, Color.RED);
	public static final Tile LAMP = new Tile((char) 244, new Color(255, 215, 0) /* GOLD */);
	public static final Tile LEFT_ARROW = new Tile((char) 174, Color.DARK_GRAY);
	public static final Tile RIGHT_ARROW = new Tile((char) 175, Color.DARK_GRAY);
	
	// Stairs down to the next level.
	// The color will change depending on which level we're going down to.
	public static final Tile STAIRS = new Tile('>', Color.WHITE);
	
	// Set static properties.
	static {
		FLOOR.IsWalkable = true;
		GRASS_1.IsWalkable = true;
		GRASS_2.IsWalkable = true;
		MUD_1.IsWalkable = true;
		MUD_2.IsWalkable = true;
		PEW.IsWalkable = true;
		THRONE.IsWalkable = true;
		STAIRS.IsWalkable = true;
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

	/**
	 * Create stairs.
	 * @param color The color to create
	 * @return The stairs
	 */
	public static Tile stairs(Color color) {
		Tile tile = new Tile('>', color);
		tile.IsWalkable = true;
		return tile;
	}
	
	/**
	 * Lookup a tile by symbol.
	 * @param symbol The symbol to lookup 
	 * @return The resulting level
	 */
	public static Tile bySymbol(char symbol) {
		switch(symbol) {
			// Walkable Tiles
			case ' ': case '-': return Tile.FLOOR;
			case '.': return (Util.Rand.nextInt(2) == 0 ? Tile.MUD_1 : Tile.MUD_2);
			case ',': return (Util.Rand.nextInt(2) == 0 ? Tile.GRASS_1 : Tile.GRASS_2);
			case 'p': return Tile.PEW;
			case 'T': return Tile.THRONE;
			
			// Non-walkable Tiles
			case '#': return Tile.WALL;
			case 't': return Tile.TREE;
			case 'm': return Tile.MUSHROOM;
			case 'f': return Tile.FORGE;
			case 'l': return Tile.LAVA;
			case 'L': return Tile.LAMP;
			
			default: return Tile.VOID;
		}	
	}
}
