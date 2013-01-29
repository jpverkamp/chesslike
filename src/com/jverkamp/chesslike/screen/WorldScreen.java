package com.jverkamp.chesslike.screen;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

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
	 * Handle input.
	 * @param event Keyboard event.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		World.input(event);
		return this;
	}

	/**
	 * Draw the world screen.
	 * @param terminal The panel to draw to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.clear('#', 0, 0, 60, 24);
		World.draw(terminal, new Rectangle(1, 1, 58, 22));
	}
}
