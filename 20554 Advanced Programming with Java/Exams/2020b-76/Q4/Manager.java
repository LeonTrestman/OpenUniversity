import java.util.ArrayList;
import java.util.List;

public class Manager {
    List<WorkThreads> workThreads ;
    boolean readyToProceed;
    int numofThreads;

    public Manager(int numofThreads) {
        workThreads = new ArrayList<>();
        this.numofThreads = numofThreads;
        this.readyToProceed = false;
    }

   public synchronized void requestToContinue(WorkThreads worker) {

        if (!(workThreads.contains(worker))){
            workThreads.add(worker);
            if (workThreads.size() == numofThreads)
                readyToProceed = true;
        }

       while (workThreads.contains(worker) && !readyToProceed) {
           try {
               wait();

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }

    public synchronized void finishedIteration(WorkThreads worker, int i) {
        System.out.println("Worker " + worker.getId() + " is finished iteration " + i);
        if (workThreads.size() == numofThreads) {
            workThreads.clear();
            readyToProceed = false;
        }
        notifyAll();
    }
}
