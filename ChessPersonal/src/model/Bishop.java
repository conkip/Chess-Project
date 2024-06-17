package model;

import java.util.ArrayList;

public class Bishop extends CheckPos
{
	static ArrayList<int[]> possibleMoves;
	static ArrayList<int[]> possibleTakes;
	
	public static boolean move(Character pieceColor, Character pieceName, int[] start, int[] destination,
			int searchNum, boolean simMove) {
		setVariables(pieceColor, pieceName, start, destination);
		
		if(!checkStart())
		{
			return false;
		}
		
		if(!simMove && !checkMoveDestination() && !checkTakeDestination())
		{
			return false;
		}
		
		boolean canMoveNortheast = true;
		boolean canMoveNorthwest = true;
		boolean canMoveSoutheast = true;
		boolean canMoveSouthwest = true;
		
		for(int i = 1; i <= searchNum; i++)
		{
			// if no more moves exit the loop
			if(!canMoveNortheast && !canMoveNorthwest && !canMoveSoutheast && !canMoveSouthwest)
			{
				break;
			}
			
			if(canMoveNortheast)
			{
				if(start[0]-i >= 0 && start[1]+i <= 7)
				{
					canMoveNortheast = addToLists(start[0]-i, start[1]+i);
				}
				else
				{
					canMoveNortheast = false;
				}
			}
			if(canMoveNorthwest)
			{
				if(start[0]-i >= 0 && start[1]-i >= 0)
				{
					canMoveNorthwest = addToLists(start[0]-i, start[1]-i);
				}
				else
				{
					canMoveNorthwest = false;
				}
			}
			if(canMoveSoutheast)
			{
				if(start[0]+i <= 7 && start[1]+i <= 7)
				{
					canMoveSoutheast = addToLists(start[0]+i, start[1]+i);
				}
				else
				{
					canMoveSoutheast = false;
				}
			}
			if(canMoveSouthwest)
			{
				if(start[0]+i <= 7 && start[1]-i >= 0)
				{
					canMoveSouthwest = addToLists(start[0]+i, start[1]-i);
				}
				else
				{
					canMoveSouthwest = false;
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
