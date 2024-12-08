package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The PassengerList class provides a method to display the list of passengers for a specified flight
 * in the Flight Booking System application.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class PassengerList {

    private FlightBookingSystem fbs;

    /**
     * Constructs a new PassengerList object.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public PassengerList(FlightBookingSystem fbs) {
        this.fbs = fbs;
    }

    /**
     * Displays a dialog for entering a flight ID and shows the list of passengers for the specified flight.
     *
     * @param parent the parent JFrame
     */
    public void displayPassengerList(JFrame parent) {
        // Customize the JOptionPane input dialog
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230)); // Light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        JLabel headingLabel = new JLabel("Passenger List");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headingLabel, gbc);

        JLabel label = new JLabel("Enter Flight ID:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLACK); // Black text for contrast
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setForeground(Color.BLACK); // Black text for readability
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(inputField, gbc);

        UIManager.put("OptionPane.background", new Color(173, 216, 230)); // Light blue background
        UIManager.put("Panel.background", new Color(173, 216, 230)); // Light blue background

        int result = JOptionPane.showConfirmDialog(parent, panel, "Passenger List", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String input = inputField.getText();
        if (result == JOptionPane.OK_OPTION && !input.isEmpty()) {
            try {
                int flightId = Integer.parseInt(input);
                Flight flight = fbs.getFlightByID(flightId);
                if (flight != null) {
                    List<Customer> passengers = flight.getPassengers();
                    new PassengerListWindow(passengers, flight.getFlightNumber());
                } else {
                    // Customize error message dialog
                    UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                    JOptionPane.showMessageDialog(parent, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                // Customize error message dialog
                UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                JOptionPane.showMessageDialog(parent, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FlightBookingSystemException ex) {
                // Customize error message dialog
                UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
