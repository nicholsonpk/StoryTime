/**
 * This class will store the entire menu scene
 * to show on the main stage.
 * 
 * This will let the user choose to start a new game,
 * load a user file, change options, and exit.
 */
package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;

/**
 * @author nicholsonpk
 *
 */
public class Menu {

	VBox menuVBox;
	
	Button newButton;
	Button loadButton;
	VBox slidersBox;
	Button exitButton;
	Pane menuPane;
	
	Scene menuScene;
	
	Main root;
	
	public Menu(Main main)
	{
		root = main;
		
		// User selection
		newButton = new Button("New Game");
		newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // Tell main to start new user
            	root.startNewUser();
            }
        });
		
		loadButton = new Button("Load character");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // Load already created user
            	root.loadChar();
            }
        });
		
		//Options
		Label musicLabel = new Label("Music volume:");
		Slider musicSlider = new Slider();
		Label sfxLabel = new Label("Effects volume:");
		Slider sfxSlider = new Slider();
		slidersBox = new VBox(musicLabel, musicSlider, sfxLabel, sfxSlider);
		
		//Exit
		exitButton = new Button("Exit");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

		// VBox for all contents
		menuVBox = new VBox(newButton, loadButton, slidersBox, exitButton);
		
		menuPane = new Pane(menuVBox);
		menuPane.setMinSize(500, 250);
		menuPane.setCenterShape(true);
		
		menuScene = new Scene(menuPane);
		
	}
	
	public Scene showMenu()
	{
		return menuScene;
	}
	
	
}