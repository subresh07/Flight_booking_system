/**
 * The BookingDataManager class is responsible for loading and storing booking data to and from a file
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

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * The BookingDataManager class is responsible for loading and storing booking data to and from a file
 * for the Flight Booking System application.
 *
 * It implements the DataManager interface and provides methods to load booking data from a text file
 * and store booking data to a text file.
 */
public class BookingDataManager implements DataManager {

    /** The path to the booking data file. */
    private final static String RESOURCE = "./resources/data/bookings.txt";

    /** The separator used to separate fields in the data file. */
    private final static String SEPARATOR = ",";

    /**
     * Loads booking data from the specified file and updates the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner scanner = new Scanner(new File(RESOURCE))) {
            int maxBookingId = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] properties = line.split(SEPARATOR);
                if (properties.length >= 5) {
                    int id = Integer.parseInt(properties[0]);
                    int customerId = Integer.parseInt(properties[1]);
                    int flightId = Integer.parseInt(properties[2]);
                    LocalDate date = LocalDate.parse(properties[3]);
                    double price = Double.parseDouble(properties[4]); 
                    boolean cancelled = properties.length > 5 && properties[5].equals("cancelled");
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight flight = fbs.getFlightByID(flightId);
                    if (customer != null && flight != null) {
                        Booking booking = new Booking(id, customer, flight, date, price);
                        if (cancelled) {
                            booking.cancelBooking();
                        }
                        customer.addBooking(booking);
                        flight.addPassenger(customer);
                        if (id > maxBookingId) {
                            maxBookingId = id;
                        }
                    }
                }
            }
            fbs.setMaxBookingId(maxBookingId);
        }
    }

    /**
     * Stores booking data to the specified file based on the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                for (Booking booking : customer.getBookings()) {
                    writer.print(booking.getId() + SEPARATOR
                            + customer.getId() + SEPARATOR
                            + booking.getFlight().getId() + SEPARATOR
                            + booking.getBookingDate() + SEPARATOR
                            + booking.getPrice());
                    
                    if (booking.isCancelled()) {
                        writer.print(SEPARATOR + "cancelled");
                    }
                    
                    writer.println();
                }
            }
        }
    }
}
