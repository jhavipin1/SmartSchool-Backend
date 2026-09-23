SET FOREIGN_KEY_CHECKS = 0;

-- 1. ROLES
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
AS new_val
ON DUPLICATE KEY UPDATE description = new_val.description;

-- 2. USERS
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Super Admin', 'superadmin', 'superadmin@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'SUPER_ADMIN' LIMIT 1)),
    ('System Admin', 'admin', 'admin@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1)),
    ('Amit Sharma', 'student', 'student@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'STUDENT' LIMIT 1)),
    ('Dr. Rajesh Verma', 'teacher', 'teacher@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER' LIMIT 1)),
    ('Suresh Sharma', 'parent', 'parent@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT' LIMIT 1)),
    ('Ramesh Gupta', 'accountant', 'accountant@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'ACCOUNTANT' LIMIT 1)),
    ('Priya Singh', 'librarian', 'librarian@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'LIBRARIAN' LIMIT 1)),
    ('Anita Roy', 'receptionist', 'receptionist@mail.com', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'RECEPTIONIST' LIMIT 1))
AS new_val
ON DUPLICATE KEY UPDATE email = new_val.email;

-- 3. CLASSES
INSERT INTO classes (class_name)
VALUES
    ('NURSERY'), ('LKG'), ('UKG'), ('CLASS_1'), ('CLASS_2'),
    ('CLASS_3'), ('CLASS_4'), ('CLASS_5'), ('CLASS_6'), ('CLASS_7'),
    ('CLASS_8'), ('CLASS_9'), ('CLASS_10'), ('CLASS_11'), ('CLASS_12')
AS new_val
ON DUPLICATE KEY UPDATE class_name = new_val.class_name;

-- 4. SECTIONS
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
AS new_val
ON DUPLICATE KEY UPDATE class_name_id = new_val.class_name_id;

-- 5. SUBJECTS
INSERT INTO subjects (name, code, description, active)
VALUES
    ('Mathematics', 'SUB001', 'Covers algebra, geometry, trigonometry, and calculus basics', true),
    ('Physics', 'SUB002', 'Fundamentals of mechanics, electricity, magnetism, and optics', true),
    ('Chemistry', 'SUB003', 'Organic, inorganic, and physical chemistry concepts', true),
    ('Biology', 'SUB004', 'Cell biology, genetics, human anatomy, and ecology', true),
    ('English', 'SUB005', 'Grammar, literature, comprehension, and writing skills', true),
    ('Hindi', 'SUB006', 'Grammar, literature, comprehension, and writing skills in Hindi', true),
    ('Environmental Science', 'SUB007', 'Study of environment, ecosystems, and sustainability', true),
    ('Computer Science', 'SUB008', 'Programming, algorithms, data structures, and databases', true),
    ('History', 'SUB009', 'World history and Indian history overview', true),
    ('Geography', 'SUB010', 'Physical geography, maps, and human geography', true),
    ('Political Science', 'SUB011', 'Civics, constitution, and governance basics', true),
    ('Economics', 'SUB012', 'Microeconomics, macroeconomics, and development economics', true),
    ('Crafts', 'SUB013', 'Creative arts, crafts, and design projects', true),
    ('Arts', 'SUB014', 'Drawing, painting, and visual arts', true),
    ('Music', 'SUB015', 'Music theory, instruments, and practice', true),
    ('Sports', 'SUB016', 'Physical education, sports science, and fitness', true),
    ('Psychology', 'SUB017', 'Human behavior, cognition, and mental health basics', true),
    ('Philosophy', 'SUB018', 'Philosophical ideas, ethics, and logic', true),
    ('Astronomy', 'SUB019', 'Stars, planets, galaxies, and space science', true),
    ('Statistics', 'SUB020', 'Probability, data analysis, and statistical methods', true)
AS new_val
ON DUPLICATE KEY UPDATE
    name = new_val.name,
    code = new_val.code,
    description = new_val.description,
    active = new_val.active;

-- 6. DEPARTMENTS
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
AS new_val
ON DUPLICATE KEY UPDATE description = new_val.description;

-- 7. DESIGNATIONS
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
    ('COUNSELOR', 'SCHOOL_COUNSELOR Designation'),
    ('SYSTEM_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR Designation'),
    ('SPORTS_TEACHER', 'SPORTS_DIRECTOR Designation'),
    ('TRANSPORT_MANAGER', 'TRANSPORT_MANAGER Designation')
AS new_val
ON DUPLICATE KEY UPDATE description = new_val.description;

-- 8. FEE DISCOUNTS
INSERT INTO fee_discounts (name, discount_code, discount_type, percentage, amount, use_count, expiry_date, description)
VALUES
    ('Sibling Discount', 'sibling-disc', 'FIX_AMOUNT', NULL, 300.00, 10, NULL, NULL),
    ('Handicapped Discount', 'handicap-disc', 'FIX_AMOUNT', NULL, 350.00, 10, '2026-01-15', NULL),
    ('Class Topper Discount', 'cls-top-disc', 'PERCENTAGE', 100.00, NULL, 20, NULL, NULL)
AS new_val
ON DUPLICATE KEY UPDATE discount_code = new_val.discount_code;

-- 9. FEE GROUPS
INSERT INTO fee_groups (name, description)
VALUES
    ('Regular (Quarterly)', 'Standard quarterly academic fee package'),
    ('Lump Sum (Annual)', 'Discounted single-payment annual fee package'),
    ('Quarter 1 (Apr - Jun)', 'First Quarter Fees due in April'),
    ('Quarter 2 (Jul - Sep)', 'Second Quarter Fees due in July'),
    ('Quarter 3 (Oct - Dec)', 'Third Quarter Fees due in October'),
    ('Quarter 4 (Jan - Mar)', 'Fourth Quarter Fees due in January'),
    ('March Settlement Fees', 'End-of-year balance and clearance fees'),
    ('Transport / Bus Fees', 'Distance-based slab fees for school transport'),
    ('Hostel & Boarding', 'Residential and mess charges'),
    ('New Admission Package', 'One-time charges for newly admitted students'),
    ('Examination & Assessment', 'Annual and board examination charges'),
    ('Concessions & Discounts', 'Fee waivers, staff child discount, sibling discount')
AS new_val
ON DUPLICATE KEY UPDATE description = new_val.description;

-- 10. FEE TYPES
INSERT INTO fee_types (fee_group, name, code, description)
VALUES
    ('New Admission Package', 'Prospectus & Registration Fee', 'REG-FEE', 'Non-refundable application registration fee'),
    ('New Admission Package', 'Admission Fee', 'ADM-FEE', 'One-time admission processing fee'),
    ('New Admission Package', 'Caution Money (Refundable)', 'CAUTION-DEP', 'Refundable security deposit collected at admission'),
    ('Regular (Quarterly)', 'Tuition Fee', 'TUIT-FEE', 'Core academic instruction fee'),
    ('Regular (Quarterly)', 'Development / Infrastructure Fee', 'DEV-FEE', 'School infrastructure and campus upkeep fee'),
    ('Regular (Quarterly)', 'Computer & STEM Lab Fee', 'LAB-STEM', 'IT, computer lab, and digital learning tools fee'),
    ('Regular (Quarterly)', 'Sports & Co-Curricular Fee', 'SPORTS-FEE', 'Sports equipment, annual day, and activity charges'),
    ('Examination & Assessment', 'Term Exam Fee', 'EXAM-TERM', 'Mid-term and final examination charges'),
    ('Examination & Assessment', 'CBSE / Board Registration Fee', 'EXAM-BOARD', 'External board registration and assessment fee'),
    ('Transport / Bus Fees', 'Bus Fee - Zone A (0-5 km)', 'BUS-ZONE-A', 'Slab 1 transport charges'),
    ('Transport / Bus Fees', 'Bus Fee - Zone B (5-10 km)', 'BUS-ZONE-B', 'Slab 2 transport charges'),
    ('Transport / Bus Fees', 'Bus Fee - Zone C (10+ km)', 'BUS-ZONE-C', 'Slab 3 transport charges'),
    ('Concessions & Discounts', 'Sibling Concession', 'DISC-SIBLING', 'Fee discount applicable for second child'),
    ('Concessions & Discounts', 'Staff Ward Discount', 'DISC-STAFF', 'Fee concession for children of school employees'),
    ('Concessions & Discounts', 'Merit Scholarship', 'DISC-MERIT', 'Academic excellence scholarship discount')
AS new_val
ON DUPLICATE KEY UPDATE
    fee_group = new_val.fee_group,
    name = new_val.name,
    description = new_val.description;

-- 11. RACKS
INSERT INTO racks (rack_code, location)
VALUES
    ('RACK-A1', 'First Floor - Science Section'),
    ('RACK-A2', 'First Floor - Physics Section'),
    ('RACK-A3', 'First Floor - Chemistry Section'),
    ('RACK-A4', 'First Floor - Biology Section'),
    ('RACK-B1', 'Second Floor - English Section'),
    ('RACK-B2', 'Second Floor - Hindi Section'),
    ('RACK-B3', 'Second Floor - Environmental Studies'),
    ('RACK-B4', 'Second Floor - Computer Science'),
    ('RACK-C1', 'Third Floor - History Section'),
    ('RACK-C2', 'Third Floor - Geography Section'),
    ('RACK-C3', 'Third Floor - Political Science'),
    ('RACK-C4', 'Third Floor - Economics Section'),
    ('RACK-D1', 'Fourth Floor - Crafts Section'),
    ('RACK-D2', 'Fourth Floor - Arts Section'),
    ('RACK-D3', 'Fourth Floor - Music Section'),
    ('RACK-D4', 'Fourth Floor - Sports Section'),
    ('RACK-E1', 'Fifth Floor - Psychology Section'),
    ('RACK-E2', 'Fifth Floor - Philosophy Section'),
    ('RACK-E3', 'Fifth Floor - Astronomy Section'),
    ('RACK-E4', 'Fifth Floor - Statistics Section')
AS new_val
ON DUPLICATE KEY UPDATE
    rack_code = new_val.rack_code,
    location = new_val.location;

-- 12. BOOKS
INSERT INTO books (
    title, book_number, isbn_number, publisher, author, subject,
    qty, available_qty, price, post_date, description, rack_code
)
VALUES
    ('Mathematics Grade 10', 'B001', 'ISBN001', 'Oxford', 'R.K. Sharma', 'Mathematics', 10, 10, 250.00, '2026-01-01', 'Comprehensive math textbook', 'RACK-A1'),
    ('Physics Fundamentals', 'B002', 'ISBN002', 'Pearson', 'S. Gupta', 'Physics', 8, 8, 300.00, '2026-01-02', 'Physics concepts explained', 'RACK-A2'),
    ('Chemistry Basics', 'B003', 'ISBN003', 'NCERT', 'A. Mehta', 'Chemistry', 12, 12, 280.00, '2026-01-03', 'Introductory chemistry guide', 'RACK-A3'),
    ('Biology Essentials', 'B004', 'ISBN004', 'Cambridge', 'P. Singh', 'Biology', 15, 15, 320.00, '2026-01-04', 'Biology for high school', 'RACK-A4'),
    ('English Reader', 'B005', 'ISBN005', 'Macmillan', 'R. Das', 'English', 20, 20, 200.00, '2026-01-05', 'English literature reader', 'RACK-B1'),
    ('Hindi Vyakaran', 'B006', 'ISBN006', 'Rajkamal', 'S. Verma', 'Hindi', 18, 18, 180.00, '2026-01-06', 'Hindi grammar reference', 'RACK-B2'),
    ('Environmental Science', 'B007', 'ISBN007', 'NCERT', 'M. Rao', 'Science', 10, 10, 220.00, '2026-01-07', 'Environmental science basics', 'RACK-B3'),
    ('Computer Science Basics', 'B008', 'ISBN008', 'Wiley', 'K. Patel', 'Computer Science', 14, 14, 350.00, '2026-01-08', 'Intro to programming', 'RACK-B4'),
    ('History of India', 'B009', 'ISBN009', 'Penguin', 'A. Roy', 'History', 9, 9, 270.00, '2026-01-09', 'Indian history overview', 'RACK-C1'),
    ('Geography Atlas', 'B010', 'ISBN010', 'Oxford', 'N. Sharma', 'Geography', 11, 11, 260.00, '2026-01-10', 'World atlas and maps', 'RACK-C2'),
    ('Political Science', 'B011', 'ISBN011', 'NCERT', 'R. Iyer', 'Political Science', 7, 7, 240.00, '2026-01-11', 'Basics of civics', 'RACK-C3'),
    ('Economics Principles', 'B012', 'ISBN012', 'Pearson', 'S. Kapoor', 'Economics', 13, 13, 310.00, '2026-01-12', 'Economics fundamentals', 'RACK-C4'),
    ('Building With Bricks', 'B013', 'ISBN013', 'Scholastic', 'T. Kumar', 'Crafts', 5, 5, 150.00, '2026-01-13', 'Creative building guide', 'RACK-D1'),
    ('Art and Drawing', 'B014', 'ISBN014', 'Macmillan', 'V. Singh', 'Arts', 6, 6, 190.00, '2026-01-14', 'Art and drawing techniques', 'RACK-D2'),
    ('Music Theory', 'B015', 'ISBN015', 'Cambridge', 'P. Bose', 'Music', 4, 4, 210.00, '2026-01-15', 'Music theory basics', 'RACK-D3'),
    ('Sports Science', 'B016', 'ISBN016', 'Wiley', 'A. Khan', 'Sports', 8, 8, 230.00, '2026-01-16', 'Sports and health', 'RACK-D4'),
    ('Psychology Basics', 'B017', 'ISBN017', 'Pearson', 'R. Gill', 'Psychology', 10, 10, 280.00, '2026-01-17', 'Intro to psychology', 'RACK-E1'),
    ('Philosophy Guide', 'B018', 'ISBN018', 'Penguin', 'S. Menon', 'Philosophy', 7, 7, 260.00, '2026-01-18', 'Philosophy overview', 'RACK-E2'),
    ('Astronomy Basics', 'B019', 'ISBN019', 'Oxford', 'K. Rao', 'Astronomy', 9, 9, 330.00, '2026-01-19', 'Stars and planets', 'RACK-E3'),
    ('Statistics Fundamentals', 'B020', 'ISBN020', 'NCERT', 'M. Gupta', 'Statistics', 12, 12, 300.00, '2026-01-20', 'Statistics for beginners', 'RACK-E4')
AS new_val
ON DUPLICATE KEY UPDATE
    title = new_val.title,
    isbn_number = new_val.isbn_number,
    publisher = new_val.publisher,
    author = new_val.author,
    subject = new_val.subject,
    qty = new_val.qty,
    available_qty = new_val.available_qty,
    price = new_val.price,
    post_date = new_val.post_date,
    description = new_val.description,
    rack_code = new_val.rack_code;

-- =============================================================================
-- 1. HOMEWORKS SEEDING
-- =============================================================================
INSERT INTO homeworks (
    class_id, section_id, subject_id,
    homework_date, submission_date, evaluation_date,
    max_marks, description, document_path, created_by, active
)
VALUES
-- Class 10A Mathematics
(
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM subjects WHERE name = 'Mathematics' LIMIT 1),
    '2026-09-20', '2026-09-25', NULL,
    50, 'Solve NCERT Chapter 5 Arithmetic Progressions exercises', '/docs/homework/maths_class10A.pdf', 'Mr. R.K. Sharma', true
),
-- Class 9B English
(
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM subjects WHERE name = 'English' LIMIT 1),
    '2026-09-21', '2026-09-26', NULL,
    30, 'Write an essay on Environmental Protection and Swachh Bharat Abhiyan', '/docs/homework/english_class9B.pdf', 'Mrs. Sunita Das', true
),
-- Class 8A Physics
(
    (SELECT id FROM classes WHERE class_name = 'CLASS_8' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_8' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM subjects WHERE name = 'Physics' LIMIT 1),
    '2026-09-19', '2026-09-24', '2026-09-27',
    40, 'Prepare class notes and numerical problems on Newton’s Laws of Motion', '/docs/homework/physics_class8A.pdf', 'Mr. Amit Gupta', true
),
-- Class 10B Chemistry
(
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM subjects WHERE name = 'Chemistry' LIMIT 1),
    '2026-09-18', '2026-09-23', '2026-09-26',
    50, 'Lab practical report on Acids, Bases and Salts reactions', '/docs/homework/chemistry_class10B.pdf', 'Dr. Rajesh Mehta', true
),
-- Class 9A History
(
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM subjects WHERE name = 'History' LIMIT 1),
    '2026-09-17', '2026-09-22', '2026-09-25',
    25, 'Prepare map work and summary notes on the Indian National Movement', '/docs/homework/history_class9A.pdf', 'Mrs. Anjali Roy', true
)
ON DUPLICATE KEY UPDATE
    subject_id = VALUES(subject_id),
    homework_date = VALUES(homework_date),
    submission_date = VALUES(submission_date),
    evaluation_date = VALUES(evaluation_date),
    max_marks = VALUES(max_marks),
    description = VALUES(description),
    document_path = VALUES(document_path),
    created_by = VALUES(created_by),
    active = VALUES(active);

-- =============================================================================
-- 2. PARENT USERS & PARENT PROFILES
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Ramesh Kumar', 'parent1', 'ramesh.kumar@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Sunita Devi', 'parent2', 'sunita.devi@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Vikram Singh', 'parent3', 'vikram.singh@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Anil Mehta', 'parent4', 'anil.mehta@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT')),
    ('Pooja Sharma', 'parent5', 'pooja.sharma@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'PARENT'))
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO parents (first_name, last_name, phone, occupation, address, user_id)
SELECT 'Ramesh', 'Kumar', '9876543201', 'Civil Engineer', 'Sector 62, Noida, UP', (SELECT id FROM users WHERE username = 'parent1')
UNION ALL SELECT 'Sunita', 'Devi', '9876543202', 'Medical Practitioner', 'Rohini Sector 9, New Delhi', (SELECT id FROM users WHERE username = 'parent2')
UNION ALL SELECT 'Vikram', 'Singh', '9876543203', 'Business Executive', 'DLF Phase 3, Gurugram, Haryana', (SELECT id FROM users WHERE username = 'parent3')
UNION ALL SELECT 'Anil', 'Mehta', '9876543204', 'Chartered Accountant', 'Preet Vihar, Delhi', (SELECT id FROM users WHERE username = 'parent4')
UNION ALL SELECT 'Pooja', 'Sharma', '9876543205', 'Senior Educator', 'Green Field Colony, Faridabad, Haryana', (SELECT id FROM users WHERE username = 'parent5')
ON DUPLICATE KEY UPDATE phone = VALUES(phone);

-- =============================================================================
-- 3. STAFF USERS & STAFF MEMBERS
-- =============================================================================
INSERT INTO users (full_name, username, email, password, active, role_id)
VALUES
    ('Aarav Sharma', 'staff1', 'aarav.sharma@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Neha Kapoor', 'staff2', 'neha.kapoor@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Rohan Verma', 'staff3', 'rohan.verma@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Simran Kaur', 'staff4', 'simran.kaur@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER')),
    ('Alok Nath', 'staff5', 'alok.nath@smartschool.edu.in', '$2a$10$e.w2X9C5/TzQ0s7vS90uuegM14KkYvN2Gj.A65QdM3aR4QY19aK1q', true, (SELECT id FROM roles WHERE name = 'TEACHER'))
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO staff_members (employee_id, first_name, last_name, phone, department_id, designation_id, joining_date, user_id)
SELECT 'EMP001', 'Aarav', 'Sharma', '9123456701', (SELECT id FROM departments WHERE department_name = 'ACADEMIC'), (SELECT id FROM designations WHERE designation_name = 'HEAD_OF_DEPARTMENT'), CURRENT_DATE, (SELECT id FROM users WHERE username = 'staff1')
UNION ALL SELECT 'EMP002', 'Neha', 'Kapoor', '9123456702', (SELECT id FROM departments WHERE department_name = 'ADMINISTRATION'), (SELECT id FROM designations WHERE designation_name = 'PRINCIPAL'), CURRENT_DATE - INTERVAL 2 MONTH, (SELECT id FROM users WHERE username = 'staff2')
UNION ALL SELECT 'EMP003', 'Rohan', 'Verma', '9123456703', (SELECT id FROM departments WHERE department_name = 'FINANCE'), (SELECT id FROM designations WHERE designation_name = 'FINANCE_MANAGER'), CURRENT_DATE - INTERVAL 4 MONTH, (SELECT id FROM users WHERE username = 'staff3')
UNION ALL SELECT 'EMP004', 'Simran', 'Kaur', '9123456704', (SELECT id FROM departments WHERE department_name = 'FRONT_OFFICE'), (SELECT id FROM designations WHERE designation_name = 'RECEPTIONIST'), CURRENT_DATE - INTERVAL 6 MONTH, (SELECT id FROM users WHERE username = 'staff4')
UNION ALL SELECT 'EMP005', 'Alok', 'Nath', '9123456705', (SELECT id FROM departments WHERE department_name = 'LIBRARY'), (SELECT id FROM designations WHERE designation_name = 'LIBRARIAN'), CURRENT_DATE - INTERVAL 8 MONTH, (SELECT id FROM users WHERE username = 'staff5')
ON DUPLICATE KEY UPDATE employee_id = VALUES(employee_id);

-- =============================================================================
-- 4. STUDENT USERS & STUDENTS
-- =============================================================================
INSERT INTO students (
    admission_number, roll_number, library_card_no, library_card_status,
    first_name, middle_name, last_name, date_of_birth,
    gender, category, religion, blood_group, house,
    class_id, section_id, user_id, parent_id,
    mobile_no, email, admission_date, height, weight, measurement_date,
    father_name, father_phone, father_occ,
    mother_name, mother_phone, mother_occ,
    guardian_is, guardian_name, guardian_relation, guardian_email, guardian_phone, guardian_occ, guardian_address,
    current_address, permanent_address,
    bank_account_no, bank_name, ifsc_code,
    national_identification_no, local_identification_no,
    rte, previous_school, note
)
VALUES
-- ADM001: Ayaan Kumar
(
    'ADM001', '101', '00L1', 'ACTIVE',
    'Ayaan', NULL, 'Kumar', '2011-01-15',
    'MALE', 'GENERAL', 'HINDUISM', 'O_POSITIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_1' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543201' LIMIT 1),
    '9876543201', 'ayaan.kumar@smartschool.edu.in', '2021-06-01', '162cm', '52kg', '2026-04-01',
    'Ramesh Kumar', '9876543201', 'Civil Engineer',
    'Sunita Kumar', '9876543209', 'Homemaker',
    'Father', 'Ramesh Kumar', 'Father', 'ramesh.kumar@smartschool.edu.in', '9876543201', 'Civil Engineer', 'Sector 62, Noida, UP',
    'Sector 62, Noida, UP', 'Sector 62, Noida, UP',
    '34890123456', 'State Bank of India', 'SBIN0001234',
    '234567890123', 'PEN-2026-001',
    'N', 'Delhi Public School, Noida', 'Academic Achiever'
),
-- ADM002: Ananya Devi
(
    'ADM002', '102', '00L2', 'ACTIVE',
    'Ananya', NULL, 'Devi', '2012-04-11',
    'FEMALE', 'OBC', 'HINDUISM', 'A_POSITIVE', 'BLUE',
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_2' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543202' LIMIT 1),
    '9876543202', 'ananya.devi@smartschool.edu.in', '2021-06-01', '155cm', '46kg', '2026-04-01',
    'Sanjay Devi', '9876543202', 'Business Executive',
    'Sunita Devi', '9876543202', 'Medical Practitioner',
    'Mother', 'Sunita Devi', 'Mother', 'sunita.devi@smartschool.edu.in', '9876543202', 'Medical Practitioner', 'Rohini Sector 9, New Delhi',
    'Rohini Sector 9, New Delhi', 'Rohini Sector 9, New Delhi',
    '56789012345', 'Punjab National Bank', 'PUNB0123400',
    '345678901234', 'PEN-2026-002',
    'Y', 'DAV Public School, Rohini', 'RTE Beneficiary'
),
-- ADM003: Kabir Singh
(
    'ADM003', '103', '00L3', 'ACTIVE',
    'Kabir', NULL, 'Singh', '2013-07-20',
    'MALE', 'GENERAL', 'SIKHISM', 'B_POSITIVE', 'GREEN',
    (SELECT id FROM classes WHERE class_name = 'CLASS_8' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_8' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_3' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543203' LIMIT 1),
    '9876543203', 'kabir.singh@smartschool.edu.in', '2021-06-01', '150cm', '42kg', '2026-04-01',
    'Vikram Singh', '9876543203', 'Business Executive',
    'Harpreet Kaur', '9876543214', 'Advocate',
    'Father', 'Vikram Singh', 'Father', 'vikram.singh@smartschool.edu.in', '9876543203', 'Business Executive', 'DLF Phase 3, Gurugram, Haryana',
    'DLF Phase 3, Gurugram, Haryana', 'DLF Phase 3, Gurugram, Haryana',
    '78901234567', 'HDFC Bank', 'HDFC0000123',
    '456789012345', 'PEN-2026-003',
    'N', 'Lancer''s Convent, Gurugram', 'Active Sportsman'
),
-- ADM004: Isha Mehta
(
    'ADM004', '104', '00L4', 'ACTIVE',
    'Isha', NULL, 'Mehta', '2011-10-28',
    'FEMALE', 'GENERAL', 'HINDUISM', 'AB_POSITIVE', 'YELLOW',
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_10' AND s.section_name = 'B' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_4' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543204' LIMIT 1),
    '9876543204', 'isha.mehta@smartschool.edu.in', '2021-06-01', '158cm', '48kg', '2026-04-01',
    'Anil Mehta', '9876543204', 'Chartered Accountant',
    'Kiran Mehta', '9876543216', 'Architect',
    'Father', 'Anil Mehta', 'Father', 'anil.mehta@smartschool.edu.in', '9876543204', 'Chartered Accountant', 'Preet Vihar, Delhi',
    'Preet Vihar, Delhi', 'Preet Vihar, Delhi',
    '89012345678', 'ICICI Bank', 'ICIC0000456',
    '567890123456', 'PEN-2026-004',
    'N', 'Modern School, Barakhamba', 'Prefect Body Member'
),
-- ADM005: Vihaan Sharma
(
    'ADM005', '105', '00L5', 'ACTIVE',
    'Vihaan', NULL, 'Sharma', '2012-02-05',
    'MALE', 'EWS', 'HINDUISM', 'O_NEGATIVE', 'RED',
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id = c.id WHERE c.class_name = 'CLASS_9' AND s.section_name = 'A' LIMIT 1),
    (SELECT id FROM users WHERE username = 'student_user_5' LIMIT 1),
    (SELECT id FROM parents WHERE phone = '9876543205' LIMIT 1),
    '9876543205', 'vihaan.sharma@smartschool.edu.in', '2021-06-01', '152cm', '44kg', '2026-04-01',
    'Rajesh Sharma', '9876543205', 'Government Servant',
    'Pooja Sharma', '9876543205', 'Senior Educator',
    'Father', 'Rajesh Sharma', 'Father', 'pooja.sharma@smartschool.edu.in', '9876543205', 'Government Servant', 'Green Field Colony, Faridabad, Haryana',
    'Green Field Colony, Faridabad, Haryana', 'Green Field Colony, Faridabad, Haryana',
    '90123456789', 'Axis Bank', 'UTIB0000789',
    '678901234567', 'PEN-2026-005',
    'Y', 'Apeejay School, Faridabad', 'Merit Scholarship Holder'
)
AS new_val
ON DUPLICATE KEY UPDATE
    roll_number = new_val.roll_number,
    library_card_no = new_val.library_card_no,
    library_card_status = new_val.library_card_status,
    first_name = new_val.first_name,
    middle_name = new_val.middle_name,
    last_name = new_val.last_name,
    date_of_birth = new_val.date_of_birth,
    gender = new_val.gender,
    category = new_val.category,
    religion = new_val.religion,
    blood_group = new_val.blood_group,
    house = new_val.house,
    class_id = new_val.class_id,
    section_id = new_val.section_id,
    user_id = new_val.user_id,
    parent_id = new_val.parent_id,
    mobile_no = new_val.mobile_no,
    email = new_val.email,
    admission_date = new_val.admission_date,
    height = new_val.height,
    weight = new_val.weight,
    measurement_date = new_val.measurement_date,
    father_name = new_val.father_name,
    father_phone = new_val.father_phone,
    father_occ = new_val.father_occ,
    mother_name = new_val.mother_name,
    mother_phone = new_val.mother_phone,
    mother_occ = new_val.mother_occ,
    guardian_is = new_val.guardian_is,
    guardian_name = new_val.guardian_name,
    guardian_relation = new_val.guardian_relation,
    guardian_email = new_val.guardian_email,
    guardian_phone = new_val.guardian_phone,
    guardian_occ = new_val.guardian_occ,
    guardian_address = new_val.guardian_address,
    current_address = new_val.current_address,
    permanent_address = new_val.permanent_address,
    bank_account_no = new_val.bank_account_no,
    bank_name = new_val.bank_name,
    ifsc_code = new_val.ifsc_code,
    national_identification_no = new_val.national_identification_no,
    local_identification_no = new_val.local_identification_no,
    rte = new_val.rte,
    previous_school = new_val.previous_school,
    note = new_val.note;

-- =============================================================================
-- 5. FEE MANAGEMENT SEEDING
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
    SELECT 'ADM001' AS adm_no, 'Fees' AS group_name, 'admission-fees' AS type_code, NULL AS disc_code, '2026-04-15' AS due_date, 10000.00 AS amount, 10000.00 AS paid_amount, 0.00 AS fine_amount, 0.00 AS discount_amount, 'PAID' AS status, 'Initial Admission & Enrolment Charges' AS description
    UNION ALL
    SELECT 'ADM001', 'Fees', 'apr-month-fees', 'rksdisc01', '2026-04-30', 3500.00, 3200.00, 0.00, 300.00, 'PAID', 'April Monthly Composite Tuition Fee with Staff Sibling Discount'
    UNION ALL
    SELECT 'ADM002', 'Fees', '1-installment-fees', 'sibling-disc', '2026-05-10', 15000.00, 7500.00, 0.00, 500.00, 'PARTIAL', 'First Quarter Composite Fee'
    UNION ALL
    SELECT 'ADM003', 'Exam', 'exam-fees', NULL, '2026-03-15', 1800.00, 0.00, 200.00, 0.00, 'OVERDUE', 'CBSE Board/Term Examination Charges'
    UNION ALL
    SELECT 'ADM004', 'Fees', 'Bus-fees', NULL, '2026-06-01', 4500.00, 0.00, 0.00, 0.00, 'UNPAID', 'Quarterly Transport Infrastructure Fee'
    UNION ALL
    SELECT 'ADM005', 'Discount', 'lumpsum-fees', 'cls-top-disc', '2026-04-10', 45000.00, 0.00, 0.00, 45000.00, 'PAID', 'Annual Lump Sum Composite Fee (100% Academic Excellence Concession)'
) f_data
INNER JOIN students s ON s.admission_number = f_data.adm_no
INNER JOIN fee_groups fg ON fg.name = f_data.group_name
INNER JOIN fee_types ft ON ft.code = f_data.type_code
LEFT JOIN fee_discounts fd ON fd.discount_code = f_data.disc_code;

-- =============================================================================
-- 6. LIBRARY ISSUE RECORDS
-- =============================================================================
INSERT INTO issue_records (
    library_card_no, student_id, book_id, issue_date, due_date, return_date, status
)
VALUES
-- Ayaan Kumar
('00L1',
 (SELECT id FROM students WHERE admission_number = 'ADM001' LIMIT 1),
 (SELECT id FROM books WHERE book_number = 'B001' LIMIT 1),
 '2026-09-01', '2026-09-21', NULL, 'ISSUED'),

-- Ananya Devi
('00L2',
 (SELECT id FROM students WHERE admission_number = 'ADM002' LIMIT 1),
 (SELECT id FROM books WHERE book_number = 'B005' LIMIT 1),
 '2026-09-02', '2026-09-22', NULL, 'ISSUED'),

-- Kabir Singh
('00L3',
 (SELECT id FROM students WHERE admission_number = 'ADM003' LIMIT 1),
 (SELECT id FROM books WHERE book_number = 'B006' LIMIT 1),
 '2026-08-15', '2026-09-01', '2026-09-01', 'RETURNED'),

-- Isha Mehta
('00L4',
 (SELECT id FROM students WHERE admission_number = 'ADM004' LIMIT 1),
 (SELECT id FROM books WHERE book_number = 'B007' LIMIT 1),
 '2026-09-05', '2026-09-25', NULL, 'ISSUED'),

-- Vihaan Sharma
('00L5',
 (SELECT id FROM students WHERE admission_number = 'ADM005' LIMIT 1),
 (SELECT id FROM books WHERE book_number = 'B008' LIMIT 1),
 '2026-09-10', '2026-09-30', NULL, 'ISSUED')
ON DUPLICATE KEY UPDATE
    library_card_no = VALUES(library_card_no),
    student_id = VALUES(student_id),
    book_id = VALUES(book_id),
    issue_date = VALUES(issue_date),
    due_date = VALUES(due_date),
    return_date = VALUES(return_date),
    status = VALUES(status);

-- =============================================================================
-- 7. STUDENT ATTENDANCE RECORDS
-- =============================================================================
INSERT INTO attendance (
    attendance_date, status, entry_time, exit_time, note, source,
    student_id, leave_start_date, leave_end_date, leave_status
) VALUES
-- Student 1 (Ayaan Kumar)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 1, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '08:05:00', '14:00:00', 'Late by 5 min', 'Biometric', 1, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Viral Fever', 'Manual', 1, CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Approved'),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '07:50:00', '14:00:00', 'Punctual', 'Biometric', 1, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 1, NULL, NULL, NULL),

-- Student 2 (Ananya Devi)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '07:45:00', '14:00:00', 'Punctual', 'Biometric', 2, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'ABSENT', NULL, NULL, 'Out of station for family event', 'Manual', 2, CURDATE() - INTERVAL 3 DAY, CURDATE() - INTERVAL 3 DAY, 'Pending'),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '08:10:00', '14:00:00', 'Late entry', 'Biometric', 2, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '07:50:00', '14:00:00', 'Punctual', 'Manual', 2, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Biometric', 2, NULL, NULL, NULL),

-- Student 3 (Kabir Singh)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '08:00:00', '14:00:00', 'Slight delay', 'Manual', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Biometric', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '07:50:00', '14:00:00', 'Punctual', 'Biometric', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'ABSENT', NULL, NULL, 'Medical leave', 'Manual', 3, CURDATE() - INTERVAL 1 DAY, CURDATE() - INTERVAL 1 DAY, 'Approved'),
(CURDATE(), 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Biometric', 3, NULL, NULL, NULL),

-- Student 4 (Isha Mehta)
(CURDATE() - INTERVAL 4 DAY, 'ABSENT', NULL, NULL, 'Inter-school competition event', 'Manual', 4, CURDATE() - INTERVAL 4 DAY, CURDATE() - INTERVAL 4 DAY, 'Approved'),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '07:50:00', '14:00:00', 'Punctual', 'Biometric', 4, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 4, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:05:00', '14:00:00', 'Late entry', 'Biometric', 4, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 4, NULL, NULL, NULL),

-- Student 5 (Vihaan Sharma)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 5, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '07:50:00', '14:00:00', 'Punctual', 'Biometric', 5, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Uninformed Absence', 'Manual', 5, CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Rejected'),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:05:00', '14:00:00', 'Late entry', 'Biometric', 5, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '07:55:00', '14:00:00', 'Punctual', 'Manual', 5, NULL, NULL, NULL);

-- =============================================================================
-- 8. LEAVE TYPES & STAFF ATTENDANCE / LEAVES
-- =============================================================================
INSERT INTO leave_types (name) VALUES
('Medical Leave'),
('Casual Leave'),
('Maternity Leave'),
('Earned Leave'),
('Duty Leave'),
('Half Day Leave'),
('Gazetted Holiday'),
('Paternity Leave'),
('Study Leave'),
('Bereavement Leave')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO staff_attendance (
    attendance_date, status, entry_time, exit_time, note, source, staff_id
) VALUES
-- Staff 1
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Manual', 1),
(CURDATE() - INTERVAL 3 DAY, 'LATE', '08:15:00', '14:30:00', 'Metro delay', 'Biometric', 1),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Sick leave', 'Manual', 1),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '07:50:00', '14:30:00', 'On time', 'Biometric', 1),
(CURDATE(), 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Manual', 1),

-- Staff 2
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '07:40:00', '14:30:00', 'On time', 'Biometric', 2),
(CURDATE() - INTERVAL 3 DAY, 'HALF_DAY', '07:45:00', '11:30:00', 'Approved Half Day', 'Manual', 2),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Biometric', 2),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:00:00', '14:30:00', 'Late entry', 'Manual', 2),
(CURDATE(), 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Biometric', 2),

-- Staff 3
(CURDATE() - INTERVAL 4 DAY, 'HOLIDAY', NULL, NULL, 'Institutional Holiday', 'Manual', 3),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Biometric', 3),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '07:50:00', '14:30:00', 'On time', 'Biometric', 3),
(CURDATE() - INTERVAL 1 DAY, 'ABSENT', NULL, NULL, 'Personal Emergency', 'Manual', 3),
(CURDATE(), 'PRESENT', '07:45:00', '14:30:00', 'On time', 'Manual', 3);

INSERT INTO staff_leaves (
    leave_type, start_date, end_date, reason, status, staff_id
) VALUES
-- Staff 1
('Medical Leave', CURDATE() - INTERVAL 7 DAY, CURDATE() - INTERVAL 6 DAY, 'Fever and medical rest advised', 'Approved', 1),
('Casual Leave', CURDATE() - INTERVAL 3 DAY, CURDATE() - INTERVAL 3 DAY, 'Personal affairs', 'Pending', 1),

-- Staff 2
('Medical Leave', CURDATE() - INTERVAL 5 DAY, CURDATE() - INTERVAL 4 DAY, 'Seasonal Flu', 'Rejected', 2),
('Maternity Leave', CURDATE() + INTERVAL 10 DAY, CURDATE() + INTERVAL 100 DAY, 'Maternity Leave Period', 'Pending', 2),

-- Staff 3
('Casual Leave', CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Family function', 'Approved', 3),
('Duty Leave', CURDATE() + INTERVAL 15 DAY, CURDATE() + INTERVAL 16 DAY, 'CBSE Evaluation Duty', 'Pending', 3);

-- =============================================================================
-- 9. EXAMINATION SCHEME SEEDING (CBSE/ICSE STYLE)
-- =============================================================================
INSERT INTO exam_group (name)
VALUES
('Term-1 Examinations'),
('Term-2 Final Examinations'),
('Periodic Tests')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO exam_type (type_name, exam_group_id)
VALUES
('Half-Yearly', (SELECT id FROM exam_group WHERE name = 'Term-1 Examinations' LIMIT 1)),
('Annual Final', (SELECT id FROM exam_group WHERE name = 'Term-2 Final Examinations' LIMIT 1)),
('Periodic Test 1', (SELECT id FROM exam_group WHERE name = 'Periodic Tests' LIMIT 1))
ON DUPLICATE KEY UPDATE
    type_name = VALUES(type_name),
    exam_group_id = VALUES(exam_group_id);

-- Subject Mapping
INSERT INTO exam_subject (subject_id, class_id, exam_type_id, max_marks)
VALUES
-- Class 10 Mathematics Half-Yearly
(
    (SELECT id FROM subjects WHERE name = 'Mathematics' LIMIT 1),
    (SELECT id FROM classes WHERE class_name = 'CLASS_10' LIMIT 1),
    (SELECT id FROM exam_type WHERE type_name = 'Half-Yearly' LIMIT 1),
    80
),
-- Class 9 English Annual Final
(
    (SELECT id FROM subjects WHERE name = 'English' LIMIT 1),
    (SELECT id FROM classes WHERE class_name = 'CLASS_9' LIMIT 1),
    (SELECT id FROM exam_type WHERE type_name = 'Annual Final' LIMIT 1),
    80
),
-- Class 8 Physics Periodic Test
(
    (SELECT id FROM subjects WHERE name = 'Physics' LIMIT 1),
    (SELECT id FROM classes WHERE class_name = 'CLASS_8' LIMIT 1),
    (SELECT id FROM exam_type WHERE type_name = 'Periodic Test 1' LIMIT 1),
    40
)
ON DUPLICATE KEY UPDATE
    subject_id = VALUES(subject_id),
    class_id = VALUES(class_id),
    exam_type_id = VALUES(exam_type_id),
    max_marks = VALUES(max_marks);

-- Marks Entry
INSERT INTO student_exam (student_id, exam_subject_id, marks_obtained)
VALUES
-- Ayaan Kumar - Class 10 Half-Yearly Mathematics
(
    (SELECT id FROM students WHERE admission_number = 'ADM001' LIMIT 1),
    (SELECT es.id FROM exam_subject es
        JOIN subjects s ON es.subject_id = s.id
        JOIN classes c ON es.class_id = c.id
        JOIN exam_type et ON es.exam_type_id = et.id
        WHERE s.name = 'Mathematics' AND c.class_name = 'CLASS_10' AND et.type_name = 'Half-Yearly' LIMIT 1),
    72
),
-- Ananya Devi - Class 9 Annual Final English
(
    (SELECT id FROM students WHERE admission_number = 'ADM002' LIMIT 1),
    (SELECT es.id FROM exam_subject es
        JOIN subjects s ON es.subject_id = s.id
        JOIN classes c ON es.class_id = c.id
        JOIN exam_type et ON es.exam_type_id = et.id
        WHERE s.name = 'English' AND c.class_name = 'CLASS_9' AND et.type_name = 'Annual Final' LIMIT 1),
    76
)
ON DUPLICATE KEY UPDATE
    marks_obtained = VALUES(marks_obtained);

SET FOREIGN_KEY_CHECKS = 1;