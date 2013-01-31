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
		int code = event.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
			return new WorldIntroScreen(new WorldScreen(null, 1));
		} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		}
		
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
