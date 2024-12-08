/**
 * The DataManager interface defines methods for loading and storing data
 * in the Flight Booking System application.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * The DataManager interface defines methods for loading and storing data
 * in the Flight Booking System application.
 */
public interface DataManager {
    
    /** The separator used to separate fields in the data file. */
    public static final String SEPARATOR = ",";
    
    /**
     * Loads data into the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
    
    /**
     * Stores data from the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    public void storeData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
}
