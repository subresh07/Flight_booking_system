package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for adding a new customer to the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class AddCustomerWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static AddCustomerWindow instance;

    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddCustomerWindow object with the specified MainWindow.
     *
     * @param mw The MainWindow object.
     */
    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Returns the instance of AddCustomerWindow.
     *
     * @param mw The MainWindow object.
     * @return The AddCustomerWindow instance.
     */
    public static AddCustomerWindow getInstance(MainWindow mw) {
        if (instance == null) {
            instance = new AddCustomerWindow(mw);
        }
        return instance;
    }

    /**
     * Initializes the AddCustomerWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Add Customer");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to manage resources properly

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        JLabel headingLabel = new JLabel("Add Customer:", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(Color.WHITE); // White text
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        contentPanel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10); // Padding around components

        // Labels with larger font size
        JLabel nameLabel = new JLabel("Name:", JLabel.LEFT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(nameLabel, gbc);

        gbc.gridy++;
        nameText.setPreferredSize(new Dimension(200, 25)); // Adjusted width for text field
        formPanel.add(nameText, gbc);

        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone:", JLabel.LEFT);
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(phoneLabel, gbc);

        gbc.gridy++;
        phoneText.setPreferredSize(new Dimension(200, 25)); // Adjusted width for text field
        formPanel.add(phoneText, gbc);

        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:", JLabel.LEFT);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(emailLabel, gbc);

        gbc.gridy++;
        emailText.setPreferredSize(new Dimension(200, 25)); // Adjusted width for text field
        formPanel.add(emailText, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.setBackground(new Color(70, 130, 180)); // Steel blue background for button
        addBtn.setForeground(Color.BLACK); // Black text for button
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(70, 130, 180)); // Steel blue background for button
        cancelBtn.setForeground(Color.BLACK); // Black text for button
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
     * Handles action events for the buttons in the AddCustomerWindow.
     *
     * @param ae the action event
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addCustomer();
        } else if (ae.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    /**
     * Adds a new customer to the flight booking system based on the user input.
     */
    private void addCustomer() {
        try {
            String name = nameText.getText().trim();
            String phone = phoneText.getText().trim();
            String email = emailText.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and execute the AddCustomer Command
            Command addCustomer = new AddCustomer(name, phone, email);
            addCustomer.execute(mw.getFlightBookingSystem());

            // Display success message
            JOptionPane.showMessageDialog(this, "Customer added successfully");

            // Refresh the view with the DisplayCustomersWindow
            mw.showDisplayCustomersWindow();

            // Hide (close) the AddCustomerWindow
            this.dispose();
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
