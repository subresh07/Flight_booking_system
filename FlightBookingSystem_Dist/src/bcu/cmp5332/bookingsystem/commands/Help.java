/**
 * Command to display the help message in the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to display the help message in the flight booking system.
 */
public class Help implements Command {

    /**
     * Executes the command to display the help message.
     *
     * @param flightBookingSystem the flight booking system
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
