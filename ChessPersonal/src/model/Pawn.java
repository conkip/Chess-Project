package model;

import java.util.ArrayList;

public class Pawn extends CheckPos
{
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination,
			boolean simMove)
	{
		// need to add en passant
		setVariables(pieceColor, pieceName, start, destination);
		
		if(!simMove && !checkMoveDestination()&& !checkTakeDestination())
		{
			return false;
		}
		
		/* check if moves go out of bounds and if not, add to the possible move or take lists
		  to compare later. pawns move down for black and up for white.*/
		
		if(pieceColor == 'B')
		{
			// if pawn hasn't moved, it can move 2 as well
			if(start[0] == 1)
			{
				if(board.getPiece(start[0]+1, start[1]).equals(""))
				{
					possibleMoves.add(new int[]{start[0]+1, start[1]});
					if(board.getPiece(start[0]+2, start[1]).equals(""))
					{
						possibleMoves.add(new int[]{start[0]+2, start[1]});
					}
				}
			}
			else
			{
				if(start[0] < 7)
				{
					if(board.getPiece(start[0]+1, start[1]).equals(""))
					{
						possibleMoves.add(new int[]{start[0]+1, start[1]});
					}
				}
			}
			
			if(start[0] < 7)
			{
				if(start[1] < 7)
				{
					String piece = board.getPiece(start[0]+1, start[1]+1);
					if(!piece.equals("") && piece.charAt(0) == enemyColor)
					{
						possibleTakes.add(new int[]{start[0]+1, start[1]+1});
					}
				}
				if(start[1] > 0)
				{
					String piece = board.getPiece(start[0]+1, start[1]-1);
					if(!piece.equals("") && piece.charAt(0) == enemyColor)
					{
						possibleTakes.add(new int[]{start[0]+1, start[1]-1});
					}
				}
			}
		}
		else //if(playerColor == 'W')
		{
			if(start[0] == 6)
			{
				if(board.getPiece(start[0]-1, start[1]).equals(""))
				{
					possibleMoves.add(new int[]{start[0]-1, start[1]});
					if(board.getPiece(start[0]-2, start[1]).equals(""))
					{
						possibleMoves.add(new int[]{start[0]-2, start[1]});
					}
				}
			}
			else
			{
				if(start[0] > 0)
				{
					if(board.getPiece(start[0]-1, start[1]).equals(""))
					{
						possibleMoves.add(new int[]{start[0]-1, start[1]});
					}
				}
			}
			if(start[0] > 0)
			{
				if(start[1] < 7)
				{
					String piece = board.getPiece(start[0]-1, start[1]+1);
					if(!piece.equals("") && piece.charAt(0) == enemyColor)
					{
						possibleTakes.add(new int[]{start[0]-1, start[1]+1});
					}
				}
				if(start[1] > 0)
				{
					String piece = board.getPiece(start[0]-1, start[1]-1);
					if(!piece.equals("") && piece.charAt(0) == enemyColor)
					{
						possibleTakes.add(new int[]{start[0]-1, start[1]-1});
					}
				}
			}
		}
		
		if(simMove)
		{
			return addAttackingSquares();
		}
		
		return CheckPos.checkLists();
	}
}
