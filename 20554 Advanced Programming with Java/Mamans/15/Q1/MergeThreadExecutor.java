/*Class of the Executor of Merge threads*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MergeThreadExecutor {

    private int numThreads;
    private ArrayPool arrayPool;

    public MergeThreadExecutor(int numOfThreads, int[] unSortedArray) {
        this.numThreads = numOfThreads;
        this.arrayPool = new ArrayPool(unSortedArray.length);
        addElementsToArrayPool(unSortedArray);
    }

    //Breaks the array into single int arrays value of the original int in the array
    //and adds it to the array pool
    public void addElementsToArrayPool(int[] unSortedArray) {
        for (int element : unSortedArray) {
            int[] newArrInPool = {element};
            arrayPool.addAndNotify(newArrInPool);
        }
    }

    //Returns the sorted array
    public int[] getSortedArray(){
        sortUsingMergeThreads();
        return arrayPool.extract();
    }

    //Sorts the array using the give number of merge threads
    private void sortUsingMergeThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        while (!arrayPool.isFinished()){
            executor.execute(new MergeThread(arrayPool.extract(), arrayPool.extract(),arrayPool));
        }
        executor.shutdown();
    }


}
