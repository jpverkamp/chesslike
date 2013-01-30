package com.jverkamp.chesslike.world;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.actor.*;
import com.jverkamp.chesslike.tile.Tile;

public class World {
	public Random Rand = new Random();
	
	// The actual world contents
	public int Width, Height;
	Tile[][] Tiles;
	List<Actor> Actors;
	int Round = 1;
	
	// The current active actor
	Actor ActiveActor;
	Point CurrentMove;
	
	// The slice the user can currently see
	Rectangle View;
	
	// Log messages
	List<String> Log;
	
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
		
		Log = new ArrayList<String>();
		
		log("Round " + Round);
		
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
		Actor player = new Knight(this, 0);
		Actors.add(player);
		ActiveActor = player;
		CurrentMove = new Point(ActiveActor.Location.x, ActiveActor.Location.y);
		
		// TODO: DEBUG
		for (int i = 1; i < 4; i++) {
			Actors.add(new Pawn(this, i));
		}
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
	 * Log a new message.
	 * @param msg The new message to log.
	 */
	public void log(String msg) {
		Log.add(0, msg);
	}
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	public void input(KeyEvent event) {
		int code = event.getKeyCode();
		
		// Actually execute a move.
		if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			// Do the move.
			if (ActiveActor.go(CurrentMove.x, CurrentMove.y))
				advanceActiveActor();
		}
		
		// Move the cursor
		int x = CurrentMove.x, y = CurrentMove.y;
		if (Rand.nextInt(10) < 0) { // Yes, the only reason this is here is for alignment. So sue me.
		} else if (code == KeyEvent.VK_NUMPAD1) {
			x -= 1; y += 1; // down left
		} else if (code == KeyEvent.VK_NUMPAD2 || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			        y += 1; // down
		} else if (code == KeyEvent.VK_NUMPAD3) {
			x += 1; y += 1; // down right
		} else if (code == KeyEvent.VK_NUMPAD4 || code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			x -= 1;         // left
		} else if (code == KeyEvent.VK_NUMPAD5) {

		} else if (code == KeyEvent.VK_NUMPAD6 || code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			x += 1;         // right
		} else if (code == KeyEvent.VK_NUMPAD7) {
			x -= 1; y -= 1; // up left
		} else if (code == KeyEvent.VK_NUMPAD8 || code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			        y -= 1; // up
		} else if (code == KeyEvent.VK_NUMPAD9) {
			x += 1; y -= 1; // up right
		}
		CurrentMove.x = x;
		CurrentMove.y = y;
	}
	
	/**
	 * Move the active actor forwards.
	 * 
	 * When it hits a computer piece, run their AI and call this again.
	 * When it hits a player piece, reset the CurrentMove.
	 * When it runs off the end, advance the turn.
	 */
	private void advanceActiveActor() {
		// Find the index of the current actor.
		int i;
		for (i = 0; i < Actors.size(); i++) {
			if (ActiveActor.equals(Actors.get(i)))
				break;
		}
		
		// Advance
		i++;
		
		// We're off the end, next turn.
		// TODO: Do I want to do anything else?
		if (i >= Actors.size()) {
			//Collections.shuffle(Actors);
			i = 0;
			Round += 1;
			log("Round " + Round);
		}
		ActiveActor = Actors.get(i);
		
		// We have a human piece
		if (ActiveActor.Team == 0) {
			CurrentMove.x = ActiveActor.Location.x;
			CurrentMove.y = ActiveActor.Location.y;
			return;
		}
		
		// Otherwise, computer player.
		else {
			ActiveActor.AI();
			advanceActiveActor();
		}
	}

	/**
	 * Draw this screen.
	 * @param terminal The panel to draw to.
	 * @param region The region to draw to.
	 */
	public void draw(AsciiPanel terminal, Rectangle region) {
		if (View.width != region.width || View.height != region.height)
			throw new IllegalArgumentException("Region size doesn't match view size");
		
		// Reset the view area.
		
		
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
						(View.x + xi == CurrentMove.x && View.y + yi == CurrentMove.y) ? Color.GRAY :
							ActiveActor.validMove(View.x + xi, View.y + yi) ? Color.DARK_GRAY : 
								Color.BLACK
					);	
				} 
				
				// Actor exists, draw it
				else {
					terminal.write(
						a.Glyph.Character,
						region.x + xi,
						region.y + yi,
						ActiveActor.equals(a) ? Color.WHITE : a.Glyph.Color,
						(View.x + xi == CurrentMove.x && View.y + yi == CurrentMove.y) ? Color.GRAY :
							ActiveActor.validCapture(View.x + xi, View.y + yi) ? Color.DARK_GRAY : 
								Color.BLACK
					);
				}
			}
		} // draw tiles
		
		// Draw the most recent log message
		for (int i = 0; i < 3; i++)
			if (i < Log.size())
				terminal.write(Log.get(i), 0, terminal.getHeightInCharacters() - 1 - i, Color.WHITE);
	}
}
