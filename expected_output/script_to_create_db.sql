CREATE DATABASE ticket_reservation_system;
USE ticket_reservation_system;

-- Create Users Table
CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create Tickets Table
CREATE TABLE Ticket (
    ticket_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_quantity INT NOT NULL,
    remaining_quantity INT NOT NULL,
    status ENUM('AVAILABLE', 'BOOKED') DEFAULT 'AVAILABLE'
);

-- Create Bookings Table
CREATE TABLE Booking (
    booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    ticket_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    booking_time DATETIME NOT NULL,
    confirmation_time DATETIME NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELED') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
);

-- Create Payments Table
CREATE TABLE Payment (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    amount DECIMAL(10, 2) NOT NULL,
    payment_time DATETIME NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'BANK_TRANSFER', 'CASH'),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);

-- Create Refunds Table
CREATE TABLE Refund (
    refund_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    refund_amount DECIMAL(10, 2) NOT NULL,
    refund_time DATETIME NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);
