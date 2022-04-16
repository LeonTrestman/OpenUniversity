/*Main class used to test the program*/
import java.util.Random;
import java.util.Scanner;

public class Main {
     private static int  numOfElements,numOfThreads;
     private static int[] array;

    public static void main(String[] args) {
        getValidUserInput();
        generateArray();
        System.out.println("Before sorting: ");
        printArray();
        mergeArray();
        System.out.println("After sorting: ");
        printArray();
    }

    //Merges the array with Merge threads
    private static void mergeArray() {
        MergeThreadExecutor executor = new MergeThreadExecutor(numOfThreads,array);
        array = executor.getSortedArray();
    }

    //Generates array with numOfElements elements values between 1 and 100
    private static void generateArray() {
        array = new int[numOfElements];
        for (int i = 0; i < numOfElements; i++) {
            array[i] = new Random().nextInt(100);
        }
    }

    //Prints the array
    private static void printArray() {
        for(int element : array){
            System.out.print(element + "\t");
        }
        System.out.println("\n");
    }

    //Gets and validates user input , throws exception if input is invalid
    private static void getValidUserInput() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the number of elements in the array: ");
            numOfElements = scanner.nextInt();
            System.out.println("Enter the number of threads: ");
            numOfThreads = scanner.nextInt();
            if (numOfElements < 0 || numOfThreads < 0) {
                throw new Exception("Invalid input");
            }
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

}
