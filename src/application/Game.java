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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * @author nicholsonpk
 *
 */
public class Game {
	
	private int selectedCSVFile = 1;
    private int selectedStoryClip = 0;
	
	private static ArrayList<StoryClip> storyClipArray = new ArrayList<StoryClip>();
	private Loading loadedData = new Loading();

	private static User myChar = new User();
	private static ImageView viewImage;
	private static Text text1;
	
	private TextFlow content = new TextFlow();
	
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
		text1.setTextAlignment(TextAlignment.CENTER);
		gridpane.add(text1, 2, 1);
		gridpane.setAlignment(Pos.CENTER);
		
		content.setMinSize(500, 250);
		//content.setAlignment(Pos.TOP_LEFT);
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
		
	    //Creating a scene object 
	    scene = new Scene(gridpane);  
	    scene.getStylesheets().add(getClass().getResource("application.css").toString());
	     
	    text1.getStyleClass().add("header");
	   
	}
	
	//Creating the mouse event handler 
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
       @Override 
       public void handle(MouseEvent e) { 
    	   
    	 //Figure out which button was pressed
    	   int buttonNumberPressed = 0;
    	   
    	   if (e.getSource() == choice1)
    	   {
    		   buttonNumberPressed = 0;
    	   } else if (e.getSource() == choice2)
    	   {
    		   buttonNumberPressed = 1;
    	   } else if (e.getSource() == choice3)
    	   {
    		   buttonNumberPressed = 2;
    	   }
    	   
    	   // Set that buttons values for the character
    	   myChar.setStyle(storyClipArray.get(selectedStoryClip).getStyleInt(buttonNumberPressed));
    	   myChar.setSpeed(storyClipArray.get(selectedStoryClip).getSpeedInt(buttonNumberPressed));
    	   
    	   // Select new storyClip
    	   chooseNextStoryClip();

    	   // Display storyClip
    	   loadNextStoryClip();
    	   
    	   // Save character
    	   saveChar();
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
	    Image image = new Image("File:///" + System.getProperty("user.dir") + "/images/WesternIcons_" + imageNumber + ".png");
	    viewImage.setImage(image);
	    text1.setText(Integer.toString(imageNumber));
	}
	
	public void startNewUser(String newUserResultString)
	{
		myChar.setCharacterName(newUserResultString);
		
		// update our users file to add this new user
		
	}
	
	
	// Randomly chooses a storyClip from the total
	// number and avoids repeats
	
	public void chooseNextStoryClip()
	{
		ArrayList<Integer> possibleStoryClip = new ArrayList<Integer>();
		
		// Random value 1-106
		Random r = new Random();
		selectedCSVFile  = r.nextInt(106) + 1;
		selectedStoryClip = 0;
		
		// Testing only
		//selectedCSVFile = 1;
		
		// Pull that into memory to check number of storyclips available
		storyClipArray = loadedData.loadThis(storyClipArray, selectedCSVFile);
		
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
		if (possibleStoryClip.size() > 0)
		{
			selectedStoryClip = r.nextInt(possibleStoryClip.size());
		} else { // 	If no storyClips apply then send the first one
			// 			This shouldn't be the case but this stops errors
			selectedStoryClip = 0;
		}
		
	}
	
	
	//
	// Loads a CSV at random
	// Then it finds which clips the user qualifies for
	// Then displays this to the fields
	//
	
	public void loadNextStoryClip()
	{
		getNewImage(selectedCSVFile);
	
		// Set clip to fields
		text1.setText(storyClipArray.get(selectedStoryClip).getTitleText());
		
		// Store string as Text for TextFlow content

		choice1.setText(storyClipArray.get(selectedStoryClip).getChoice(0));
		choice2.setText(storyClipArray.get(selectedStoryClip).getChoice(1));
		choice3.setText(storyClipArray.get(selectedStoryClip).getChoice(2));
		
		// Find all instances of user generated input
		// and replace the temp with that value
		// If not found, it will ask user to enter one
		
		// Code from online... how to have different text types in one TextFlow
//	     Text text1 = new Text("Big italic red text");
//	     text1.setFill(Color.RED);
//	     text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
//	     Text text2 = new Text(" little bold blue text");
//	     text2.setFill(Color.BLUE);
//	     text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
//	     TextFlow textFlow = new TextFlow(text1, text2);
		
		// Start new ArrayList of Text
		ArrayList<Text> totalTextArray = new ArrayList<Text>();
		String totalText = storyClipArray.get(selectedStoryClip).getText();
		
		// Add each chunk of text to a new location (with their own formatting)
		
		boolean keepLooping = true;
		
		// Loop through entire text.
		while (keepLooping)
		{
			// store currentLoc through indexOf
			if (totalText.indexOf("[") >= 0)
			{
				// start and end points of word to replace
				int start = totalText.indexOf("[");
				int end = totalText.indexOf("]");
				
				// save the information preceding this
				Text addingText = new Text(totalText.substring(0, start - 1));
				totalTextArray.add(addingText);
				
				String returnedText = myChar.getDictionary(totalText.substring(start + 1, end));
				
				// Save the replaced text with proper formatting
				Text addingText2 = new Text(" " + returnedText);
				addingText2.setFill(Color.RED);
				totalTextArray.add(addingText2);
				
				// Remove text already converted
				totalText = totalText.substring(end + 1, totalText.length());
				
			} else {
				Text addingText3 = new Text(totalText);
				totalTextArray.add(addingText3);
				keepLooping = false;
			}
		}
		
		content.getChildren().clear();
	
		// Then add them all to the content TextFlow
		for (int i = 0; i < totalTextArray.size(); i++)
		{
			content.getChildren().add(totalTextArray.get(i));
		}
		
	}
	
	// Initialization to start storyClip
	// when user logs in
	
	public void initialize()
	{
 	   // Select new storyClip
 	   chooseNextStoryClip();

 	   // Display storyClip
 	   loadNextStoryClip();
 	   
 	  saveChar();
	}
	
	//
	// GETS
	//
	public Scene getScene()
	{
		return scene;
	}
	
	
	// Called when the window size is changed
	public void setNewScreenSize(double d, double e)
	{
		gridpane.setMinSize(d,e);
	}
}
