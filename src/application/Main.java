package application;
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class Main extends Application implements ActionListener{
	
	static Character myChar;
	static ImageView viewImage;
	static Text text1;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Creating the mouse event handler 
		      EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
		         @Override 
		         public void handle(MouseEvent e) { 
		        	 // Get that buttons values
		        	 // Update the character
		        	 // Select new storyClip
		        	 // Display storyClip
		            getNewImage();
		         } 
		      };  
			
			GridPane gridpane = new GridPane();
			
		    Random r = new Random();
		    int imageNumber  = r.nextInt(139) + 1;
		    Image image = new Image("File:///" + System.getProperty("user.dir") + "/images/SpellBook05_" + imageNumber + ".png");
		    viewImage = new ImageView(image);
		    gridpane.getChildren().add(viewImage);
		    GridPane.setColumnIndex(viewImage, 1);
		    GridPane.setRowIndex(viewImage, 2);
		      
			text1 = new Text("TITLE"); 
			gridpane.add(text1, 2, 1);
		    
			TextArea content = new TextArea();
			content.setMinSize(500, 250);
			content.setDisable(true);
			gridpane.add(content, 2, 2);
		       
		    //Creating Buttons 
			Button choice1 = new Button("CHOICE 1");  
			Button choice2 = new Button("CHOICE 2"); 
		    Button choice3 = new Button("CHOICE 3");
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
		
		myChar = new Character();
		loadChar();
		
		// Temp to show all dictionary items
		for (int i=0; i < myChar.dictionary.size(); i++)
		{
			System.out.println(myChar.dictionary.get(i));
		}
		
		launch(args);
		
	}
	
	public static void loadChar()
	{
		try {
			// TODO Send the player's selected or new character name
			myChar.loadCharacter("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error loading character file");
		}
	}
	
	public static void saveChar()
	{
		try {
			myChar.saveCharacter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error saving character file");
		}
	}
	
	public static void getNewImage() {
	    Random r = new Random();
	    int imageNumber  = r.nextInt(139) + 1;
	    Image image = new Image("File:///" + System.getProperty("user.dir") + "/images/SpellBook05_" + imageNumber + ".png");
	    viewImage.setImage(image);
	    text1.setText(Integer.toString(imageNumber));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
