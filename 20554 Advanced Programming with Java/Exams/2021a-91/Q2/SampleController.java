
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class SampleController {

    private int matSize = 5;
    private int numsStored = 0;
    private int firstNum;
    private int secondNum;
    private int thirdNum;
    private int correctAnswers =0;

    @FXML
    private GridPane gridPane;
    Button[][] buttons = new Button[matSize][matSize];

    @FXML
    public void initialize() {

        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setText(1 + j + (i * 5) + "");
                buttons[i][j].setPrefSize(gridPane.getPrefWidth() / matSize, gridPane.getPrefHeight() / matSize);
                gridPane.add(buttons[i][j], j, i);

                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        int numClicked = Integer.parseInt(((Button) event.getSource()).getText());
                        if (numsStored == 0) {
                            firstNum = numClicked;
                            numsStored++;
                        } else if (numsStored == 1) {
                            secondNum = numClicked;
                            numsStored++;
                            System.out.println(firstNum + " " + secondNum);
                        } else if (numsStored == 2) {
                            thirdNum = numClicked;
                            numsStored++;
                        }

                        if (numsStored == 3) {
                            numsStored = 0; //reset for next calculation
                            if (firstNum + secondNum != thirdNum) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Failure");
                                alert.setHeaderText(firstNum + " + " + secondNum + " != " + thirdNum);
                                alert.showAndWait();
                            } else {
                                correctAnswers++;
                                for (int i = 0; i < matSize; i++) {
                                    for (int j = 0; j < matSize; j++) {
                                        if (buttons[i][j].getText().equals(firstNum + "") ||
                                                buttons[i][j].getText().equals(secondNum + "") ||
                                                    buttons[i][j].getText().equals(thirdNum + "")) {
                                            buttons[i][j].setDisable(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    @FXML
    private void onFinishClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You finished!");
        alert.setHeaderText(correctAnswers + " correct answers");
        alert.showAndWait();
    }
}
