/**
 * Command to load the graphical user interface (GUI) for the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.LoginWindow;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * Command to load the graphical user interface (GUI) for the flight booking system.
 */
public class LoadGUI implements Command {

    private final FlightBookingSystem flightBookingSystem;

    /**
     * Constructs a LoadGUI command with the specified flight booking system.
     *
     * @param flightBookingSystem the flight booking system
     */
    public LoadGUI(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;
    }

    /**
     * Executes the command to load the graphical user interface (GUI).
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while executing the command
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new LoginWindow(flightBookingSystem).setVisible(true);
    }
}
