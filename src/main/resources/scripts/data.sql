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


--------Subjects
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
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    code = VALUES(code),
    description = VALUES(description),
    active = VALUES(active);
-----

---HomeWork---

INSERT INTO homeworks (
    class_id, section_id, subject_id,
    homework_date, submission_date, evaluation_date,
    max_marks, description, document_path, created_by, active
)
VALUES
-- Class 10A Mathematics Homework
(
 (SELECT id FROM classes WHERE class_name='CLASS_10' LIMIT 1),
 (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_10' AND s.section_name='A' LIMIT 1),
 (SELECT id FROM subjects WHERE name='Mathematics' LIMIT 1),
 '2026-09-20','2026-09-25',NULL,
 50,'Solve algebra and geometry problems','/docs/homework/math_class10A.pdf','Teacher Sharma',true
),
-- Class 9B English Homework
(
 (SELECT id FROM classes WHERE class_name='CLASS_9' LIMIT 1),
 (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_9' AND s.section_name='B' LIMIT 1),
 (SELECT id FROM subjects WHERE name='English' LIMIT 1),
 '2026-09-21','2026-09-26',NULL,
 30,'Write an essay on environmental conservation','/docs/homework/english_class9B.pdf','Teacher Das',true
),
-- Class 8A Science Homework
(
 (SELECT id FROM classes WHERE class_name='CLASS_8' LIMIT 1),
 (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_8' AND s.section_name='A' LIMIT 1),
 (SELECT id FROM subjects WHERE name='Physics' LIMIT 1),
 '2026-09-19','2026-09-24','2026-09-27',
 40,'Prepare notes on Newton’s Laws of Motion','/docs/homework/physics_class8A.pdf','Teacher Gupta',true
),
-- Class 10B Chemistry Homework
(
 (SELECT id FROM classes WHERE class_name='CLASS_10' LIMIT 1),
 (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_10' AND s.section_name='B' LIMIT 1),
 (SELECT id FROM subjects WHERE name='Chemistry' LIMIT 1),
 '2026-09-18','2026-09-23','2026-09-26',
 50,'Lab report on acids and bases','/docs/homework/chemistry_class10B.pdf','Teacher Mehta',true
),
-- Class 9A History Homework
(
 (SELECT id FROM classes WHERE class_name='CLASS_9' LIMIT 1),
 (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_9' AND s.section_name='A' LIMIT 1),
 (SELECT id FROM subjects WHERE name='History' LIMIT 1),
 '2026-09-17','2026-09-22','2026-09-25',
 25,'Write a summary of the Mughal Empire','/docs/homework/history_class9A.pdf','Teacher Roy',true
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
---------------

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
-- ADM001
(
    'ADM001','101','00L1','ACTIVE',
    'Ayaan',NULL,'Kumar','2010-01-01',
    'MALE','GENERAL','HINDUISM','O_POSITIVE','RED',
    (SELECT id FROM classes WHERE class_name='CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_10' AND s.section_name='A' LIMIT 1),
    (SELECT id FROM users WHERE username='student_user_1' LIMIT 1),
    (SELECT id FROM parents WHERE phone='9876543201' LIMIT 1),
    '9876543201','student1@smartschool.com','2021-06-01',NULL,NULL,NULL,
    'Father Kumar','9876543201','Engineer',
    'Mother Kumar','9876543209','Teacher',
    'Father','Guardian Kumar','Father','guardian1@smartschool.com','9876543210','Business','Guardian Address 1',
    'Current Address 1','Permanent Address 1',
    '1234567890','Bank A','IFSC001',
    'NID001','LID001',
    'Y','Previous School A','Note A'
),
-- ADM002
(
    'ADM002','102','00L2','ACTIVE',
    'Ananya',NULL,'Devi','2010-04-11',
    'FEMALE','GENERAL','HINDUISM','O_POSITIVE','RED',
    (SELECT id FROM classes WHERE class_name='CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_9' AND s.section_name='B' LIMIT 1),
    (SELECT id FROM users WHERE username='student_user_2' LIMIT 1),
    (SELECT id FROM parents WHERE phone='9876543202' LIMIT 1),
    '9876543202','student2@smartschool.com','2021-06-01',NULL,NULL,NULL,
    'Father Devi','9876543202','Doctor',
    'Mother Devi','9876543212','Homemaker',
    'Mother','Guardian Devi','Mother','guardian2@smartschool.com','9876543213','Business','Guardian Address 2',
    'Current Address 2','Permanent Address 2',
    '2234567890','Bank B','IFSC002',
    'NID002','LID002',
    'Y','Previous School B','Note B'
),
-- ADM003
(
    'ADM003','103','00L3','ACTIVE',
    'Kabir',NULL,'Singh','2010-07-20',
    'MALE','GENERAL','HINDUISM','O_POSITIVE','RED',
    (SELECT id FROM classes WHERE class_name='CLASS_8' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_8' AND s.section_name='A' LIMIT 1),
    (SELECT id FROM users WHERE username='student_user_3' LIMIT 1),
    (SELECT id FROM parents WHERE phone='9876543203' LIMIT 1),
    '9876543203','student3@smartschool.com','2021-06-01',NULL,NULL,NULL,
    'Father Singh','9876543203','Lawyer',
    'Mother Singh','9876543214','Teacher',
    'Father','Guardian Singh','Father','guardian3@smartschool.com','9876543215','Business','Guardian Address 3',
    'Current Address 3','Permanent Address 3',
    '3234567890','Bank C','IFSC003',
    'NID003','LID003',
    'Y','Previous School C','Note C'
),
-- ADM004
(
    'ADM004','104','00L4','ACTIVE',
    'Isha',NULL,'Mehta','2010-10-28',
    'FEMALE','GENERAL','HINDUISM','O_POSITIVE','RED',
    (SELECT id FROM classes WHERE class_name='CLASS_10' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_10' AND s.section_name='B' LIMIT 1),
    (SELECT id FROM users WHERE username='student_user_4' LIMIT 1),
    (SELECT id FROM parents WHERE phone='9876543204' LIMIT 1),
    '9876543204','student4@smartschool.com','2021-06-01',NULL,NULL,NULL,
    'Father Mehta','9876543204','Engineer',
    'Mother Mehta','9876543216','Doctor',
    'Father','Guardian Mehta','Father','guardian4@smartschool.com','9876543217','Business','Guardian Address 4',
    'Current Address 4','Permanent Address 4',
    '4234567890','Bank D','IFSC004',
    'NID004','LID004',
    'Y','Previous School D','Note D'
),
-- ADM005
(
    'ADM005','105','00L5','ACTIVE',
    'Vihaan',NULL,'Sharma','2011-02-05',
    'MALE','GENERAL','HINDUISM','O_POSITIVE','RED',
    (SELECT id FROM classes WHERE class_name='CLASS_9' LIMIT 1),
    (SELECT s.id FROM sections s JOIN classes c ON s.class_name_id=c.id WHERE c.class_name='CLASS_9' AND s.section_name='A' LIMIT 1),
    (SELECT id FROM users WHERE username='student_user_5' LIMIT 1),
    (SELECT id FROM parents WHERE phone='9876543205' LIMIT 1),
    '9876543205','student5@smartschool.com','2021-06-01',NULL,NULL,NULL,
    'Father Sharma','9876543205','Businessman',
    'Mother Sharma','9876543218','Teacher',
    'Father','Guardian Sharma','Father','guardian5@smartschool.com','9876543219','Business','Guardian Address 5',
    'Current Address 5','Permanent Address 5',
    '5234567890','Bank E','IFSC005',
    'NID005','LID005',
    'Y','Previous School E','Note E'
)
ON DUPLICATE KEY UPDATE
    roll_number = VALUES(roll_number),
    library_card_no = VALUES(library_card_no),
    library_card_status = VALUES(library_card_status),
    first_name = VALUES(first_name),
    middle_name = VALUES(middle_name),
    last_name = VALUES(last_name),
    date_of_birth = VALUES(date_of_birth),
    gender = VALUES(gender),
    category = VALUES(category),
    religion = VALUES(religion),
    blood_group = VALUES(blood_group),
    house = VALUES(house),
    class_id = VALUES(class_id),
    section_id = VALUES(section_id),
    user_id = VALUES(user_id),
    parent_id = VALUES(parent_id),
    mobile_no = VALUES(mobile_no),
    email = VALUES(email),
    admission_date = VALUES(admission_date),
    height = VALUES(height),
    weight = VALUES(weight),
    measurement_date = VALUES(measurement_date),
    father_name = VALUES(father_name),
    father_phone = VALUES(father_phone),
    father_occ = VALUES(father_occ),
    mother_name = VALUES(mother_name),
    mother_phone = VALUES(mother_phone),
    mother_occ = VALUES(mother_occ),
    guardian_is = VALUES(guardian_is),
    guardian_name = VALUES(guardian_name),
    guardian_relation = VALUES(guardian_relation),
    guardian_email = VALUES(guardian_email),
    guardian_phone = VALUES(guardian_phone),
    guardian_occ = VALUES(guardian_occ),
    guardian_address = VALUES(guardian_address),
    current_address = VALUES(current_address),
    permanent_address = VALUES(permanent_address),
    bank_account_no = VALUES(bank_account_no),
    bank_name = VALUES(bank_name),
    ifsc_code = VALUES(ifsc_code),
    national_identification_no = VALUES(national_identification_no),
    local_identification_no = VALUES(local_identification_no),
    rte = VALUES(rte),
    previous_school = VALUES(previous_school),
    note = VALUES(note);



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


-----------------
---Books---------
----------------
INSERT INTO books (
    title, book_number, isbn_number, publisher, author, subject, rack_number,
    qty, available_qty, price, post_date, description, rack_id
)
VALUES
('Mathematics Grade 10','B001','ISBN001','Oxford','R.K. Sharma','Mathematics','RACK-A1',10,10,250.00,'2026-01-01','Comprehensive math textbook',
 (SELECT id FROM racks WHERE rack_code='RACK-A1' LIMIT 1)),
('Physics Fundamentals','B002','ISBN002','Pearson','S. Gupta','Physics','RACK-A2',8,8,300.00,'2026-01-02','Physics concepts explained',
 (SELECT id FROM racks WHERE rack_code='RACK-A2' LIMIT 1)),
('Chemistry Basics','B003','ISBN003','NCERT','A. Mehta','Chemistry','RACK-A3',12,12,280.00,'2026-01-03','Introductory chemistry guide',
 (SELECT id FROM racks WHERE rack_code='RACK-A3' LIMIT 1)),
('Biology Essentials','B004','ISBN004','Cambridge','P. Singh','Biology','RACK-A4',15,15,320.00,'2026-01-04','Biology for high school',
 (SELECT id FROM racks WHERE rack_code='RACK-A4' LIMIT 1)),
('English Reader','B005','ISBN005','Macmillan','R. Das','English','RACK-B1',20,20,200.00,'2026-01-05','English literature reader',
 (SELECT id FROM racks WHERE rack_code='RACK-B1' LIMIT 1)),
('Hindi Vyakaran','B006','ISBN006','Rajkamal','S. Verma','Hindi','RACK-B2',18,18,180.00,'2026-01-06','Hindi grammar reference',
 (SELECT id FROM racks WHERE rack_code='RACK-B2' LIMIT 1)),
('Environmental Science','B007','ISBN007','NCERT','M. Rao','Science','RACK-B3',10,10,220.00,'2026-01-07','Environmental science basics',
 (SELECT id FROM racks WHERE rack_code='RACK-B3' LIMIT 1)),
('Computer Science Basics','B008','ISBN008','Wiley','K. Patel','Computer Science','RACK-B4',14,14,350.00,'2026-01-08','Intro to programming',
 (SELECT id FROM racks WHERE rack_code='RACK-B4' LIMIT 1)),
('History of India','B009','ISBN009','Penguin','A. Roy','History','RACK-C1',9,9,270.00,'2026-01-09','Indian history overview',
 (SELECT id FROM racks WHERE rack_code='RACK-C1' LIMIT 1)),
('Geography Atlas','B010','ISBN010','Oxford','N. Sharma','Geography','RACK-C2',11,11,260.00,'2026-01-10','World atlas and maps',
 (SELECT id FROM racks WHERE rack_code='RACK-C2' LIMIT 1)),
('Political Science','B011','ISBN011','NCERT','R. Iyer','Political Science','RACK-C3',7,7,240.00,'2026-01-11','Basics of civics',
 (SELECT id FROM racks WHERE rack_code='RACK-C3' LIMIT 1)),
('Economics Principles','B012','ISBN012','Pearson','S. Kapoor','Economics','RACK-C4',13,13,310.00,'2026-01-12','Economics fundamentals',
 (SELECT id FROM racks WHERE rack_code='RACK-C4' LIMIT 1)),
('Building With Bricks','B013','ISBN013','Scholastic','T. Kumar','Crafts','RACK-D1',5,5,150.00,'2026-01-13','Creative building guide',
 (SELECT id FROM racks WHERE rack_code='RACK-D1' LIMIT 1)),
('Art and Drawing','B014','ISBN014','Macmillan','V. Singh','Arts','RACK-D2',6,6,190.00,'2026-01-14','Art and drawing techniques',
 (SELECT id FROM racks WHERE rack_code='RACK-D2' LIMIT 1)),
('Music Theory','B015','ISBN015','Cambridge','P. Bose','Music','RACK-D3',4,4,210.00,'2026-01-15','Music theory basics',
 (SELECT id FROM racks WHERE rack_code='RACK-D3' LIMIT 1)),
('Sports Science','B016','ISBN016','Wiley','A. Khan','Sports','RACK-D4',8,8,230.00,'2026-01-16','Sports and health',
 (SELECT id FROM racks WHERE rack_code='RACK-D4' LIMIT 1)),
('Psychology Basics','B017','ISBN017','Pearson','R. Gill','Psychology','RACK-E1',10,10,280.00,'2026-01-17','Intro to psychology',
 (SELECT id FROM racks WHERE rack_code='RACK-E1' LIMIT 1)),
('Philosophy Guide','B018','ISBN018','Penguin','S. Menon','Philosophy','RACK-E2',7,7,260.00,'2026-01-18','Philosophy overview',
 (SELECT id FROM racks WHERE rack_code='RACK-E2' LIMIT 1)),
('Astronomy Basics','B019','ISBN019','Oxford','K. Rao','Astronomy','RACK-E3',9,9,330.00,'2026-01-19','Stars and planets',
 (SELECT id FROM racks WHERE rack_code='RACK-E3' LIMIT 1)),
('Statistics Fundamentals','B020','ISBN020','NCERT','M. Gupta','Statistics','RACK-E4',12,12,300.00,'2026-01-20','Statistics for beginners',
 (SELECT id FROM racks WHERE rack_code='RACK-E4' LIMIT 1))
ON DUPLICATE KEY UPDATE
    title = VALUES(title),
    isbn_number = VALUES(isbn_number),
    publisher = VALUES(publisher),
    author = VALUES(author),
    subject = VALUES(subject),
    rack_number = VALUES(rack_number),
    qty = VALUES(qty),
    available_qty = VALUES(available_qty),
    price = VALUES(price),
    post_date = VALUES(post_date),
    description = VALUES(description),
    rack_id = VALUES(rack_id);

-----------------------
---RACKS
---------------
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
ON DUPLICATE KEY UPDATE
    rack_code = VALUES(rack_code),
    location = VALUES(location);
--------------------
---Issue records
----------------
INSERT INTO issue_records (
    library_card_no, student_id, book_id, issue_date, due_date, return_date, status
)
VALUES
-- Ayaan Kumar issues Mathematics Grade 10
('00L1',
 (SELECT id FROM students WHERE admission_number='ADM001' LIMIT 1),
 (SELECT id FROM books WHERE book_number='B001' LIMIT 1),
 '2026-09-01','2026-09-21',NULL,'ISSUED'),

-- Ananya Devi issues English Reader
('00L2',
 (SELECT id FROM students WHERE admission_number='ADM002' LIMIT 1),
 (SELECT id FROM books WHERE book_number='B005' LIMIT 1),
 '2026-09-02','2026-09-22',NULL,'ISSUED'),

-- Kabir Singh issues Hindi Vyakaran and already returned
('00L3',
 (SELECT id FROM students WHERE admission_number='ADM003' LIMIT 1),
 (SELECT id FROM books WHERE book_number='B006' LIMIT 1),
 '2026-08-15','2026-09-01','2026-09-01','RETURNED'),

-- Isha Mehta issues Environmental Science
('00L4',
 (SELECT id FROM students WHERE admission_number='ADM004' LIMIT 1),
 (SELECT id FROM books WHERE book_number='B007' LIMIT 1),
 '2026-09-05','2026-09-25',NULL,'ISSUED'),

-- Vihaan Sharma issues Computer Science Basics
('00L5',
 (SELECT id FROM students WHERE admission_number='ADM005' LIMIT 1),
 (SELECT id FROM books WHERE book_number='B008' LIMIT 1),
 '2026-09-10','2026-09-30',NULL,'ISSUED')
ON DUPLICATE KEY UPDATE
    library_card_no = VALUES(library_card_no),
    student_id = VALUES(student_id),
    book_id = VALUES(book_id),
    issue_date = VALUES(issue_date),
    due_date = VALUES(due_date),
    return_date = VALUES(return_date),
    status = VALUES(status);

-------------
----Attendance -

-------------

-- Assuming student IDs 1–5 exist in your students table

INSERT INTO attendance (
    attendance_date, status, entry_time, exit_time, note, source,
    student_id, leave_start_date, leave_end_date, leave_status
) VALUES
-- Student 1 (ADM001 - Ayaan Kumar)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 1, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '08:50:00', '15:30:00', 'Late by 5 min', 'RFID', 1, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Sick leave', 'Manual', 1, CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Approved'),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:40:00', '15:30:00', 'Good attendance', 'Biometric', 1, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 1, NULL, NULL, NULL),

-- Student 2 (ADM002 - Ananya Devi)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '08:35:00', '15:30:00', 'Excellent punctuality', 'RFID', 2, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'ABSENT', NULL, NULL, 'Family function', 'Manual', 2, CURDATE() - INTERVAL 3 DAY, CURDATE() - INTERVAL 3 DAY, 'Pending'),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '08:50:00', '15:30:00', 'Late entry', 'Biometric', 2, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:40:00', '15:30:00', 'On time', 'Manual', 2, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '08:45:00', '15:30:00', 'On time', 'RFID', 2, NULL, NULL, NULL),

-- Student 3 (ADM003 - Kabir Singh)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '08:50:00', '15:30:00', 'Late entry', 'Manual', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '08:45:00', '15:30:00', 'On time', 'RFID', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '08:40:00', '15:30:00', 'Good attendance', 'Biometric', 3, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'ABSENT', NULL, NULL, 'Medical leave', 'Manual', 3, CURDATE() - INTERVAL 1 DAY, CURDATE() - INTERVAL 1 DAY, 'Approved'),
(CURDATE(), 'PRESENT', '08:45:00', '15:30:00', 'On time', 'RFID', 3, NULL, NULL, NULL),

-- Student 4 (ADM004 - Isha Mehta)
(CURDATE() - INTERVAL 4 DAY, 'ABSENT', NULL, NULL, 'Travel leave', 'Manual', 4, CURDATE() - INTERVAL 4 DAY, CURDATE() - INTERVAL 4 DAY, 'Approved'),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '08:40:00', '15:30:00', 'On time', 'Biometric', 4, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 4, NULL, NULL, NULL),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:50:00', '15:30:00', 'Late entry', 'RFID', 4, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 4, NULL, NULL, NULL),

-- Student 5 (ADM005 - Student 5)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 5, NULL, NULL, NULL),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '08:40:00', '15:30:00', 'Good attendance', 'RFID', 5, NULL, NULL, NULL),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Family emergency', 'Manual', 5, CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Rejected'),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '08:50:00', '15:30:00', 'Late entry', 'Biometric', 5, NULL, NULL, NULL),
(CURDATE(), 'PRESENT', '08:45:00', '15:30:00', 'On time', 'Manual', 5, NULL, NULL, NULL);

----------
----LeaveType
INSERT INTO leave_types (name) VALUES
('Medical Leave'),
('Casual Leave'),
('Maternity Leave'),
('Sick Leave'),
('Mandatory Leave'),
('Half Day Leave'),
('Holiday'),
('Paternity Leave'),
('Study Leave'),
('Bereavement Leave');
------------------
---Staff Attendance-------

----------

-- Assuming staff IDs 1–3 exist in staff_members table

INSERT INTO staff_attendance (
    attendance_date, status, entry_time, exit_time, note, source, staff_id
) VALUES
-- Staff 1 (Employee ID: 9001)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '09:00:00', '17:00:00', 'On time', 'Manual', 1),
(CURDATE() - INTERVAL 3 DAY, 'LATE', '09:30:00', '17:00:00', 'Traffic delay', 'RFID', 1),
(CURDATE() - INTERVAL 2 DAY, 'ABSENT', NULL, NULL, 'Medical leave', 'Manual', 1),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '09:05:00', '17:00:00', 'Slightly late', 'Biometric', 1),
(CURDATE(), 'PRESENT', '09:00:00', '17:00:00', 'On time', 'Manual', 1),

-- Staff 2 (Employee ID: 9002)
(CURDATE() - INTERVAL 4 DAY, 'PRESENT', '09:10:00', '17:00:00', 'Good attendance', 'RFID', 2),
(CURDATE() - INTERVAL 3 DAY, 'HALF_DAY', '09:00:00', '13:00:00', 'Half day leave', 'Manual', 2),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '09:00:00', '17:00:00', 'On time', 'Biometric', 2),
(CURDATE() - INTERVAL 1 DAY, 'PRESENT', '09:15:00', '17:00:00', 'Late entry', 'Manual', 2),
(CURDATE(), 'PRESENT', '09:00:00', '17:00:00', 'On time', 'RFID', 2),

-- Staff 3 (Employee ID: 9003)
(CURDATE() - INTERVAL 4 DAY, 'HOLIDAY', NULL, NULL, 'School holiday', 'Manual', 3),
(CURDATE() - INTERVAL 3 DAY, 'PRESENT', '09:00:00', '17:00:00', 'On time', 'Biometric', 3),
(CURDATE() - INTERVAL 2 DAY, 'PRESENT', '09:05:00', '17:00:00', 'Slightly late', 'RFID', 3),
(CURDATE() - INTERVAL 1 DAY, 'ABSENT', NULL, NULL, 'Family emergency', 'Manual', 3),
(CURDATE(), 'PRESENT', '09:00:00', '17:00:00', 'On time', 'Manual', 3);

----------------
--------StaffLeave-----

-------------

-- Assuming staff IDs 1–3 exist in staff_members table

INSERT INTO staff_leaves (
    leave_type, start_date, end_date, reason, status, staff_id
) VALUES
-- Staff 1 (Employee ID: 9001)
('Medical Leave', CURDATE() - INTERVAL 7 DAY, CURDATE() - INTERVAL 6 DAY, 'Fever and rest advised', 'Approved', 1),
('Casual Leave', CURDATE() - INTERVAL 3 DAY, CURDATE() - INTERVAL 3 DAY, 'Personal work', 'Pending', 1),

-- Staff 2 (Employee ID: 9002)
('Sick Leave', CURDATE() - INTERVAL 5 DAY, CURDATE() - INTERVAL 4 DAY, 'Flu symptoms', 'Rejected', 2),
('Maternity Leave', CURDATE() + INTERVAL 10 DAY, CURDATE() + INTERVAL 40 DAY, 'Maternity period', 'Pending', 2),

-- Staff 3 (Employee ID: 9003)
('Casual Leave', CURDATE() - INTERVAL 2 DAY, CURDATE() - INTERVAL 2 DAY, 'Family function', 'Approved', 3),
('Mandatory Leave', CURDATE() + INTERVAL 15 DAY, CURDATE() + INTERVAL 16 DAY, 'School policy leave', 'Pending', 3);


-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;