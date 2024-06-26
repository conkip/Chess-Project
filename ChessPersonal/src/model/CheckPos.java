package model;

import java.util.ArrayList;
import java.util.Arrays;

abstract class CheckPos {
	static ChessBoard board = ChessBoard.getInstance();
	static ArrayList<int[]> possibleMoves;
	static ArrayList<int[]> possibleTakes;
	
	static char pieceColor;
	static char enemyColor;
	static char pieceName;
	
	static int[] start;
	static int[] destination;
	
	public static void setVariables(char pieceColor, char pieceName, int[] start, int[] destination)
	{
		CheckPos.pieceColor = pieceColor;
		CheckPos.pieceName = pieceName;
		CheckPos.start = start;
		CheckPos.destination = destination;
		
		CheckPos.possibleMoves = new ArrayList<>();
		CheckPos.possibleTakes = new ArrayList<>();
		
		enemyColor = 'B';
		if(pieceColor == 'B')
		{
			enemyColor = 'W';
		}
	}
	
	/**
	 * checks to see if the destination square is empty to move to or take
	 * @return true if the destination square is valid
	 */
	public static boolean checkMoveDestination()
	{
		String piece = board.getPiece(destination[0], destination[1]);
		
		// must be an empty square to move to
		if(piece.equals(""))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * checks to see if the destination square is empty to move to or take
	 * @return true if the destination square is valid
	 */
	public static boolean checkTakeDestination()
	{
		String piece = board.getPiece(destination[0], destination[1]);
		
		// must be an enemy color to take
		if(!piece.equals("") && piece.charAt(0) == enemyColor)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * checks to see if the destination square is in the possible moves or takes
	 * @return true if the piece is able to move or take a piece
	 */
	public static boolean checkLists()
	{
		//check if it can move
		for(int[] square: possibleMoves)
		{
			if(Arrays.equals(square, destination))
			{
				if(pieceName == 'P')
				{
					String piece = board.getPiece(destination[0], destination[1]);
					if(!piece.equals(""))
					{
						return false;
					}
				}
				
				if(!checkMoveDestination())
				{
					return false;
				}
				
				String pieceToMove = board.getPiece(start[0], start[1]);
				board.removePiece(start);
				board.setPiece(pieceToMove, destination);
				return true;
			}
		}
		
		//check if it can take
		for(int[] square: possibleTakes)
		{
			if(Arrays.equals(square, destination))
			{
				if(!checkTakeDestination())
				{
					return false;
				}
				
				String pieceToMove = board.getPiece(start[0], start[1]);
				board.removePiece(start);
				board.setPiece(pieceToMove, destination);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * adds attacking squares based on the possible moves and takes of each piece for the board lists
	 * @return true regardless of the call to pass to and exit the original function
	 */
	public static boolean addAttackingSquares()
	{
		for(int[] square: possibleTakes)
		{
			if(pieceColor == 'W')
			{
				board.addWhiteSquare(square);
			}
			else// if(playerColor == 'B')
			{
				board.addBlackSquare(square);
			}
		}
		
		// just used for displaying all of the attacked squares
		if(pieceName != 'P')
		{
			for(int[] square: possibleMoves)
			{
				if(pieceColor == 'W')
				{
					board.addWhiteSquare(square);
				}
				else// if(playerColor == 'B')
				{
					board.addBlackSquare(square);
				}
			}
		}
		return true;
	}
	
	/**
	 * adds the square in line of sight of the piece to possible takes or possible moves
	 * @param row: integer row of square
	 * @param col: integer column of square
	 * @return true if the move is blank (so it can move past the space)
	 */
	public static boolean addToLists(int row, int col)
	{
		if(board.getPiece(row, col).equals(""))
		{
			possibleMoves.add(new int[]{row, col});
		}
		else
		{
			// adds to possible takes if it is an enemy color
			// also adds it to take list if it is its own color for checking finding squares
			possibleTakes.add(new int[]{row, col});
			
			//returns false so it does not look past a piece
			return false;
		}
		return true;
	}
	
	public ArrayList<int[]> getPossibleMoves()
	{
		if(possibleMoves != null)
		{
			return (ArrayList<int[]>) possibleMoves.clone();
		}
		return null;
	}
	public ArrayList<int[]> getPossibleTakes()
	{
		if(possibleTakes != null)
		{
			return (ArrayList<int[]>) possibleTakes.clone();
		}
		return null;
	}
}
