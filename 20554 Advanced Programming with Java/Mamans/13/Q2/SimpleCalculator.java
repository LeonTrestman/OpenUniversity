/*represent the simple calculator*/

public class SimpleCalculator {

    private Double resultNumber;
    private Double newNumber;
    private String operator;

    public SimpleCalculator() {
        reset();
    }

    //adds a number to the simple calculator
    public void addNumber(Double number) {
        if (resultNumber == null)
            resultNumber = number;
        else
            newNumber =  number;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    //returns the result as null if null, int if int, double if double
    public Number getResultNumber() {
        //if null return null
        if (resultNumber == null )
            return null;
        else if (resultNumber.doubleValue() == resultNumber.intValue())
            return resultNumber.intValue();
        else
            return resultNumber;
    }

    public String getOperator() {
        return operator;
    }

    //reset the calculator,nullifies all the variables
    public void reset() {
        resultNumber = null;
        newNumber = null;
        operator = null;
    }

    //calculates the two existing numbers according to the operator
    public void calculate() throws DivisionByZeroException {
        if (resultNumber != null && newNumber != null && operator != null) {
            switch (operator) {
                case "+":
                    resultNumber = resultNumber + newNumber;
                    break;
                case "-":
                    resultNumber = resultNumber - newNumber;
                    break;
                case "*":
                    resultNumber = resultNumber * newNumber;
                    break;
                case "/":
                    //if the new number is 0 throw exception
                    if (newNumber == 0)
                        throw new DivisionByZeroException();
                    else
                        resultNumber = resultNumber / newNumber;
                    break;
                case "=":
                    resultNumber = newNumber;
                    break;
            }
        }
    }

}
