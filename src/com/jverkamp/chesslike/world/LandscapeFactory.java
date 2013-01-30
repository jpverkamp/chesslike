package com.jverkamp.chesslike.world;

import com.jverkamp.chesslike.tile.Tile;

/**
 * A factory that will set up landscapes.
 */
public class LandscapeFactory {
	/** Hide the constructor. */
	private LandscapeFactory(){}
	
	/**
	 * A map of names to their descriptions.
	 */
	public static String[][] Names = new String[][]{
		{"Empty field", "Completely empty"},
		{"Sparse walls", "A set of sparse walls"}
	};
	
	/**
	 * Run a factory function.
	 * @param world The world to add the landscape to.
	 * @param name The name of function to use.
	 */
	public static void run(World world, String name) {
		// You get a king, the enemy gets eight enemy pawns
		if (name.equals("Empty field")) {
			for (int x = 0; x < world.Width; x++)
				for (int y = 0; y < world.Height; y++)
					world.Tiles[x][y] = Tile.FLOOR;
		}
		
		// Add a full set of chess pieces for the player and a single enemy.
		else if (name.equals("Sparse walls")) {
			// Randomly generate open and closed areas.
			for (int x = 0; x < world.Width; x++)
				for (int y = 0; y < world.Height; y++)
					world.Tiles[x][y] = (world.Rand.nextDouble() < 0.75 ? Tile.FLOOR : Tile.WALL);
			
			// Apply smoothing
			int x, y, walls;
			for (int i = 0; i < 1000000; i++) {
				x = world.Rand.nextInt(world.Width - 2) + 1;
				y = world.Rand.nextInt(world.Height - 2) + 1;
				walls = 0;
				for (Tile t : world.neighbors8(x, y))
					if (t.equals(Tile.WALL)) walls++;
				
				if (walls < 4) world.Tiles[x][y] = Tile.FLOOR;
				if (walls > 4) world.Tiles[x][y] = Tile.WALL;
			}
		}
		
		// Unknown type
		else {
			throw new IllegalArgumentException("Unknown type name");
		}
	}
}
