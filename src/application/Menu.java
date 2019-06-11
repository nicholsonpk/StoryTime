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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;

/**
 * @author nicholsonpk
 *
 */
public class Menu {

	VBox menuVBox;
	
	Button newButton;
	VBox slidersBox;
	Button exitButton;
	Pane menuPane;
	
	Scene menuScene;
	
	Main root;
	
	ArrayList<String> allUsers = new ArrayList<String>();
	
	public Menu(Main main)
	{
		root = main;
		
		Image startButtonImage  = new Image("File:///" + System.getProperty("user.dir") + "/images/button_start.png");
		Image exitButtonImage  = new Image("File:///" + System.getProperty("user.dir") + "/images/button_exit.png");
		
		// User selection
		newButton = new Button("");
		newButton.setMinSize(302, 52);
		Background startButtonBackground = new Background(new BackgroundImage(startButtonImage, null, null, null, null));
		newButton.setBackground(startButtonBackground);
		newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	// Find all created users and add them to list
            	BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader("users.csv"));
					
					String line = "";
					Scanner scanner;
					
					while ((line = reader.readLine()) != null) {
						scanner = new Scanner(line);
						
						while (scanner.hasNext()) {
							allUsers.add(scanner.nextLine());
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	

            	ChoiceDialog<String> dialog = new ChoiceDialog<>("New User", allUsers);
            	dialog.setTitle("Choice the user");
            	dialog.setHeaderText("");
            	dialog.setContentText("Choose your character:");

            	// Traditional way to get the response value.
            	Optional<String> result = dialog.showAndWait();
            	if (result.isPresent()){
            		
            		String resultString = result.get();
            		
            		if (resultString != null)
            		{
            			if (resultString.contentEquals("New User"))
            			{
            				// Create new user file with input name
            				TextInputDialog newUserDialog = new TextInputDialog("");
            				newUserDialog.setTitle("Your username:");
            				newUserDialog.setHeaderText("");
            				newUserDialog.setContentText("What is your username? ");

            				// Traditional way to get the response value.
            				Optional<String> newUserResult = newUserDialog.showAndWait();
            				
            				String newUserResultString = "";
            				
            				if (newUserResult.isPresent())
            				{
            					// Make it a string
            					newUserResultString = newUserResult.get();
            					
            					// Add it to allUsers and start the user in the game
            					allUsers.add(newUserResultString);
            					main.currentGame.startNewUser(newUserResultString);
            					
            					// Save users file
            					saveAllUsers();
            				}
            				
            			} else {
            				main.currentGame.loadChar(resultString);
            			}
            			
                        // Load the game scene
                    	root.changeScene();
                    	
                    	// Pull first storyClip
                    	main.currentGame.initialize();
                    	
            		} else { // Cancelled
            			
            		} // end if
            	} // end if 
            	
            }
        }); // End event handler
		
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
		
		exitButton = new Button("");
		exitButton.setMinSize(302, 52);
		Background exitButtonBackground = new Background(new BackgroundImage(exitButtonImage, null, null, null, null));
		exitButton.setBackground(exitButtonBackground);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

		// VBox for all contents
		menuVBox = new VBox(newButton, slidersBox, exitButton);
		menuVBox.setAlignment(Pos.CENTER);
		menuVBox.setMinSize(800,400);
		
		menuPane = new Pane(menuVBox);
		menuPane.setMinSize(800,400);
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
	
	public void saveAllUsers()
	{
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("users.csv"));
			
			for (int i=0; i < allUsers.size(); i++)
			{
				writer.write(allUsers.get(i));
				if (i < allUsers.size() - 1)
				{
					writer.newLine();
				}
			}
			
			writer.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
