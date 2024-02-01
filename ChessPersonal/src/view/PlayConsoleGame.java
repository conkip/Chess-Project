package view;

import java.util.HashMap;
import java.util.Scanner;

import model.Bishop;
import model.ChessBoard;
import model.King;
import model.Knight;
import model.Pawn;
import model.Rook;

public class PlayConsoleGame {
	public static HashMap<Character, Integer> filePairings;
	public static HashMap<Character, Integer> rankPairings;
	
	public static void main(String[] args)
	{
		filePairings = new HashMap<>();
		filePairings.put('a', 0);
		filePairings.put('b', 1);
		filePairings.put('c', 2);
		filePairings.put('d', 3);
		filePairings.put('e', 4);
		filePairings.put('f', 5);
		filePairings.put('g', 6);
		filePairings.put('h', 7);
		
		rankPairings = new HashMap<>();
		//8-row# to get rank
		rankPairings.put('8', 0);
		rankPairings.put('7', 1);
		rankPairings.put('6', 2);
		rankPairings.put('5', 3);
		rankPairings.put('4', 4);
		rankPairings.put('3', 5);
		rankPairings.put('2', 6);
		rankPairings.put('1', 7);
		
		ChessBoard board = ChessBoard.getInstance();
		System.out.println("Format: type moves in the form of [piece you want to move][start][destination]."
				+ "\nKing = K, Queen = Q, Knight = N, Bishop = B, Rook = R, and Pawn = P."
				+ "\nExample: if white wants to movie their pawn from d2 to d4 they would type \"Pd2d4\"."
				+ "\nThe pieces must be capital and the files must be lowercase.\n");
		// potentially allow chess notation and to not need to specify start square
		
		boolean isCheckmate = false;
		String curPlayer = "White";
		Character pieceColor = curPlayer.charAt(0);
		
		Scanner input = new Scanner(System.in);
		
		while(!isCheckmate)
		{
			board.printBoard();
			System.out.println("\n" + curPlayer + " to move:");
			
	        String move = input.nextLine();
	        
	        // check if the piece and files are letters
	        if(move.length() != 5)
	        {
	        	System.out.println("Incorrect input format: length should be 5");
	        	continue;
	        }
	        else if(!Character.isLetter(move.charAt(0)) || !Character.isLetter(move.charAt(1)) 
	        		|| !Character.isLetter(move.charAt(3)))
	        {
	        	System.out.println("Incorrect input format: 1st, 2nd, and 3rd characters should be letters");
	        	continue;
	        }
	        // check if ranks are integers
	        else if(!Character.isDigit(move.charAt(2)) || !Character.isDigit(move.charAt(4)))
	        {
	        	System.out.println("Incorrect input format: 3rd and 5th characters should be integers");
	        	continue;
	        }
	        
        	if(filePairings.get(move.charAt(1)) == null)
        	{
        		System.out.println("Incorrect input format: start file is incorrect");
	        	continue;
        	}
        	int startCol = filePairings.get(move.charAt(1));
        	
        	if(rankPairings.get(move.charAt(2)) == null)
        	{
        		System.out.println("Incorrect input format: start rank is incorrect");
	        	continue;
        	}
        	int startRow = rankPairings.get(move.charAt(2));
        	
        	if(filePairings.get(move.charAt(3)) == null)
        	{
        		System.out.println("Incorrect input format: destination file is incorrect");
	        	continue;
        	}
        	int destCol = filePairings.get(move.charAt(3));
        	
        	if(rankPairings.get(move.charAt(4)) == null)
        	{
        		System.out.println("Incorrect input format: destination rank is incorrect");
	        	continue;
        	}
        	int destRow = rankPairings.get(move.charAt(4));
        	
        	Character pieceName = move.charAt(0);
        	pieceColor = curPlayer.charAt(0);
        	int[] start = {startRow, startCol};
        	int[] destination = {destRow, destCol};
        	
        	if(pieceName == 'P')
        	{
        		if(!Pawn.move(pieceColor, pieceName, start, destination, false))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
	        }
        	else if(pieceName == 'R')
        	{
        		if(!Rook.move(pieceColor, pieceName, start, destination, 7, false))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
        	}
        	else if(pieceName == 'N')
        	{
        		if(!Knight.move(pieceColor, pieceName, start, destination, false))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
	        }
        	else if(pieceName == 'B')
        	{
        		if(!Bishop.move(pieceColor, pieceName, start, destination, 7, false))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
        	}
        	else if(pieceName == 'K')
        	{
        		/* King also moves in all directions, but pass in 1 to specify it can only move 1.
        		   Also use the 1 to check if it can castle and move near the enemy king */
        		if(!King.move(pieceColor, pieceName, start, destination))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
	        }
        	else if(pieceName == 'Q')
        	{
        		// can use the rook and bishop class because queen has both abilities combined
        		if(!Rook.move(pieceColor, pieceName, start, destination, 7, false) 
        				&& !Bishop.move(pieceColor, pieceName, start, destination, 7, false))
        		{
        			System.out.println("Invalid move");
        			continue;
        		}
        	}
        	else
        	{
        		System.out.println("Incorrect input format: piece letter is incorrect");
	        	continue;
        	}
	        
			if(curPlayer.equals("White"))
			{
				curPlayer = "Black";
			}
			else
			{
				curPlayer = "White";
			}
		}
		
		input.close();
        
		board.updateSquares();
		if(board.checkIfCheck(pieceColor))
		{
			board.checkIfCheckMate(pieceColor);
			System.out.println();
		}
		
        board.setupBoard();
		board.printBoard();
	}
}
