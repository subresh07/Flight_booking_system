package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A JPanel for displaying bookings in a table format.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class DisplayBookingsWindow extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image backgroundImage;

    /**
     * Constructs a new DisplayBookingsWindow.
     */
    public DisplayBookingsWindow() {
        try {
            backgroundImage = new ImageIcon("resources/images/background.jpg").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();
    }

    /**
     * Initializes the DisplayBookingsWindow.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Booking Details", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columns = {"Booking ID", "Customer Name", "Flight Number", "Booking Date", "Price", "Status"};
        Object[][] data = readBookingsFromFile("resources/data/bookings.txt");

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
     * Reads bookings from a file and converts them into a 2D array.
     *
     * @param filename The name of the file containing bookings.
     * @return A 2D array representing bookings data.
     */
    private Object[][] readBookingsFromFile(String filename) {
        List<Object[]> bookingsData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int bookingId = Integer.parseInt(parts[0]);
                String customerName = getCustomerName(Integer.parseInt(parts[1]));
                String flightNumber = getFlightNumber(Integer.parseInt(parts[2]));
                String bookingDate = parts[3];
                double price = Double.parseDouble(parts[4]);
                String status = parts.length > 5 && parts[5].equals("cancelled") ? "Cancelled" : "Active";

                bookingsData.add(new Object[]{bookingId, customerName, flightNumber, bookingDate, price, status});
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading bookings from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bookingsData.toArray(new Object[0][]);
    }

    /**
     * Retrieves the name of a customer.
     *
     * @param customerId The ID of the customer.
     * @return The name of the customer.
     */
    private String getCustomerName(int customerId) {
        return "Customer " + customerId;
    }

    /**
     * Retrieves the flight number.
     *
     * @param flightId The ID of the flight.
     * @return The flight number.
     */
    private String getFlightNumber(int flightId) {
        return "Flight " + flightId;
    }

    /**
     * Applies custom styling to the JTable.
     *
     * @param table The JTable to style.
     */
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setBackground(Color.WHITE); // White background for the table
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new GradientTableCellRenderer());
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setBackground(new Color(70, 130, 180)); // Steel blue for header background
        tableHeader.setForeground(Color.WHITE); // White text for header

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
        private Border darkBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cellLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cellLabel.setHorizontalAlignment(JLabel.CENTER);
            cellLabel.setForeground(Color.BLACK); // Black text for better contrast
            cellLabel.setBorder(darkBorder); // Light border for cells
            return cellLabel;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Color startColor = new Color(240, 240, 240);
            Color endColor = new Color(210, 210, 210);
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, 0, height, endColor);
            g2.setPaint(gp);
            g2.fillRect(0, 0, width, height);
            super.paintComponent(g);
        }
    }
}
