/*Class that represent an Airport*/
import java.util.LinkedList;
import java.util.Queue;

public class Airport {

    private String name;
    private LinkedList<Integer> freeRunways;
    private Queue<Integer> flightsQueue;

    public Airport(String name, int numberOfRunways) {
        //throws IllegalArgumentException if numberOfRunways is negative
        checkValidNumberOfRunways(numberOfRunways);

        this.name = name;
        this.flightsQueue = new LinkedList<Integer>();
        this.freeRunways = new LinkedList<Integer>();
        initFreeRunways(numberOfRunways);
    }

    //Initializes the free runways list with the given number of runways
    private void initFreeRunways(int numberOfRunways) {
        for (int i = 1; i < numberOfRunways+1; i++) {
            freeRunways.add(i);
        }
    }

    //Returns if it's flight turn to land or depart according to the queue
    private boolean isFlightTurn(int flightNumber) {
        return !flightsQueue.isEmpty() && flightsQueue.peek().equals(flightNumber);
    }

    //Departs the given flight form airport, if the flight isn't next in the queue or there is no runway available, waits.
    public synchronized int depart(int flightNumber) {
        flightsQueue.add(flightNumber);
        System.out.println("Flight #" + flightNumber + " scheduled to depart from " + name);
        //while there is no runway available or it's not the flight turn to depart/land, wait
        while (freeRunways.isEmpty() || !isFlightTurn(flightNumber))
        {
            try {
                System.out.println("Flight #" + flightNumber + " is waiting to depart from " + name);
                wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }

        flightsQueue.poll();
        Integer runway = freeRunways.removeFirst();
        System.out.println("Flight #" + flightNumber + " departing from " + name + " on runway #" + runway);
        return runway;
    }

    //Lands the given flight at the airport, if the flight isn't first in the queue or there is no runway available, waits.
    public synchronized int land(int flightNumber) {
        flightsQueue.add(flightNumber);
        System.out.println("Flight #" + flightNumber + " scheduled to land at " + name);
        //while there is no runway available or it's not the flight turn to depart/land, wait
        while (freeRunways.isEmpty() || !isFlightTurn(flightNumber)) {
            try {
                System.out.println("Flight #" + flightNumber + " is waiting to land at " + name );
                wait();
            }catch (InterruptedException e) {e.printStackTrace();}
        }

        flightsQueue.poll();
        Integer runway = freeRunways.removeFirst();
        System.out.println("Flight #" + flightNumber + " landing at " + name + " on runway #" + runway);
        return runway;
    }

    //Frees given runway
    public synchronized void freeRunway(int flightNumber , int runway) {
        System.out.println("Flight #" + flightNumber + " freed runway #" + runway + " at " + name);
        freeRunways.add(runway);
        notifyAll();
    }

    //If the number of runways is non-positive , throws IllegalArgumentException
    private void checkValidNumberOfRunways(int numberOfRunways) {
        if (numberOfRunways <= 0) {
            throw new IllegalArgumentException("Number of runways cannot be Non-positive");
        }
    }

    public String getName() {
        return name;
    }
}
