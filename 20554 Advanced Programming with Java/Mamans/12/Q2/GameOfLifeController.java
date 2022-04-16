/*Represents controller for GameOfLife */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class GameOfLifeController {

    private final static int PADDING_SIZE = 2;
    private int gameSize = 10; //Change game size here
    private GameOfLifeBoard gameBoard = null;

    @FXML
    private Canvas canvas;
    @FXML
    private Button gameButton;

    public void gameButtonAction(ActionEvent e){
        clearCanvas();
        createNextGeneration(getBoardSize());
        DrawGeneration();
    }

    private int getBoardSize(){
        return  gameSize + PADDING_SIZE;
    }

    private void initGameBoard(int boardSize){
        gameBoard = new GameOfLifeBoard(boardSize,canvas);
        gameButton.setText("Next Generation");
    }

    //if game board doesn't exist creates a new one , else updates the current game board
    private void createNextGeneration(int boardSize){
        if (gameBoard == null)
            initGameBoard(boardSize);
        else
            updateCurrentBoard();
    }

    private void updateCurrentBoard() {
        gameBoard.updateGeneration();
    }

    private void DrawGeneration(){
        gameBoard.draw();
    }

    //clears the canvas
    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
