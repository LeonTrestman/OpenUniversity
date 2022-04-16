/*Represents controller for simple calculator  */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SimpleCalculatorController {

    @FXML
    private Label numberLabel;
    @FXML
    private Label expressionLabel;

    private SimpleCalculator simpleCalc;
    private boolean isError;

    @FXML
    public void initialize() {
        simpleCalc = new SimpleCalculator();
        resetCalculation();
    }

    //Executes a click for the corresponding button
    //if there is an error, only reset button works
    @FXML
    private void OnClick(ActionEvent event) {
        String input = ((Button) event.getSource()).getText();

        //No error
        if (!isError) {
            switch (input) {
                case "0":
                    onZeroClick();
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    onNumberClick(input);
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "=":
                    onOperatorClick(input);
                    break;
                case "Del":
                    onDelClick();
                    break;
                case ".":
                    onDecimalPointClick();
                    break;
                case "Clear":
                    onClearClick();
                    break;
                case "Reset":
                    onResetClick();
                    break;
                case "+/-":
                    onPlusMinusClick();
                    break;
            }
        } //Error only reset works
        else if (input.equals("Reset")) {
            onResetClick();
        }
    }

    //For Number Button ,adds a number character to the number If it's valid to insert a number character
    private void onNumberClick(String number) {
        if (!isValidToInsertNumber())
            resetCalculation();
        insertNumber(number);
    }

    //For Zero Button ,adds zero to the number if valid otherwise if it's a new calculation
    //resets expression with inserting zero
    private void onZeroClick() {
        if (!isValidToInsertNumber())
            resetCalculation();
        if (!isNumberLabelOpeningZero())
            insertNumber("0");
    }

    //returns if it's valid to insert a number
    //valid if empty expression or expression has an operator
    private boolean isValidToInsertNumber() {
        return isThereMathematicalOperator() || expressionLabel.getText().isEmpty();
    }

    //For decimal point Button
    //Adds a decimal point if it's valid to insert a decimal point
    private void onDecimalPointClick() {
        if (!isValidToInsertNumber())
            resetCalculation();
        if (!isNumberContainingDot())
            insertDecimalPoint();
    }

    //inserts Decimal Point to the number if it's valid to insert it
    private void insertDecimalPoint() {
        //if the expression is empty, insert a 0 before the decimal point
        if (isNumberLabelEmptyOrMinus())
            numberLabel.setText(numberLabel.getText() + "0.");
        else
            numberLabel.setText(numberLabel.getText() + ".");
    }

    //returns if the expression contains a dot
    private boolean isNumberContainingDot() {
        return !numberLabel.getText().isEmpty() && numberLabel.getText().contains(".");
    }

    //inserts a number or a decimal point to the number
    private void insertNumber(String number) {
        numberLabel.setText(numberLabel.getText() + number);
    }

    //passes the number to the calculator
    private void passNumToCalculator() {
        simpleCalc.addNumber(Double.parseDouble(numberLabel.getText()));
    }


    //for Clear Button ,clears the number
    private void onClearClick() {
        clearNumber();
    }

    //for del button,deletes the last character of the number
    private void onDelClick() {
        if (!numberLabel.getText().isEmpty())
            deleteLastChar();
    }

    //deletes the last character from number (numberlabel)
    private void deleteLastChar() {
        removeLastNumberLabelChar();
        //special case for dot at start (0. or -0.) to delete the fraction
        if (isNumberLabelOpeningZero())
            removeLastNumberLabelChar();
    }

    //returns if number label is -0 or 0
    private boolean isNumberLabelOpeningZero() {
        return (numberLabel.getText().equals("0") || numberLabel.getText().equals("-0"));
    }

    //For PlusMinus Button
    //changes the sign of the number
    private void onPlusMinusClick() {
        if (numberLabel.getText().isEmpty())
            numberLabel.setText("-");
        else
            toggleNumSign();
    }

    //For Operator Buttons,adds or changes the operator if it's valid to do so
    //valid if the expression is not empty or the number is not empty
    private void onOperatorClick(String operator) {
        if (isOperatorChangeOnly()) {
            passOperatorToCalculator(operator);
            updateView();
        } else if (!isNumberLabelEmptyOrMinus()) {
            try {
                passNumToCalculator();
                CalculateExpression();
                passOperatorToCalculator(operator);
                updateView();
            } catch (Exception e) {
                raiseException(e);
            }
        }
    }

    //returns if the operator is change only,meaning there is an expression but no number
    private boolean isOperatorChangeOnly() {
        return isNumberLabelEmptyOrMinus() && !expressionLabel.getText().isEmpty();
    }


    //passes the operator to the calculator
    private void passOperatorToCalculator(String operator) {
        simpleCalc.setOperator(operator);
    }

    //calculates the expression
    private void CalculateExpression() throws DivisionByZeroException {
        simpleCalc.calculate();
    }

    //updates calculator view
    private void updateView() {
        clearNumber();
        updateExpression();
    }

    //update calculator view with error message
    private void updateViewWithError(String errorMessage) {
        expressionLabel.setText(errorMessage);
        numberLabel.setText("Press RESET to continue");
    }

    //updates the expression
    private void updateExpression() {
        if (isThereMathematicalOperator())
            updateExpressionWithOperator();
        else
            updateExpressionWithoutOperator();
    }

    //Returns if there is a mathematical operator in the calculator
    //Mathematical operators are +,-,*,/
    private boolean isThereMathematicalOperator() {
        try {
            String operator = simpleCalc.getOperator();
            return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
        } catch (Exception e) {
            return false;
        }
    }

    //updates expression with result without operator
    private void updateExpressionWithoutOperator() {
        expressionLabel.setText(simpleCalc.getResultNumber() + "");
    }

    //updates expression with result and operator
    private void updateExpressionWithOperator() {
        expressionLabel.setText(simpleCalc.getResultNumber() + simpleCalc.getOperator());
    }

    //removes the last character of the expression
    private void removeLastNumberLabelChar() {
        numberLabel.setText(numberLabel.getText().substring(0, numberLabel.getText().length() - 1));
    }

    //For Reset button,resets the calculator
    private void onResetClick() {
        resetCalculation();
    }

    //switches positive to negative and vice versa
    private void toggleNumSign() {
        if (numberLabel.getText().charAt(0) == '-')
            numberLabel.setText(numberLabel.getText().substring(1));
        else
            numberLabel.setText("-" + numberLabel.getText());
    }

    //clears the number
    private void clearNumber() {
        numberLabel.setText("");
    }

    //resets the calculator
    private void resetCalculation() {
        isError = false;
        simpleCalc.reset();
        resetView();
    }

    //resets the view
    private void resetView() {
        if (isNegativeSign())
            numberLabel.setText("-");
        else
            numberLabel.setText("");
        expressionLabel.setText("");
    }

    //returns if the number label is negative or not
    private boolean isNegativeSign() {
        return !numberLabel.getText().isEmpty() && numberLabel.getText().charAt(0) == '-';
    }


    //updates view with error message
    private void raiseException(Exception e) {
        isError = true;
        updateViewWithError(e.getMessage());
    }

    //returns if there is number in string label
    private boolean isNumberLabelEmptyOrMinus() {
        return numberLabel.getText().isEmpty() || numberLabel.getText().equals("-");
    }

}
