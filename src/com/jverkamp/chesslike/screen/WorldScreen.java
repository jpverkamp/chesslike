package com.jverkamp.chesslike.screen;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.actor.*;
import com.jverkamp.chesslike.world.*;

public class WorldScreen extends Screen {
	World World;
	boolean VictoryMessage;
	int CurrentDepth;

	/**
	 * Create a new world screen.
	 * @param world
	 */
	public WorldScreen(World world) {
		World = world;
	}
	
	/**
	 * Create a new world screen descending on a given color of stairs and a certain level.
	 * @param pieces The pieces that are coming along for the ride.
	 * @param stairs The stairs we used to get here.
	 * @param depth The depth we are at.
	 */
	public WorldScreen(List<Actor> pieces, Color stairs, int depth) {
		World = new World(58, 18);
		World.setViewSize(58, 18);
		
		CurrentDepth = depth;
		
		for (Actor a : pieces)
			a.World = World;
		
		LevelFactory.run(World, pieces, stairs, depth);
	}

	/**
	 * Handle input.
	 * @param event Keyboard event.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		// Pass through to the world
		World.input(event);
		
		// Check if we'll be descending
		// This will return either a color or null if we can't
		Color stairColor = World.playerDescends();
		
		// Check for winning!
		if (World.Title.equals("Throne Room") && !World.containsKing(1)) {
			return new GameOverScreen(true);
		}
		
		// Check if the player has gotten rid of all opponents
		else if (World.playerWins() && !VictoryMessage) {
			VictoryMessage = true;
			return new MessageScreen(this, "You've eliminated all enemies.\nContinue to the stairs for a reward.");
		}
		
		// Check for a loss condition.
		else if (World.playerLoses()) {
			return new GameOverScreen(false);
		}
		
		// Check for stairway
		else if (stairColor != null) {
			// Get your army
			List<Actor> yourHumbleArmy = new ArrayList<Actor>();
			yourHumbleArmy.addAll(World.getPieces(0));
			
			// Potentially add a new piece
			if (VictoryMessage) {
				List<Actor> bonus = World.Bonus;
				if (bonus != null)
					yourHumbleArmy.addAll(bonus);
			}
			
			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, stairColor, CurrentDepth + 1));
		}
		
		// Otherwise, just keep on going
		else {
			return this;
		}
	}

	/**
	 * Draw the world screen.
	 * @param terminal The panel to draw to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.clear((char) 219, 0, 0, 60, 20);
		World.draw(terminal, new Rectangle(1, 1, 58, 18));
		
		// Help functions
		String[] lines = new String[]{
				"",
				"Press F1 for help",
				"Press F2 for stats",
				"",
				"Good luck!"
		};
		for (int i = 0; i < lines.length; i++)
			terminal.write(lines[i], 61, i);
	}
}
