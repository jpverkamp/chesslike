package com.jverkamp.chesslike;

import java.awt.Color;

/**
 * Represent a single glyph.
 */
public class Glyph {
	public char Character;
	public Color Color;

	/**
	 * Create a glyph.
	 * @param character The character to draw.
	 * @param color The color to draw it in.
	 */
	public Glyph(char character, Color color) {
		Character = character;
		Color = color;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Glyph && ((Glyph) obj).Character == Character && ((Glyph) obj).Color.equals(Color);
	}
}
