public class Tester {

    public static void main(String[] args) {
        int[] vec = {1,2,3,4};
        WorkThread[] workers = new WorkThread[3];
        for (int i = 0; i < 3; i++) {
            workers[i] = new WorkThread(vec, i);
        }

        for (int i = 0; i < 3; i++) {
            workers[i].start();
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main thread done");
    }
}
