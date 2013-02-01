package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.world.World;

public class Queen extends Actor {
	// For move validation
	Bishop MyBishop;
	Rook MyRook;
	
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Queen(World world, int team) {
		super(world, 'Q', team);
		
		MyBishop = new Bishop(world, team);
		MyRook = new Rook(world, team);
	}

	/**
	 * Check if a move would be valid assuming it's empty.
	 * @param x The x of the location to move to.
	 * @param y The y of the location to move to.
	 * @return If we can move there.
	 */
	@Override
	public boolean validMove(int x, int y) {
		MyRook.World = World;
		MyRook.Location.x = Location.x;
		MyRook.Location.y = Location.y;
		MyBishop.World = World;
		MyBishop.Location.x = Location.x;
		MyBishop.Location.y = Location.y;
		
		return MyBishop.validMove(x, y) || MyRook.validMove(x, y);
	}

	/**
	 * Check if a capture would be valid if there's something there to capture.
	 * @param x The x of the location to capture at.
	 * @param y The y of the location to capture at.
	 * @return If we can capture it.
	 */
	@Override
	public boolean validCapture(int x, int y) {
		MyRook.World = World;
		MyRook.Location.x = Location.x;
		MyRook.Location.y = Location.y;
		MyBishop.World = World;
		MyBishop.Location.x = Location.x;
		MyBishop.Location.y = Location.y;
		
		return MyBishop.validCapture(x, y) || MyRook.validCapture(x, y);
	}
}
