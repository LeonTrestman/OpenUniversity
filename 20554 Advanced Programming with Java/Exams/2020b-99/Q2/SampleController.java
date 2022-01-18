
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class SampleController {


    @FXML
    private TextField firstInputBox;
    @FXML
    private TextField secondInputBox;
    @FXML
    private TextField resultBox;
    @FXML
    String[] operators = {"+", "-", "*"};

    @FXML
    private ComboBox<String> operationBox;



    @FXML
    public void initialize() {
        //setting the combo box
        operationBox.setItems(FXCollections.observableArrayList(operators));
    }

    @FXML
    private void onEqualsClick(ActionEvent event) {
        try {
            double firstnum = Double.parseDouble(firstInputBox.getText());
            double secondnum = Double.parseDouble(secondInputBox.getText());
            String operation = operationBox.getValue();
            if (operation != null){
                switch (operation){
                    case "+":
                        resultBox.setText(String.valueOf(firstnum + secondnum));
                        break;
                    case "-":
                        resultBox.setText(String.valueOf(firstnum - secondnum));
                        break;
                    case "*":
                        resultBox.setText(String.valueOf(firstnum * secondnum));
                        break;
                }
            }


        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter a valid number");
            alert.showAndWait();
        }

    }


}
