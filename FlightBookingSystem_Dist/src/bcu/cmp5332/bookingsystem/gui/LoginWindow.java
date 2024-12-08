package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

/**
 * The LoginWindow class represents a graphical user interface window for logging into the Flight Booking System.
 * It prompts the user to enter a username and password, and upon successful authentication, it transitions to the main application window.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private final FlightBookingSystem flightBookingSystem;

    /**
     * Constructs a new LoginWindow.
     *
     * @param flightBookingSystem the FlightBookingSystem instance
     */
    public LoginWindow(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;

        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set background image
        JLabel background = new JLabel(new ImageIcon("resources/images/background.jpg"));
        background.setLayout(new GridBagLayout());
        setContentPane(background);
        GridBagConstraints gbc = new GridBagConstraints();

        // Add logo
        ImageIcon logoIcon = new ImageIcon("resources/images/logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 2;
        add(logoLabel, gbc);

        // Login form panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints formGbc = new GridBagConstraints();

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.BLACK); // Set to black
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, formGbc);

        // Username field
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setForeground(Color.BLACK); // Set to black
        formGbc.gridx = 1;
        formGbc.gridy = 0;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, formGbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.BLACK); // Set to black
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, formGbc);

        // Password field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setForeground(Color.BLACK); // Set to black
        formGbc.gridx = 1;
        formGbc.gridy = 1;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, formGbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2;
        formGbc.insets = new Insets(10, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, formGbc);

        // Add panel to the center
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        add(panel, gbc);

        // Action Listener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Updated validation logic
                if (username.equals("admin") && password.equals("admin")) {
                    dispose();
                    new LoadingWindow().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });
    }

    /**
     * The main method to start the LoginWindow.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FlightBookingSystem fbs = FlightBookingSystemData.load();
            new LoginWindow(fbs).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
