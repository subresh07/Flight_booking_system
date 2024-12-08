/**
 * Command to add a new customer to the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to add a new customer to the flight booking system.
 */
public class AddCustomer implements Command {

    private final String name; // Name of the customer
    private final String phone; // Phone number of the customer
    private final String email; // Email address of the customer

    /**
     * Constructs an AddCustomer command with the specified name, phone, and email.
     *
     * @param name  the name of the customer
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command to add a new customer to the flight booking system.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        // Get the maximum customer ID currently in the system
        int maxId = 0;
        if (!fbs.getCustomers().isEmpty()) {
            maxId = fbs.getCustomers().stream().mapToInt(Customer::getId).max().orElse(0);
        }

        // Create a new customer object with the next available ID
        Customer customer = new Customer(++maxId, name, phone, email);
        fbs.addCustomer(customer); // Add the customer to the flight booking system
        System.out.println("Customer #" + customer.getId() + " added.");

        // Write customer data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/customers.txt", true))) {
            writer.write(customer.getId() + "," + customer.getName() + "," + customer.getPhone() + "," + customer.getEmail());
            writer.newLine();
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error writing to customers.txt: " + e.getMessage());
        }
    }
}
