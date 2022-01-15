
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.swing.*;
import java.util.Random;

public class SampleController {

    @FXML
    private Button change;

    @FXML
    private Button stop;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    private Random r = new Random();
    private int leftTurns = 2;

    @FXML
    public void initialize() {
        generateCards();
    }

    private void generateCards() {
        l1.setText(r.nextInt(100) + 1 + "");
        l2.setText(r.nextInt(100) + 1 + "");
        l3.setText(r.nextInt(100) + 1 + "");
    }

    @FXML
    public void change(ActionEvent event) {
        if (leftTurns > 0) {
            generateCards();
            leftTurns--;
        }else
            stop(event);
    }

    @FXML
    public void stop(ActionEvent event) {
        int sum = Integer.parseInt(l1.getText())+ Integer.parseInt(l2.getText()) + Integer.parseInt(l3.getText());
        JOptionPane.showMessageDialog(null, "Final score: " + sum );
    }


}
