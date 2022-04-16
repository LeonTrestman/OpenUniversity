/*Class that represent an Exam */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Exam {

    public static final double FULL_GRADE = 100;
    private double score;
    private int numOfQuestions;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    public Exam() throws IOException,invalidQuestionFileException  {
        if (!isValidNumOfLines())
            throw new invalidQuestionFileException();
        this.score = 0;
        this.questions = new ArrayList<Question>();
        scanQuestions();
        Collections.shuffle(questions);
        this.numOfQuestions = this.questions.size();
    }

    //updates score on valid ans
    public void updateScore(boolean ans){
        if (ans)
            score = score + FULL_GRADE/numOfQuestions;
    }

    public boolean isFinished(){
        return currentQuestion ==null;
    }

    //scans all question from valid file named exam.txt
    private  void scanQuestions() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader("exam.txt"));
            String temp;
            while ((temp = br.readLine()) != null){
                    String question = temp;
                    String validAns = br.readLine();
                    String wrongAns1 = br.readLine();
                    String wrongAns2 = br.readLine();
                    String wrongAns3 = br.readLine();
                    questions.add(new Question(question,validAns,wrongAns1,wrongAns2,wrongAns3));
                }
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public double getScore() {
        return score;
    }

    //update current question with removing it from questions
    public void updateCurrentQuestion(){
        if (questions.isEmpty())
            setCurrentQuestion(null);
        else
            setCurrentQuestion(getRandomExamQuestion());
    }

    //pops question from list questions
    private Question getRandomExamQuestion(){
        Random rand = new Random();
        int i = rand.nextInt(questions.size());
        return questions.remove(i);
    }

    //returns if file has valid number of lines (each question is 5 lines so the total num of
    //lines should be divisible by 5 and not zero (empty file)
    private boolean isValidNumOfLines() throws IOException {
        int numOflines = countFileLines();
        return numOflines % 5 == 0 && numOflines != 0 ;
    }

    //counts all lines in exam.txt
    private int countFileLines() throws IOException {
        int lines = 0;
        BufferedReader br = new BufferedReader(new FileReader("exam.txt"));
        while (br.readLine() != null)
            lines++;
        return lines;
    }


}
