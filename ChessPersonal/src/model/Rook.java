package model;

import java.util.ArrayList;

public class Rook extends CheckPos
{
	static ArrayList<int[]> possibleMoves;
	static ArrayList<int[]> possibleTakes;
	
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination,
			int searchNum, boolean updateSquares) {
		CheckPos.pieceColor = pieceColor;
		CheckPos.pieceName = pieceName;
		CheckPos.start = start;
		CheckPos.destination = destination;
		
		enemyColor = 'B';
		if(pieceColor == 'B')
		{
			enemyColor = 'W';
		}
		
		if(!checkStart())
		{
			return false;
		}
		
		if(!updateSquares && !checkMoveDestination() && !checkTakeDestination())
		{
			return false;
		}
		
		possibleMoves = new ArrayList<>();
		possibleTakes = new ArrayList<>();
		
		boolean canMoveUp = true;
		boolean canMoveDown = true;
		boolean canMoveRight = true;
		boolean canMoveLeft = true;
		
		for(int i = 1; i <= searchNum; i++)
		{
			// if no more moves exit the loop
			if(!canMoveUp && !canMoveDown && !canMoveRight && !canMoveLeft)
			{
				break;
			}
			
			if(canMoveUp)
			{
				if(start[0]-i >= 0)
				{
					canMoveUp = addToLists(start[0]-i, start[1]);
				}
				else
				{
					canMoveUp = false;
				}
			}
			if(canMoveDown)
			{
				if(start[0]+i <= 7)
				{
					canMoveDown = addToLists(start[0]+i, start[1]);
				}
				else
				{
					canMoveDown = false;
				}
			}
			if(canMoveRight)
			{
				if(start[1]+i <= 7)
				{
					canMoveRight = addToLists(start[0], start[1]+i);
				}
				else
				{
					canMoveRight = false;
				}
			}
			if(canMoveLeft)
			{
				if(start[1]-i >= 0)
				{
					canMoveLeft = addToLists(start[0], start[1]-i);
				}
				else
				{
					canMoveLeft = false;
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
