/**
 * The FlightBookingSystemData class is responsible for managing the loading and storing of data 
 * for the Flight Booking System application.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The FlightBookingSystemData class is responsible for managing the loading and storing of data 
 * for the Flight Booking System application.
 */
public class FlightBookingSystemData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new FlightDataManager());
        dataManagers.add(new CustomerDataManager());
        dataManagers.add(new BookingDataManager());
    }
    
    /**
     * Loads data into the FlightBookingSystem instance.
     *
     * @return the FlightBookingSystem instance with loaded data
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     * @throws IOException if an I/O error occurs
     */
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }

    /**
     * Stores data from the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     */
    public static void store(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/customers.txt"))) {
            for (Customer customer : fbs.getCustomers()) {
                writer.println(customer.getId() + "," + customer.getName() + "," + customer.getPhone() + "," + customer.getEmail());
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/flights.txt"))) {
            for (Flight flight : fbs.getFlights()) {
                writer.println(flight.getId() + "," + flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + flight.getDepartureDate() + "," + flight.getNumberOfSeats() + "," + flight.getPrice());
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/bookings.txt"))) {
            for (Booking booking : fbs.getBookings()) {
                writer.println(booking.getId() + "," + booking.getCustomer().getId() + "," + booking.getFlight().getId() + "," + booking.getBookingDate());
            }
        }
    }
}
