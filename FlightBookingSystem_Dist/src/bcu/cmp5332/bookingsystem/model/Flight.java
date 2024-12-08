package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The Flight class represents a flight in the flight booking system.
 * It includes details such as the flight's ID, flight number, origin, destination,
 * departure date, number of seats, price, and the passengers booked on the flight.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class Flight {

    private int id; // The unique identifier for the flight
    private String flightNumber; // The flight number
    private String origin; // The origin of the flight
    private String destination; // The destination of the flight
    private LocalDate departureDate; // The departure date of the flight
    private int numberOfSeats; // The total number of seats available on the flight
    private double price; // The price of the flight
    private final Set<Customer> passengers; // Set of passengers booked on the flight
    private List<Booking> bookings = new ArrayList<>(); // List of bookings associated with the flight
    private boolean deleted; // Flag indicating whether the flight is deleted

    /**
     * Constructs a new Flight object with the specified details.
     * 
     * @param id The unique identifier for the flight.
     * @param flightNumber The flight number.
     * @param origin The origin of the flight.
     * @param destination The destination of the flight.
     * @param departureDate The departure date of the flight.
     * @param numberOfSeats The total number of seats available on the flight.
     * @param price The price of the flight.
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.passengers = new HashSet<>();
        this.deleted = false;
    }

    // Getters and setters for Flight attributes

    /**
     * Gets the unique identifier for the flight.
     * 
     * @return The flight ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the flight.
     * 
     * @param id The flight ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the flight number.
     * 
     * @return The flight number.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     * 
     * @param flightNumber The flight number to set.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the origin of the flight.
     * 
     * @return The origin of the flight.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin of the flight.
     * 
     * @param origin The origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the destination of the flight.
     * 
     * @return The destination of the flight.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the flight.
     * 
     * @param destination The destination to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the departure date of the flight.
     * 
     * @return The departure date.
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     * 
     * @param departureDate The departure date to set.
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the total number of seats available on the flight.
     * 
     * @return The number of seats.
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the total number of seats available on the flight.
     * 
     * @param numberOfSeats The number of seats to set.
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the price of the flight.
     * 
     * @return The price of the flight.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the flight.
     * 
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the list of passengers booked on the flight.
     * 
     * @return The list of passengers.
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Gets a short description of the flight.
     * 
     * @return A short description of the flight.
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to "
                + destination + " on " + departureDate.format(dtf) + " - Price: $" + price + " - Seats: " + numberOfSeats;
    }

    /**
     * Gets a detailed description of the flight.
     * 
     * @return A detailed description of the flight.
     */
    public String getDetailsLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        StringBuilder sb = new StringBuilder();
        sb.append("Flight #").append(id).append("\n");
        sb.append("Flight Number: ").append(flightNumber).append("\n");
        sb.append("Origin: ").append(origin).append("\n");
        sb.append("Destination: ").append(destination).append("\n");
        sb.append("Departure Date: ").append(departureDate.format(dtf)).append("\n");
        sb.append("Number of Seats: ").append(numberOfSeats).append("\n");
        sb.append("Price: Rs").append(price).append("\n");
        sb.append("Passengers: ").append("\n");
        for (Customer passenger : passengers) {
            sb.append(passenger.getName()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Adds a passenger to the flight if there are available seats and the departure date has not passed.
     * 
     * @param customer The customer to add as a passenger.
     */
    public void addPassenger(Customer customer) {
        if (passengers.size() >= numberOfSeats || departureDate.isBefore(LocalDate.now())) {
            return;
        }
        passengers.add(customer);
    }

    /**
     * Removes a passenger from the flight if they are not cancelled.
     * 
     * @param customer The customer to remove as a passenger.
     */
    public void removePassenger(Customer customer) {
        passengers.removeIf(passenger -> passenger.equals(customer) && !passenger.isCancelled());
    }

    /**
     * Checks if the flight is deleted.
     * 
     * @return True if the flight is deleted, otherwise false.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted status of the flight.
     * 
     * @param deleted The deleted status to set.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gets a booking by its ID.
     * 
     * @param bookingId The ID of the booking.
     * @return The booking with the specified ID, or null if no such booking exists.
     */
    public Booking getBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Removes a booking from the flight.
     * 
     * @param booking The booking to remove.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Adds a booking to the flight.
     * 
     * @param booking The booking to add.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Gets the list of bookings associated with the flight.
     * 
     * @return An array of bookings.
     */
    public Booking[] getBookings() {
        // Return only non-deleted bookings
        List<Booking> nonDeletedBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                nonDeletedBookings.add(booking);
            }
        }
        return nonDeletedBookings.toArray(new Booking[0]);
    }

    /**
     * Checks if the flight has not yet departed.
     * 
     * @param systemDate The current system date.
     * @return True if the flight has not yet departed, otherwise false.
     */
    public boolean hasNotDeparted(LocalDate systemDate) {
        return departureDate.isAfter(systemDate);
    }

    /**
     * Calculates the price of the flight based on the current date and the number of available seats.
     * 
     * @param currentDate The current date.
     * @return The calculated price.
     * @throws FlightBookingSystemException If there are no available seats for booking.
     */
    public int calculatePrice(LocalDate currentDate) throws FlightBookingSystemException {
        // Check if the flight is fully booked
        int bookedSeats = 0;
        for (Customer passenger : passengers) {
            if (!passenger.isCancelled()) {
                bookedSeats++;
            }
        }
        if (bookedSeats >= numberOfSeats) {
            return (int) price; // Return the original price
        }

        int daysLeft = (int) ChronoUnit.DAYS.between(currentDate, departureDate);
        int priceFactor;

        // Adjust the price factor based on the number of days left
        if (daysLeft >= 6) {
            priceFactor = 1; // Price factor for flights departing in more than 6 days
        } else if (daysLeft >= 1) {
            priceFactor = 2; // Price factor for flights departing in 1 to 5 days
        } else {
            priceFactor = 3; // Price factor for flights departing within 24 hours
        }

        // Calculate the final price based on the price factor
        double finalPrice = price * priceFactor;

        // Ensure that there are available seats
        int seatsLeft = numberOfSeats - bookedSeats;
        if (seatsLeft <= 0) {
            throw new FlightBookingSystemException("No seats available for booking.");
        }

        double seatsPriceIncrease = 0.0;
        if (seatsLeft <= 4) {
            seatsPriceIncrease = 50.0 * (5 - seatsLeft); // Increase price by $50 for each seat less than 5
        }

        finalPrice += seatsPriceIncrease;

        return (int) finalPrice;
    }

    /**
     * Checks if the flight is fully booked.
     * 
     * @return True if the flight is fully booked, otherwise false.
     */
    public boolean isFullyBooked() {
        int bookedSeats = 0;
        for (Customer passenger : passengers) {
            if (!passenger.isCancelled()) {
                bookedSeats++;
            }
        }
        return bookedSeats >= numberOfSeats;
    }
}
