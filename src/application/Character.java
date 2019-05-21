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
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author nicholsonp
 *
 */
public class Character{

	public int style;
	public int speed;
	
	public ArrayList<String> dictionary = new ArrayList<String>();
	
	public Character()
	{
		style = 0;
		speed = 0;
		
		dictionary.add(""); // first location is users name
	}
	
	public void loadCharacter(String username) throws IOException {
		
		username = "kingphilip";
		
		// ask user to pick character
		
		
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
	} // end loadCharacter
	
	
	public void saveCharacter() throws IOException {
		
		String filename = dictionary.get(0) + ".csv";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		String line = null;
		
		line  = dictionary.get(0);
		writer.write(line);
		writer.newLine();
		
		line  = Integer.toString(style) + "," + Integer.toString(speed);
		writer.write(line);
		writer.newLine();
		
		for (int i = 1; i < dictionary.size(); i+=2)
		{
			line  = dictionary.get(i) + "," + dictionary.get(i+1);
			writer.write(line);
			writer.newLine();
		}
		
		writer.close();
	} // end saveCharacter
	
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
			String newTerm = askForTerm(type);
			return newTerm;
		}
	}
	
	private String askForTerm(String type)
	{
		String output = JOptionPane.showInputDialog(null, "Enter a new " + type + ":", "Add Term", JOptionPane.OK_OPTION);
		dictionary.add(type);
		dictionary.add(output);
		return output;
	}
	
} // end character Class
