/**
 * Philip Nicholson
 * 3/14/2019
 * This class holds the character data.
 * It can load and save a csv file to allow the player to continue.
 * 
 */
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.scene.control.TextInputDialog;

/**
 * @author nicholsonp
 *
 */
public class User{

	private int style;
	private int speed;
	
	public ArrayList<String> dictionary = new ArrayList<String>();
	
	public User()
	{
		style = 0;
		speed = 0;
		
		dictionary.add(""); // first location is users name
	}
	
	public void loadUser(String username) throws IOException {
		
		// set username from menu
		dictionary.set(0, username);
		
		
		// load csv for character
		BufferedReader reader = new BufferedReader(new FileReader(username + ".csv"));
			
		// read file line by line
		String line = null;
		Scanner scanner = null;
		int lineNumber = 0;

		while ((line = reader.readLine()) != null) {
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				if (lineNumber == 0)  // first line stores the users name
				{
					dictionary.set(0, scanner.nextLine());
				} else if (lineNumber == 1)
				{
					String[] data = scanner.nextLine().split(","); // second line stores all numerical stats
					style = Integer.parseInt(data[0]);
					speed = Integer.parseInt(data[1]);
				} else {
					String[] data = scanner.nextLine().split(","); // each additional line has a title,text pair (starts at dictionary.get(1). Odds are name, evens are values)
					dictionary.add(data[0]);
					dictionary.add(data[1]);
				}
				lineNumber++;
			} // while hasNext
				
			//close reader
			
		} // end while readLine
		reader.close();
	} // end loadUser
	
	
	public void saveUser() throws IOException {
		
		// 0 stores the username
		// 1 stores the style and speed
		String filename = dictionary.get(0) + ".csv";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		String line = null;
		
		line  = dictionary.get(0);
		writer.write(line);
		writer.newLine();
		
		line  = Integer.toString(style) + "," + Integer.toString(speed);
		writer.write(line);
		writer.newLine();
		
		for (int i = 2; i < dictionary.size(); i+=2)
		{
			line  = dictionary.get(i) + "," + dictionary.get(i+1);
			writer.write(line);
			writer.newLine();
		}
		
		writer.close();
	} // end saveUser
	
	
	/*
	 * getDictionary sends back the value for a given word name.
	 * Ex - main_enemy returns the next value which is that enemy's name
	 * If it isn't found, we need to ask for it
	 * 
	 * askForTerm handles this task by getting the new term from an Input Dialog Box
	 */
	public String getDictionary(String type)
	{
		int index = dictionary.indexOf(type);
		if (index != -1)
			return dictionary.get(index + 1);// add one to get the correct value
		else {
			// type not found, ask the user to input it
			String newTerm = askForTerm(type);
			return newTerm;
		}
	}
	
	private String askForTerm(String type)
	{
		
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Enter a new term:");
		dialog.setHeaderText("Use your imagination!");
		dialog.setContentText("Please enter your [" + type + "] term:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		
		String resultString = "";
		
		if (result.isPresent())
		{
			resultString = result.get();
		}

		// add both the request term type and the result
		dictionary.add(type);
		dictionary.add(resultString);
		return resultString;
	}
	
	//
	// GETS
	//
	public int getStyle()
	{
		return style;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
} // end User Class
