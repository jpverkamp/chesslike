package com.jverkamp.chesslike.screen;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

import com.jverkamp.chesslike.world.LandscapeFactory;
import com.jverkamp.chesslike.world.PiecesFactory;
import com.jverkamp.chesslike.world.World;

public class WorldScreen extends Screen {
	World World;

	/**
	 * Create a new world screen.
	 * @param world
	 */
	public WorldScreen(World world) {
		World = world;
	}
	
	/**
	 * Create a new world screen with a given set of generators.
	 * @param landscape The landscape to generate.
	 * @param pieces The pieces to generate.
	 */
	public WorldScreen(String landscape, String pieces) {
		this(null);
		
		World = new World(58, 18);
		World.setViewSize(58, 18);
		
		LandscapeFactory.run(World, landscape);
		PiecesFactory.run(World, pieces);
	}

	/**
	 * Handle input.
	 * @param event Keyboard event.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		World.input(event);
		
		// Check to 
		if (World.playerWins()) {
			return new GameOverScreen(true);
		} else if (World.playerLoses()) {
			return new GameOverScreen(false);
		}
		
		return this;
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
				"Pieces",
				"------",
				"p - Pawn",
				"b - Bishop",
				"k - Knight",
				"r - Rook",
				"Q - Queen",
				"K - King",
				"",
				"Capture all of the",
				"enemy pieces to",
				"win.",
				"",
				"Use WASD, arrows,",
				"or numpad to",
				"select and ENTER/",
				"SPACE to move.",
				"",
				"Good luck!"
		};
		for (int i = 0; i < lines.length; i++)
			terminal.write(lines[i], 61, i);
	}
}
