package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TakenPiecesVBox extends VBox{
	private static Square[][] spaces = new Square[8][2];
	
	public TakenPiecesVBox()
	{
		for(int i = 0; i < 8; i++)
		{
			HBox pair = new HBox();
			
			Square sq1 = new Square("Wood");
			pair.getChildren().add(sq1);
			spaces[i][0] = sq1;
			
			Square sq2 = new Square("Wood");
			pair.getChildren().add(sq2);
			spaces[i][1] = sq2;
			
			this.getChildren().add(pair);
		}
	}
	
	public Square[][] getSpaces()
	{
		return spaces;
	}
	
	public void resetPieces()
	{
		for(int row = 0; row < 7; row++)
		{
			for(int col = 0; col < 2; col++)
			{
				spaces[row][col].setImage("Wood01");
			}
		}
	}

}
