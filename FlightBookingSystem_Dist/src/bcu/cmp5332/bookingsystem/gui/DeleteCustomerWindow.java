package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;

/**
 * The DeleteCustomerWindow class represents a graphical user interface window for deleting a customer
 * in the Flight Booking System application.
 *
 * It allows the user to input a customer ID to delete the customer from the system.
 *
 * This class extends the JFrame class to create a window.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class DeleteCustomerWindow extends JFrame {
    private FlightBookingSystem fbs;

    /**
     * Constructs a new DeleteCustomerWindow.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public DeleteCustomerWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the DeleteCustomerWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Delete Customer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        JLabel customerIdLabel = new JLabel("Enter Customer ID:");
        customerIdLabel.setForeground(Color.BLACK); // Black text for contrast
        customerIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField customerIdField = new JTextField();
        customerIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        deleteButton.setForeground(Color.BLACK); // Black text color
        deleteButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Dark gray border
        deleteButton.setFocusPainted(false); // Remove focus border

        deleteButton.addActionListener(e -> {
            String input = customerIdField.getText();
            if (input != null && !input.isEmpty()) {
                try {
                    int customerId = Integer.parseInt(input);
                    deleteCustomer(customerId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(customerIdLabel);
        panel.add(customerIdField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(deleteButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Deletes a customer with the specified customer ID from the FlightBookingSystem.
     *
     * @param customerId the ID of the customer to delete
     */
    private void deleteCustomer(int customerId) {
        try {
            fbs.deleteCustomer(customerId);
            JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
