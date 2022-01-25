
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleController {

    private Random rand;
    private int matSize = 10;
    private Button[][] buttons;
    private int[][] numMatrix;
    private List<Integer> numbersRevealed;
    int numOfTries;

    @FXML
    private GridPane grid;


    @FXML
    public void initialize() {
        numbersRevealed = new ArrayList<>();
        numOfTries= 0;
        numMatrix = new int[matSize][matSize];
        rand = new Random();
        buttons = new Button[matSize][matSize];
        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                //the number matrix
                numMatrix[i][j] = rand.nextInt(20) + 1;
                //buttons
                buttons[i][j] = new Button();

                buttons[i][j].setId("" + j + i);
                buttons[i][j].setPrefSize(grid.getPrefWidth() / matSize, grid.getPrefHeight() / matSize);
                grid.add(buttons[i][j], i, j);

                //event handler
                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        numOfTries++;
                        String btnId = ((Button) event.getSource()).getId();
                        for (int i = 0; i < matSize; i++) {
                            for (int j = 0; j < matSize; j++) {
                                if (buttons[i][j].getId().equals(btnId)) {
                                    buttons[i][j].setText(numMatrix[i][j] + "");
                                    buttons[i][j].setDisable(true);

                                    if (numbersRevealed.contains(numMatrix[i][j])) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("End");
                                        alert.setHeaderText("Congratulations!");
                                        alert.setContentText("You won in " + numOfTries + " tries!");
                                        alert.showAndWait();
                                        resetGame();
                                    }else {
                                        numbersRevealed.add(numMatrix[i][j]);
                                    }


                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void resetGame(){
        numOfTries = 0;
        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                //reset the buttons
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                //reset the number matrix
                numMatrix[i][j] = rand.nextInt(20) + 1;
                //reset the list
                numbersRevealed.clear();
            }
        }
    }


}
