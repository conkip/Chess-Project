package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BoardGUI extends VBox
{
	private String[][] board;
	private Square[][] squares = new Square[8][8];
	
	public BoardGUI(String [][] board)
	{
		setupBoard();
		this.board = board;
		updateBoard();
	}
	
	public void setupBoard()
	{
		this.getChildren().clear();
		
		for(int row = 0; row < 8; row++)
		{
			HBox boardRow = new HBox();
			
			for(int col = 0; col < 8; col++)
			{
				String color = "";
				if(row%2 == 0)
				{
					if(col%2 == 0)
					{
						color = "White";
					}
					else
					{
						color = "Black";
					}
				}
				else
				{
					if(col%2 == 0)
					{
						color = "Black";
					}
					else
					{
						color = "White";
					}
				}
				
				Square square = new Square(color);
				squares[row][col] = square;
				boardRow.getChildren().add(square);
			}
			this.getChildren().add(boardRow);
		}
	}
	
	public void updateBoard()
	{
		for(int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				Square curSquare = (Square)((Pane) this.getChildren().get(row)).getChildren().get(col);
				if (board[row][col] != "")
				{
					curSquare.setImage(board[row][col]);
				}
				else
				{
					curSquare.removeImage();
				}
			}
		}
	}
	
	public Square[][] getSquares()
	{
		return squares;
	}
}
