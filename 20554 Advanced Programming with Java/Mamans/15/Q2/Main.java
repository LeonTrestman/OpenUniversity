/*Main class for Q02 in Maman 15*/

import java.util.LinkedList;
import java.util.Random;

public class Main {

    private static int numOfFlights = 10 ; //change here number of flights
    private static int numOfFreeRunways = 3; //change here number of free runways

    public static void main(String[] args) {

        //Creating 2 airports
        Airport ap1 = new Airport("TLV", numOfFreeRunways);
        Airport ap2 = new Airport("JFK", numOfFreeRunways);

        //creating 10 flights, with random source and destination airports
        LinkedList<Flight> flights = new LinkedList<Flight>();
        for (int i = 1; i < numOfFlights+1; i++) {
            int randCase = new Random().nextInt(2);
            switch (randCase) {
                case 0:
                    flights.add(new Flight(i, ap1, ap2));
                    break;
                case 1:
                    flights.add(new Flight(i, ap2, ap1));
                    break;
            }
        }

        //staring all flights
        for (Flight f : flights) {
            f.start();
        }


    }
}
