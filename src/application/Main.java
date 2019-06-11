package application;
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application implements ActionListener{
	
	Stage currentStage = new Stage();
	public Game currentGame = new Game();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Loads all users and, if there's no
			// users file then create it
			try {
				// Pull file of all users
				BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
				reader.close();
			} catch(Exception e) { // No users file so it is created
				BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"));
				writer.write("New User");
				writer.close();
			}
			
			// Save stage to be used by other functions
			currentStage = primaryStage;
			currentStage.setMinWidth(800);
			currentStage.setHeight(400);
			
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
