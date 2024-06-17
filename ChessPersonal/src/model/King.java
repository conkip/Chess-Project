package model;

public class King extends CheckPos{
	//add castling
	//to do much check if rook and king havent moved
	//if path is not in sight of enemies (does not put king in check in any of the squares it moves
	//if the king moves over 2 to the left or right
	//and then must move rook to the other side
	//add counter in some method like the main one to keep track if the rook or king have moved and pass to king
	//mabye call rook and bishop methods from this
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination, boolean simMove)
	{
		// also add something to check if move puts king in check
		if(!Rook.move(pieceColor, pieceName, start, destination, 1, simMove) 
				&& !Bishop.move(pieceColor, pieceName, start, destination, 1, simMove))
		{
			return false;
		}
		board.updateSquares();
		if(board.checkIfCheck(pieceColor))
		{
			Rook.move(pieceColor, pieceName, destination, start, 1, simMove);
			Bishop.move(pieceColor, pieceName, destination, start, 1, simMove);
			return false;
		}
		
		return true;
	}
}
