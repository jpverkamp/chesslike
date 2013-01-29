package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display the main menu.
 */
public class MainMenuScreen extends Screen {

	/**
	 * Draw this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		switch(event.getKeyCode()) {
		// DEBUG: On enter, win the game
		case KeyEvent.VK_ENTER:
			return new GameOverScreen(true);
			
		// On escape, exit (by returning no screen)
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
		
		// Otherwise, no change.
		return this;
	}
	
	/**
	 * Draw the panel
	 * @param terminal The event to respond to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.writeCenter("ChessLike", 8);
		terminal.writeCenter("Roguelike + Chess!", 9);
		terminal.writeCenter("Press [Enter] to play", 11);
		terminal.writeCenter("Press [Esc] to quit", 12);
	}
}
