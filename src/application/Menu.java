/**
 * This class will store the entire menu scene
 * to show on the main stage.
 * 
 * This will let the user choose to start a new game,
 * load a user file, change options, and exit.
 */
package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
            	root.changeScene();
            }
        });
		
		loadButton = new Button("Load character");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	String input = "";
            	
                // Check files for already created users
            	
            	// Show list and allow choice for that
            	// or a new user
            	
            	// Load already created user
            	//root.loadChar(input);
            }
        });
		
		//Options
		Label musicLabel = new Label("Music volume:");
		Slider musicSlider = new Slider();
		musicSlider.setMaxWidth(300);
		musicSlider.setMin(0);
		musicSlider.setMax(100);
		musicSlider.setValue(100);
		
		Label sfxLabel = new Label("Effects volume:");
		Slider sfxSlider = new Slider();
		sfxSlider.setMaxWidth(300);
		sfxSlider.setMin(0);
		sfxSlider.setMax(100);
		sfxSlider.setValue(100);
		
		slidersBox = new VBox(musicLabel, musicSlider, sfxLabel, sfxSlider);
		slidersBox.setAlignment(Pos.CENTER);
		
		//Exit
		exitButton = new Button("Exit");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

		// VBox for all contents
		menuVBox = new VBox(newButton, loadButton, slidersBox, exitButton);
		menuVBox.setAlignment(Pos.CENTER);
		menuVBox.setMinSize(800,600);
		
		menuPane = new Pane(menuVBox);
		menuPane.setMinSize(800,600);
		menuPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		
		menuScene = new Scene(menuPane);
		
	}
	
	public Scene showMenu()
	{
		return menuScene;
	}
	
	public void setNewScreenSize(double d, double e)
	{
		menuVBox.setMinSize(d,e);
		menuPane.setMinSize(d,e);
	}
	
}
