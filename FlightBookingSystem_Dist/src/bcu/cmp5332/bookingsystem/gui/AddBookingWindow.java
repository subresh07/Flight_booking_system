package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Represents a window for adding a new booking to the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class AddBookingWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static AddBookingWindow instance;

    private MainWindow mw;
    private FlightBookingSystem fbs;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddBookingWindow object with the specified MainWindow.
     * 
     * @param mw The MainWindow object.
     */
    public AddBookingWindow(MainWindow mw) {
        this.mw = mw;
        this.fbs = mw.getFlightBookingSystem();
        initialize();
    }

    /**
     * Returns the instance of AddBookingWindow.
     *
     * @param mw The MainWindow object.
     * @return The AddBookingWindow instance.
     */
    public static AddBookingWindow getInstance(MainWindow mw) {
        if (instance == null) {
            instance = new AddBookingWindow(mw);
        }
        return instance;
    }

    /**
     * Initializes the AddBookingWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Add Booking");
        setSize(400, 250);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Light background

        JLabel headingLabel = new JLabel("Add Booking", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240)); // Match background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerIdLabel.setForeground(Color.BLACK);
        formPanel.add(customerIdLabel, gbc);

        gbc.gridx = 1;
        customerIdText.setFont(new Font("Arial", Font.PLAIN, 14));
        customerIdText.setForeground(Color.BLACK);
        customerIdText.setPreferredSize(new Dimension(200, 25));
        formPanel.add(customerIdText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel flightIdLabel = new JLabel("Flight ID:");
        flightIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        flightIdLabel.setForeground(Color.BLACK);
        formPanel.add(flightIdLabel, gbc);

        gbc.gridx = 1;
        flightIdText.setFont(new Font("Arial", Font.PLAIN, 14));
        flightIdText.setForeground(Color.BLACK);
        flightIdText.setPreferredSize(new Dimension(200, 25));
        formPanel.add(flightIdText, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240)); // Match background color
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(new JLabel("")); // Spacer
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setBackground(new Color(70, 130, 180)); // Steel blue for button background
        addBtn.setForeground(Color.BLACK); // Black text for button
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(220, 53, 69)); // Bootstrap red for button background
        cancelBtn.setForeground(Color.BLACK); // Black text for button
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        mainPanel.add(headingLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    /**
     * Handles action events for the buttons in the AddBookingWindow.
     *
     * @param ae the action event
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBooking();
        } else if (ae.getSource() == cancelBtn) {
            setVisible(false);
        }
    }

    /**
     * Adds a new booking to the flight booking system based on the user input.
     */
    private void addBooking() {
        try {
            String customerIdStr = customerIdText.getText();
            String flightIdStr = flightIdText.getText();

            if (customerIdStr.isEmpty() || flightIdStr.isEmpty()) {
                throw new FlightBookingSystemException("Customer ID and Flight ID must be provided.");
            }

            int customerId = Integer.parseInt(customerIdStr);
            int flightId = Integer.parseInt(flightIdStr);

            if (customerId <= 0 || flightId <= 0) {
                throw new FlightBookingSystemException("Customer ID and Flight ID must be positive integers.");
            }

            Customer customer = fbs.getCustomerByID(customerId);
            Flight flight = fbs.getFlightByID(flightId);

            if (customer == null || flight == null) {
                throw new FlightBookingSystemException("Customer or Flight not found.");
            }

            LocalDate today = LocalDate.now();
            if (!flight.hasNotDeparted(today)) {
                JOptionPane.showMessageDialog(this, "Sorry, the flight has already departed. Booking cannot be made.", "Error", JOptionPane.WARNING_MESSAGE);
                return; // Exit method if the flight has departed
            }

            // Check if the flight's number of seats is full
            if (flight.getPassengers().size() >= flight.getNumberOfSeats()) {
                JOptionPane.showMessageDialog(this, "Sorry, the flight is already at full capacity. Booking cannot be made.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Command addBooking = new AddBooking(customerId, flightId, LocalDate.now());
                addBooking.execute(fbs);
                JOptionPane.showMessageDialog(this, "Booking added successfully.");
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            clearFields();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears the input fields in the AddBookingWindow.
     */
    private void clearFields() {
        customerIdText.setText("");
        flightIdText.setText("");
    }
}
