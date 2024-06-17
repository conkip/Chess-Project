package view;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Square extends Button
{
	private Map<String, String> colors;
	/*
	 * possibly add different color schemes like:
	 * black and white
	 * red and black
	 * dark green and light green
	 * make menu with 4 alternating boxes for each color scheme and add to dictionary
	 */
	private String color;
	private String pieceName;
	private int SIZE = 70;
	
	//used for wood part
	private int borderWidth = 1;
	private int imageOffset = 20;
	private boolean hasPieceImage = true;
	
	private String ogColor;
	
	public Square(String color) { //add boolean clickable
		pieceName = "";
		
		colors = new HashMap<String, String>();
		colors.put("Black", "696969"); //000000 for true black and can adjust color
		colors.put("White", "ffffff");
		colors.put("Red", "ff0000");
		colors.put("Wood", "69431d");
		colors.put("Wood?", "a1662f");
		colors.put("Green", "00a300");
		
		if(color.equals("Wood"))
		{
			//trying stuff
			/*
			this.setMinSize(SIZE, SIZE);
		    this.setMaxSize(SIZE, SIZE);

		    Image image = new Image("images/Wood01.png", this.getWidth(), this.getHeight(), false, true, true);
		    BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(this.getWidth(), this.getHeight(), true, true, true, false));

		    Background backGround = new Background(bImage);
		    this.setBackground(backGround);
			*/
			
			borderWidth = 0;
			imageOffset = 10;
			this.setPadding(new Insets(-1, -1, -1, -1));
			setImage("Wood01");
			
			hasPieceImage = false;
		}
		
		setBackgroundColor(color);
		this.ogColor = color;
		
		setSize(SIZE);
		this.setFocusTraversable(false);
		this.setOnMouseClicked(getOnDragDetected());
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setBackgroundColor(String color)
	{
		if(color.equals("Green"))
		{
			this.setFocusTraversable(true);
		}
		
		this.color = color;
		setSquareStyle();
	}
	
	/**
	 * sets the square style with the correct colors and removes rounded corners
	 */
	public void setSquareStyle()
	{
		this.setStyle("-fx-background-color: #"+colors.get(color)+"; -fx-background-radius: 0; "
				+ "-fx-border-color: #000000; -fx-border-width: "+ borderWidth + "px;");
	}
	
	public void setSize(int size)
	{
		this.setPrefSize(size, size);
	}
	
	public void setImage(String pieceName)
	{
		if(pieceName.equals("Wood01"))
		{
			hasPieceImage = false;
		}
		else
		{
			hasPieceImage = true;
		}
		
		Image pieceImage = new Image("images/"+pieceName+".png");
		ImageView pieceImageView = new ImageView(pieceImage);
		this.setGraphic(pieceImageView);
		pieceImageView.setFitHeight(SIZE - imageOffset);
		pieceImageView.setFitWidth(SIZE - imageOffset);
		//pieceImageView.setPreserveRatio(true);
		
		this.pieceName = pieceName;
	}
	
	public void removeImage()
	{
		this.setGraphic(null);
	}
	
	//used to look for open spaces on the taken pieces part of the GUI on left and right
	public boolean hasAPieceImage()
	{
		return hasPieceImage;
	}
	
	public String getPieceName()
	{
		return pieceName;
	}
	
	public void setOGColor()
	{
		setBackgroundColor(ogColor);
		this.setFocusTraversable(false);
	}
}
