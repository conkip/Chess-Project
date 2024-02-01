package view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Button;

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
	int[] coord;
	String color;
	
	public Square(int[] coord, String color) {
		colors = new HashMap<String, String>();
		colors.put("Black", "000000");
		colors.put("White", "ffffff");
		
		setColor(color);
		setCoord(coord);
		setSize(50);
		
	}
	
	public void setColor(String color)
	{
		this.setStyle("-fx-background-color: #ff"+colors.get(color)+"; ");
		this.color = color;
	}
	
	public void setCoord(int[] coord)
	{
		this.coord = coord;
	}
	
	public void setSize(int size)
	{
		this.setPrefSize(size, size);
	}
}
