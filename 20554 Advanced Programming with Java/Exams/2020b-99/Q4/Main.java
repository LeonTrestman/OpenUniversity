
public class Main {

    public static void main(String[] args) {

        Manager m = new Manager(10,3,5);
        Worker[] workers = new Worker[10];
        for (int i = 0; i < 10; i++) {
            workers[i] = new Worker(m);
        }

        for (Worker w : workers) {
            w.start();
        }

    }

}
