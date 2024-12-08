package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The CancelBookingWindow class represents a graphical user interface window for canceling a booking
 * in the Flight Booking System application.
 *
 * It allows the user to input a customer ID and a flight ID to cancel the booking for that specific
 * customer and flight. Upon cancellation, the booking is removed from the system.
 *
 * This class extends the JFrame class and implements the ActionListener interface to handle button clicks.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class CancelBookingWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JTextField customerIdField;
    private JTextField flightIdField;
    private JButton cancelButton;
    private FlightBookingSystem fbs;

    /**
     * Constructs a new CancelBookingWindow.
     *
     * @param mw the MainWindow instance
     */
    public CancelBookingWindow(MainWindow mw) {
        this.fbs = mw.getFlightBookingSystem();
        setTitle("Cancel Booking");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Light background

        JLabel headingLabel = new JLabel("Cancel Booking", JLabel.CENTER);
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
        customerIdField = new JTextField(20);
        customerIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        customerIdField.setForeground(Color.BLACK);
        customerIdField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(customerIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel flightIdLabel = new JLabel("Flight ID:");
        flightIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        flightIdLabel.setForeground(Color.BLACK);
        formPanel.add(flightIdLabel, gbc);

        gbc.gridx = 1;
        flightIdField = new JTextField(20);
        flightIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        flightIdField.setForeground(Color.BLACK);
        flightIdField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(flightIdField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240)); // Match background color
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(new JLabel("")); // Spacer
        cancelButton = new JButton("Cancel Booking");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(70, 130, 180)); // Steel blue for button background
        cancelButton.setForeground(Color.BLACK); // Black text for button
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(this);

        mainPanel.add(headingLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    /**
     * Handles the action performed event.
     *
     * @param e the ActionEvent instance
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            String customerIdInput = customerIdField.getText();
            String flightIdInput = flightIdField.getText();
            if (!customerIdInput.isEmpty() && !flightIdInput.isEmpty()) {
                try {
                    int customerId = Integer.parseInt(customerIdInput);
                    int flightId = Integer.parseInt(flightIdInput);

                    // Create and execute CancelBooking command
                    CancelBooking cancelBooking = new CancelBooking(customerId, flightId);
                    cancelBooking.execute(fbs);

                    // Update the UI to reflect the cancellation
                    JOptionPane.showMessageDialog(this, "Booking successfully canceled for customer ID: "
                            + customerId + " and flight ID: " + flightId);

                    // Close the window
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (FlightBookingSystemException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter valid customer ID and flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
