/*Class that represent a Flight*/
import java.util.Random;

public class Flight extends Thread {
    private int flightNumber;
    private Airport originAirport;
    private Airport destinationAirport;

    public Flight(int flightNumber, Airport originAirport, Airport destinationAirport) {
        super(Integer.toString(flightNumber)); //for thread naming
        this.flightNumber = flightNumber;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    @Override
    //simulates a flight
    public void run() {
        super.run();
        int departureRunway = originAirport.depart(flightNumber);
        try {
            sleep(1000 * getRandomNumber(2, 5)); //simulating departure time
        } catch (InterruptedException e) {e.printStackTrace();}
        originAirport.freeRunway(flightNumber,departureRunway);
        try {
            sleep(1000 * getRandomNumber(4, 6)); //simulating flight time
        } catch (InterruptedException e) {e.printStackTrace();}
        int landingRunway = destinationAirport.land(flightNumber);
        try {
            sleep(1000 * getRandomNumber(2, 5)); //simulating landing time
        }catch (InterruptedException e) {e.printStackTrace();}
        destinationAirport.freeRunway(flightNumber, landingRunway);
    }

    //Returns random integer between given min and max
    private int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public String toString() {
        return "Flight #" + flightNumber + " scheduled to departing from " + originAirport.getName() +
                " to " + destinationAirport.getName();
    }


}
