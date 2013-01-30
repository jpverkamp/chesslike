package com.jverkamp.chesslike.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.world.LandscapeFactory;
import com.jverkamp.chesslike.world.PiecesFactory;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display the main menu.
 */
public class MainMenuScreen extends Screen {
	// Possible screen states.
	enum Mode {
		FirstMenu,
		ChooseLandscape,
		ChoosePieces
	};
	Mode CurrentMode = Mode.FirstMenu;
	
	// Selected options
	int MenuOption = 0;
	String SelectedLandscape;
	String SelectedPieces;
	
	/**
	 * Draw this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		int code = event.getKeyCode();
		
		// Display the first layer of menu.
		if (CurrentMode == Mode.FirstMenu) {
			switch(code) {
			
			case KeyEvent.VK_ENTER:
				CurrentMode = Mode.ChooseLandscape;
				return this;
				
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			}
		}
		
		// Choose a landscape
		else if (CurrentMode == Mode.ChooseLandscape) {
			switch(code) {
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_NUMPAD8:
				MenuOption -= 1;
				break;
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_NUMPAD2:
				MenuOption += 1;
				break;

			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_SPACE:
				SelectedLandscape = LandscapeFactory.Names[MenuOption][0];
				MenuOption = 0;
				CurrentMode = Mode.ChoosePieces;
				break;
				
			case KeyEvent.VK_ESCAPE:
				MenuOption = 0;
				CurrentMode = Mode.FirstMenu;
				break;
				
			}
			
			if (MenuOption < 0) MenuOption += LandscapeFactory.Names.length;
			if (MenuOption >= LandscapeFactory.Names.length) MenuOption = 0;
		}
		
		// Choose pieces
		else if (CurrentMode == Mode.ChoosePieces) {
			switch(code) {
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_NUMPAD8:
				MenuOption -= 1;
				break;
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_NUMPAD2:
				MenuOption += 1;
				break;
	
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_SPACE:
				SelectedPieces = PiecesFactory.Names[MenuOption][0];
				MenuOption = 0;
				return new WorldScreen(SelectedLandscape, SelectedPieces);
				
			case KeyEvent.VK_ESCAPE:
				MenuOption = 0;
				CurrentMode = Mode.ChooseLandscape;
				break;
				
			}
			
			if (MenuOption < 0) MenuOption += PiecesFactory.Names.length;
			if (MenuOption >= PiecesFactory.Names.length) MenuOption = 0;
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
		// Display the first layer of menu.
		if (CurrentMode == Mode.FirstMenu) {
			terminal.writeCenter("ChessLike", 8);
			terminal.writeCenter("Roguelike + Chess!", 9);
			terminal.writeCenter("Press [Enter] to play", 11);
			terminal.writeCenter("Press [Esc] to quit", 12);
		}
		
		// Choose a landscape
		else if (CurrentMode == Mode.ChooseLandscape) {
			int offset = terminal.getHeightInCharacters() / 2 - (LandscapeFactory.Names.length + 6) / 2;
			terminal.writeCenter("Choose a landscape", offset);
			for (int i = 0; i < LandscapeFactory.Names.length; i++)
				terminal.writeCenter(
					LandscapeFactory.Names[i][1], 
					offset + i + 2, 
					MenuOption == i ? Color.BLACK : Color.WHITE, 
					MenuOption == i ? Color.WHITE : Color.BLACK
				);
			terminal.writeCenter("[Up/Down] to change", offset + LandscapeFactory.Names.length + 3);
			terminal.writeCenter("[Enter] to select", offset + LandscapeFactory.Names.length + 4);
			terminal.writeCenter("[Esc] to go back", offset + LandscapeFactory.Names.length + 5);
		}
		
		// Choose pieces
		else if (CurrentMode == Mode.ChoosePieces) {
			int offset = terminal.getHeightInCharacters() / 2 - (PiecesFactory.Names.length + 6) / 2;
			terminal.writeCenter("Choose the kinds of pieces", offset);
			for (int i = 0; i < PiecesFactory.Names.length; i++)
				terminal.writeCenter(
					PiecesFactory.Names[i][1], 
					offset + i + 2, 
					MenuOption == i ? Color.BLACK : Color.WHITE, 
					MenuOption == i ? Color.WHITE : Color.BLACK
				);
			terminal.writeCenter("[Up/Down] to change", offset + PiecesFactory.Names.length + 3);
			terminal.writeCenter("[Enter] to select", offset + PiecesFactory.Names.length + 4);
			terminal.writeCenter("[Esc] to go back", offset + PiecesFactory.Names.length + 5);
		}
	}
}
