package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer in the flight booking system.
 * It includes details such as the customer's ID, name, phone number, email address,
 * and a list of bookings made by the customer.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class Customer {

    private int id; // The unique identifier for the customer
    private String name; // The name of the customer
    private String phone; // The phone number of the customer
    private String email; // The email address of the customer
    private final List<Booking> bookings = new ArrayList<>(); // List of bookings made by the customer
    private boolean deleted; // Flag indicating whether the customer is deleted

    /**
     * Constructs a new Customer object with the specified details.
     * 
     * @param id The unique identifier for the customer.
     * @param name The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deleted = false;
    }

    /**
     * Gets the unique identifier for the customer.
     * 
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the customer.
     * 
     * @param id The customer ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer.
     * 
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the customer.
     * 
     * @return The customer's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phone The phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email address of the customer.
     * 
     * @return The customer's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     * 
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of bookings made by the customer.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the customer's list of bookings.
     * 
     * @param booking The booking to add.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Gets a short description of the customer.
     * 
     * @return A short description of the customer.
     */
    public String getDetailsShort() {
        return "Customer #" + id + " - " + name + " - " + phone + " - " + email;
    }

    /**
     * Gets the booking made by the customer for a specific flight.
     * 
     * @param flightId The ID of the flight.
     * @return The booking for the specified flight, or null if no such booking exists.
     */
    public Booking getBookingByFlightId(int flightId) {
        for (Booking booking : bookings) {
            if (booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Gets the number of bookings made by the customer.
     * 
     * @return The number of bookings.
     */
    public int getNumberOfBookings() {
        return bookings.size();
    }

    /**
     * Removes a booking from the customer's list of bookings.
     * 
     * @param booking The booking to remove.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Checks if the customer is deleted.
     * 
     * @return True if the customer is deleted, otherwise false.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted status of the customer.
     * 
     * @param deleted The deleted status to set.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Checks if any of the customer's bookings are cancelled.
     * 
     * @return True if any bookings are cancelled, otherwise false.
     */
    public boolean isCancelled() {
        for (Booking booking : bookings) {
            if (booking.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of active bookings (not cancelled) made by the customer.
     * 
     * @return The list of active bookings.
     */
    public List<Booking> getActiveBookings() {
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                activeBookings.add(booking);
            }
        }
        return activeBookings;
    }

    /**
     * Updates the number of bookings for the customer to reflect only active bookings.
     */
    public void updateNumberOfBookings() {
        // Get the list of active bookings (not cancelled)
        List<Booking> activeBookings = getActiveBookings();

        // Update the number of bookings for the customer
        bookings.clear();
        bookings.addAll(activeBookings);
    }
}
