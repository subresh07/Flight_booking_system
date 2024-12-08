package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The UpdateBookingWindow class represents a window for updating an existing booking
 * in the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class UpdateBookingWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel bookingIdLabel;
    private JTextField bookingIdField;
    private JLabel currentFlightIdLabel;
    private JTextField currentFlightIdField;
    private JLabel newFlightIdLabel;
    private JTextField newFlightIdField;
    private JButton updateButton;

    private BookingDataManager bookingDataManager;
    private FlightBookingSystem fbs;

    /**
     * Constructs a new UpdateBookingWindow.
     *
     * @param bookingDataManager The BookingDataManager to use.
     * @param fbs                The FlightBookingSystem to use.
     */
    public UpdateBookingWindow(BookingDataManager bookingDataManager, FlightBookingSystem fbs) {
        this.bookingDataManager = bookingDataManager;
        this.fbs = fbs;
        initialize();
        setTitle("Update Booking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes the UpdateBookingWindow by setting up the UI components.
     */
    private void initialize() {
        getContentPane().setBackground(new Color(240, 240, 240)); // Light background
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create heading label
        JLabel headingLabel = new JLabel("Update Booking", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create panel for form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingIdField = new JTextField();
        currentFlightIdLabel = new JLabel("Current Flight ID:");
        currentFlightIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentFlightIdField = new JTextField();
        newFlightIdLabel = new JLabel("New Flight ID:");
        newFlightIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        newFlightIdField = new JTextField();
        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(70, 130, 180)); // Steel blue for button background
        updateButton.setForeground(Color.BLACK); // Black text for button
        updateButton.addActionListener(this);

        formPanel.add(bookingIdLabel);
        formPanel.add(bookingIdField);
        formPanel.add(currentFlightIdLabel);
        formPanel.add(currentFlightIdField);
        formPanel.add(newFlightIdLabel);
        formPanel.add(newFlightIdField);
        formPanel.add(new JLabel());
        formPanel.add(updateButton);

        getContentPane().add(headingLabel, BorderLayout.NORTH);
        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the action events for the Update button.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            updateBooking();
        }
    }

    /**
     * Updates an existing booking based on the user input.
     */
    private void updateBooking() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            int currentFlightId = Integer.parseInt(currentFlightIdField.getText());
            int newFlightId = Integer.parseInt(newFlightIdField.getText());

            Booking booking = fbs.getBookingByID(bookingId);
            if (booking != null && booking.getFlight().getId() == currentFlightId && !booking.isCancelled()) {
                Flight newFlight = fbs.getFlightByID(newFlightId);
                if (newFlight != null) {
                    booking.setFlight(newFlight);
                    bookingDataManager.storeData(fbs);
                    JOptionPane.showMessageDialog(this, "Booking updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "New Flight ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found, is cancelled, or current flight ID is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid booking ID, current flight ID, and new flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
