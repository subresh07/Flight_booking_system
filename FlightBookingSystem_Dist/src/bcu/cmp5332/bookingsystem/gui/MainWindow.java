package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The MainWindow class represents the main graphical user interface window for the Flight Booking System.
 * It provides access to various functionalities such as viewing, adding, updating, and deleting flights,
 * bookings, and customers.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class MainWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JButton btnViewFlights;
    private JButton btnAddFlight;
    private JButton btnDeleteFlight;
    private JButton btnPassengerList;
    private JButton btnAddBooking;
    private JButton btnUpdateBooking;
    private JButton btnCancelBooking;
    private JButton btnViewBookings;
    private JButton btnViewCustomers;
    private JButton btnAddCustomer;
    private JButton btnDeleteCustomer;
    private JButton btnLogout; // Logout button

    private FlightBookingSystem fbs;

    /**
     * Constructs a new MainWindow.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public MainWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
        setTitle("Sunway Flights");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Returns the FlightBookingSystem instance.
     *
     * @return the FlightBookingSystem instance
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initializes the MainWindow.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTitle("Flight Booking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar Panel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(4, 3, 10, 10)); // 4 rows, 3 columns with 10px gaps
        sidebarPanel.setPreferredSize(new Dimension(getWidth(), 200));
        sidebarPanel.setBackground(new Color(40, 44, 52));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Buttons
        btnViewFlights = createSidebarButton("View Flights");
        btnAddFlight = createSidebarButton("Add Flight");
        btnDeleteFlight = createSidebarButton("Delete Flight");
        btnPassengerList = createSidebarButton("Passenger List");
        btnAddBooking = createSidebarButton("Add Booking");
        btnUpdateBooking = createSidebarButton("Update Booking");
        btnCancelBooking = createSidebarButton("Cancel Booking");
        btnViewBookings = createSidebarButton("View Bookings");
        btnViewCustomers = createSidebarButton("View Customers");
        btnAddCustomer = createSidebarButton("Add Customer");
        btnDeleteCustomer = createSidebarButton("Delete Customer");
        btnLogout = createSidebarButton("Logout");

        // Adding Buttons to Sidebar
        sidebarPanel.add(btnViewFlights);
        sidebarPanel.add(btnAddFlight);
        sidebarPanel.add(btnDeleteFlight);
        sidebarPanel.add(btnPassengerList);
        sidebarPanel.add(btnAddBooking);
        sidebarPanel.add(btnUpdateBooking);
        sidebarPanel.add(btnCancelBooking);
        sidebarPanel.add(btnViewBookings);
        sidebarPanel.add(btnViewCustomers);
        sidebarPanel.add(btnAddCustomer);
        sidebarPanel.add(btnDeleteCustomer);
        sidebarPanel.add(btnLogout);

        add(sidebarPanel, BorderLayout.NORTH);

        // Main Panel
        mainPanel = new BackgroundPanel("resources/images/background.jpg");
        mainPanel.setLayout(new BorderLayout()); // Use BorderLayout for main content
        add(mainPanel, BorderLayout.CENTER);

        JLabel welcomeLabel = new JLabel("<html><center>WELCOME TO<br><br>OUR<br><br> FLIGHT MANAGEMENT SYSTEM<br></center></html>");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
    }

    /**
     * Creates a sidebar button with the specified text.
     *
     * @param text the text to display on the button
     * @return the created JButton
     */
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 50)); // Adjust button size
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));  // Initial background color
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(this);

        // Make button round
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.setMaximumSize(new Dimension(150, 50));
        button.setMinimumSize(new Dimension(150, 50));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                c.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                c.setBackground(new Color(70, 130, 180));
                c.setForeground(Color.WHITE);
                c.setPreferredSize(new Dimension(150, 50));
                c.setMaximumSize(new Dimension(150, 50));
                c.setMinimumSize(new Dimension(150, 50));
                c.setFont(new Font("Arial", Font.BOLD, 16));
                c.setFocusable(false);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g, c);
            }
        });

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));  // Lighter color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));  // Revert back to original color
            }
        });

        return button;
    }

    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == btnViewFlights) {
            mainPanel.removeAll();
            DisplayFlightsWindow flightsWindow = new DisplayFlightsWindow(fbs);
            mainPanel.add(flightsWindow, BorderLayout.CENTER);
            mainPanel.revalidate();
        } else if (source == btnAddFlight) {
            new AddFlightWindow(this);
        } else if (source == btnDeleteFlight) {
            new DeleteFlightWindow(fbs);
        } else if (source == btnPassengerList) {
            displayPassengerList();
        } else if (source == btnAddBooking) {
            new AddBookingWindow(this);
        } else if (source == btnCancelBooking) {
            new CancelBookingWindow(this);
        } else if (source == btnUpdateBooking) {
            BookingDataManager bookingDataManager = new BookingDataManager();
            new UpdateBookingWindow(bookingDataManager, fbs);
        } else if (source == btnViewBookings) {
            mainPanel.removeAll();
            DisplayBookingsWindow bookingsWindow = new DisplayBookingsWindow();
            mainPanel.add(bookingsWindow, BorderLayout.CENTER);
            mainPanel.revalidate();
        } else if (source == btnViewCustomers) {
            mainPanel.removeAll();
            DisplayCustomersWindow customersWindow = new DisplayCustomersWindow(fbs);
            mainPanel.add(customersWindow, BorderLayout.CENTER);
            mainPanel.revalidate();
        } else if (source == btnAddCustomer) {
            new AddCustomerWindow(this);
        } else if (source == btnDeleteCustomer) {
            new DeleteCustomerWindow(fbs);
        } else if (source == btnLogout) {
            this.dispose(); // Close the current window
            new LoginWindow(fbs).setVisible(true); // Show the login window
        }
    }

    public void showDisplayCustomersWindow() {
        mainPanel.removeAll();
        DisplayCustomersWindow customersWindow = new DisplayCustomersWindow(fbs);
        mainPanel.add(customersWindow, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showBookingDetails(Customer customer) {
        List<Booking> bookings = customer.getBookings();

        String[] columns = {"Booking ID", "Flight", "Booking Date", "Price", "Cancellation Fee", "Rebook Fee"};
        Object[][] data = new Object[bookings.size()][6];
        int i = 0;
        for (Booking booking : bookings) {
            data[i][0] = booking.getId();
            data[i][1] = booking.getFlight().getFlightNumber();
            data[i][2] = booking.getBookingDate();
            data[i][3] = booking.getPrice();
            data[i][4] = booking.getCancellationFee();
            data[i][5] = booking.getRebookFee();
            i++;
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Booking Details");
        frame.getContentPane().add(scrollPane);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        LocalDate today = LocalDate.now();

        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Number of Seats", "Price", "Fully Booked", "Departed"};

        Object[][] data = new Object[flightsList.size()][9];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            boolean fullyBooked = flight.isFullyBooked();
            boolean departed = !flight.hasNotDeparted(today); // Check if flight has departed

            String price;
            try {
                price = String.valueOf(flight.calculatePrice(today));
            } catch (FlightBookingSystemException ex) {
                // Handle the exception by setting a placeholder message for the price
                price = "Price calculation error";
            }

            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = flight.getNumberOfSeats();
            data[i][6] = price;
            data[i][7] = fullyBooked ? "Yes" : "No";
            data[i][8] = departed ? "Yes" : "No";
        }

        JTable table = new JTable(data, columns);
        mainPanel.removeAll();
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayPassengerList() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
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

        UIManager.put("OptionPane.background", new Color(240, 248, 255)); // Light blue background
        UIManager.put("Panel.background", new Color(240, 248, 255)); // Light blue background

        int result = JOptionPane.showConfirmDialog(this, panel, "Passenger List", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String input = inputField.getText();
        if (result == JOptionPane.OK_OPTION && !input.isEmpty()) {
            try {
                int flightId = Integer.parseInt(input);
                Flight flight = fbs.getFlightByID(flightId);
                if (flight != null) {
                    List<Customer> passengers = flight.getPassengers();
                    new PassengerListWindow(passengers, flight.getFlightNumber());
                } else {
                    UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FlightBookingSystemException ex) {
                UIManager.put("OptionPane.messageForeground", Color.RED); // Red text for error message
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * The BackgroundPanel class is a custom JPanel that displays a background image.
     */
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        /**
         * Constructs a new BackgroundPanel with the specified image path.
         *
         * @param imagePath the path to the background image
         */
        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(60, 63, 65)); // Default background color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}
