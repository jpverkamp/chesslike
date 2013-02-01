package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Statistics;

import trystans.asciiPanel.AsciiPanel;

public class GameOverScreen extends Screen {
	boolean Won;
	
	/**
	 * Create a new game over screen.
	 * @param isWinner Did you win?
	 */
	public GameOverScreen(boolean isWinner) {
		Won = isWinner;
		Statistics.recordGameOver(Won);
	}

	/**
	 * Draw this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		return new MainMenuScreen();
	}
	
	/**
	 * Draw to the panel
	 * @param terminal The event to respond to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.writeCenter(Won ? "You win! " + (char) 2 : "You lose! :(", 9);
		terminal.writeCenter("Press any key to return to the menu", 11);
	}
}
