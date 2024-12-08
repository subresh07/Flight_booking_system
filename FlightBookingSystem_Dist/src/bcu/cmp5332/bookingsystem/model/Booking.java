package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

/**
 * The Booking class represents a booking made by a customer for a flight.
 * It includes details such as the booking ID, customer, flight, price, booking date,
 * and cancellation status, along with any applicable fees for cancellation or rebooking.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class Booking {

    private int id; // The unique identifier for the booking
    private Customer customer; // The customer who made the booking
    private Flight flight; // The flight booked by the customer
    private double price; // The price of the booking
    private LocalDate bookingDate; // The date when the booking was made
    private boolean cancelled; // Flag indicating whether the booking is cancelled
    private double cancellationFee; // The fee charged upon cancellation of the booking
    private double rebookFee; // The fee charged upon rebooking the flight

    /**
     * Constructs a new Booking object with the specified details.
     * 
     * @param id The unique identifier for the booking.
     * @param customer The customer who made the booking.
     * @param flight The flight booked by the customer.
     * @param bookingDate The date when the booking was made.
     * @param price The price of the booking.
     */
    public Booking(int id, Customer customer, Flight flight, LocalDate bookingDate, double price) {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.price = price;
        this.cancelled = false;
    }

    /**
     * Gets the unique identifier for the booking.
     * 
     * @return The booking ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the customer who made the booking.
     * 
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the booking.
     * 
     * @param customer The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the flight booked by the customer.
     * 
     * @return The flight.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight booked by the customer.
     * 
     * @param flight The flight to set.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Gets the date when the booking was made.
     * 
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date when the booking was made.
     * 
     * @param bookingDate The booking date to set.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets the price of the booking.
     * 
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Checks if the booking is cancelled.
     * 
     * @return True if the booking is cancelled, otherwise false.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancels the booking and removes the passenger from the flight.
     * Sets the cancellation fee to 10% of the booking price.
     */
    public void cancelBooking() {
        if (!cancelled) { // Check if the booking is not already cancelled
            this.cancelled = true;
            flight.removePassenger(customer);
            // Set cancellation fee only if the booking is cancelled
            this.cancellationFee = price * 0.1;
        }
    }

    /**
     * Gets a detailed string representation of the booking.
     * 
     * @return The booking details.
     */
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Booking ID: ").append(id).append("\n");
        details.append("Customer: ").append(customer.getName()).append("\n");
        details.append("Flight: ").append(flight.getFlightNumber()).append("\n");
        details.append("Booking Date: ").append(bookingDate.toString()).append("\n");
        details.append("Price: ").append(price).append("\n");
        details.append("Status: ").append(cancelled ? "Cancelled" : "Active").append("\n");
        return details.toString();
    }

    /**
     * Gets the cancellation fee for the booking.
     * 
     * @return The cancellation fee.
     */
    public double getCancellationFee() {
        return cancellationFee;
    }

    /**
     * Sets the cancellation fee for the booking.
     * 
     * @param cancellationFee The cancellation fee to set.
     */
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    /**
     * Gets the rebook fee for the booking.
     * 
     * @return The rebook fee.
     */
    public double getRebookFee() {
        return rebookFee;
    }

    /**
     * Sets the rebook fee for the booking.
     * 
     * @param rebookFee The rebook fee to set.
     */
    public void setRebookFee(double rebookFee) {
        this.rebookFee = rebookFee;
    }
}
