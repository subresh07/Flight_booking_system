package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
 * The FlightBookingSystem class represents a system for managing flights, customers, and bookings.
 * It includes methods for adding, retrieving, and deleting flights, customers, and bookings.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Booking> bookings = new TreeMap<>();
    private int maxBookingId;

    /**
     * Generates a new booking ID.
     * 
     * @return The new booking ID.
     */
    public int generateBookingId() {
        return ++maxBookingId;
    }

    /**
     * Sets the maximum booking ID.
     * 
     * @param maxBookingId The maximum booking ID to set.
     */
    public void setMaxBookingId(int maxBookingId) {
        this.maxBookingId = maxBookingId;
    }

    /**
     * Gets the maximum booking ID.
     * 
     * @return The maximum booking ID.
     */
    public int getMaxBookingId() {
        return maxBookingId;
    }

    /**
     * Gets the system date.
     * 
     * @return The system date.
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }

    /**
     * Gets the list of flights.
     * 
     * @return The list of flights.
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (!flight.getDepartureDate().isBefore(systemDate)) {
                out.add(flight);
            }
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Gets the list of customers.
     * 
     * @return The list of customers.
     */
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }

    /**
     * Gets the list of bookings.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        List<Booking> out = new ArrayList<>(bookings.values());
        out.removeIf(Objects::isNull);
        return Collections.unmodifiableList(out);
    }

    /**
     * Gets a flight by its ID.
     * 
     * @param id The flight ID.
     * @return The flight with the specified ID.
     * @throws FlightBookingSystemException If the flight is not found.
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Gets a customer by their ID.
     * 
     * @param id The customer ID.
     * @return The customer with the specified ID.
     * @throws FlightBookingSystemException If the customer is not found.
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customers.get(id);
    }

    /**
     * Gets a booking by its ID.
     * 
     * @param id The booking ID.
     * @return The booking with the specified ID.
     * @throws FlightBookingSystemException If the booking is not found.
     */
    public Booking getBookingByID(int id) throws FlightBookingSystemException {
        if (!bookings.containsKey(id)) {
            throw new FlightBookingSystemException("There is no booking with that ID.");
        }
        return bookings.get(id);
    }

    /**
     * Adds a flight to the system.
     * 
     * @param flight The flight to be added.
     * @throws FlightBookingSystemException If there is a duplicate flight ID or a flight with the same number and departure date already exists in the system.
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Adds a customer to the system.
     * 
     * @param customer The customer to be added.
     * @throws FlightBookingSystemException If there is a duplicate customer ID.
     */
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        customers.put(customer.getId(), customer);
    }

    /**
     * Adds a booking to the system.
     * 
     * @param booking The booking to be added.
     * @throws FlightBookingSystemException If the customer or flight associated with the booking is not found, or if there is a duplicate booking ID.
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        // Get the customer and flight associated with the booking
        Customer customer = booking.getCustomer();
        Flight flight = booking.getFlight();
        
        // Check if the customer and flight exist in the system
        if (customer == null || flight == null) {
            throw new FlightBookingSystemException("Customer or Flight not found.");
        }
        
        // Check if the booking ID already exists
        if (bookings.containsKey(booking.getId())) {
            throw new IllegalArgumentException("Duplicate booking ID.");
        }
        
        // Add the booking to the system
        bookings.put(booking.getId(), booking);
    }

    /**
     * Gets the bookings for a specific customer and flight.
     * 
     * @param customer The customer.
     * @param flight The flight.
     * @return The list of bookings for the specified customer and flight.
     */
    public List<Booking> getBookingsByCustomerAndFlight(Customer customer, Flight flight) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().equals(customer) && booking.getFlight().equals(flight)) {
                result.add(booking);
            }
        }
        return result;
    }

    /**
     * Deletes a flight from the system.
     * 
     * @param flightId The ID of the flight to be deleted.
     * @throws FlightBookingSystemException If the flight is not found.
     */
    public void deleteFlight(int flightId) throws FlightBookingSystemException {
        Flight flight = getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found.");
        }
        List<Booking> flightBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().equals(flight)) {
                flightBookings.add(booking);
            }
        }
        for (Booking booking : flightBookings) {
            bookings.remove(booking.getId());
        }
        flights.remove(flightId);
    }

    /**
     * Deletes a customer from the system.
     * 
     * @param customerId The ID of the customer to be deleted.
     * @throws FlightBookingSystemException If the customer is not found.
     */
    public void deleteCustomer(int customerId) throws FlightBookingSystemException {
        Customer customer = getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found.");
        }
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().equals(customer)) {
                customerBookings.add(booking);
            }
        }
        for (Booking booking : customerBookings) {
            bookings.remove(booking.getId());
        }
        customers.remove(customerId);
    }

    /**
     * Gets a booking by customer and flight ID.
     * 
     * @param customerId The ID of the customer.
     * @param flightId The ID of the flight.
     * @return The booking with the specified customer and flight ID.
     */
    public Booking getBookingByCustomerAndFlightId(int customerId, int flightId) {
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().getId() == customerId && booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Gets the bookings for a specific flight.
     * 
     * @param flight The flight.
     * @return The list of bookings for the specified flight.
     */
    public List<Booking> getBookingsByFlight(Flight flight) {
        List<Booking> bookingsByFlight = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().getId() == flight.getId()) {
                bookingsByFlight.add(booking);
            }
        }
        return bookingsByFlight;
    }

    /**
     * Updates the flight for a booking.
     * 
     * @param bookingId The ID of the booking to update.
     * @param newFlightId The ID of the new flight.
     * @throws FlightBookingSystemException If the booking or new flight is not found, or if the booking is cancelled.
     */
    public void updateBookingFlight(int bookingId, int newFlightId) throws FlightBookingSystemException {
        Booking booking = getBookingByID(bookingId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking not found.");
        }

        if (booking.isCancelled()) {
            throw new FlightBookingSystemException("Cannot update a canceled booking.");
        }

        Flight newFlight = getFlightByID(newFlightId);
        if (newFlight == null) {
            throw new FlightBookingSystemException("Invalid new flight ID.");
        }

        booking.setFlight(newFlight);
    }
}
