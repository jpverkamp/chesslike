package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.Statistics;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display help information
 */
public class StatScreen extends Screen {
	Screen Original;
	static final String[] PieceNames = new String[]{
		"Pawn",
		"Bishop", "Knight", "Rook",
		"Snake", "Unicorn",
		"Archbishop", "Marshall",
		"Queen", "King"
	};
	static final String[] LevelNames = new String[]{
		"Forest",
		"Caves", "Underground Lake", "Underground Forest", 
		"Dungeon", "Cathedral", "Foundry",
		"Throne Room"
	};

	/**
	 * Create a new help screen
	 * @param original The screen to return to
	 */
	public StatScreen(Screen original) {
		Original = original;
	}
	
	/**
	 * Close on any key.
	 * @param event The event to process.
	 * @return Close this screen.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		return Original;
	}

	/**
	 * Draw the help screen.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		// Error message
		if (!Statistics.isLoaded()) {
			terminal.write("Error loading statistics (it doesn't work in the applet version)", 1, 1);
			terminal.write("Good luck! Press any key to return to the game.", 1, 22);
			return;
		}
			
		
		
		// Header
		terminal.write("Statistics ", 1, 1);
		
		// Pieces captured/lost
		terminal.write("   Captured", 14, 3); terminal.write("This Best All", 14, 4);
		terminal.write("     Lost", 30, 3);     terminal.write("This Best All", 30, 4);
		terminal.write("             ---- ---- ----  ---- ---- ----      -------------", 1, 5);
		for (int i = 0; i < PieceNames.length; i++) {
			terminal.write(PieceNames[i], 1, 6 + i);
			
			int[] computer = Statistics.getCaptured(PieceNames[i], true);
			int[] player = Statistics.getCaptured(PieceNames[i], false);
			
			for (int j = 0; j < 3; j++) {
				terminal.write("" + player[j], 14 + j * 5, 6 + i);
				terminal.write("" + computer[j], 30 + j * 5, 6 + i);
			}
		}
		
		// Win/loss statistics
		terminal.write("Games won", 1, 7 + PieceNames.length);
		terminal.write("" + Statistics.getWins(), 14, 7 + PieceNames.length);
		
		terminal.write("Games lost", 1, 8 + PieceNames.length);
		terminal.write("" + Statistics.getWins(), 14, 8 + PieceNames.length);
		
		// Level statistics
		terminal.write("Levels played", 50, 4);
		for (int i = 0; i < LevelNames.length; i++) {
			terminal.write(LevelNames[i], 50, 6 + i);
			terminal.write("" + Statistics.getLevels(LevelNames[i]), 70, 6 + i);
		}
		
		// Final message
		terminal.write("Good luck! Press any key to return to the game.", 1, 22);
	}

}
