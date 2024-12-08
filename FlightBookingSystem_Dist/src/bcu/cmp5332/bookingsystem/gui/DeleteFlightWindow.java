package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The DeleteFlightWindow class represents a graphical user interface window for deleting a flight
 * in the Flight Booking System application.
 *
 * It allows the user to input a flight ID to delete the flight from the system.
 *
 * This class extends the JFrame class to create a window.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class DeleteFlightWindow extends JFrame {
    private FlightBookingSystem fbs;

    /**
     * Constructs a new DeleteFlightWindow.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public DeleteFlightWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the DeleteFlightWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Delete Flight");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main panel with light blue background
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(173, 216, 230));  // Light blue background

        JLabel flightIdLabel = new JLabel("Enter Flight ID:");
        flightIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        flightIdLabel.setForeground(Color.BLACK);  // Black text for clear visibility

        JTextField flightIdField = new JTextField();
        flightIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        flightIdField.setForeground(Color.BLACK);  // Black text
        flightIdField.setBackground(Color.WHITE);  // White background for text field

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBackground(new Color(70, 130, 180));  // Steel blue color
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));  // Add border to button

        // Add hover effect to the button
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteButton.setBackground(new Color(100, 149, 237));  // Lighter blue color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteButton.setBackground(new Color(70, 130, 180));  // Revert back to original color
            }
        });

        deleteButton.addActionListener(e -> {
            String input = flightIdField.getText();
            if (input != null && !input.isEmpty()) {
                try {
                    int flightId = Integer.parseInt(input);
                    deleteFlight(flightId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(flightIdLabel);
        panel.add(flightIdField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(deleteButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Deletes a flight with the specified flight ID from the FlightBookingSystem.
     *
     * @param flightId the ID of the flight to delete
     */
    private void deleteFlight(int flightId) {
        try {
            FlightDataManager dataManager = new FlightDataManager();
            dataManager.deleteFlight(flightId, fbs);
            JOptionPane.showMessageDialog(this, "Flight deleted successfully.");
        } catch (IOException | FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
