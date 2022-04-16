/*Class represents the game board*/
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeBoard {

    private final Canvas canvas;
    private final int boardSize;
    private Cell[][] cells;

    public GameOfLifeBoard(int boardSize, Canvas canvas) {
        this.boardSize = boardSize;
        this.canvas = canvas;
        initCells(boardSize);
    }

    public GameOfLifeBoard(GameOfLifeBoard copyGameBoard) {
        this.boardSize = copyGameBoard.getBoardSize();
        this.canvas = copyGameBoard.getCanvas();
        cells = new Cell[boardSize][boardSize];
        copyCells(copyGameBoard.getCells());
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void initCells(int boardSize) {
        cells = new Cell[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++)
                initCell(row, col);
        }
    }

    //if it's a padding cell it will be dead else random cell (dead or alive)
    private void initCell(int row, int col) {
        if (isCellAPadding(row, col))
            cells[row][col] = new Cell(false);
        else
            cells[row][col] = new Cell();
    }

    private boolean isCellAPadding(int row, int col) {
        return row == 0 || col == 0 || row == boardSize - 1 || col == boardSize - 1;
    }


    private void copyCells(Cell[][] copyCells) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++)
                copyCell(copyCells, row, col);
        }
    }

    private void copyCell(Cell[][] copyCells, int row, int col) {
        if (copyCells[row][col].getIsAlive())
            cells[row][col] = new Cell(true);
        else
            cells[row][col] = new Cell(false);
    }


    public void draw() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++)
                drawCell(row, col);
        }
    }

    private void drawCell(int row, int col) {
        double x = col / (double) boardSize * canvas.getWidth();
        double y = row / (double) boardSize * canvas.getHeight();
        double width = canvas.getWidth() / boardSize;
        double height = canvas.getHeight() / boardSize;
        //padding cell
        if (isCellAPadding(row, col))
            drawPaddingCell(x, y, width, height);
        //alive cell
        else if (cells[row][col].getIsAlive())
            drawAliveCell(x, y, width, height);
    }

    private void drawPaddingCell(double x, double y, double width, double height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BEIGE);
        gc.fillRect(x, y, width, height);
    }

    private void drawAliveCell(double x, double y, double width, double height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height);
    }

    public void updateGeneration() {
        GameOfLifeBoard futureBoard = new GameOfLifeBoard(this);
        int borderWithoutPadding = boardSize - 1;
        //starting from 1 skipping the padding
        for (int row = 1; row < borderWithoutPadding; row++) {
            for (int col = 1; col < borderWithoutPadding; col++) {
                updateNextGenerationCell(futureBoard, row, col);
            }
        }
        this.cells = futureBoard.getCells();
    }

    //update cell according to the rules of game of life
    private void updateNextGenerationCell(GameOfLifeBoard futureBoard, int row, int col) {
        int aliveNeighbours = countAliveNeighbours(row, col);
        //Lonely cell dies
        if ((cells[row][col].getIsAlive()) && (aliveNeighbours < 2))
            futureBoard.getCells()[row][col].setAlive(false);
        //Overpopulation cell dies
        else if ((cells[row][col].getIsAlive()) && (aliveNeighbours > 3))
            futureBoard.getCells()[row][col].setAlive(false);
        // A new cell is born
        else if ((!cells[row][col].getIsAlive()) && (aliveNeighbours == 3))
            futureBoard.getCells()[row][col].setAlive(true);
        // Remains the same
    }


    private int countAliveNeighbours(int row, int col) {
        int aliveNeighbours = 0;
        //count all neighbours including the cell itself
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (cells[i + row][j + col].getIsAlive())
                    aliveNeighbours++;

        //subtract itself if alive
        if (cells[row][col].getIsAlive())
            aliveNeighbours--;

        return aliveNeighbours;
    }


}
