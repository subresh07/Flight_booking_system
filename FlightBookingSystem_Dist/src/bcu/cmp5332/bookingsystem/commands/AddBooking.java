/**
 * Class description goes here.
 *
 * @Author Subresh Thakulla / Bikki Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;


public class AddBooking implements Command {

    private final int customerId; // ID of the customer
    private final int flightId; // ID of the flight

    
    public AddBooking(int customerId, int flightId, LocalDate localDate) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

   
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        LocalDate today = LocalDate.now();
        LocalDate twoYearsFromToday = today.plusYears(2);

        if (today.isAfter(twoYearsFromToday)) {
            throw new FlightBookingSystemException("Bookings more than 2 years in advance are not allowed.");
        }

        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        if (!flight.hasNotDeparted(today)) {
            throw new FlightBookingSystemException("Cannot book a flight that has already departed.");
        }

        if (flight.getPassengers().size() >= flight.getNumberOfSeats()) {
            throw new FlightBookingSystemException("The flight is full. Booking cannot be made.");
        }

        int price = flight.calculatePrice(today);

        int bookingId = fbs.generateBookingId();
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingId, customer, flight, bookingDate, price);
        customer.addBooking(booking);
        flight.addPassenger(customer);

        if (!booking.isCancelled()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/bookings.txt", true))) {
                writer.write(booking.getId() + "," + customer.getId() + "," + flight.getId() + "," + booking.getBookingDate() + "," + booking.getPrice());
                writer.newLine();
            } catch (IOException e) {
                throw new FlightBookingSystemException("Error writing to bookings.txt: " + e.getMessage());
            }
        }

        System.out.println("Booking was issued successfully to the customer.");
    }
}
