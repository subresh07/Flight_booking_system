/**
 * Command to list all customers in the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to list all customers in the flight booking system.
 */
public class ListCustomers implements Command {

    /**
     * Executes the command to list all customers in the flight booking system.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while executing the command
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Customer> customers = readCustomersFromFile("resources/data/customers.txt");
        for (Customer customer : customers) {
            System.out.println(customer.getDetailsShort());
        }
        System.out.println(customers.size() + " customer(s)");
    }

    /**
     * Reads the list of customers from the specified file.
     *
     * @param filename the name of the file containing customer data
     * @return a list of customers
     * @throws FlightBookingSystemException if an error occurs while reading the file
     */
    private List<Customer> readCustomersFromFile(String filename) throws FlightBookingSystemException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String phone = parts[2];
                String email = parts[3];
                Customer customer = new Customer(id, name, phone, email);
                customers.add(customer);
            }
        } catch (IOException | NumberFormatException e) {
            throw new FlightBookingSystemException("Error reading customers from file: " + e.getMessage());
        }
        return customers;
    }
}
