/*Class that represent a question */

import java.util.ArrayList;
import java.util.Collections;


public class Question {

    private String question;
    private String validAnswer;
    private ArrayList<String> allAnswers;

    public Question(String question, String validAns, String wrongAns1,String wrongAns2, String wrongAns3) {
        this.question = question;
        this.validAnswer = validAns;
        this.allAnswers = new ArrayList<>();
        addAllAnswersToAnsList(validAns,wrongAns1,wrongAns2,wrongAns3);
        Collections.shuffle(allAnswers);
    }

    private void addAllAnswersToAnsList(String... ans){
        for (String answer:ans)
           this.allAnswers.add(answer);
    }

    public String getQuestion() {
        return question;
    }

    public String getValidAnswer() {
        return validAnswer;
    }

    public ArrayList<String> getAllAnswers() {
        return allAnswers;
    }

}
