/**
 * 
 */
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author nicho
 *
 */
public class Game {
	
	private int selectedCSVFile = 1;
    private int selectedStoryClip = 0;
	
	private static ArrayList<StoryClip> storyClipArray = new ArrayList<StoryClip>();
	private Loading loadedData = new Loading();

	private static User myChar;
	private static ImageView viewImage;
	private static Text text1;
	
	private Label content = new Label("My Label");
	
	private Button choice1; 
	private Button choice2;
	private Button choice3;
	
	GridPane gridpane;
	
	Scene scene;
	
	public Game()
	{
		gridpane = new GridPane();
		
	    viewImage = new ImageView();
	    gridpane.getChildren().add(viewImage);
	    GridPane.setColumnIndex(viewImage, 1);
	    GridPane.setRowIndex(viewImage, 2);
	    
		text1 = new Text("TITLE"); 
		gridpane.add(text1, 2, 1);
		gridpane.setAlignment(Pos.CENTER);
		
		content.setMinSize(500, 250);
		content.setAlignment(Pos.TOP_LEFT);
		gridpane.add(content, 2, 2);
	       
	    //Creating Buttons 
		choice1 = new Button("CHOICE 1");  
		choice2 = new Button("CHOICE 2"); 
	    choice3 = new Button("CHOICE 3");
	    choice1.setDisable(false);
	    choice1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler); 
	    choice2.setDisable(false);
	    choice2.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler); 
	    choice3.setDisable(false);
	    choice3.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler); 
	    
	    // HBox
	    HBox choicesBox = new HBox(choice1, choice2, choice3);
	    choicesBox.setSpacing(20.0);
	    choicesBox.setAlignment(Pos.CENTER);
	    
	    // Add all inside grid
	    gridpane.add(choicesBox, 2, 3);
	      
	    //Setting the padding  
	    gridpane.setPadding(new Insets(10, 10, 10, 10)); 
	      
	    //Setting the vertical and horizontal gaps between the columns 
	    gridpane.setVgap(5); 
	    gridpane.setHgap(5);
	    
		myChar = new User();
		try {
			myChar.loadUser("kingphilip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Temp to show all dictionary items
		for (int i=0; i < myChar.dictionary.size(); i++)
		{
			System.out.println(myChar.dictionary.get(i));
		}
		
	    //Creating a scene object 
	    scene = new Scene(gridpane);  
	    scene.getStylesheets().add(getClass().getResource("application.css").toString());
	     
	    text1.getStyleClass().add("header");
	}
	
	//Creating the mouse event handler 
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
       @Override 
       public void handle(MouseEvent e) { 
      	 // Get that buttons values
      	 // Update the character
      	 // Select new storyClip
      	 // Display storyClip
      	 
      	loadNextStoryClip();
       } 
    };  
    
	public void loadChar(String charName)
	{
		try {
			// TODO Send the player's selected or new character name
			myChar.loadUser(charName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error loading character file");
		}
	}
	
	public static void saveChar()
	{
		try {
			myChar.saveUser();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error saving character file");
		}
	}
	
	public static void getNewImage(int imageNumber)
	{
	    Image image = new Image("File:///" + System.getProperty("user.dir") + "/images/SpellBook05_" + imageNumber + ".png");
	    viewImage.setImage(image);
	    text1.setText(Integer.toString(imageNumber));
	}
	
	public void startNewUser()
	{
		System.out.println("New user created");
	}
	
	//
	// Loads a CSV at random
	// Then it finds which clips the user qualifies for
	// Then displays this to the fields
	//
	
	public void loadNextStoryClip()
	{
		ArrayList<Integer> possibleStoryClip = new ArrayList<Integer>();
		
		// Random value 1-139
		Random r = new Random();
		selectedCSVFile  = r.nextInt(139) + 1;
		selectedStoryClip = 0;
		
		selectedCSVFile = 1;
		
		storyClipArray = loadedData.loadThis(storyClipArray, selectedCSVFile);
		
		getNewImage(selectedCSVFile);
		
		// Loop through array to get list of available entries
		for (int i=0; i < storyClipArray.size(); i++)
		{
			// If conditions are met, add to list of possible choices
			if (myChar.getSpeed() >= storyClipArray.get(selectedStoryClip).getSpeedReq() &&
					myChar.getStyle() >= storyClipArray.get(selectedStoryClip).getStyleReq())
			{
				possibleStoryClip.add(i);
			}
		}
		
		// Pick one of the storyClips
		selectedStoryClip = r.nextInt(possibleStoryClip.size());
		
		// Set clip to fields
		text1.setText(storyClipArray.get(selectedStoryClip).getTitleText());
		content.setText(storyClipArray.get(selectedStoryClip).getText());
		choice1.setText(storyClipArray.get(selectedStoryClip).getChoice(0));
		choice2.setText(storyClipArray.get(selectedStoryClip).getChoice(1));
		choice3.setText(storyClipArray.get(selectedStoryClip).getChoice(2));
		
		// Find all instances of user generated input
		// and replace the temp with that value
		// If not found, it will ask user to enter one
		if (content.getText().indexOf("[") >= 0)
		{
			int start = content.getText().indexOf("[");
			int end = content.getText().indexOf("]");
			
			String returnedText = myChar.getDictionary(content.getText().substring(start + 1, end));
			content.setText(content.getText().replace(content.getText().substring(start, end + 1), returnedText));
		}
}
	public Scene getScene()
	{
		return scene;
	}
	
	public void setNewScreenSize(double d, double e)
	{
		gridpane.setMinSize(d,e);
	}
}
