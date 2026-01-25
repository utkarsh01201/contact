import java.util.*;
import java.io.*;

/**
 * Demo version of Contact Management System that works WITHOUT MySQL
 * Perfect for testing the UI/business logic before database setup
 */
public class MainAppDemo {
    private static List<ContactDemo> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;

    public static void main(String[] args) {
        // Add sample data
        addSampleData();
        
        System.out.println("========================================");
        System.out.println("   CONTACT MANAGEMENT SYSTEM (DEMO)");
        System.out.println("   (No Database - Testing Mode)");
        System.out.println("========================================");
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewAllContacts();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    updateContact();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    viewContactDetails();
                    break;
                case 7:
                    displayStats();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using Contact Management System!");
                    System.out.println("DEMO MODE - Data not saved. Set up MySQL to persist data.\n");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
        scanner.close();
    }

    private static void addSampleData() {
        // No sample data - start with empty list
    }

    private static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("           MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Add New Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. View Contact Details");
        System.out.println("7. View Statistics");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    private static void addContact() {
        System.out.println("\n--- Add New Contact ---");
        String name = getStringInput("Enter name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone number: ");
        String address = getStringInput("Enter address: ");

        ContactDemo contact = new ContactDemo(nextId++, name, email, phone, address);
        contacts.add(contact);
        System.out.println("✓ Contact added successfully!\n");
    }

    private static void viewAllContacts() {
        System.out.println("\n--- All Contacts ---");

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.\n");
            return;
        }

        System.out.printf("%-5s %-20s %-30s %-15s %-30s%n", "ID", "Name", "Email", "Phone", "Address");
        System.out.println("=".repeat(105));

        for (ContactDemo contact : contacts) {
            System.out.printf("%-5d %-20s %-30s %-15s %-30s%n",
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getAddress());
        }
        System.out.println();
    }

    private static void searchContact() {
        System.out.println("\n--- Search Contact ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Email");

        int choice = getIntInput("Enter search option: ");
        List<ContactDemo> results = new ArrayList<>();

        if (choice == 1) {
            String name = getStringInput("Enter name to search: ");
            for (ContactDemo c : contacts) {
                if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                    results.add(c);
                }
            }
        } else if (choice == 2) {
            String email = getStringInput("Enter email to search: ");
            for (ContactDemo c : contacts) {
                if (c.getEmail().toLowerCase().contains(email.toLowerCase())) {
                    results.add(c);
                }
            }
        } else {
            System.out.println("Invalid search option!\n");
            return;
        }

        if (results.isEmpty()) {
            System.out.println("No contacts found matching your search.\n");
            return;
        }

        System.out.printf("%-5s %-20s %-30s %-15s %-30s%n", "ID", "Name", "Email", "Phone", "Address");
        System.out.println("=".repeat(105));

        for (ContactDemo contact : results) {
            System.out.printf("%-5d %-20s %-30s %-15s %-30s%n",
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getAddress());
        }
        System.out.println();
    }

    private static void updateContact() {
        System.out.println("\n--- Update Contact ---");
        int id = getIntInput("Enter contact ID to update: ");

        ContactDemo contact = null;
        for (ContactDemo c : contacts) {
            if (c.getId() == id) {
                contact = c;
                break;
            }
        }

        if (contact == null) {
            System.out.println("Contact not found!\n");
            return;
        }

        System.out.println("Current Details: " + contact);

        String name = getStringInput("Enter new name (or press Enter to skip): ");
        String email = getStringInput("Enter new email (or press Enter to skip): ");
        String phone = getStringInput("Enter new phone (or press Enter to skip): ");
        String address = getStringInput("Enter new address (or press Enter to skip): ");

        if (!name.isEmpty()) contact.setName(name);
        if (!email.isEmpty()) contact.setEmail(email);
        if (!phone.isEmpty()) contact.setPhone(phone);
        if (!address.isEmpty()) contact.setAddress(address);

        System.out.println("✓ Contact updated successfully!\n");
    }

    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");
        int id = getIntInput("Enter contact ID to delete: ");

        ContactDemo contact = null;
        for (ContactDemo c : contacts) {
            if (c.getId() == id) {
                contact = c;
                break;
            }
        }

        if (contact == null) {
            System.out.println("Contact not found!\n");
            return;
        }

        System.out.println("Contact to delete: " + contact);
        String confirm = getStringInput("Are you sure? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes")) {
            contacts.remove(contact);
            System.out.println("✓ Contact deleted successfully!\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }
    }

    private static void viewContactDetails() {
        System.out.println("\n--- View Contact Details ---");
        int id = getIntInput("Enter contact ID: ");

        ContactDemo contact = null;
        for (ContactDemo c : contacts) {
            if (c.getId() == id) {
                contact = c;
                break;
            }
        }

        if (contact == null) {
            System.out.println("Contact not found!\n");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("         CONTACT DETAILS");
        System.out.println("========================================");
        System.out.println("ID       : " + contact.getId());
        System.out.println("Name     : " + contact.getName());
        System.out.println("Email    : " + contact.getEmail());
        System.out.println("Phone    : " + contact.getPhone());
        System.out.println("Address  : " + contact.getAddress());
        System.out.println("========================================\n");
    }

    private static void displayStats() {
        System.out.println("\n========================================");
        System.out.println("       CONTACT STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Contacts : " + contacts.size());
        System.out.println("========================================\n");
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    // Simple Contact class for demo
    static class ContactDemo {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String address;

        public ContactDemo(int id, String name, String email, String phone, String address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        @Override
        public String toString() {
            return "Contact{id=" + id + ", name='" + name + "', email='" + email + "', phone='" + phone + "', address='" + address + "'}";
        }
    }
}
