package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChessBoard {
	private static ChessBoard instance;
	private static String[][] board;
	
	private static String curPlayer;
	private static char curColor;
	
	// lists to keep track of taken pieces
	private ArrayList<String> takenWhitePieces;
	private ArrayList<String> takenBlackPieces;
	
	// lists to track squares that white and black pieces are attacking
	private ArrayList<int[]> whiteAttackedSquares;
	private ArrayList<int[]> blackAttackedSquares;
	
	public static HashMap<String, String> pieceSymbols;
	
	// singleton design pattern to allow only one shared instance of the board
	public synchronized static ChessBoard getInstance() {
		if (instance == null) {
			instance = new ChessBoard();
		}
		
		return instance;
	}

	// private constructor so other classes don't use it
	private ChessBoard() {
		board = new String[8][8];
		
		//Font big = new Font("serif", Font.PLAIN, 20);
		
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
		
		setupBoard();
	}
	
	/**
	 * sets up and resets the chess board with the white perspective.
	 * must call before playing.
	 */
	public void setupBoard()
	{
		curPlayer = "White";
		curColor = curPlayer.charAt(0);
		
		takenWhitePieces = new ArrayList<>();
		takenBlackPieces = new ArrayList<>();
		
		whiteAttackedSquares = new ArrayList<>();
		blackAttackedSquares = new ArrayList<>();
		
		for(int row = 0; row < 8; row++) // rank
		{
			for(int col = 0; col < 8; col++) // file
			{
				board[row][col] = "";
				
				if(row <= 1)
				{
					board[row][col] += "B"; // black pieces start with B
				}
				else if(row >= 6)
				{
					board[row][col] += "W"; // white
				}
				
				if(row == 1 || row == 6)
				{
					board[row][col] += "P"; // pawn
				}
				else if(row == 0 || row == 7)
				{
					if(col == 0 || col == 7)
					{
						board[row][col] += "R"; // rook
					}
					else if(col == 1 || col == 6)
					{
						board[row][col] += "N"; // knight
					}
					else if(col == 2 || col == 5)
					{
						board[row][col] += "B"; // bishop
					}
					else if(col == 3)
					{
						board[row][col] += "Q"; // king
					}
					else if(col == 4)
					{
						board[row][col] += "K"; // queen
					}
				}
			}
		}
	}
	
	/**
	 * loads a pre-set board given a 2-d array in the same way the board is set up
	 */
	public void loadBoard(String[][] newBoard, String newPlayer)
	{
		board = newBoard;
		curPlayer = newPlayer;
		curColor = curPlayer.charAt(0);
	}
	
	/**
	 * prints the board to the console.
	 */
	public void printBoard()
	{
		System.out.println("    a    b    c    d    e    f    g    h");
		System.out.println("   _______________________________________");
		for(int row = 0; row < 8; row++) // rank
		{
			System.out.print(8-row + " | ");
			for(int col = 0; col < 8; col++) // file
			{
				if(board[row][col].equals(""))
				{
					System.out.print("   | ");
				}
				else
				{
					System.out.print(pieceSymbols.get(board[row][col]) + " | ");
				}
			}
			System.out.println(8-row);
			System.out.println("  |____|____|____|____|____|____|____|____|");
		}
		System.out.println("\n    a    b    c    d    e    f    g    h");
		
		System.out.println("\nTaken White Pieces:");
		for(String piece: takenWhitePieces)
		{
			System.out.print(pieceSymbols.get(piece) + " ");
		}
		
		System.out.println("\nTaken Black Pieces:");
		for(String piece: takenBlackPieces)
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
	
	/**
	 * just sets the piece to an empty string
	 * @param square: integer array of location of the square on the board array
	 */
	public void removePiece(int[] square)
	{
		board[square[0]][square[1]] = "";
	}
	
	/**
	 * sets the square to the new passed piece and adds the piece it took to the appropriate
	 * taken list if the square was not empty.
	 * @param piece: String name of the new piece
	 * @param square: integer array of location of the square on the board array
	 */
	public void setPiece(String piece, int[] square)
	{
		String pieceToRemove = board[square[0]][square[1]];
		if(!pieceToRemove.equals(""))
		{
			if(pieceToRemove.charAt(0) == 'B')
			{
				takenBlackPieces.add(pieceToRemove);
			}
			else //if it is white
			{
				takenWhitePieces.add(pieceToRemove);
			}
		}
		
		board[square[0]][square[1]] = piece;
	}
	
	/**
	 * gets the piece of the passed square in the board
	 * @param row: integer row of the board array
	 * @param col: integer column of the board array
	 * @return String piece name of the square
	 */
	public String getPiece(int row, int col)
	{
		return board[row][col];
	}
	
	/**
	 * updates the squares that white and black pieces can see after a move
	 */
	public void updateSquares()
	{
		whiteAttackedSquares.clear();
		blackAttackedSquares.clear();
		
		for(int row = 0; row <= 7; row++)
		{
			for(int col = 0; col <= 7; col++)
			{
				String piece = board[row][col];
				char pieceColor = piece.charAt(0);
				char pieceName = piece.charAt(1);
				int[] start = new int[] {row, col};
				
				if(piece.equals(""))
				{
					continue;
				}
				if(pieceName == 'P')
				{
					Pawn.move(pieceColor, pieceName, start, start, true);
				}
				else if(pieceName == 'R')
				{
					Rook.move(pieceColor, pieceName, start, start, 7, true);
				}
				else if(pieceName == 'N')
				{
					Knight.move(pieceColor, pieceName, start, start, true);
				}
				else if(pieceName == 'B')
				{
					Bishop.move(pieceColor, pieceName, start, start, 7, true);
				}
				else if(pieceName == 'K')
				{
					Rook.move(pieceColor, pieceName, start, start, 1, true);
					Bishop.move(pieceColor, pieceName, start, start, 1, true);
				}
				else if(pieceName == 'Q')
				{
					Rook.move(pieceColor, pieceName, start, start, 7, true);
					Bishop.move(pieceColor, pieceName, start, start, 7, true);
				}
			}
		}
	}
	
	/**
	 * adds a square to the white attacked squares
	 * @param square: integer array location of square
	 */
	public void addWhiteSquare(int[] square)
	{
		whiteAttackedSquares.add(square);
	}
	
	/**
	 * adds a square to the black attacked squares
	 * @param square: integer array location of square
	 */
	public void addBlackSquare(int[] square)
	{
		blackAttackedSquares.add(square);
	}
	
	/**
	 * Determines if there is a check on the passed colored king
	 * @param pieceColor: character color of the king, W or B
	 * @return true if the king is in check
	 */
	public boolean checkIfCheck(char pieceColor)
	{
		for(int row = 0; row <= 7; row++)
		{
			for(int col = 0; col <= 7; col++)
			{
				String piece = board[row][col];
				if(piece.equals(pieceColor + "K"))
				{
					ArrayList<int[]> enemySquares = whiteAttackedSquares;
					if(pieceColor == 'W')
					{
						enemySquares = blackAttackedSquares;
					}
					
					for(int[] enemySquare: enemySquares)
					{
						if(Arrays.equals(enemySquare, new int[] {row, col}))
						{
							return true;
						}
					}
					
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines if the passed color king is in checkmate
	 * @param pieceColor: character color of the king, W or B
	 * @return true if the king in checkmate
	 */
	public boolean checkIfCheckMate(char pieceColor)
	{
		/*
		 * need to add way to check if a piece can block check.
		 * can see what piece/ direction check is coming from and then see if a that square is
		 * in the whiteAttackedSquare list and then if it is not it is checkmate, but if it is
		 * you can move the thing
		 */
		for(int row = 0; row <= 7; row++)
		{
			for(int col = 0; col <= 7; col++)
			{
				String piece = board[row][col];
				if(piece.equals(pieceColor + "K"))
				{
					ArrayList<int[]> enemySquares = whiteAttackedSquares;
					if(pieceColor == 'W')
					{
						enemySquares = blackAttackedSquares;
					}
					if(row-1 >= 0)
					{
						if(!arrayListContains(enemySquares, new int[] {row-1, col}))
						{
							return false;
						}
					}
					if(row+1 <= 7)
					{
						if(!arrayListContains(enemySquares, new int[] {row+1, col}))
						{
							return false;
						}
					}
					if(col+1 <= 7)
					{
						if(!arrayListContains(enemySquares, new int[] {row, col+1}))
						{
							return false;
						}
					}
					if(col-1 >= 0)
					{
						if(!arrayListContains(enemySquares, new int[] {row, col-1}))
						{
							return false;
						}
					}
					if(row+1 <= 7 && col+1 <= 7)
					{
						if(!arrayListContains(enemySquares, new int[] {row+1, col+1}))
						{
							return false;
						}
					}
					if(row+1 <= 7 && col-1 >= 0)
					{
						if(!arrayListContains(enemySquares, new int[] {row+1, col-1}))
						{
							return false;
						}
					}
					if(row-1 >= 0 && col+1 <= 7)
					{
						if(!arrayListContains(enemySquares, new int[] {row-1, col+1}))
						{
							return false;
						}
					}
					if(row-1 >= 0 && col-1 >= 0)
					{
						if(!arrayListContains(enemySquares, new int[] {row-1, col-1}))
						{
							return false;
						}
					}
				}
			}
		}
		//return true;
		return false; //for now so it does not give false checkmates
	}
	
	/**
	 * Loops through the array list to see if it contains the passed square
	 * @param enemySquares: array list of enemy attacked squares
	 * @param adjSquare: integer array representing a square on a chess board adjacent to the king
	 * @return true if the array list contains the square
	 */
	public boolean arrayListContains(ArrayList<int[]> enemySquares, int[] adjSquare)
	{
		for(int[] enemySquare: enemySquares)
		{
			if(Arrays.equals(enemySquare, adjSquare))
			{
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getTakenWhitePieces()
	{
		if(takenWhitePieces != null)
		{
			return (ArrayList<String>) takenWhitePieces.clone();
		}
		return null;
	}
	
	public ArrayList<String> getTakenBlackPieces()
	{
		if(takenBlackPieces != null)
		{
			return (ArrayList<String>) takenBlackPieces.clone();
		}
		return null;
	}
	
	public ArrayList<int[]> getWhiteAttackedSquares()
	{
		if(whiteAttackedSquares != null)
		{
			return (ArrayList<int[]>) whiteAttackedSquares.clone();
		}
		return null;
	}
	
	public ArrayList<int[]> getBlackAttackedSquares()
	{
		if(blackAttackedSquares != null)
		{
			return (ArrayList<int[]>) blackAttackedSquares.clone();
		}
		return null;
	}
	
	public String getCurPlayer()
	{
		return curPlayer;
	}
	
	public char getCurColor()
	{
		return curColor;
	}
	
	public void setCurPlayer(String newPlayer)
	{
		curPlayer = newPlayer;
		curColor = newPlayer.charAt(0);
	}
	
	public String[][] getBoard()
	{
		return board.clone();
	}
	
	/*
	 * helper function to add to the possible takes to the move list for the GUI board to show green
	 */
	private void addTakesToMoves(ArrayList<int[]> moves, ArrayList<int[]> takes)
	{
		if(takes != null)
		{
			for(int[] take: takes)
			{
				if(board[take[0]][take[1]].equals("") || board[take[0]][take[1]].charAt(0) != curColor)
				{
					moves.add(take);
				}
			}
		}
	}
	
	
	public ArrayList<int[]> tryMove(char pieceName, int[] start, int[] destination, boolean simMove)
	{
		ArrayList<int[]> moves = null;
		ArrayList<int[]> takes = null;
		boolean isValid = false;
		
		if(pieceName == 'P')
    	{
			Pawn tempPawn = new Pawn();
			
			isValid = tempPawn.move(curColor, pieceName, start, destination, simMove);
			moves = tempPawn.getPossibleMoves();
			takes = tempPawn.getPossibleTakes();
			
			if(takes != null)
			{
				for(int[] take: takes)
				{
					String pieceToTake = board[take[0]][take[1]];
					if(!pieceToTake.equals("") && pieceToTake.charAt(0) != curColor)
					{
						moves.add(take);
					}
				}
			}
        }
    	else if(pieceName == 'R')
    	{
    		Rook tempRook = new Rook();
    		
    		isValid = tempRook.move(curColor, pieceName, start, destination, 7, simMove);
    		moves = tempRook.getPossibleMoves();
			takes = tempRook.getPossibleTakes();
			
			addTakesToMoves(moves, takes);
    	}
    	else if(pieceName == 'N')
    	{
    		Knight tempKnight = new Knight();
    		
    		isValid = !tempKnight.move(curColor, pieceName, start, destination, simMove);
    		moves = tempKnight.getPossibleMoves();
    		takes = tempKnight.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
        }
    	else if(pieceName == 'B')
    	{
    		Bishop tempBishop = new Bishop();
    		
    		isValid = !tempBishop.move(curColor, pieceName, start, destination, 7, simMove);
    		moves = tempBishop.getPossibleMoves();
    		takes = tempBishop.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
    	}
    	else if(pieceName == 'K')
    	{
    		King tempKing = new King();
    		
    		/* King also moves in all directions, but pass in 1 to specify it can only move 1.
    		   Also use the 1 to check if it can castle and move near the enemy king */
    		isValid = tempKing.move(curColor, pieceName, start, destination, simMove);
    		moves = tempKing.getPossibleMoves();
    		takes = tempKing.getPossibleTakes();
    		
			addTakesToMoves(moves, takes);
        }
    	else if(pieceName == 'Q')
    	{
    		Rook tempRook = new Rook();
    		// can use the rook and bishop class because queen has both abilities combined
    		
    		boolean isValidR = tempRook.move(curColor, pieceName, start, destination, 7, false);
    		moves = tempRook.getPossibleMoves();
			takes = tempRook.getPossibleTakes();
			
			addTakesToMoves(moves, takes);
			
			Bishop tempBishop = new Bishop();
    		
    		boolean isValidB = tempBishop.move(curColor, pieceName, start, destination, 7, false);
    		
    		if(tempBishop.getPossibleMoves() != null)
    		{
    			moves.addAll(tempBishop.getPossibleMoves());
    		}
    		
    		addTakesToMoves(moves, takes);
			
    		if(!isValidR || !isValidB || simMove)
    		{
    			return moves;
    		}   		
    	}
		
		if(!isValid || simMove)
		{
			return moves;
		}
        
		if(curPlayer.equals("White"))
		{
			setCurPlayer("Black");
		}
		else
		{
			setCurPlayer("White");
		}
		
		return moves;
	}
}
