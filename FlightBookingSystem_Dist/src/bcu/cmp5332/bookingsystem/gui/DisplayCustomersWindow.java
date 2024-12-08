package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * The DisplayCustomersWindow class represents a JPanel for displaying customers in a table format.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class DisplayCustomersWindow extends JPanel {

    private static final long serialVersionUID = 1L;
    private FlightBookingSystem fbs;
    private Image backgroundImage;

    /**
     * Constructs a new DisplayCustomersWindow.
     *
     * @param fbs the FlightBookingSystem instance
     */
    public DisplayCustomersWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        try {
            backgroundImage = new ImageIcon("resources/images/background.jpg").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();
    }

    /**
     * Initializes the DisplayCustomersWindow by setting up the UI components.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Customer Details", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(70, 130, 180)); // Steel blue for heading background
        headingLabel.setForeground(Color.WHITE); // White text for contrast
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columns = {"ID", "Name", "Phone", "Email", "Number of Active Bookings"};
        Object[][] data = getCustomersData();

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
     * Retrieves customer data from the FlightBookingSystem and converts it into a 2D array.
     *
     * @return A 2D array representing customer data.
     */
    private Object[][] getCustomersData() {
        List<Customer> customersList = fbs.getCustomers();
        Object[][] data = new Object[customersList.size()][5];

        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            int activeBookingsCount = customer.getActiveBookings().size();
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = activeBookingsCount;
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
        table.setBackground(Color.WHITE); // White background for the table
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new GradientTableCellRenderer());
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setBackground(new Color(70, 130, 180)); // Steel blue header background
        tableHeader.setForeground(Color.WHITE); // White header text

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
        private Border lightBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cellLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cellLabel.setHorizontalAlignment(JLabel.CENTER);
            cellLabel.setForeground(Color.BLACK); // Black text for better contrast
            cellLabel.setBorder(lightBorder); // Light border for cells
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
