import java.util.ArrayList;

import processing.core.PImage;

public class Main {
	public static final String PDF_PATH = "/omrtest.pdf";
	public static OpticalMarkReader markReader = new OpticalMarkReader();
	
	public static void main(String[] args) {
		System.out.println("Welcome!  I will now auto-score your pdf!");
		System.out.println("Loading file..." + PDF_PATH);
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);

		System.out.println("Scoring all pages...");
		scoreAllPages(images);

		System.out.println("Complete!");
		
		// Optional:  add a saveResults() method to save answers to a csv file
	}

	/***
	 * Score all pages in list, using index 0 as the key.
	 * 
	 * NOTE:  YOU MAY CHANGE THE RETURN TYPE SO YOU RETURN SOMETHING IF YOU'D LIKE
	 * 
	 * @param images List of images corresponding to each page of original pdf
	 */
	private static void scoreAllPages(ArrayList<PImage> images) {
		ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();

		// Score the first page as the key
		AnswerSheet key = markReader.processPageImage(images.get(0));
		
		ArrayList<String> results = key.getAnswers();
		
		for(int i = 0; i < results.size(); i++){
			System.out.println((i+1) + ") " + results.get(i));
		}

		for (int i = 1; i < images.size(); i++) {
			PImage image = images.get(i);

			AnswerSheet answers = markReader.processPageImage(image);
			scoredSheets.add(answers);
			
		}
		System.out.println(scoredSheets.size());
		
		String[] columnNames = {"Problem Number", "Correct Answer", "Answer 1", "Answer 2", "Answer 3", "Answer 4", 
				"Answer 5", "Answer 6", "Number Correct", "Number Incorrect", "Percent Correct", "Percent Incorrect"}; 
		
		String[][] finalResults = new String[101][columnNames.length];
		
		for(int i = 0; i < columnNames.length; i++){
			finalResults[0][i] = columnNames[i];
		}
		
		for(int i = 1; i < finalResults.length; i++){
			finalResults[i][0] = Integer.toString(i);
			finalResults[i][1] = key.getAnswer(i);
			finalResults[i][2] = scoredSheets.get(0).getAnswer(i-1);
			finalResults[i][3] = scoredSheets.get(1).getAnswer(i-1);
			finalResults[i][4] = scoredSheets.get(2).getAnswer(i-1);
			finalResults[i][5] = scoredSheets.get(3).getAnswer(i-1);
			finalResults[i][6] = scoredSheets.get(4).getAnswer(i-1);
			finalResults[i][7] = scoredSheets.get(5).getAnswer(i-1);
			
			String answer = key.getAnswer(i-1);
			String[] percentageResults = getStats(i, scoredSheets, answer);
			
			finalResults[i][8] = percentageResults[0];
			finalResults[i][9] = percentageResults[1];
			finalResults[i][10] = percentageResults[2];
			finalResults[i][11] = percentageResults[3];

		}
	}

	private static String[] getStats(int i, ArrayList<AnswerSheet> arr, String answer) {
		int correct = 0;
		String[] answers = new String[arr.size()];
		for(int j = 0; j < arr.size(); j++){
			answers[j] = (arr.get(j).getAnswer(i));
		}
		for(int j = 0; j < answers.length; j++){
			if(answers[j].equals(answer))correct++;
		}
		int incorrect = answers.length-correct;
		int percentCorrect = (int)(correct/(double)answers.length);
		int percentIncorrect = (int)(incorrect/(double)answers.length);
		
		String[] ans = {Integer.toString(correct), Integer.toString(incorrect), Integer.toString(percentCorrect), Integer.toString(percentIncorrect)};

		return ans;
	}
}