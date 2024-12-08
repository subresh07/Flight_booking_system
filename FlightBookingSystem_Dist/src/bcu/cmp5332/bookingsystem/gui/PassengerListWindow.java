package bcu.cmp5332.bookingsystem.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * A JFrame for displaying the list of passengers for a flight.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class PassengerListWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PassengerListWindow.
     *
     * @param passengers   The list of passengers.
     * @param flightNumber The flight number.
     */
    public PassengerListWindow(List<Customer> passengers, String flightNumber) {
        setTitle("Passenger List for Flight " + flightNumber);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background

        // Create heading label
        JLabel headingLabel = new JLabel("Passenger List", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create panel for the table
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(245, 245, 245)); // Match background color

        String[] columnNames = {"ID", "Name", "Phone", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Filter out cancelled bookings from the list of passengers
        List<Customer> activePassengers = passengers.stream()
                .filter(customer -> !customer.isCancelled())
                .collect(Collectors.toList());

        for (Customer passenger : activePassengers) {
            Object[] rowData = {passenger.getId(), passenger.getName(), passenger.getPhone(), passenger.getEmail()};
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(Color.BLACK); // Black text
        table.setBackground(Color.WHITE); // White background for the table
        table.setGridColor(Color.LIGHT_GRAY); // Light gray grid lines for better visibility
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180)); // Steel blue header background
        table.getTableHeader().setForeground(Color.BLACK); // Black header text

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE); // Ensure the viewport background matches
        tablePanel.add(scrollPane);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(headingLabel, java.awt.BorderLayout.NORTH);
        getContentPane().add(tablePanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
