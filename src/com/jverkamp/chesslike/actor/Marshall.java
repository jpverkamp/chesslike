package com.jverkamp.chesslike.actor;

import com.jverkamp.chesslike.world.World;

public class Marshall extends Actor {
	// For move validation
	Rook MyRook;
	Knight MyKnight;
	
	/**
	 * Create a new piece.
	 * @param world The world to create the piece in.
	 * @param team The team to add the piece to.
	 */
	public Marshall(World world, int team) {
		super(world, 'M', team);
		
		MyRook = new Rook(world, team);
		MyKnight = new Knight(world, team);
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
		MyKnight.World = World;
		MyKnight.Location.x = Location.x;
		MyKnight.Location.y = Location.y;
		
		return MyRook.validMove(x, y) || MyKnight.validMove(x, y);
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
		MyKnight.World = World;
		MyKnight.Location.x = Location.x;
		MyKnight.Location.y = Location.y;
		
		return MyRook.validCapture(x, y) || MyKnight.validCapture(x, y);
	}
}
