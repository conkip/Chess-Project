package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoardGUI extends VBox
{
	public BoardGUI()
	{
		updateBoard();
	}
	
	public void updateBoard()
	{
		this.getChildren().clear();
		
		for(int row = 0; row <= 7; row++)
		{
			HBox boardRow = new HBox();
			
			for(int col = 0; col <= 7; col++)
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
				
				Square square = new Square(new int[] {row, col}, color);
				boardRow.getChildren().add(square);
			}
			this.getChildren().add(boardRow);
		}
	}
}
