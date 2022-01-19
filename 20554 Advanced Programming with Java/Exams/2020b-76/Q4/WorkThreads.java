import java.util.Random;

public class WorkThreads extends Thread {
    Manager m;

    public WorkThreads(Manager m) {
        this.m = m;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10; i++) {
            m.requestToContinue(this);
            try {
                sleep(1000 * new Random().nextInt(3)); //simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m.finishedIteration(this,i);

        }
    }


}
