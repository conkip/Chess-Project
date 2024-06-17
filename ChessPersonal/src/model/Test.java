package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ChessBoard;

public class Test extends Application
{
	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane primaryPane;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		this.primaryStage = primaryStage;
		setUpStage();
	}
	
	/**
	 * Where every element of the stage is initialized
	 */
	public void setUpStage() {

		// Instantiates the different Panes and Scenes to be used
		primaryPane = new BorderPane();
		primaryScene = new Scene(primaryPane, 500, 500);
		ChessBoard board = ChessBoard.getInstance();
		Button b = new Button();
		Image pieceImage = new Image("file:images/BB.png");
		ImageView pieceImageView = new ImageView(pieceImage);
		pieceImageView.setFitHeight(50);
		pieceImageView.setPreserveRatio(true);
		b.setGraphic(pieceImageView);
		primaryPane.setCenter(b);
		
		//eventHandlers();
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}
}
