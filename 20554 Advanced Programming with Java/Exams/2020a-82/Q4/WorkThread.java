import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WorkThread extends Thread {
    private int[] vec;
    private int id;
    private int result;
    public static int threadTurn;
    public static Lock lock = new ReentrantLock();
    public static Condition threadTurnCond = lock.newCondition();

    public WorkThread(int[] vec, int id) {
        this.vec = vec;
        this.id = id;
    }

    public synchronized int process(int[] vec, int id) {
        int result = 0;
        System.out.println("Task " + id);
        for (int i = 0; i < vec.length; i++) {
            vec[i] = vec[i] + 1;
            result = result + vec[i];
        }
        return result;

    }

    @Override
    public void run() {
        super.run();
        lock.lock();
        try {
            while (WorkThread.threadTurn != id) {
                try {
                    threadTurnCond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            result = process(vec, id);
            System.out.println("Task " + id + " result: " + result);

        } finally {
            threadTurn++;
            threadTurnCond.signalAll();
            lock.unlock();
        }

    }


}
