package com.jverkamp.chesslike.world;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.*;

import com.jverkamp.chesslike.Statistics;
import com.jverkamp.chesslike.Util;
import com.jverkamp.chesslike.actor.*;
import com.jverkamp.chesslike.tile.Tile;

/**
 * A factory that will set up landscapes.
 */
public class LevelFactory {
	/** Hide the constructor. */
	private LevelFactory(){}
	static List<String> GeneratedLevels = new ArrayList<String>();
	
	/**
	 * A map of names to their descriptions.
	 */
	private static Level[] Levels = new Level[]{
		// Starting level
		new Level("Forest", "An idyllic landscape, dotted with lovely trees", Color.WHITE, 1, 1) {
			@Override void generateLandscape(World world) {
				// Mostly empty space, but spread some trees and grass about.
				for (int x = 0; x < world.Width; x++) {
					for (int y = 0; y < world.Height; y++) {
						switch (Util.Rand.nextInt(10)) {
						case 0:
							world.Tiles[x][y] = Tile.TREE;
							break;
						case 1:
						case 2:
							world.Tiles[x][y] = Tile.GRASS_1;
							break;
						case 3:
						case 4:
							world.Tiles[x][y] = Tile.GRASS_2;
							break;
						default:
							world.Tiles[x][y] = Tile.FLOOR;
							break;
						}
					}
				}
				
				// Place a random stairway downwards.
				int x = Util.Rand.nextInt(world.Width / 2) + world.Width / 2;
				int y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				world.Tiles[x][y] = Tile.stairs(Color.WHITE);
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Pawn(world, 1), enemyBounds);
			}
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Pawn(null, 0)));
			}
		}, 
		// First tier
		new Level("Caves", "Twisting passways cut into the rock, leading deep into the earth", Color.WHITE, 2, 5) {
			@Override void generateLandscape(World world) {
				// Fill with rock first
				for (int x = 0; x < world.Width; x++)
					for (int y = 0; y < world.Height; y++)
						world.Tiles[x][y] = Tile.WALL;
				
				// Carve some nice twisty caves.
				int x = Util.Rand.nextInt(world.Width / 4);
				int y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				int targetx = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
				int targety = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				
				// Carve at least 10 more chunks
				for (int i = 0; i < 10; i++) {
					do {
						// Clear a chunk
						for (int xi = x - 1; xi <= x + 1; xi++)
							for (int yi = y - 1; yi <= y + 1; yi++)
								if (!world.getTile(xi, yi).equals(Tile.VOID))
									world.Tiles[xi][yi] = Tile.FLOOR;
						
						// Wiggle towards the target
						if (x != targetx && Util.Rand.nextInt(2) != 0)
							x += (x > targetx ? -1 : 1);
						if (y != targety && Util.Rand.nextInt(2) != 0)
							y += (y > targety ? -1 : 1);
						
					} while (x != targetx || y != targety);
					
					targetx = Util.Rand.nextInt(world.Width);
					targety = Util.Rand.nextInt(world.Height);
				}
				
				// Add down stairs
				do {
					x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
					y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				} while(!world.getTile(x, y).IsWalkable);
				world.Tiles[x][y] = Tile.stairs(Color.WHITE);
				
				// Sometimes add stairs to the underground lake / forest
				int r = Util.Rand.nextInt(4);
				if (r == 0 && !GeneratedLevels.contains("Underground Lake")) {
					
					do {
						x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
						y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
					} while(!world.getTile(x, y).IsWalkable);
					
					world.Tiles[x][y] = Tile.stairs(levelByName("Underground Lake").Stairs);
					
				} else if (r == 1 && !GeneratedLevels.contains("Underground Forest")) {
					
					do {
						x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
						y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
					} while(!world.getTile(x, y).IsWalkable);
					
					world.Tiles[x][y] = Tile.stairs(levelByName("Underground Forest").Stairs);
				}
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Pawn(world, 1), enemyBounds);
				
				switch(Util.Rand.nextInt(3)) {
					case 0: placeRandomly(world, new Bishop(world, 1), enemyBounds); break;
					case 1: placeRandomly(world, new Knight(world, 1), enemyBounds); break;
					case 2: placeRandomly(world, new Rook(world, 1), enemyBounds); break;
				}
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Pawn(null, 0)));
			}
		},
		new Level("Underground Lake", "A wide open area with a lake in the center", Color.BLUE, 2, 5) {
			@Override void generateLandscape(World world) {
				// Add a solid border
				for (int x = 0; x < world.Width; x++) {
					world.Tiles[x][0] = Tile.WALL;
					world.Tiles[x][world.Height - 1] = Tile.WALL;
				}
				for (int y = 0; y < world.Height; y++) {
					world.Tiles[0][y] = Tile.WALL;
					world.Tiles[world.Width - 1][y] = Tile.WALL;
				}
				
				// Add some water in the center
				for (int x = world.Width / 2 - 1; x <= world.Width / 2 + 1; x++)
					for (int y = world.Height / 2 - 1; y <= world.Height / 2 + 1; y++)
						world.Tiles[x][y] = Tile.WATER;
				
				// Smooth outwards
				// I've never used a labeled continue before! :)
				int toAdd = (world.Width * world.Height - 2 * (world.Width + world.Height) + 5) / 3; 
				smooth: for (int i = 0; i < toAdd; i++) {
					// Generate a location
					int x = Util.Rand.nextInt(world.Width);
					int y = Util.Rand.nextInt(world.Height);
					
					// If it's not already empty, skip it
					if (!world.getTile(x, y).equals(Tile.FLOOR)) {
						i--;
						continue;
					}
					
					// If we're bordering wall or water, expand it
					for (Tile t : world.neighbors4(x, y)) {
						if (t.equals(Tile.WATER)) {
							world.Tiles[x][y] = Tile.WATER;
							continue smooth;
						} else if (t.equals(Tile.WALL)) {
							world.Tiles[x][y] = Tile.WALL;
							continue smooth;
						}
					}
					
					// If we made it here, we didn't actually expand something
					i--;
					continue;
				}
				
				// Place a random stairway downwards.
				int x = Util.Rand.nextInt(world.Width / 2) + world.Width / 2;
				int y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				world.Tiles[x][y] = Tile.stairs(Color.WHITE);
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Pawn(world, 1), enemyBounds);
			
				for (int i = 0; i < 2; i++)
					placeRandomly(world, new Snake(world, 1), enemyBounds);
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Snake(null, 0)));
			}
		}, 
		new Level("Underground Forest", "Towering mushrooms with a feel of magic in the area", Color.GREEN, 2, 5) {
			@Override void generateLandscape(World world) {
				// Mostly empty space, but spread some trees and grass about.
				for (int x = 0; x < world.Width; x++) {
					for (int y = 0; y < world.Height; y++) {
						switch (Util.Rand.nextInt(10)) {
						case 0:
							world.Tiles[x][y] = Tile.MUSHROOM;
							break;
						case 1:
							world.Tiles[x][y] = Tile.GRASS_1;
							break;
						case 2:
							world.Tiles[x][y] = Tile.GRASS_2;
							break;
						case 3:
							world.Tiles[x][y] = Tile.MUD_1;
							break;
						case 4:
							world.Tiles[x][y] = Tile.MUD_2;
							break;
						default:
							world.Tiles[x][y] = Tile.FLOOR;
							break;
						}
					}
				}
				
				// Place a random stairway downwards.
				int x = Util.Rand.nextInt(world.Width / 2) + world.Width / 2;
				int y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				world.Tiles[x][y] = Tile.stairs(Color.WHITE);
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				placeRandomly(world, new Knight(world, 1), enemyBounds);
				placeRandomly(world, new Knight(world, 1), enemyBounds);
				placeRandomly(world, new Unicorn(world, 1), enemyBounds);
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Unicorn(null, 0)));
			}
		},
		// Second tier
		new Level("Dungeon", "Artifical tunnels, descending ever further into the darkness", Color.WHITE, 6, 9) {
			@Override void generateLandscape(World world) {
				// Fill with rock first
				for (int x = 0; x < world.Width; x++)
					for (int y = 0; y < world.Height; y++)
						world.Tiles[x][y] = Tile.WALL;
				
				// Carve some nice twisty caves.
				int x = Util.Rand.nextInt(world.Width / 4);
				int y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				int targetx = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
				int targety = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				
				// Carve a few rooms
				for (int i = 0; i < 4; i++) {
					// Carve a room
					for (int xi = x - 3; xi <= x + 3; xi++)
						for (int yi = y - 3; yi <= y + 3; yi++)
							if (!world.getTile(xi, yi).equals(Tile.VOID))
								world.Tiles[xi][yi] = Tile.FLOOR;
					
					// Add some decorations
					try {
						switch(Util.Rand.nextInt(4)) {
							case 0:
								for (int xj = x - 1; xj <= x + 1; xj++)
									for (int yj = y - 1; yj <= y + 1; yj++)
										world.Tiles[xj][yj] = Tile.WATER;
								break;
							case 1:
								world.Tiles[x + Util.Rand.nextInt(3) - 1][y + Util.Rand.nextInt(3) - 1] = Tile.MUSHROOM;
								world.Tiles[x + Util.Rand.nextInt(3) - 1][y + Util.Rand.nextInt(3) - 1] = Tile.MUSHROOM;
								break;
								
							case 2:
								world.Tiles[x - 1][y] = Tile.LEFT_ARROW;
								world.Tiles[x + 1][y] = Tile.RIGHT_ARROW;
								break;
								
							case 3:
								world.Tiles[x - 1][y] = Tile.LAMP;
								world.Tiles[x][y] = Tile.THRONE;
								world.Tiles[x + 1][y] = Tile.LAMP;
								break;
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					do {
						// Clear a chunk
						for (int xi = x - 1; xi < x + 1; xi++)
							for (int yi = y - 1; yi < y + 1; yi++)
								if (world.getTile(xi, yi).equals(Tile.WALL))
									world.Tiles[xi][yi] = Tile.FLOOR;
						
						// Wiggle towards the target
						if (x != targetx)
							x += (x > targetx ? -1 : 1);
						else if (y != targety)
							y += (y > targety ? -1 : 1);
						
					} while (x != targetx || y != targety);
					
					targetx = Util.Rand.nextInt(world.Width);
					targety = Util.Rand.nextInt(world.Height);
				}
				
				// Carve the last room
				for (int xi = x - 3; xi <= x + 3; xi++)
					for (int yi = y - 3; yi <= y + 3; yi++)
						if (!world.getTile(xi, yi).equals(Tile.VOID))
							world.Tiles[xi][yi] = Tile.FLOOR;
				
				// Add down stairs
				do {
					x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
					y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
				} while(!world.getTile(x, y).IsWalkable);
				world.Tiles[x][y] = Tile.stairs(Color.WHITE);
				
				// Sometimes add stairs to the underground lake / forest
				int r = Util.Rand.nextInt(4);
				if (r == 0 && !GeneratedLevels.contains("Underground Lake")) {
					
					do {
						x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
						y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
					} while(!world.getTile(x, y).IsWalkable);
					
					world.Tiles[x][y] = Tile.stairs(levelByName("Underground Lake").Stairs);
					
				} else if (r == 1 && !GeneratedLevels.contains("Underground Forest")) {
					
					do {
						x = Util.Rand.nextInt(world.Width / 4) + 3 * world.Width / 4;
						y = Util.Rand.nextInt(world.Height / 2) + world.Height / 4;
					} while(!world.getTile(x, y).IsWalkable);
					
					world.Tiles[x][y] = Tile.stairs(levelByName("Underground Forest").Stairs);
				}
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Pawn(world, 1), enemyBounds);
				
				for (int i = 0; i < 2; i++) {
					switch(Util.Rand.nextInt(3)) {
						case 0: placeRandomly(world, new Bishop(world, 1), enemyBounds); break;
						case 1: placeRandomly(world, new Knight(world, 1), enemyBounds); break;
						case 2: placeRandomly(world, new Rook(world, 1), enemyBounds); break;
					}
				}
			}	
			
			@Override List<Actor> getBonus() {
				switch(new Random().nextInt(3)) {
					case 0: return new ArrayList<Actor>(Arrays.asList(new Bishop(null, 0)));
					case 1: return new ArrayList<Actor>(Arrays.asList(new Knight(null, 0)));
					case 2: return new ArrayList<Actor>(Arrays.asList(new Rook(null, 0)));
				}
				return null;
			}
		},
		new LevelFromFile("Cathedral.txt", "Cathedral", "A place of worship, far from the light of day", new Color(255, 215, 0) /* GOLD */, 6, 9) { 
			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Bishop(world, 1), enemyBounds);
				
				placeRandomly(world, new Archbishop(world, 1), enemyBounds);
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Bishop(null, 0), new Bishop(null, 0)));
			}
		},
		new LevelFromFile("Foundry.txt", "Foundry", "Preparing for a war, although against whom is unclear", new Color(183, 65, 14) /* RUST */, 6, 9) {
			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 4; i++)
					placeRandomly(world, new Rook(world, 1), enemyBounds);
				
				placeRandomly(world, new Marshall(world, 1), enemyBounds);
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new Rook(null, 0), new Rook(null, 0)));
			}
		},
//		// Third tier
//		new Level("Sunken City", "A city where no city ought to be", Color.WHITE, 10, 12) {
//			@Override void generateLandscape(World world) {
//				
//			}
//
//			@Override void generatePieces(World world) {
//
//			}	
//			
//			@Override List<Actor> getBonus() {
//				return new ArrayList<Actor>(Arrays.asList(new Bishop(null, 0), new Knight(null, 0), new Rook(null, 0)));
//			}
//		},
		// Final tier
		new LevelFromFile("ThroneRoom.txt", "Throne Room", "The seat of power of this underground civilization (literally)", Color.WHITE, 10, 10) {
			@Override void generatePieces(World world) {
				place(world, new Pawn(world, 1), 44, 5);
				place(world, new Pawn(world, 1), 43, 6);
				place(world, new Pawn(world, 1), 43, 7);
				place(world, new Pawn(world, 1), 43, 8);
				place(world, new Pawn(world, 1), 43, 9);
				place(world, new Pawn(world, 1), 43, 10);
				place(world, new Pawn(world, 1), 43, 11);
				place(world, new Pawn(world, 1), 44, 12);
				
				place(world, new Bishop(world, 1), 46, 6);
				place(world, new Bishop(world, 1), 46, 11);
				
				place(world, new Knight(world, 1), 47, 4);
				place(world, new Knight(world, 1), 47, 13);
				
				place(world, new Rook(world, 1), 49, 4);
				place(world, new Rook(world, 1), 49, 13);
				
				place(world, new Queen(world, 1), 48, 8);
				place(world, new King(world, 1), 48, 9);
			}	
			
			private void place(World world, Actor piece, int x, int y) {
				world.Actors.add(piece);
				piece.Location.x = x;
				piece.Location.y = y;
			}

			@Override List<Actor> getBonus() {
				return null;
			}
		},
		// Error level
		new Level("Error", 
				((char) 24) + ((char) 24) + ((char) 25) + ((char) 25) + 
				((char) 27) + ((char) 26) + ((char) 27) + ((char) 26) + 
				"BA" + ((char) 251),
				null, 
				Integer.MIN_VALUE, Integer.MAX_VALUE) 
		{
			@Override void generateLandscape(World world) {
				for (int x = 0; x < world.Width; x++) {
					for (int y = 0; y < world.Height; y++) {
						switch (Util.Rand.nextInt(50)) {
							case 0: world.Tiles[x][y] = Tile.GRASS_1; break;
							case 1: world.Tiles[x][y] = Tile.GRASS_2; break;
							case 2: world.Tiles[x][y] = Tile.PEW; break;
							case 3: world.Tiles[x][y] = Tile.THRONE; break;
							case 4: world.Tiles[x][y] = Tile.WALL; break;
							case 5: world.Tiles[x][y] = Tile.TREE; break;
							case 6: world.Tiles[x][y] = Tile.WATER; break;
							case 7: world.Tiles[x][y] = Tile.MUSHROOM; break;
							case 8: world.Tiles[x][y] = Tile.FORGE; break;
							case 9: world.Tiles[x][y] = Tile.LAVA; break;
							default: world.Tiles[x][y] = Tile.FLOOR; break;
						}						
					}
				}
			}

			@Override void generatePieces(World world) {
				Rectangle enemyBounds = new Rectangle(world.Width / 4, 0, 3 * world.Width / 4, world.Height);
				
				for (int i = 0; i < 8; i++)
					for (int j = 1; j < 4; j++)
						placeRandomly(world, new Pawn(world, j), enemyBounds);
			}	
			
			@Override List<Actor> getBonus() {
				return new ArrayList<Actor>(Arrays.asList(new King(null, 0), new King(null, 0), new King(null, 0), new King(null, 0)));
			}
		}
	};
	
	/**
	 * Generate the proper level.
	 * @param world The world to work with.
	 * @param forces The actors that you currently control
	 * @param color The color of stairway we're coming down
	 * @param depth The level of the world we're in
	 */
	public static void run(World world, List<Actor> forces, Color stairs, int depth) {
		// Find the proper level.
		Level level = null;
		for (int i = 0; i < Levels.length; i++) {
			level = Levels[i];
				
			if ((stairs == null || level.Stairs == null || level.Stairs.equals(stairs)) 
					&& level.Lowest <= depth 
					&& level.Highest >= depth)
				break;
		}
		
		// Store the name and description
		world.Title = level.Name;
		world.Description = level.Description;
		world.Bonus = level.getBonus();
		
		Statistics.recordLevel(level.Name);
		
		// Generate the landscape
		level.generateLandscape(world);
		
		// Add your forces to the left quarter of the world
		Rectangle playerBounds = new Rectangle(0, 0, world.Width / 4, world.Height);
		for (Actor actor : forces)
			placeRandomly(world, actor, playerBounds);
		
		// Generate enemy forces
		level.generatePieces(world);
		
		// Remember that we generated it
		LevelFactory.GeneratedLevels.add(level.Name);
	}

	/**
	 * Random add a given actor into the world at a random location in the given bounds.
	 * @param world The world to add the actor to
	 * @param actor The actor to add (will change its Location)
	 * @param bounds The bounds to add the actor in
	 */
	static void placeRandomly(World world, Actor actor, Rectangle bounds) {
		int x, y;
		do {
			x = Util.Rand.nextInt(bounds.width) + bounds.x;
			y = Util.Rand.nextInt(bounds.height) + bounds.y;
		} while(!world.getTile(x, y).IsWalkable || world.getActorAt(x, y) != null);
		
		actor.Location.x = x;
		actor.Location.y = y;
		world.Actors.add(actor);
	}
	
	/**
	 * Get a level definition by name.
	 * @param name The level's name.
	 * @return The level (or null)
	 */
	private static Level levelByName(String name) {
		for (Level level : Levels)
			if (level.Name.equals(name))
				return level;
		
		return null;
	}
}
	
/**
 * Represent a level that we can generate.
 */
abstract class Level {
	String Name;
	String Description;
	Color Stairs;
	int Lowest, Highest;
	
	/**
	 * Create a level.
	 * @param name Name to use for round announcements.
	 * @param description Print this out when first visiting the level
	 * @param stairs The color of stairs to get here (to indicate special levels)
	 * @param lowest The lowest level this can be found at (inclusive)
	 * @param highest The highest level this can be found at (inclusive)
	 */
	public Level(String name, String description, Color stairs, int lowest, int highest) {
		Name = name;
		Description = description;
		Stairs = stairs;
		Lowest = lowest;
		Highest = highest;
	}
	
	/**
	 * Get the bonus pieces for beating this level.
	 * @return THe bonus piece(s)
	 */
	abstract List<Actor> getBonus();
	
	/**
	 * Add terrain to the world.
	 * @param world The world to add the terrain to.
	 */
	abstract void generateLandscape(World world);
	
	/**
	 * Add the pieces to the world.
	 * @param world The world to add the pieces to.
	 */
	abstract void generatePieces(World world);
}

abstract class LevelFromFile extends Level {
	String Filename;
	
	/**
	 * Create a level.
	 * @param filename The filename to use
	 * @param name Name to use for round announcements.
	 * @param description Print this out when first visiting the level
	 * @param stairs The color of stairs to get here (to indicate special levels)
	 * @param lowest The lowest level this can be found at (inclusive)
	 * @param highest The highest level this can be found at (inclusive)
	 */
	public LevelFromFile(String filename, String name, String description, Color stairs, int lowest, int highest) {
		super(name, description, stairs, lowest, highest);

		Filename = filename;
	}
	
	/**
	 * Add terrain to the world.
	 * @param world The world to add the terrain to.
	 */
	@Override
	void generateLandscape(World world) {
		// Load the file
		Scanner s = new Scanner(LevelFactory.class.getResourceAsStream(Filename));
		List<String> lines = new ArrayList<String>();
		while (s.hasNextLine())
			lines.add(s.nextLine());
		s.close();
		
		// Fill the level with walls
		for (int x = 0; x < world.Width; x++)
			for (int y = 0; y < world.Height; y++)
				world.Tiles[x][y] = Tile.WALL;
		
		// Load in the file
		for (int y = 0; y < lines.size(); y++)
			for (int x = 0; x < lines.get(y).length(); x++)
				world.Tiles[x][y] = Tile.bySymbol(lines.get(y).charAt(x));
	}
}