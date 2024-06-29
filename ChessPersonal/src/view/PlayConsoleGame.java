package view;

import java.util.HashMap;
import java.util.Scanner;

import model.ChessBoard;

public class PlayConsoleGame {
	public static HashMap<Character, Integer> filePairings;
	public static HashMap<Character, Integer> rankPairings;
	private static HashMap<String, String> pieceSymbols;
	
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
		
		pieceSymbols = new HashMap<>();
		pieceSymbols.put("BR", "♜   ");
		pieceSymbols.put("BN", "♞   ");
		pieceSymbols.put("BB", "♝   ");
		pieceSymbols.put("BK", "♚   ");
		pieceSymbols.put("BQ", "♛   ");
		pieceSymbols.put("BP", "♟ ");
		
		pieceSymbols.put("WR", "♖   ");
		pieceSymbols.put("WN", "♘   ");
		pieceSymbols.put("WB", "♗   ");
		pieceSymbols.put("WK", "♔   ");
		pieceSymbols.put("WQ", "♕   ");
		pieceSymbols.put("WP", "♙   ");
		
		System.out.println("Format: type moves in the form of [piece you want to move][start][destination]."
				+ "\nKing = K, Queen = Q, Knight = N, Bishop = B, Rook = R, and Pawn = P."
				+ "\nExample: if white wants to movie their pawn from d2 to d4 they would type \"Pd2d4\"."
				+ "\nThe pieces must be capital and the files must be lowercase."
				+ "\nType 'END' to conciede.\n");
		// potentially allow chess notation and to not need to specify start square
		
		
		
		//load game one away from checkmate
		
		/*
		String[][] newBoard = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
							   {"", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
							   {"", "", "", "", "", "", "", ""},
							   {"", "", "", "", "", "", "", ""},
							   {"", "", "WB", "", "WP", "", "", ""},
							   {"BP", "", "", "", "", "WQ", "", ""},
							   {"WP", "WP", "WP", "WP", "", "WP", "WP", "WP"},
							   {"WR", "WN", "WB", "", "WK", "", "WN", "WR"}};
		board.loadBoard(newBoard, "White");
		*/
		
		ChessBoard board = ChessBoard.getInstance();
		Scanner input = new Scanner(System.in);
		
		playGame(board, input);
		
		while(true)
		{
			System.out.println("Play again? (Y/N)");
			String ans = input.nextLine();
			if(ans.equals("Y"))
			{
				board.setupBoard();
				playGame(board, input);
			}
			else
			{
				input.close();
				System. exit(0);
			}
		}
	}

	public static void playGame(ChessBoard board, Scanner input)
	{
		boolean isCheckmate = false;
		
		while(!isCheckmate)
		{
			System.out.println();
			printBoard(board);
			System.out.println("\n" + board.getCurPlayer() + " to move:");
			
	        String move = input.nextLine();
	        
	        if(move.equals("END"))
	        {
	        	String enemyPlayer = "White";
	        	if (board.getCurPlayer() == "White")
	        	{
	        		enemyPlayer = "Black";
	        	}
	        	System.out.println(board.getCurPlayer() + " Concieded the game. " +enemyPlayer + " wins!");
	        	break;
	        }
	        
	        // check if the piece and files are letters
	        if(move.length() != 5)
	        {
	        	System.out.println("Incorrect input format: length should be 5");
	        	continue;
	        }
	        if(!Character.isLetter(move.charAt(0)) || !Character.isLetter(move.charAt(1)) 
	        		|| !Character.isLetter(move.charAt(3)))
	        {
	        	System.out.println("Incorrect input format: 1st, 2nd, and 3rd characters should be letters");
	        	continue;
	        }
	        // check if ranks are integers
	        if(!Character.isDigit(move.charAt(2)) || !Character.isDigit(move.charAt(4)))
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
        	
        	int[] start = {startRow, startCol};
        	int[] destination = {destRow, destCol};
        	
        	if(pieceName != 'K' && pieceName != 'Q' && pieceName != 'N' && 
        			pieceName != 'B' && pieceName != 'R' && pieceName != 'P')
        	{
        		System.out.println("Incorrect input format: piece letter is incorrect");
        		continue;
        	}
        	else
        	{
        		//check the start location
        		String piece = board.getPiece(start[0], start[1]);
        		
        		if(piece == "")
        		{
        			System.out.println("Invalid move: Start location has no piece");
        			continue;
        		}
        		if(piece.charAt(0) != board.getCurColor())
        		{
        			System.out.println("Invalid move: Start piece is the wrong color");
        			continue;
        		}
        		if(piece.charAt(1) != pieceName)
        		{
        			System.out.println("Invalid move: Start location does not match the start piece");
        			continue;
        		}
        		
        		if(board.tryMove(pieceName, start, destination, false, false) == null)
        		{
        			System.out.println("Invalid move");
        		}
        	}
		}
	}
	
	/**
	 * prints the board to the console.
	 */
	public static void printBoard(ChessBoard board)
	{
		String[][] boardArr = board.getBoard();
		
		System.out.println("    a    b    c    d    e    f    g    h");
		System.out.println("   _______________________________________");
		for(int row = 0; row < 8; row++) // rank
		{
			System.out.print(8-row + " | ");
			for(int col = 0; col < 8; col++) // file
			{
				if(boardArr[row][col].equals(""))
				{
					System.out.print("   | ");
				}
				else
				{
					System.out.print(pieceSymbols.get(boardArr[row][col]) + " | ");
				}
			}
			System.out.println(8-row);
			System.out.println("  |____|____|____|____|____|____|____|____|");
		}
		System.out.println("\n    a    b    c    d    e    f    g    h");
		
		System.out.println("\nTaken White Pieces:");
		for(String piece: board.getTakenWhitePieces())
		{
			System.out.print(pieceSymbols.get(piece) + " ");
		}
		
		System.out.println("\nTaken Black Pieces:");
		for(String piece: board.getTakenBlackPieces())
		{
			System.out.print(pieceSymbols.get(piece) + " ");
		}
	}
	
	/**
	 * prints the board from the black perspective.
	 */
	public void printBoardReverse()
	{
		// add later possibly, but probably not because the main part is the GUI
	}
}
