import java.util.ArrayList;

import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {

	/***
	 * Method to do optical mark reading on page image.  Return an AnswerSheet object representing the page answers.
	 * @param image
	 * @return
	 */
	public AnswerSheet processPageImage(PImage image) {
		ArrayList<String> answers = new ArrayList<String>();
		
		int width = 194;
		int height = 37;
		int initialXCorner = 122;
		int initialYCorner = 459;
		int xIncrement = 283;
		
		image.loadPixels();
		int[] initialPixels = image.pixels;
		
		int imageHeight = image.height;
		int imageLength = image.width;
		
		int[][] pixels = new int[imageHeight][imageLength];
		int arrCounter = 0;
		
		for(int i = 0; i < pixels.length; i++){
			for(int j = 0; j < pixels[0].length; j++){
				int temp = initialPixels[arrCounter];
				pixels[i][j] = temp&255;
				arrCounter++;
			}
		}
		
		//alternates over 5 columns on answer sheet
		for(int column = 0; column < 4; column++){
			int xCorner = initialXCorner + (column*xIncrement);
			//alternates over each of 25 problems on each column on answer sheet
			for(int row = 0; row < 25; row++){
				int yCorner = initialYCorner + (row*height);
				//System.out.println("Checking rectangle from " + xCorner + ", " + yCorner + " to " + (xCorner+width) + ", " + (yCorner+height));
				String thisAnswer = determineBubble(xCorner, yCorner, width, height, pixels);
				
				answers.add(thisAnswer);
			}
		}
		
		AnswerSheet currentPageAnswers = new AnswerSheet(answers);
		
		return currentPageAnswers;
	}
	
	public String determineBubble(int r, int c, int width, int height, int[][] pixels){
		String[] answerChoices = {"A", "B", "C", "D", "E"};
		int sectionWidth = (int)(width/5.0);
		int darkest = 0;
		int currentSection = 0;
		int mostDarkPixels = -1;
		//System.out.println(pixels.length + ", " + pixels[0].length);
		
		for(int s = r; s < r + width; s += sectionWidth){
			int numDarkPixels = 0;
			for(int i = r; i < r+height; i++){
				for(int j = s; j < s+sectionWidth; j++){
					numDarkPixels += isDark(i, j, pixels);
				}
			}
			if(numDarkPixels > mostDarkPixels){
				mostDarkPixels = numDarkPixels;
				darkest = currentSection;
			}
			currentSection++;
			if (currentSection > 4)return answerChoices[darkest];
		}
		return answerChoices[darkest];
	}
 
	private int isDark(int r, int c, int[][] arr) {
		int thisPixel = arr[r][c];
		if(thisPixel < (255/2.0))return 1;
		return 0;
	}
	
}


	
