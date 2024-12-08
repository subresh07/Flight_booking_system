package bcu.cmp5332.bookingsystem.main;

/**
 * The FlightBookingSystemException class represents an exception specific to the Flight Booking System.
 * This exception is thrown when an error occurs within the Flight Booking System.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class FlightBookingSystemException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new FlightBookingSystemException with the specified detail message.
     * 
     * @param message The detail message.
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}
