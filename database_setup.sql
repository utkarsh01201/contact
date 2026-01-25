-- Create Database
CREATE DATABASE IF NOT EXISTS contact_db;

-- Use the database
USE contact_db;

-- Create Contacts Table
CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    address VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- No sample data - database ready for use
-- Add your own contacts using the application
-- Example: INSERT INTO contacts (name, email, phone, address) VALUES ('Your Name', 'your.email@example.com', '1234567890', 'Your Address');
