package model;

import java.util.ArrayList;

public class Knight extends CheckPos
{	
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination,
			boolean simMove)
	{
		setVariables(pieceColor, pieceName, start, destination);
		
		if(!simMove && !checkMoveDestination() && !checkTakeDestination())
		{
			return false;
		}
		
		// up 2
		if(start[0]-2 >= 0)
		{
			checkRightLeft(start, enemyColor, -2);
		}
		// down 2
		if(start[0]+2 <= 7)
		{
			checkRightLeft(start, enemyColor, 2);
		}
		// right 2
		if(start[1]+2 <= 7)
		{
			checkUpDown(start, enemyColor, 2);
		}
		// left 2
		if(start[1]-2 >= 0)
		{
			checkUpDown(start, enemyColor, -2);
		}
		
		if(simMove)
		{
			return addAttackingSquares();
		}
		
		return CheckPos.checkLists();
	}
	
	private static void checkRightLeft(int[] start, Character enemyColor, int direction)
	{
		// right 1
		if(start[1]+1 <= 7)
		{
			addToLists(start[0]+direction, start[1]+1);
		}
		// left 1
		if(start[1]-1 >= 0)
		{
			addToLists(start[0]+direction, start[1]-1);
		}
	}
	
	private static void checkUpDown(int[] start, Character enemyColor, int direction)
	{
		// down 1
		if(start[0]+1 <= 7)
		{
			addToLists(start[0]+1, start[1]+direction);
		}
		// up 1
		if(start[0]-1 >= 0)
		{
			addToLists(start[0]-1, start[1]+direction);
		}
	}
}
