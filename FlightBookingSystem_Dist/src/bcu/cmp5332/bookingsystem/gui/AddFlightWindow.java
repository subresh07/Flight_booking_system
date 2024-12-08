package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a window for adding a new flight to the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField depDateText = new JTextField();
    private JTextField seatsText = new JTextField();
    private JTextField priceText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddFlightWindow object with the specified MainWindow.
     *
     * @param mw The MainWindow object.
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the AddFlightWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Add a New Flight");
        setSize(800, 750); // Adjusted size for better visibility and layout

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 248, 255)); // Alice blue background

        JLabel headingLabel = new JLabel("Add Flight:", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setForeground(Color.WHITE); // White text
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(0, 51, 102)); // Dark blue background
        headingLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        contentPanel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255)); // Alice blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Flight Number
        JLabel flightNoLabel = new JLabel("Flight Number:");
        flightNoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(flightNoLabel, gbc);

        gbc.gridy++;
        flightNoText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(flightNoText, gbc);

        // Origin
        gbc.gridy++;
        JLabel originLabel = new JLabel("Origin:");
        originLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(originLabel, gbc);

        gbc.gridy++;
        originText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(originText, gbc);

        // Destination
        gbc.gridy++;
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(destinationLabel, gbc);

        gbc.gridy++;
        destinationText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(destinationText, gbc);

        // Departure Date
        gbc.gridy++;
        JLabel depDateLabel = new JLabel("Departure Date (YYYY-MM-DD):");
        depDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(depDateLabel, gbc);

        gbc.gridy++;
        depDateText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(depDateText, gbc);

        // Number of Seats
        gbc.gridy++;
        JLabel seatsLabel = new JLabel("Number of Seats:");
        seatsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(seatsLabel, gbc);

        gbc.gridy++;
        seatsText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(seatsText, gbc);

        // Price
        gbc.gridy++;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(priceLabel, gbc);

        gbc.gridy++;
        priceText.setPreferredSize(new Dimension(300, 30)); // Adjusted width for text field
        formPanel.add(priceText, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255)); // Alice blue background
        addBtn.setFont(new Font("Arial", Font.BOLD, 18));
        addBtn.setBackground(new Color(0, 102, 204)); // Button background color
        addBtn.setForeground(Color.BLACK); // Button text color
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 18));
        cancelBtn.setBackground(new Color(192, 192, 192)); // Button background color
        cancelBtn.setForeground(Color.BLACK); // Button text color
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    /**
     * Handles the action events for the Add and Cancel buttons.
     *
     * @param ae The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addFlight();
        } else if (ae.getSource() == cancelBtn) {
            setVisible(false); // Hide the window
        }
    }

    /**
     * Adds a new flight to the flight booking system based on the user input.
     */
    private void addFlight() {
        try {
            String flightNumber = flightNoText.getText().trim();
            String origin = originText.getText().trim();
            String destination = destinationText.getText().trim();
            LocalDate departureDate = LocalDate.parse(depDateText.getText().trim());
            int numberOfSeats = Integer.parseInt(seatsText.getText().trim());
            int price = Integer.parseInt(priceText.getText().trim());

            if (flightNumber.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
                throw new FlightBookingSystemException("Flight Number, Origin, and Destination cannot be empty.");
            }

            if (numberOfSeats <= 0 || price <= 0) {
                throw new FlightBookingSystemException("Number of Seats and Price must be positive integers.");
            }

            if (departureDate.isBefore(LocalDate.now())) {
                throw new FlightBookingSystemException("Departure Date must be in the future.");
            }

            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);
            addFlight.execute(mw.getFlightBookingSystem());

            // Show success message in popup dialog
            JOptionPane.showMessageDialog(this, "Flight added successfully.");

            // Clear fields after successful addition
            clearFields();
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears the text fields in the AddFlightWindow.
     */
    private void clearFields() {
        flightNoText.setText("");
        originText.setText("");
        destinationText.setText("");
        depDateText.setText("");
        seatsText.setText("");
        priceText.setText("");
    }
}
