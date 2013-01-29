package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

public class GameOverScreen extends Screen {
	boolean Won;
	
	/**
	 * Create a new game over screen.
	 * @param isWinner Did you win?
	 */
	public GameOverScreen(boolean isWinner) {
		Won = isWinner;
	}

	/**
	 * Update this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	public void input(KeyEvent event) {
		switch(event.getKeyChar()) {
		default:
			Screen.pop();
		}
	}
	
	/**
	 * Respond to user input
	 * @param terminal The event to respond to.
	 */
	@Override
	public void update() {
		Terminal.writeCenter("You win!", 9);
		Terminal.writeCenter("Press any key to return to the menu", 11);
	}
}
