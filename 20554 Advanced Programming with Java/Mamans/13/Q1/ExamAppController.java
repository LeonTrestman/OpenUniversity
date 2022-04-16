/*Represents controller for Exam App */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ExamAppController {

    public final String invalidQuestionFileMsg = "Invalid Question file exam.txt.\n" +
            "Please place a valid exam.txt in the folder\n" +
            "A valid file contains atleast one question\n" +
            "each question has 5 lines,first line is the question\n" +
            "second line is the right answer\n" +
            "the rest lines are the wrong answers";
    public final String ioExceptionMsg = "There have been IOException\n" +
            "Please check if exam.txt is valid and not corrupted\n" +
            "Then restart the program";

    @FXML
    private TextArea textBox;
    @FXML
    private Button ans1Btn;
    @FXML
    private Button ans2Btn;
    @FXML
    private Button ans3Btn;
    @FXML
    private Button ans4Btn;
    @FXML
    private Button nextBtn;
    @FXML
    private FlowPane ansBtnsPane;

    private List<Button> ansBtns;
    private Exam currentExam;
    private boolean examStarted = false;
    private boolean answerSubmitted = false;


    @FXML
    public void initialize() {
        ansBtns = Arrays.asList(ans1Btn, ans2Btn, ans3Btn, ans4Btn);
        hidaAnsBtns();
        resizeLayoutForStart();
    }

    //starting a new exam
    private void startExam() {

        try {
            currentExam = new Exam();
            resizeLayoutForQuestion();
            showButton(nextBtn);
            updateQuestion();
            presentQuestion();
            examStarted = true;

        } catch (IOException e) {
            textBox.setText(ioExceptionMsg);
        } catch (invalidQuestionFileException e) {
            textBox.setText(invalidQuestionFileMsg);
        }

    }

    //action for next btn, if exam is not started, start it otherwise continue to the
    //next stage in the exam
    @FXML
    private void nextBtnAction(ActionEvent e) {
        if (!examStarted)
            startExam();
        else {
            updateQuestion();
            presentNextExamStage();
        }
    }

    //if exam is over, present ending stage otherwise present next question
    private void presentNextExamStage() {
        if (currentExam.isFinished())
            presentEndingStage();
        else
            presentQuestion();
    }

    //show the user the total result and prompt to restart
    private void presentEndingStage() {
        presentResult();
        promptReset();
    }


    //show ans buttons for reset
    public void promptReset() {
        textBox.appendText("\nWould you like to restart?");
        hideFirstTwoAnsBtns();
        resizeLayoutForReset();
        hideButton(nextBtn);
        present2BtnsForReset();

    }

    //resize the layout for question
    private void resizeLayoutForQuestion() {
        ansBtnsPane.setPrefHeight(300);
        textBox.setPrefHeight(200);
    }

    //resize the layout for starting stage
    private void resizeLayoutForStart() {
        ansBtnsPane.setPrefHeight(60);
        textBox.setPrefHeight(440);
    }

    //resizes the layout for reset prompt
    private void resizeLayoutForReset() {
        ansBtnsPane.setPrefHeight(120);
        textBox.setPrefHeight(380);
    }

    //present results
    private void presentResult() {
        textBox.setText("Your final score is: " + currentExam.getScore());
    }

    //shows all ans buttons
    private void showAnsBtns() {
        for (Button btn : ansBtns)
            showButton(btn);
    }

    //hides all ans buttons
    private void hidaAnsBtns() {
        for (Button btn : ansBtns)
            hideButton(btn);
    }

    //disables (without hiding) all ans buttons
    private void disableAnsBtns() {
        for (Button btn : ansBtns)
            btn.setDisable(true);
    }


    //present 2 buttons for reset (yes and no buttons)
    private void present2BtnsForReset() {
        showButton(ans3Btn);
        showButton(ans4Btn);
        ans3Btn.setText("Yes");
        ans4Btn.setText("No");
    }

    //disables a button
    private void hideButton(Button btn) {
        btn.setDisable(true);
        btn.setVisible(false);
        btn.setManaged(false);
    }

    //enables a button
    private void showButton(Button btn) {
        btn.setDisable(false);
        btn.setVisible(true);
        btn.setManaged(true);
    }

    //hide first two buttons
    private void hideFirstTwoAnsBtns() {
        hideButton(ans1Btn);
        hideButton(ans2Btn);
    }

    //updates current exam question
    private void updateQuestion() {
        currentExam.updateCurrentQuestion();
    }

    //presents a question
    private void presentQuestion(){
        loadCurrentQuestion();
        setAnswerSubmitted(false);
        showAnsBtns();
    }

    //present current question in exam
    private void loadCurrentQuestion() {
        loadQuestionText();
        loadBtnsAnswers();
    }

    //updates all ans-btns text with answers
    private void loadBtnsAnswers() {
        for (int i = 0; i < ansBtns.size(); i++) {
            ansBtns.get(i).setText(currentExam.getCurrentQuestion().getAllAnswers().get(i));
        }
    }

    //updates the question text
    private void loadQuestionText() {
        textBox.setText(currentExam.getCurrentQuestion().getQuestion());
    }

    //check if answer matches
    private boolean isValidAnsSubmission(ActionEvent e) {
        String validAns = currentExam.getCurrentQuestion().getValidAnswer();
        return validAns.equals(getClickedBtnText(e));
    }

    //returns String of button text
    private String getClickedBtnText(ActionEvent e) {
        return ((Button) e.getSource()).getText();
    }

    //set questionSubmitted
    private void setAnswerSubmitted(boolean answerSubmitted) {
        this.answerSubmitted = answerSubmitted;
    }

    //resolves an action according to the submission of the buttons and the state of the game
    @FXML
    private void submitAnswer(ActionEvent e) {
        //game is Finished handle reset submission
        if (currentExam.isFinished())
            resolveResetSubmission(e);
        //game Isn't Finished, handle answer submission
        else if (!answerSubmitted)
            resolveAnsSubmission(e);
    }

    //resolve answer submission
    private void resolveAnsSubmission(ActionEvent e) {
        updateTextWithResult(e);
        currentExam.updateScore(isValidAnsSubmission(e));
        setAnswerSubmitted(true);
        disableAnsBtns();
    }

    //updates result text according result of answer
    private void updateTextWithResult(ActionEvent e) {
        textBox.appendText("\nYou've picked: " + getClickedBtnText(e));
        if (isValidAnsSubmission(e))
            textBox.appendText("\nYou're right");
        else
            textBox.appendText("\nYou're Wrong\nThe correct answer is: "
                    + currentExam.getCurrentQuestion().getValidAnswer());
    }

    //Resolves reset submission, clicking on yes resets the game
    //Clicking on no exits the game, otherwise do nothing
    private void resolveResetSubmission(ActionEvent e) {
        switch (getClickedBtnText(e)) {
            case "Yes":
                startExam();
                break;
            case "No":
                System.exit(0);
                break;
        }
    }
}
