package application;
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application implements ActionListener{
	
	Stage currentStage = new Stage();
	Game currentGame = new Game();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Save stage to be used by other functions
			currentStage = primaryStage;
			currentStage.setMinWidth(800);
			currentStage.setHeight(600);
			
			// Show Start/Options/Exit stage first
			Menu pauseMenu = new Menu(this);
			Scene pauseScene = pauseMenu.showMenu();
			primaryStage.setScene(pauseScene);


		    //Setting title to the Stage 
			currentStage.setTitle("Story Time");
		         
		    //Displaying the contents of the stage 
			currentStage.show(); 
			
			// Handles window size changes and resizes the leftSideCover and rightSideCover
			// without changing the game play region
			ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
			{
				pauseMenu.setNewScreenSize(currentStage.getWidth(), currentStage.getHeight());
			};

			currentStage.widthProperty().addListener(stageSizeListener);
			currentStage.heightProperty().addListener(stageSizeListener); 
		      
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeScene()
	{
		currentStage.setScene(currentGame.getScene());
	}
	

}
