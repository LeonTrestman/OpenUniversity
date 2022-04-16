/*Class represent the array pool which is a shared resource for all Merge Threads*/
import java.util.ArrayList;

public class ArrayPool {
    private ArrayList<int[]> arrPool;
    private int numOfElements;
    private static int FirstArr = 0;

    public ArrayPool(int size) {
        this.numOfElements = size;
        this.arrPool = new ArrayList<>();
    }

    //Adds array to the pool of arrays and notifies all waiting threads
    public synchronized void addAndNotify(int[] arr) {
        arrPool.add(arr);
        notifyAll();
    }

    //Extracts first array from the pool of arrays.
    //If the pool is empty, waits until an array is added
    public synchronized int[] extract() {
        //if no array in pool wait
        while (arrPool.isEmpty()) {
            try {wait();} catch (InterruptedException e) {e.printStackTrace();}
        }
        return arrPool.remove(FirstArr);
    }

    //Returns if merge is finished,the merge is finished when the pool
    //Has only one (sorted) array with length equal to the number of elements of original array
    public synchronized boolean isFinished(){
        if(arrPool.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int[] firstArr = arrPool.get(FirstArr);
        return firstArr.length == numOfElements;
    }
}
