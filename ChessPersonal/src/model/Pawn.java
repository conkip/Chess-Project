package model;

import java.util.ArrayList;

public class Pawn extends CheckPos
{
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination,
			boolean updateSquares)
	{
		CheckPos.pieceColor = pieceColor;
		CheckPos.pieceName = pieceName;
		CheckPos.start = start;
		CheckPos.destination = destination;
		
		enemyColor = 'B';
		if(pieceColor == 'B')
		{
			enemyColor = 'W';
		}
		
		// need to add en passant
		
		// can exit early if the start square does not match the piece
		if(!checkStart())
		{
			return false;
		}
		
		// can also return false if the destination is the own players color
		if(!updateSquares && !checkMoveDestination() && !checkTakeDestination())
		{
			return false;
		}
		
		possibleMoves = new ArrayList<>();
		possibleTakes = new ArrayList<>();
		
		/* check if moves go out of bounds and if not, add to the possible move or take lists
		  to compare later. pawns move down for black and up for white.*/
		
		if(pieceColor == 'B')
		{
			// if pawn hasn't moved, it can move 2 as well
			if(start[0] == 1)
			{
				possibleMoves.add(new int[]{start[0]+1, start[1]});
				possibleMoves.add(new int[]{start[0]+2, start[1]});
			}
			else
			{
				if(start[0]+1 <= 7)
				{
					possibleMoves.add(new int[]{start[0]+1, start[1]});
				}
			}
			if(start[0]+1 <= 7)
			{
				if(start[1]+1 <= 7)
				{
					possibleTakes.add(new int[]{start[0]+1, start[1]+1});
				}
				if(start[1]-1 >= 0)
				{
					possibleTakes.add(new int[]{start[0]+1, start[1]-1});
				}
			}
		}
		else //if(playerColor == 'W')
		{
			if(start[0] == 6)
			{
				possibleMoves.add(new int[]{start[0]-1, start[1]});
				possibleMoves.add(new int[]{start[0]-2, start[1]});
			}
			else
			{
				if(start[0]-1 >= 0)
				{
					possibleMoves.add(new int[]{start[0]-1, start[1]});
				}
			}
			if(start[0]-1 >= 0)
			{
				if(start[1]+1 <= 7)
				{
					possibleTakes.add(new int[]{start[0]-1, start[1]+1});
				}
				if(start[1]-1 >= 0)
				{
					possibleTakes.add(new int[]{start[0]-1, start[1]-1});
				}
			}
		}
		
		if(updateSquares)
		{
			return addAttackingSquares();
		}
		
		return CheckPos.checkLists();
	}
}
