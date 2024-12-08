package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The DisplayFlightsWindow class represents a JPanel for displaying flights in a table format.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class DisplayFlightsWindow extends JPanel {

    private static final long serialVersionUID = 1L;
    private FlightBookingSystem fbs;
    private Image backgroundImage;

    /**
     * Constructs a new DisplayFlightsWindow.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public DisplayFlightsWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        try {
            backgroundImage = new ImageIcon("resources/images/background.jpg").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();
    }

    /**
     * Initializes the DisplayFlightsWindow by setting up the UI components.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Flight Details", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(0, 51, 102)); // Dark blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        String[] columns = {"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Number of Seats", "Price", "Fully Booked", "Departed"};
        Object[][] data = getFlightsData();

        JTable table = new JTable(data, columns);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(headingLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Retrieves flight data from the FlightBookingSystem and converts it into a 2D array.
     *
     * @return A 2D array representing flight data.
     */
    private Object[][] getFlightsData() {
        List<Flight> flightsList = fbs.getFlights();
        LocalDate today = LocalDate.now();

        Object[][] data = new Object[flightsList.size()][9];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            boolean fullyBooked = flight.isFullyBooked();
            boolean departed = !flight.hasNotDeparted(today);

            String price;
            try {
                price = String.valueOf(flight.calculatePrice(today));
            } catch (FlightBookingSystemException ex) {
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

        return data;
    }

    /**
     * Applies custom styling to the JTable.
     *
     * @param table The JTable to style.
     */
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setBackground(new Color(255, 255, 255)); // White color for the table
        table.setForeground(Color.BLACK);
        table.setGridColor(new Color(192, 192, 192)); // Light gray grid color

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setBackground(new Color(0, 51, 102)); // Dark blue for header background
        tableHeader.setForeground(Color.WHITE); // White text for header
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 35));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBackground(new Color(0, 51, 102));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        GradientTableCellRenderer gradientRenderer = new GradientTableCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(gradientRenderer);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Custom table cell renderer to apply gradient background to the cells.
     */
    private class GradientTableCellRenderer extends DefaultTableCellRenderer {
        private Border darkBorder = BorderFactory.createLineBorder(new Color(0, 51, 102), 1); // Dark blue border

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cellLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cellLabel.setHorizontalAlignment(JLabel.CENTER);
            cellLabel.setForeground(Color.BLACK); // Black text for better contrast
            cellLabel.setBorder(darkBorder); // Dark border for cells
            return cellLabel;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Color startColor = new Color(240, 248, 255); // Alice blue for gradient start
            Color endColor = new Color(173, 216, 230); // Light blue for gradient end
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, 0, height, endColor);
            g2.setPaint(gp);
            g2.fillRect(0, 0, width, height);
            super.paintComponent(g);
        }
    }
}
