import java.util.HashMap;
import java.util.Map;


public class Manager {

    private int numofthreads;
    private int max; //num of max non-active threads
    private int currentNonActiveThreads;
    private Map<Worker, Long> lastTime;


    private static int x; //x miliseconds

    public Manager(int numofthreads, int max, int x) {
        this.numofthreads = numofthreads;
        this.max = max;
        this.x = x;
        this.lastTime = new HashMap<>();
        currentNonActiveThreads = 0;
    }

    public synchronized void permit(Worker worker)  {

        try {
            while ( currentNonActiveThreads == max){
                System.out.println(worker.getName() + " is waiting for someone to finish");
                wait();
            }
            while ( lastTime.containsKey(worker) && System.currentTimeMillis() - lastTime.get(worker) < x){
                System.out.println(worker.getName() + " needs to wait " );
                wait(x -(System.currentTimeMillis() - lastTime.get(worker)) );
            }


        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        lastTime.put(worker, System.currentTimeMillis());
        currentNonActiveThreads++;
    }

    public synchronized void back(Worker worker) {
        currentNonActiveThreads--;
        notifyAll();
    }



}
