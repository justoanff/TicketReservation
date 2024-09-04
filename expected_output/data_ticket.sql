-- Insert sample records into User Table
INSERT INTO User (name, email) VALUES 
('Alice Johnson', 'alice.johnson@example.com'),
('Bob Smith', 'bob.smith@example.com'),
('Charlie Davis', 'charlie.davis@example.com'),
('Diana Prince', 'diana.prince@example.com'),
('Edward King', 'edward.king@example.com');

-- Insert sample records into Ticket Table
INSERT INTO Ticket (name, price, total_quantity, remaining_quantity, status) VALUES 
('Concert A', 50.00, 100, 80, 'AVAILABLE'),
('Concert B', 75.00, 150, 150, 'AVAILABLE'),
('Theater C', 40.00, 200, 150, 'AVAILABLE'),
('Festival D', 60.00, 100, 60, 'BOOKED'),
('Sports Event E', 100.00, 300, 290, 'AVAILABLE');

-- Insert sample records into Booking Table
INSERT INTO Booking (user_id, ticket_id, quantity, booking_time, confirmation_time, status) VALUES 
(1, 1, 2, '2024-09-04 10:00:00', '2024-09-04 11:00:00', 'CONFIRMED'),
(2, 2, 3, '2024-09-04 10:15:00', NULL, 'PENDING'),
(3, 3, 1, '2024-09-04 10:30:00', '2024-09-04 10:45:00', 'CONFIRMED'),
(4, 4, 4, '2024-09-04 11:00:00', NULL, 'PENDING'),
(5, 5, 2, '2024-09-04 11:30:00', NULL, 'CANCELED');

-- Insert sample records into Payment Table
INSERT INTO Payment (booking_id, amount, payment_time, payment_method) VALUES 
(1, 100.00, '2024-09-04 11:10:00', 'CREDIT_CARD'),
(3, 40.00, '2024-09-04 10:50:00', 'BANK_TRANSFER'),
(4, 240.00, '2024-09-04 11:05:00', 'CASH'),
(2, 225.00, '2024-09-04 10:20:00', 'CREDIT_CARD'),
(5, 300.00, '2024-09-04 11:35:00', 'BANK_TRANSFER');

-- Insert sample records into Refund Table
INSERT INTO Refund (booking_id, refund_amount, refund_time) VALUES 
(5, 75.00, '2024-09-04 12:00:00'),
(4, 60.00, '2024-09-04 12:10:00'),
(2, 112.50, '2024-09-04 12:20:00'),
(1, 100.00, '2024-09-04 12:30:00'),
(3, 40.00, '2024-09-04 12:40:00');
