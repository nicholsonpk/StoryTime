/**
 * This class stores an entire storyClip.
 * Each image will have multiple storyClips in
 * order to give the user a unique experience
 * each time they play the game.
 */
package application;

/**
 * @author nicholsonpk
 * 5/10/2019
 *
 */
public class StoryClip {
	
	private int ID = 0;
	private String headerText = "Header";
	
	private int styleReq = 1;
	private int speedReq = 1;
	private int difficulty = 1;
	
	private String bodyText = "Body";
	
	private String choice1Text = "Choice 1";
	private int choice1Style = 0;
	private int choice1Speed = 0;
	
	private String choice2Text = "Choice 2";
	private int choice2Style = 0;
	private int choice2Speed = 0;
	
	private String choice3Text = "Choice 3";
	private int choice3Style = 0;
	private int choice3Speed = 0;
	
	public StoryClip(int sentID)
	{
		ID = sentID;
	}
	
	/*/
	 * ONE SET TO RULE THEM ALL
	 * 
	 * This is called from Load button and from Save before using the Gets
	 */
	
	public void setAll(int sentID, String sentTitle,
				int sentStyleReq, int sentSpeedReq,	int sentDifficulty,
				String sentText,
				String sentChoice1Text, int sentChoice1Style, int sentChoice1Speed,
				String sentChoice2Text, int sentChoice2Style, int sentChoice2Speed,
				String sentChoice3Text, int sentChoice3Style, int sentChoice3Speed)
	{
		ID = sentID;
		headerText = sentTitle;
		
		styleReq = sentStyleReq;
		speedReq = sentSpeedReq;
		difficulty = sentDifficulty;
		
		bodyText = sentText;
		
		choice1Text = sentChoice1Text;
		choice1Style = sentChoice1Style;
		choice1Speed = sentChoice1Speed;
		
		choice2Text = sentChoice2Text;
		choice2Style = sentChoice2Style;
		choice2Speed = sentChoice2Speed;
		
		choice3Text = sentChoice3Text;
		choice3Style = sentChoice3Style;
		choice3Speed = sentChoice3Speed;
	}
	
	public void setText(String inputText)
	{
		bodyText = inputText;
	}
	
	/*
	 * GETS
	 */
	public String getIDString()
	{
		return Integer.toString(ID);
	}
	
	public String getTitleText()
	{
		return headerText;
	}
	
	public int getStyleReq()
	{
		return styleReq;
	}
	
	public int getSpeedReq()
	{
		return speedReq;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public String getText()
	{
		return bodyText;
	}
	
	public String getChoice(int choiceNum)
	{
		String returnText = "ERROR";
		
		switch (choiceNum)
		{
		case 0:
			//returnText = choice1Text + "," + choice1Style + "," + choice1Speed;
			return choice1Text;
		case 1:
			//returnText = choice2Text + "," + choice2Style + "," + choice2Speed;
			return choice2Text;
		case 2:
			//returnText = choice3Text + "," + choice3Style + "," + choice3Speed;
			return choice3Text;
		default:
			return returnText;
		}
	}
	
	
	// Returns a choices Style value
	public int getStyleInt(int choiceNum)
	{
		switch (choiceNum)
		{
		case 0:
			return choice1Style;
		case 1:
			return choice2Style;
		case 2:
			return choice3Style;
		default:
			return 99;
		}
	}
	
	// Returns a choices Speed value
	public int getSpeedInt(int choiceNum)
	{
		switch (choiceNum)
		{
		case 0:
			return choice1Speed;
		case 1:
			return choice2Speed;
		case 2:
			return choice3Speed;
		default:
			return 99;
		}
	}
}
