package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PlayGUIGame extends Application 
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
		primaryPane.getChildren().add(new BoardGUI());
		
		//eventHandlers();
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}
}
