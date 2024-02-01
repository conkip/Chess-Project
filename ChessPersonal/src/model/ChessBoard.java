package model;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
	private static ChessBoard instance;
	private static String[][] board;
	
	// lists to keep track of taken pieces
	private ArrayList<String> takenWhitePieces;
	private ArrayList<String> takenBlackPieces;
	
	// lists to track squares that white and black pieces are attacking
	private ArrayList<int[]> whiteAttackedSquares;
	private ArrayList<int[]> blackAttackedSquares;
	
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
		setupBoard();
		
		takenWhitePieces = new ArrayList<>();
		takenBlackPieces = new ArrayList<>();
		
		whiteAttackedSquares = new ArrayList<>();
		blackAttackedSquares = new ArrayList<>();
	}
	
	/**
	 * sets up and resets the chess board with the white perspective.
	 * must call before playing.
	 */
	public void setupBoard()
	{
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
						board[row][col] += "K"; // king
					}
					else if(col == 4)
					{
						board[row][col] += "Q"; // queen
					}
				}
			}
		}
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
					System.out.print(board[row][col] + " | ");
				}
			}
			System.out.println(8-row);
			System.out.println("  |____|____|____|____|____|____|____|____|");
		}
		System.out.println("\n    a    b    c    d    e    f    g    h");
	}
	
	/**
	 * prints the board from the black perspective.
	 */
	public void printBoardReverse()
	{
		// add later possibly
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
		takenWhitePieces.clear();
		takenBlackPieces.clear();
		
		for(int row = 0; row <= 7; row++)
		{
			for(int col = 0; col <= 7; col++)
			{
				String piece = board[row][col];
				Character pieceColor = piece.charAt(0);
				Character pieceName = piece.charAt(1);
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
	public boolean checkIfCheck(Character pieceColor)
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
	public boolean checkIfCheckMate(Character pieceColor)
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
		return takenWhitePieces;
	}
	
	public ArrayList<String> getTakenBlackPieces()
	{
		return takenBlackPieces;
	}
	
	public ArrayList<int[]> getWhiteAttackedSquares()
	{
		return whiteAttackedSquares;
	}
	
	public ArrayList<int[]> getBlackAttackedSquares()
	{
		return blackAttackedSquares;
	}
	
	public String[][] getBoard()
	{
		return board.clone();
	}
}
