package application;
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class Main extends Application implements ActionListener{
	
	private static User myChar;
	private static ImageView viewImage;
	private static Text text1;
	
	private Label content = new Label("My Label");
	
	private Button choice1; 
	private Button choice2;
	private Button choice3;
    
	private int selectedCSVFile = 1;
    private int selectedStoryClip = 0;
	
	private static ArrayList<StoryClip> storyClipArray = new ArrayList<StoryClip>();
	private Loading loadedData = new Loading();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Show Start/Options/Exit stage first
			//Menu pauseMenu = new Menu(this);
			//Scene pauseScene = pauseMenu.showMenu();
			//primaryStage.setScene(pauseScene);
			
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
			
			GridPane gridpane = new GridPane();
			
			// TODO replace/remove random code
		    Random r = new Random();
		    int imageNumber  = r.nextInt(139) + 1;
		    Image image = new Image("File:///" + System.getProperty("user.dir") + "/images/SpellBook05_" + imageNumber + ".png");
		    
		    viewImage = new ImageView(image);
		    gridpane.getChildren().add(viewImage);
		    GridPane.setColumnIndex(viewImage, 1);
		    GridPane.setRowIndex(viewImage, 2);
		      
			text1 = new Text("TITLE"); 
			gridpane.add(text1, 2, 1);
			
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

		    //Creating a scene object 
		    Scene scene = new Scene(gridpane);  
		    scene.getStylesheets().add(getClass().getResource("application.css").toString());
		     
		    text1.getStyleClass().add("header");
		      
		    //Setting title to the Stage 
		    primaryStage.setTitle("Story Time"); 
		         
		    //Adding scene to the stage 
		    primaryStage.setScene(scene); 
		         
		    //Displaying the contents of the stage 
		    primaryStage.show(); 
		      
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		myChar = new User();
		try {
			myChar.loadUser("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Temp to show all dictionary items
		for (int i=0; i < myChar.dictionary.size(); i++)
		{
			System.out.println(myChar.dictionary.get(i));
		}
		
		launch(args);
		
	}
	
	public void loadChar()
	{
		try {
			// TODO Send the player's selected or new character name
			myChar.loadUser("");
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
		
		selectedCSVFile = 23;
		
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
		
	}
}
