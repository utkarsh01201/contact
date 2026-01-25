import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    // Create a new contact
    public boolean addContact(Contact contact) {
        String query = "INSERT INTO contacts (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getPhone());
            stmt.setString(4, contact.getAddress());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding contact: " + e.getMessage());
            return false;
        }
    }

    // Get all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving contacts: " + e.getMessage());
        }
        return contacts;
    }

    // Get contact by ID
    public Contact getContactById(int id) {
        String query = "SELECT * FROM contacts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contact(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving contact: " + e.getMessage());
        }
        return null;
    }

    // Update a contact
    public boolean updateContact(Contact contact) {
        String query = "UPDATE contacts SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getPhone());
            stmt.setString(4, contact.getAddress());
            stmt.setInt(5, contact.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating contact: " + e.getMessage());
            return false;
        }
    }

    // Delete a contact
    public boolean deleteContact(int id) {
        String query = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }

    // Search contacts by name
    public List<Contact> searchByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching contacts: " + e.getMessage());
        }
        return contacts;
    }

    // Search contacts by email
    public List<Contact> searchByEmail(String email) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE email LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + email + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching contacts: " + e.getMessage());
        }
        return contacts;
    }

    // Get total number of contacts
    public int getTotalContacts() {
        String query = "SELECT COUNT(*) as count FROM contacts";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("Error counting contacts: " + e.getMessage());
        }
        return 0;
    }
}
