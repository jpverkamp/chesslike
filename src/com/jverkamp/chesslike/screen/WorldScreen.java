package com.jverkamp.chesslike.screen;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.actor.Actor;
import com.jverkamp.chesslike.actor.King;
import com.jverkamp.chesslike.world.LevelFactory;
import com.jverkamp.chesslike.world.World;

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
	 * @param stairs The stairs we used to get here.
	 * @param depth The depth we are at.
	 */
	public WorldScreen(Color stairs, int depth) {
		World = new World(58, 18);
		World.setViewSize(58, 18);
		
		CurrentDepth = depth;
		
		List<Actor> valiants = new ArrayList<Actor>();
		valiants.add(new King(World, 0));
		
		LevelFactory.run(World, valiants, stairs, depth);
	}

	/**
	 * Handle input.
	 * @param event Keyboard event.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		// Check for help screen
		if (event.getKeyCode() == KeyEvent.VK_F1)
			return new HelpScreen(this);
		
		// Pass through to the world
		World.input(event);
		
		// Check if we'll be descending
		// This will return either a color or null if we can't
		Color stairColor = World.playerDescends();
		
		// Check if the player has gotten rid of all opponents
		if (World.playerWins() && !VictoryMessage) {
			VictoryMessage = true;
			return new MessageScreen(this, "You've eliminated all enemies.\nContinue to the stairs for a reward.");
		}
		
		// Check for a loss condition.
		else if (World.playerLoses()) {
			return new GameOverScreen(false);
		}
		
		// Check for stairway
		else if (stairColor != null) {
			return new WorldIntroScreen(new WorldScreen(stairColor, CurrentDepth + 1));
		}
		
		// Otherwise, just keep on going
		else {
			return this;
		}
	}

	/**
	 * Draw the world scre		int maxWidth = terminal.getWidthInCharacters();
		int maxHeight = terminal.getHeightInCharacters();
		
en.
	 * @param terminal The panel to draw to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.clear((char) 219, 0, 0, 60, 20);
		World.draw(terminal, new Rectangle(1, 1, 58, 18));
		
		// Help functions
		String[] lines = new String[]{
				"Press F1 for help",
				"",
				"Good luck!"
		};
		for (int i = 0; i < lines.length; i++)
			terminal.write(lines[i], 61, i);
	}
}
