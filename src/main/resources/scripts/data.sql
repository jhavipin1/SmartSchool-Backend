-- =============================================================================
-- COMPLETE DATA INITIALIZATION SCRIPT (data.sql)
-- =============================================================================

-- Disable foreign key checks to allow smooth table cleanup if needed during re-runs
SET FOREIGN_KEY_CHECKS = 0;

-- =============================================================================
-- 1. SEED ROLES
-- =============================================================================
INSERT INTO roles (name, description)
VALUES
    ('SUPER_ADMIN', 'Super Administrator with full permissions'),
    ('ADMIN', 'Administrator with management access'),
    ('STUDENT', 'Student role with course and classroom access'),
    ('TEACHER', 'Teacher role for managing classes and grades'),
    ('PARENT', 'Parent role for monitoring student progress'),
    ('ACCOUNTANT', 'Accountant role for financial transactions'),
    ('LIBRARIAN', 'Librarian role for managing school library resources'),
    ('RECEPTIONIST', 'Receptionist role for front-desk visitor management')
ON DUPLICATE KEY UPDATE description=VALUES(description);


-- =============================================================================
-- 2. SEED ENTITY LOOKUPS (CLASSES, SECTIONS, DEPARTMENTS, DESIGNATIONS)
-- =============================================================================

-- Class Names (@Table(name = "classes"))
INSERT INTO classes (class_name)
VALUES
    ('NURSERY'), ('LKG'), ('UKG'), ('CLASS_1'), ('CLASS_2'),
    ('CLASS_3'), ('CLASS_4'), ('CLASS_5'), ('CLASS_6'), ('CLASS_7'),
    ('CLASS_8'), ('CLASS_9'), ('CLASS_10'), ('CLASS_11'), ('CLASS_12')
ON DUPLICATE KEY UPDATE class_name=VALUES(class_name);

-- Sections (@Table(name = "sections"))
-- CROSS JOIN against existing classes guarantees non-null class_name_id
INSERT INTO sections (section_name, class_name_id)
SELECT
    sec.sec_name,
    c.id
FROM classes c
CROSS JOIN (
    SELECT 'A' AS sec_name UNION ALL
    SELECT 'B' UNION ALL
    SELECT 'C' UNION ALL
    SELECT 'D' UNION ALL
    SELECT 'E'
) sec
WHERE c.id IS NOT NULL
ON DUPLICATE KEY UPDATE class_name_id = VALUES(class_name_id);

-- Departments
INSERT INTO departments (department_name, description)
VALUES
    ('ADMINISTRATION', 'ADMINISTRATION Department'),
    ('ACADEMIC', 'ACADEMIC Department'),
    ('FINANCE', 'FINANCE Department'),
    ('FRONT_OFFICE', 'FRONT_OFFICE Department'),
    ('LIBRARY', 'LIBRARY Department'),
    ('STUDENT_SUPPORT', 'STUDENT_SUPPORT Department'),
    ('IT_SUPPORT', 'IT_SUPPORT Department'),
    ('SPORTS', 'SPORTS Department'),
    ('TRANSPORT', 'TRANSPORT Department'),
    ('FACILITIES', 'FACILITIES Department')
ON DUPLICATE KEY UPDATE description=VALUES(description);

-- Designations
INSERT INTO designations (designation_name, description)
VALUES
    ('PRINCIPAL', 'PRINCIPAL Designation'),
    ('VICE_PRINCIPAL', 'VICE_PRINCIPAL Designation'),
    ('HEAD_OF_DEPARTMENT', 'HEAD_OF_DEPARTMENT Designation'),
    ('SENIOR_TEACHER', 'SENIOR_TEACHER Designation'),
    ('FACULTY', 'FACULTY Designation'),
    ('ASSISTANT_TEACHER', 'ASSISTANT_TEACHER Designation'),
    ('FINANCE_MANAGER', 'FINANCE_MANAGER Designation'),
    ('ACCOUNTANT', 'ACCOUNTANT Designation'),
    ('RECEPTIONIST', 'RECEPTIONIST Designation'),
    ('LIBRARIAN', 'LIBRARIAN Designation'),
    ('SCHOOL_COUNSELOR', 'SCHOOL_COUNSELOR Designation'),
    ('SYSTEM_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR Designation'),
    ('SPORTS_DIRECTOR', 'SPORTS_DIRECTOR Designation'),
    ('TRANSPORT_MANAGER', 'TRANSPORT_MANAGER Designation'),
    ('ESTATE_MANAGER', 'ESTATE_MANAGER Designation')
ON DUPLICATE KEY UPDATE description=VALUES(description);


-- =============================================================================
-- 3. SEED DEFAULT SYSTEM USERS
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Super Admin', 'superadmin', 'superadmin@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'SUPER_ADMIN')),
    ('System Admin', 'admin', 'admin@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'ADMIN')),
    ('Amit Sharma', 'student', 'student@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT')),
    ('Dr. Rajesh Verma', 'teacher', 'teacher@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Suresh Sharma', 'parent', 'parent@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Ramesh Gupta', 'accountant', 'accountant@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'ACCOUNTANT')),
    ('Priya Singh', 'librarian', 'librarian@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'LIBRARIAN')),
    ('Anita Roy', 'receptionist', 'receptionist@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'RECEPTIONIST'))
ON DUPLICATE KEY UPDATE email=VALUES(email);


-- =============================================================================
-- 4. SEED PARENT USERS & PARENTS
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Ramesh Kumar', 'parent1', 'parent1@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Sunita Devi', 'parent2', 'parent2@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Vikram Singh', 'parent3', 'parent3@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Anil Mehta', 'parent4', 'parent4@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Pooja Sharma', 'parent5', 'parent5@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT'))
ON DUPLICATE KEY UPDATE email=VALUES(email);

INSERT INTO parents (first_name, last_name, phone, occupation, address, user_id)
SELECT 'Ramesh', 'Kumar', '9876543201', 'Engineer', 'New Delhi', (SELECT id FROM users WHERE username = 'parent1')
UNION ALL SELECT 'Sunita', 'Devi', '9876543202', 'Doctor', 'Noida', (SELECT id FROM users WHERE username = 'parent2')
UNION ALL SELECT 'Vikram', 'Singh', '9876543203', 'Business', 'Gurugram', (SELECT id FROM users WHERE username = 'parent3')
UNION ALL SELECT 'Anil', 'Mehta', '9876543204', 'Accountant', 'Delhi', (SELECT id FROM users WHERE username = 'parent4')
UNION ALL SELECT 'Pooja', 'Sharma', '9876543205', 'Teacher', 'Faridabad', (SELECT id FROM users WHERE username = 'parent5')
ON DUPLICATE KEY UPDATE phone=VALUES(phone);


-- =============================================================================
-- 5. SEED STAFF USERS & STAFF MEMBERS
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Aarav Sharma', 'staff1', 'staff1@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Neha Kapoor', 'staff2', 'staff2@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Rohan Verma', 'staff3', 'staff3@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Simran Kaur', 'staff4', 'staff4@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Alok Nath', 'staff5', 'staff5@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER'))
ON DUPLICATE KEY UPDATE email=VALUES(email);

INSERT INTO staff_members (employee_id, first_name, last_name, phone, department_id, designation_id, joining_date, user_id)
SELECT 'EMP001', 'Aarav', 'Sharma', '9123456701', (SELECT id FROM departments WHERE department_name = 'ACADEMIC'), (SELECT id FROM designations WHERE designation_name = 'HEAD_OF_DEPARTMENT'), CURRENT_DATE, (SELECT id FROM users WHERE username = 'staff1')
UNION ALL SELECT 'EMP002', 'Neha', 'Kapoor', '9123456702', (SELECT id FROM departments WHERE department_name = 'ADMINISTRATION'), (SELECT id FROM designations WHERE designation_name = 'PRINCIPAL'), CURRENT_DATE - INTERVAL 2 MONTH, (SELECT id FROM users WHERE username = 'staff2')
UNION ALL SELECT 'EMP003', 'Rohan', 'Verma', '9123456703', (SELECT id FROM departments WHERE department_name = 'FINANCE'), (SELECT id FROM designations WHERE designation_name = 'FINANCE_MANAGER'), CURRENT_DATE - INTERVAL 4 MONTH, (SELECT id FROM users WHERE username = 'staff3')
UNION ALL SELECT 'EMP004', 'Simran', 'Kaur', '9123456704', (SELECT id FROM departments WHERE department_name = 'FRONT_OFFICE'), (SELECT id FROM designations WHERE designation_name = 'RECEPTIONIST'), CURRENT_DATE - INTERVAL 6 MONTH, (SELECT id FROM users WHERE username = 'staff4')
UNION ALL SELECT 'EMP005', 'Alok', 'Nath', '9123456705', (SELECT id FROM departments WHERE department_name = 'LIBRARY'), (SELECT id FROM designations WHERE designation_name = 'LIBRARIAN'), CURRENT_DATE - INTERVAL 8 MONTH, (SELECT id FROM users WHERE username = 'staff5')
ON DUPLICATE KEY UPDATE employee_id=VALUES(employee_id);


-- =============================================================================
-- 6. SEED STUDENT USERS & STUDENTS
-- Exact join on classes and sections ensures valid class_id and section_id
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Ayaan Kumar', 'student_user_1', 'student1@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT')),
    ('Ananya Devi', 'student_user_2', 'student2@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT')),
    ('Kabir Singh', 'student_user_3', 'student3@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT')),
    ('Isha Mehta', 'student_user_4', 'student4@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT')),
    ('Vihaan Sharma', 'student_user_5', 'student5@smartschool.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT'))
ON DUPLICATE KEY UPDATE email=VALUES(email);

INSERT INTO students (
    admission_number, roll_number, first_name, last_name, date_of_birth,
    gender, category, religion, blood_group, house,
    class_id, section_id, user_id, parent_id
)
SELECT
    'ADM001', '101', 'Ayaan', 'Kumar', '2010-01-01', 'MALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_1' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543201' LIMIT 1)
UNION ALL
SELECT
    'ADM002', '102', 'Ananya', 'Devi', '2010-04-11', 'FEMALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_2' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543202' LIMIT 1)
UNION ALL
SELECT
    'ADM003', '103', 'Kabir', 'Singh', '2010-07-20', 'MALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_8' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_8' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_3' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543203' LIMIT 1)
UNION ALL
SELECT
    'ADM004', '104', 'Isha', 'Mehta', '2010-10-28', 'FEMALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_4' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543204' LIMIT 1)
UNION ALL
SELECT
    'ADM005', '105', 'Vihaan', 'Sharma', '2011-02-05', 'MALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_5' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543205' LIMIT 1)
ON DUPLICATE KEY UPDATE admission_number=VALUES(admission_number);


-- =============================================================================
-- 7. SEED FEE MANAGEMENT ENTITIES
-- =============================================================================

-- Fee Discounts
INSERT INTO fee_discounts (name, discount_code, discount_type, percentage, amount, use_count, expiry_date, description)
VALUES
    ('RKS Discount 1', 'rksdisc01', 'FIX_AMOUNT', NULL, 100.00, 5, '2026-01-31', NULL),
    ('Sibling Discount', 'sibling-disc', 'FIX_AMOUNT', NULL, 300.00, 10, NULL, NULL),
    ('Handicapped Discount', 'handicap-disc', 'FIX_AMOUNT', NULL, 350.00, 10, '2026-01-15', NULL),
    ('Class Topper Discount', 'cls-top-disc', 'PERCENTAGE', 100.00, NULL, 20, NULL, NULL)
ON DUPLICATE KEY UPDATE discount_code=VALUES(discount_code);

-- Fee Groups
INSERT INTO fee_groups (name, description)
VALUES
    ('Class 1 General', NULL),
    ('Class 1 Lump Sum', NULL),
    ('Class 2 General', NULL),
    ('Discount', NULL),
    ('March Fees', NULL),
    ('Exam', NULL),
    ('Fees', NULL)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Fee Types
INSERT INTO fee_types (fee_group, name, code, description)
VALUES
    ('General', 'Admission Fees', 'admission-fees', NULL),
    ('Installments', '1st Installment Fees', '1-installment-fees', NULL),
    ('Installments', '2nd Installment Fees', '2-installment-fees', NULL),
    ('Monthly', 'April Month Fees', 'apr-month-fees', NULL),
    ('Transport', 'Bus-fees', 'Bus-fees', NULL),
    ('General', 'Exam Fees', 'exam-fees', NULL),
    ('General', 'Lumpsum fees', 'lumpsum-fees', NULL)
ON DUPLICATE KEY UPDATE code=VALUES(code);


-- =============================================================================
-- 8. SEED FEES (Strict INNER JOINs resolve non-null Foreign Key IDs)
-- =============================================================================
INSERT INTO fees (
    student_id, fee_group_id, fee_type_id, fee_discount_id,
    due_date, amount, paid_amount, fine_amount, discount_amount, status, description
)
SELECT
    s.id AS student_id,
    fg.id AS fee_group_id,
    ft.id AS fee_type_id,
    fd.id AS fee_discount_id,
    f_data.due_date,
    f_data.amount,
    f_data.paid_amount,
    f_data.fine_amount,
    f_data.discount_amount,
    f_data.status,
    f_data.description
FROM (
    SELECT 'ADM001' AS adm_no, 'Fees' AS group_name, 'admission-fees' AS type_code, NULL AS disc_code, '2026-04-15' AS due_date, 5000.00 AS amount, 5000.00 AS paid_amount, 0.00 AS fine_amount, 0.00 AS discount_amount, 'PAID' AS status, 'Initial Admission Fee' AS description
    UNION ALL
    SELECT 'ADM001', 'Fees', 'apr-month-fees', 'rksdisc01', '2026-04-30', 2500.00, 2400.00, 0.00, 100.00, 'PAID', 'April Monthly Fee with discount'
    UNION ALL
    SELECT 'ADM002', 'Fees', '1-installment-fees', 'sibling-disc', '2026-05-10', 12000.00, 6000.00, 0.00, 300.00, 'PARTIAL', 'First Installment Fee'
    UNION ALL
    SELECT 'ADM003', 'Exam', 'exam-fees', NULL, '2026-03-15', 1500.00, 0.00, 100.00, 0.00, 'OVERDUE', 'Mid-Term Exam Fee'
    UNION ALL
    SELECT 'ADM004', 'Fees', 'Bus-fees', NULL, '2026-06-01', 1800.00, 0.00, 0.00, 0.00, 'UNPAID', 'Quarterly Transport Fee'
    UNION ALL
    SELECT 'ADM005', 'Discount', 'lumpsum-fees', 'cls-top-disc', '2026-04-10', 35000.00, 0.00, 0.00, 35000.00, 'PAID', 'Annual Lump Sum Fee (Full Topper Discount)'
) f_data
INNER JOIN students s ON s.admission_number = f_data.adm_no
INNER JOIN fee_groups fg ON fg.name = f_data.group_name
INNER JOIN fee_types ft ON ft.code = f_data.type_code
LEFT JOIN fee_discounts fd ON fd.discount_code = f_data.disc_code;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;