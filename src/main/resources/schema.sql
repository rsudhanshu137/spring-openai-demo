CREATE TABLE IF NOT EXISTS support_tickets (
    id INT PRIMARY KEY,
    category VARCHAR(100),
    question VARCHAR(500),
    answer VARCHAR(2000)
);
