INSERT INTO department (id, name, parent_id, leader_id, sort, status) VALUES
(1, 'Head Office', 0, 1, 1, 1),
(2, 'Human Resources', 1, 3, 2, 1),
(3, 'Engineering', 1, 1, 3, 1);

INSERT INTO position (id, name, level, min_salary, max_salary, description, status) VALUES
(1, 'General Manager', 10, 30000, 50000, 'Company manager', 1),
(2, 'HR Specialist', 4, 6000, 10000, 'HR operations', 1),
(3, 'Engineer', 4, 8000, 15000, 'Engineering role', 1);

INSERT INTO employee (id, emp_no, name, gender, phone, email, id_card, birthday, address, department_id, position_id, entry_date, leave_date, status) VALUES
(1, 'EMP001', 'Admin Employee', 'M', '13800138001', 'admin@example.com', '110101199001011234', '1990-01-01', 'Beijing', 3, 1, '2020-03-15', NULL, 'regular'),
(2, 'EMP002', 'HR Employee', 'F', '13800138002', 'hr@example.com', '110101199103021234', '1991-03-02', 'Beijing', 2, 2, '2021-06-01', NULL, 'regular'),
(3, 'EMP003', 'Normal Employee', 'M', '13800138003', 'employee@example.com', '110101199205031234', '1992-05-03', 'Beijing', 3, 3, '2022-01-10', NULL, 'regular');

INSERT INTO user (id, username, password, name, avatar, role, employee_id, status) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'Admin', NULL, 'admin', 1, 1),
(2, 'hr', 'e10adc3949ba59abbe56e057f20f883e', 'HR', NULL, 'hr', 2, 1),
(3, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', 'Zhangsan', NULL, 'employee', 3, 1);

INSERT INTO salary (id, employee_id, year_month, base_salary, performance, allowance, deduction, actual_salary, status, pay_date) VALUES
(1, 3, '2026-04', 10000, 1000, 500, 100, 11400, 'paid', '2026-04-30');

INSERT INTO recruitment (id, position_id, department_id, count, salary_range, requirements, status) VALUES
(1, 3, 3, 2, '10000-18000', 'Java and Spring Boot experience', 'open');

INSERT INTO resume (id, recruitment_id, name, phone, email, education, experience, status) VALUES
(1, 1, 'Candidate A', '13900139001', 'candidate@example.com', 'Bachelor', '3 years Java', 'pending');

INSERT INTO training (id, title, content, trainer, start_time, end_time, location, max_count, status) VALUES
(1, 'Spring Boot Training', 'Backend best practices', 'Tech Lead', '2026-06-01 09:00:00', '2026-06-01 17:00:00', 'Room 301', 30, 1);

INSERT INTO contract (id, employee_id, contract_no, type, start_date, end_date, status) VALUES
(1, 3, 'HT2026001', 'Formal', '2026-01-01', '2028-12-31', 'active');

INSERT INTO announcement (id, title, content, publisher_id, is_top, status) VALUES
(1, 'HRM Notice', 'Welcome to the HRM system.', 1, 1, 1);
