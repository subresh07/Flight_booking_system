package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;

/**
 * The LoadingWindow class represents a graphical user interface window for loading the Flight Booking System.
 * It displays a loading animation and a message while the system data is being loaded.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
public class LoadingWindow extends JFrame {
    private JLabel gearLabel;
    private Timer timer;
    private int angle = 0;
    private FlightBookingSystem fbs;

    /**
     * Constructs a new LoadingWindow.
     */
    public LoadingWindow() {
        setTitle("Loading...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the window to maximize and fit the screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 50));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Loading Flight Booking System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 50, 0);
        panel.add(welcomeLabel, gbc);

        // Gear Image
        ImageIcon gearIcon = new ImageIcon("resources/images/gear.png");
        Image gearImage = gearIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size as needed
        gearLabel = new JLabel(new ImageIcon(gearImage));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(gearLabel, gbc);

        loadFlightBookingSystem();
        startLoading();
    }

    /**
     * Loads the FlightBookingSystem data.
     */
    private void loadFlightBookingSystem() {
        try {
            fbs = FlightBookingSystemData.load();
        } catch (IOException | FlightBookingSystemException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading flight booking system.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Starts the loading animation and transitions to the MainWindow after loading is complete.
     */
    private void startLoading() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                angle += 10;
                rotateGear();
                if (angle >= 360) {
                    angle = 0;
                }
                if (angle % 360 == 0) { // Adjust the condition to stop loading after a certain time
                    timer.cancel();
                    dispose();
                    SwingUtilities.invokeLater(() -> new MainWindow(fbs).setVisible(true));
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);  // Adjust the delay as needed
    }

    /**
     * Rotates the gear image to create a loading animation.
     */
    private void rotateGear() {
        Icon icon = gearLabel.getIcon();
        if (icon instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) icon;
            BufferedImage bufferedImage = new BufferedImage(
                imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.rotate(Math.toRadians(angle), imageIcon.getIconWidth() / 2, imageIcon.getIconHeight() / 2);
            g2d.drawImage(imageIcon.getImage(), 0, 0, null);
            g2d.dispose();
            gearLabel.setIcon(new ImageIcon(bufferedImage));
        }
    }

    /**
     * The main method to start the LoadingWindow.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoadingWindow().setVisible(true));
    }
}
