/**
 * Interface representing a command in the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Interface for commands that can be executed in the flight booking system.
 */
public interface Command {

    /**
     * Help message displaying all available commands in the flight booking system.
     */
    public static final String HELP_MESSAGE = "Commands:\n"
        + "\tlistflights                               print all flights\n"
        + "\tlistcustomers                             print all customers\n"
        + "\taddflight                                 add a new flight\n"
        + "\taddcustomer                               add a new customer\n"
        + "\tshowflight [flight id]                    show flight details\n"
        + "\tshowcustomer [customer id]                show customer details\n"
        + "\taddbooking [customer id] [flight id]      add a new booking\n"
        + "\tcancelbooking [customer id] [flight id]   cancel a booking\n"
        + "\teditbooking [booking id] [flight id]      update a booking\n"
        + "\tloadgui                                   loads the GUI version of the app\n"
        + "\thelp                                      prints this help message\n"
        + "\texit                                      exits the program";

    /**
     * Executes the command in the flight booking system.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while executing the command
     */
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
}
