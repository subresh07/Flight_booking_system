/**
 * The CustomerDataManager class is responsible for loading and storing customer data to and from a file
 * for the Flight Booking System application.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The CustomerDataManager class is responsible for loading and storing customer data to and from a file
 * for the Flight Booking System application.
 *
 * It implements the DataManager interface and provides methods to load customer data from a text file
 * and store customer data to a text file.
 */
public class CustomerDataManager implements DataManager {

    /** The path to the customer data file. */
    private final String RESOURCE = "./resources/data/customers.txt";

    /** The separator used to separate fields in the data file. */
    private final String SEPARATOR = ",";

    /**
     * Loads customer data from the specified file and updates the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(properties[0]);
                String name = properties[1];
                String phone = properties[2];
                String email = properties[3];
                Customer customer = new Customer(id, name, phone, email);
                fbs.addCustomer(customer);
            }
        }
    }

    /**
     * Stores customer data to the specified file based on the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.println();
            }
        }
    }
}
