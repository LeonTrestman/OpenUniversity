/*Class represent a cell in game*/

import java.util.Random;

public class Cell {
    private boolean isAlive;

    //Alive or Dead cell
    public Cell(){
        this.isAlive = getAliveOrDead();
    }

    public Cell(boolean isAlive){
        this.isAlive = isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    //returns random alive or dead boolean
    private boolean getAliveOrDead(){
        return new Random().nextBoolean();
    }


}
