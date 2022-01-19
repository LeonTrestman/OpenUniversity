import java.util.Random;

public class Worker extends Thread {

    private Manager manager;
    private Random rand;


    public Worker(Manager manager) {
        this.manager = manager;
        rand= new Random();

    }

    @Override
    public void run() {
        super.run();
        System.out.println("Worker " + Thread.currentThread().getName() + " is running");
        try {
            while (true) {
                sleep(rand.nextInt(10)  * 1000);
                manager.permit(this);
                System.out.println("Worker " + this.getName() + " is taking a break");
                sleep(rand.nextInt(10) * 1000);
                System.out.println("Worker " + this.getName() + " is back");
                manager.back(this);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
