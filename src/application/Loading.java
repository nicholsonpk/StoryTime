/**
 * This class loads a CSV file into an arrayList of StoryClips to send back.
 */
package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author nicholsonpk
 *
 */
public class Loading {

	/**
	 * @param storyClipArray 
	 * @param args
	 */
 public ArrayList<StoryClip> loadThis(ArrayList<StoryClip> storyClipArray, int CSVValue)
 {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Img-" + CSVValue + ".csv"));
			
			String line = null;
			Scanner scanner = null;
			
			// Empties any current story clips to make way for this numbers data
			storyClipArray.clear();
			
			// temp variables to store data before setAll()
			String headerText = "";
			
			int styleReq = 0;
			int speedReq = 0;
			int difficulty = 0;
			
			String bodyText = "";
			
			String choice1Text = "";
			int choice1Style = 0;
			int choice1Speed = 0;
			
			String choice2Text = "";
			int choice2Style = 0;
			int choice2Speed = 0;
			
			String choice3Text = "";
			int choice3Style = 0;
			int choice3Speed = 0;

			// While there is still data to read
			while ((line = reader.readLine()) != null) {
				scanner = new Scanner(line);
				scanner.useDelimiter(",");
				
				// Get one storyClip to variables, 
				// then to the new clip
				// then storyClipToFields();
				while (scanner.hasNext()) {
					
					// Check which image 
					// Check which line
					// Check that it is the current StoryClip
					String totalLine = scanner.nextLine();
					int lineNumber = Character.getNumericValue(totalLine.charAt(3));
					int storyClipNumber = Character.getNumericValue(totalLine.charAt(4));
					
					if (lineNumber == 0)  									// Header
					{
						headerText = totalLine.substring(5);
					} else if (lineNumber == 1) { 							// Requirements
						String[] data = totalLine.substring(5).split(",");
						styleReq = Integer.parseInt(data[0]);
						speedReq = Integer.parseInt(data[1]);
						
					} else if (lineNumber == 2) { 							// Difficulty
						difficulty = Integer.parseInt(totalLine.substring(5));
						
					} else if (lineNumber == 3) { 							// Main Text
						bodyText = totalLine.substring(5);
						
					} else if (lineNumber == 4) { 							// Choice 1
						String[] data0 = totalLine.substring(5).split(",");
						choice1Text = data0[0];
						choice1Style = Integer.parseInt(data0[1]);
						choice1Speed = Integer.parseInt(data0[2]);
						
					} else if (lineNumber == 5) { 							// Choice 2
						String[] data1 = totalLine.substring(5).split(",");
						choice2Text = data1[0];
						choice2Style = Integer.parseInt(data1[1]);
						choice2Speed = Integer.parseInt(data1[2]);
						
					} else if (lineNumber == 6) { 							// Choice 3
																			// and last of this
																			// StoryClip
						String[] data2 = totalLine.substring(5).split(",");
						choice3Text = data2[0];
						choice3Style = Integer.parseInt(data2[1]);
						choice3Speed = Integer.parseInt(data2[2]);
						
						// Create new StoryClip
						storyClipArray.add(new StoryClip(storyClipNumber));
						
						// Set this all to a storyClip
						storyClipArray.get(storyClipNumber).setAll(storyClipNumber, headerText,
								styleReq, speedReq,	difficulty,
								bodyText,
								choice1Text, choice1Style, choice1Speed,
								choice2Text, choice2Style, choice2Speed,
								choice3Text, choice3Style, choice3Speed);
					}
				}
			}
			
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return storyClipArray;
	}
}
