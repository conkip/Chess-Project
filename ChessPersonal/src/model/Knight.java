package model;

import java.util.ArrayList;

public class Knight extends CheckPos
{
	static ArrayList<int[]> possibleMoves;
	static ArrayList<int[]> possibleTakes;
	
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
		
		if(updateSquares)
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
