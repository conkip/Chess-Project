package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChessBoard {
	private static ChessBoard instance;
	private static String[][] board;
	
	private static String curPlayer;
	private static char curColor;
	
	// lists to keep track of taken pieces
	private ArrayList<String> takenWhitePieces;
	private ArrayList<String> takenBlackPieces;
	
	// lists to track squares that white and black pieces are attacking
	private HashSet<int[]> whiteAttackedSquares;
	private HashSet<int[]> blackAttackedSquares;
	
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
		
		whiteAttackedSquares = new HashSet<>();
		blackAttackedSquares = new HashSet<>();
		
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
	
	private void setupAttackedSquares()
	{
		whiteAttackedSquares.clear();
		blackAttackedSquares.clear();
		
		for(int row = 0; row < 7; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if(!board[row][col].equals(""))
				{
					tryMove(board[row][col].charAt(1), new int[] {row, col}, new int[] {row, col}, true, true);
				}
			}
		}
	}
	
	/**
	 * Determines if there is a check on the passed colored king
	 * @param pieceColor: character color of the king, W or B
	 * @return true if the king is in check
	 */
	public boolean checkIfCheck(char pieceColor)
	{
		setupAttackedSquares();
		for(int row = 0; row <= 7; row++)
		{
			for(int col = 0; col <= 7; col++)
			{
				String piece = board[row][col];
				if(piece.equals(pieceColor + "K"))
				{
					HashSet<int[]> enemySquares = whiteAttackedSquares;
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
					HashSet<int[]> enemySquares = whiteAttackedSquares;
					if(pieceColor == 'W')
					{
						enemySquares = blackAttackedSquares;
					}
					if(row-1 >= 0)
					{
						if(!enemySquares.contains(new int[] {row-1, col}))
						{
							return false;
						}
					}
					if(row+1 <= 7)
					{
						if(!enemySquares.contains(new int[] {row+1, col}))
						{
							return false;
						}
					}
					if(col+1 <= 7)
					{
						if(!enemySquares.contains(new int[] {row, col+1}))
						{
							return false;
						}
					}
					if(col-1 >= 0)
					{
						if(!enemySquares.contains(new int[] {row, col-1}))
						{
							return false;
						}
					}
					if(row+1 <= 7 && col+1 <= 7)
					{
						if(!enemySquares.contains(new int[] {row+1, col+1}))
						{
							return false;
						}
					}
					if(row+1 <= 7 && col-1 >= 0)
					{
						if(!enemySquares.contains(new int[] {row+1, col-1}))
						{
							return false;
						}
					}
					if(row-1 >= 0 && col+1 <= 7)
					{
						if(!enemySquares.contains(new int[] {row-1, col+1}))
						{
							return false;
						}
					}
					if(row-1 >= 0 && col-1 >= 0)
					{
						if(!enemySquares.contains(new int[] {row-1, col-1}))
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
	 * This method will attempt to make a move.
	 * 
	 * @param pieceName: character of the piece name on the board
	 * @param start: col and row of the selected piece on the board
	 * @param destination: attempted destination row and col on the board
	 * @param simMove: if it is just used to select a piece or add to the taken squares, not move it
	 * @param check: if it is used to check for a check so there is not infinite recursion
	 * 
	 * @return array of possible moves for showing on a gui
	 */
	public ArrayList<int[]> tryMove(char pieceName, int[] start, int[] destination, boolean simMove, boolean check)
	{
		char enemyColor = 'B';
		if(curColor == 'B')
		{
			enemyColor = 'W';
		}
		
		if(!check)
		{
			if(checkIfCheck(curColor))
			{
				if(pieceName != 'K')
				{
					return null;
				}
			}
		}
		
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
    		
    		isValid = tempKnight.move(curColor, pieceName, start, destination, simMove);
    		
    		moves = tempKnight.getPossibleMoves();
    		takes = tempKnight.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
        }
    	else if(pieceName == 'B')
    	{
    		Bishop tempBishop = new Bishop();
    		
    		isValid = tempBishop.move(curColor, pieceName, start, destination, 7, simMove);
    		
    		moves = tempBishop.getPossibleMoves();
    		takes = tempBishop.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
    	}
    	else if(pieceName == 'K')
    	{
    		/* King also moves in all directions, but pass in 1 to specify it can only move 1.
    		   Also check if it can castle and or moves into check */
    		
    		Rook tempRook = new Rook();
    		
    		boolean isValidR = tempRook.move(curColor, pieceName, start, destination, 1, simMove);
    		
    		moves = tempRook.getPossibleMoves();
			takes = tempRook.getPossibleTakes();
			
			addTakesToMoves(moves, takes);
			
			Bishop tempBishop = new Bishop();
    		
    		boolean isValidB = tempBishop.move(curColor, pieceName, start, destination, 1, simMove);
    		
    		if(tempBishop.getPossibleMoves() != null)
    		{
    			moves.addAll(tempBishop.getPossibleMoves());
    		}
    		takes = tempBishop.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
			
    		if((!isValidR && !isValidB) || simMove)
    		{
    			return moves;
    		}   
        }
    	else if(pieceName == 'Q')
    	{
    		// can use the rook and bishop class because queen has both abilities combined
    		Rook tempRook = new Rook();
    		
    		boolean isValidR = tempRook.move(curColor, pieceName, start, destination, 7, simMove);
    		
    		moves = tempRook.getPossibleMoves();
			takes = tempRook.getPossibleTakes();
			
			addTakesToMoves(moves, takes);
			
			Bishop tempBishop = new Bishop();
    		
    		boolean isValidB = tempBishop.move(curColor, pieceName, start, destination, 7, simMove);
    		
    		if(tempBishop.getPossibleMoves() != null)
    		{
    			moves.addAll(tempBishop.getPossibleMoves());
    		}
    		takes = tempBishop.getPossibleTakes();
    		
    		addTakesToMoves(moves, takes);
			
    		if((!isValidR && !isValidB) || simMove)
    		{
    			return moves;
    		}   		
    	}
		
		if((pieceName != 'Q' && pieceName != 'K' && !isValid) || simMove)
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
	
	public void setCurPlayer(String newPlayer)
	{
		curPlayer = newPlayer;
		curColor = newPlayer.charAt(0);
	}
	
	
	/*
	 * getters
	 */
	
	public String[][] getBoard()
	{
		return board.clone();
	}
	
	public String getPiece(int row, int col)
	{
		return board[row][col];
	}
	
	public String getCurPlayer()
	{
		return curPlayer;
	}
	
	public char getCurColor()
	{
		return curColor;
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
}
