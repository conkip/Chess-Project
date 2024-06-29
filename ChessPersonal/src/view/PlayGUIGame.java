package view;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ChessBoard;

public class PlayGUIGame extends Application 
{
	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane primaryPane;
	private BoardGUI bg;
	private ChessBoard board;
	
	private TakenPiecesVBox takenWPs;
	private TakenPiecesVBox takenBPs;
	
	private int[] start;
	private char startPiece;
	private ArrayList<int[]> startMoves;
	
	private boolean showPossibleMoves;
	
	private int SIZE = 70;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		this.primaryStage = primaryStage;
		setUpStage();
		start = null;
	}
	/**
	 * Where every element of the stage is initialized
	 */
	public void setUpStage() {
		
		showPossibleMoves = true;

		// Instantiates the different Panes and Scenes to be used
		primaryPane = new BorderPane();
		primaryScene = new Scene(primaryPane, SIZE*(8 + 4), SIZE *8);
		
		primaryStage.getIcons().add(new Image("images/BP.png")); //makes icon symbol a pawn (can change)
		
		board = ChessBoard.getInstance();
		bg = new BoardGUI(board.getBoard());
		primaryPane.setCenter(bg);
		
		takenWPs = new TakenPiecesVBox();
		takenBPs = new TakenPiecesVBox();
		
		primaryPane.setLeft(takenWPs);
		primaryPane.setRight(takenBPs);
		
		registerHandlers();
		primaryStage.setScene(primaryScene);
		primaryStage.setTitle("Chess");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/**
	 * registers all of the buttons in the keyboard to the ButtonHandler
	 */
	private void registerHandlers() {
		ButtonHandler bh = new ButtonHandler();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				bg.getSquares()[row][col].setOnAction(bh);
			}
		}
	}
	
	/**
	 * This class handles when a piece in the keyboard is clicked
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent ae) {
			Button buttonClicked = (Button) ae.getSource();
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					Square curSquare = bg.getSquares()[row][col];
					if (curSquare == buttonClicked) {
						String pieceName = curSquare.getPieceName();
						if(start == null)
						{
							if(!pieceName.equals("") && (pieceName.charAt(0) == board.getCurColor()))
							{
								start = new int[]{row, col};
								startPiece = pieceName.charAt(1);
								curSquare.setBackgroundColor("Red");
								
								if(showPossibleMoves)
								{
									if(board.checkIfCheck(board.getCurColor()))
									{
										if(board.checkIfCheckMate(board.getCurColor()))
										{
											System.out.println(board.getCurPlayer() + " lost by checkmate!");
											System.exit(1);
										}
										if(startPiece != 'K')
										{
											return;
										}
									}
									
									startMoves = board.tryMove(startPiece, start, start, true, false);
									if(startMoves != null)
									{
										for(int[] square: startMoves)
										{
											bg.getSquares()[square[0]][square[1]].setBackgroundColor("Green");
										}
									}
								}
							}
						}
						else
						{
							//passes in destination as second argument
							board.tryMove(startPiece, start, new int[] {row, col}, false, false);
							if(showPossibleMoves)
							{
								if(startMoves != null)
								{
									for(int[] square: startMoves)
									{
										bg.getSquares()[square[0]][square[1]].setOGColor();
									}
								}
							}
							
							takenWPs.resetPieces();
							takenBPs.resetPieces();
							
							for(String piece: board.getTakenWhitePieces())
							{
								addToTakenVBOX(piece, takenWPs);
							}
							
							for(String piece: board.getTakenBlackPieces())
							{
								addToTakenVBOX(piece, takenBPs);
							}
							
							Square startSquare = bg.getSquares()[start[0]][start[1]];
							startSquare.setOGColor();
							
							start = null;
							
							bg.updateBoard();
						}
					}
				}
			}
		}
	}
	
	private void addToTakenVBOX(String piece, TakenPiecesVBox takenPs)
	{
		for(int row = 0; row < 7; row++)
		{
			for(int col = 0; col < 2; col++)
			{
				Square sqr = takenPs.getSpaces()[row][col];
				
				if(!sqr.hasAPieceImage())
				{
					sqr.setImage(piece);
					return;
				}
			}
		}
	}
}
