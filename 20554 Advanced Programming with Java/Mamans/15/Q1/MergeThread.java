/*Class of Merge Thread*/
public class MergeThread implements Runnable {
    private int[] arr1;
    private int[] arr2;
    private int[] resultArr;
    private ArrayPool pool;

    public MergeThread(int[] arr1, int[] arr2,ArrayPool pool) {
        this.arr1 = arr1;
        this.arr2 = arr2;
        this.resultArr = new int[arr1.length + arr2.length];
        this.pool = pool;
    }

    //Sorts and merges the two arrays into sorted result array,then adds the sorted array to the array pool
    @Override
    public void run() {
        mergeSort();
        pool.addAndNotify(resultArr);
    }

    //Merge two arrays into sorted result array
    private void mergeSort() {
        // i for first array, j for second array, k for result array
        int i = 0, j = 0, k = 0;

        //while there are elements in both arrays
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                resultArr[k] = arr1[i];
                i++;
            } else {
                resultArr[k] = arr2[j];
                j++;
            }
            k++;
        }
        //first array still has elements
        while (i < arr1.length) {
            resultArr[k] = arr1[i];
            i++;
            k++;
        }
        //second array still has elements
        while (j < arr2.length) {
            resultArr[k] = arr2[j];
            j++;
            k++;
        }
    }


}
