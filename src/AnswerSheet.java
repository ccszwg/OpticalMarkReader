import java.util.ArrayList;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private static int ID = 0;
	private ArrayList<String> answers = new ArrayList<String>();
	
	public AnswerSheet(ArrayList<String> answerList){
		this.ID = ID;
		this.answers = answerList;
		ID++;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public ArrayList<String> getAnswers(){
		return this.answers;
	}
	
	public String getAnswer(int i){
		return this.answers.get(i);
	}
}
