package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

/**
 * Display the main menu.
 */
public class MainMenuScreen extends Screen {

	/**
	 * Update this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected void input(KeyEvent event) {
		switch(event.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			Screen.push(new GameOverScreen(true));
			break;
			
		case KeyEvent.VK_ESCAPE:
			Screen.pop();
			break;
		}
	}
	
	/**
	 * Respond to user input
	 * @param terminal The event to respond to.
	 */
	@Override
	protected void update() {
		Terminal.writeCenter("ChessLike", 8);
		Terminal.writeCenter("Roguelike + Chess!", 9);
		Terminal.writeCenter("Press [Enter] to play", 11);
		Terminal.writeCenter("Press [Esc] to quit", 12);
	}
}
