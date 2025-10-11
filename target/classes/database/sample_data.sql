-- Sample Data for Payroll Management System

-- Insert default users
INSERT INTO login (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('hr', 'hr123', 'hr');

-- Insert sample employees
INSERT INTO employee (emp_id, name, gender, address, state, city, email, phone) VALUES
('EMP001', 'John Smith', 'Male', '123 Main St', 'California', 'Los Angeles', 'john.smith@company.com', '555-0101'),
('EMP002', 'Jane Doe', 'Female', '456 Oak Ave', 'New York', 'New York City', 'jane.doe@company.com', '555-0102'),
('EMP003', 'Robert Johnson', 'Male', '789 Pine St', 'Texas', 'Houston', 'robert.johnson@company.com', '555-0103'),
('EMP004', 'Sarah Williams', 'Female', '321 Elm St', 'Florida', 'Miami', 'sarah.williams@company.com', '555-0104'),
('EMP005', 'Michael Brown', 'Male', '654 Maple Ave', 'Illinois', 'Chicago', 'michael.brown@company.com', '555-0105');

-- Insert sample salary data
INSERT INTO salary (emp_id, basic_salary, hra, da, med, pf, advance) VALUES
('EMP001', 50000.00, 5000.00, 2500.00, 1000.00, 2000.00, 0.00),
('EMP002', 55000.00, 5500.00, 2750.00, 1000.00, 2200.00, 1000.00),
('EMP003', 48000.00, 4800.00, 2400.00, 1000.00, 1920.00, 500.00),
('EMP004', 52000.00, 5200.00, 2600.00, 1000.00, 2080.00, 0.00),
('EMP005', 60000.00, 6000.00, 3000.00, 1000.00, 2400.00, 2000.00);