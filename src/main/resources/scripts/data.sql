SET FOREIGN_KEY_CHECKS = 0;

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

-- Insert Classes
INSERT INTO classes (class_name)
VALUES
    ('NURSERY'), ('LKG'), ('UKG'),
    ('CLASS_1'), ('CLASS_2'), ('CLASS_3'),
    ('CLASS_4'), ('CLASS_5'), ('CLASS_6'),
    ('CLASS_7'), ('CLASS_8'), ('CLASS_9'),
    ('CLASS_10'), ('CLASS_11'), ('CLASS_12')
ON DUPLICATE KEY UPDATE class_name = VALUES(class_name);

-- Insert Sections mapped to each Class
INSERT INTO sections (section_name, class_name_id)
SELECT s.section_name, c.id
FROM classes c
CROSS JOIN (
    SELECT 'A' AS section_name
    UNION ALL SELECT 'B'
) s
ON DUPLICATE KEY UPDATE
    section_name = VALUES(section_name),
    class_name_id = VALUES(class_name_id);

INSERT INTO exam_groups (name)
VALUES
('Annual'),
('First Term'),
('Second Term')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Step 2: Insert Exam Types for each Exam Group
INSERT INTO exam_types (type_name, exam_group_id)
SELECT 'Theory', eg.id FROM exam_groups eg WHERE eg.name = 'Annual' UNION ALL
SELECT 'Practical', eg.id FROM exam_groups eg WHERE eg.name = 'Annual' UNION ALL
SELECT 'Theory', eg.id FROM exam_groups eg WHERE eg.name = 'First Term' UNION ALL
SELECT 'Practical', eg.id FROM exam_groups eg WHERE eg.name = 'First Term' UNION ALL
SELECT 'Theory', eg.id FROM exam_groups eg WHERE eg.name = 'Second Term' UNION ALL
SELECT 'Practical', eg.id FROM exam_groups eg WHERE eg.name = 'Second Term'
ON DUPLICATE KEY UPDATE type_name = VALUES(type_name);

-- Step 3: Insert Subjects mapped to Classes + Exam Types
INSERT INTO subjects (name, code, description, active, class_id, exam_type_id, max_marks)
SELECT
    bs.name,
    bs.code,
    bs.description,
    bs.active,
    c.id AS class_id,
    et.id AS exam_type_id,
    CASE
        WHEN eg.name IN ('First Term','Second Term') AND et.type_name = 'Theory' THEN 20
        WHEN eg.name IN ('First Term','Second Term') AND et.type_name = 'Practical' THEN 20
        WHEN eg.name = 'Annual' AND et.type_name = 'Theory' THEN 100
        WHEN eg.name = 'Annual' AND et.type_name = 'Practical' THEN 50
        ELSE 0
    END AS max_marks
FROM (
    SELECT 'Mathematics' AS name, 'SUB001' AS code, 'Covers algebra, geometry, trigonometry, and calculus basics' AS description, true AS active UNION ALL
    SELECT 'Science', 'SUB002', 'Fundamentals of Science', true UNION ALL
    SELECT 'English', 'SUB003', 'Grammar, literature, comprehension, and writing skills', true UNION ALL
    SELECT 'Hindi', 'SUB004', 'Grammar, literature, comprehension, and writing skills in Hindi', true UNION ALL
    SELECT 'Computer Science', 'SUB005', 'Programming, algorithms, data structures, and databases', true UNION ALL
    SELECT 'Arts', 'SUB006', 'Drawing, painting, and visual arts', true UNION ALL
    SELECT 'Music', 'SUB007', 'Music theory, instruments, and practice', true UNION ALL
    SELECT 'Sports', 'SUB008', 'Physical education, sports science, and fitness', true
) bs
CROSS JOIN classes c
JOIN exam_types et ON et.id IS NOT NULL
JOIN exam_groups eg ON eg.id = et.exam_group_id
ON DUPLICATE KEY UPDATE
    description = VALUES(description),
    max_marks = VALUES(max_marks);


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

INSERT INTO designations (designation_name, description)
VALUES
    ('PRINCIPAL', 'PRINCIPAL Designation'),
    ('VICE_PRINCIPAL', 'VICE_PRINCIPAL Designation'),
    ('HEAD_OF_DEPARTMENT', 'HEAD_OF_DEPARTMENT Designation'),
    ('TEACHER', 'TEACHER Designation'),
    ('FINANCE_MANAGER', 'FINANCE_MANAGER Designation'),
    ('ACCOUNTANT', 'ACCOUNTANT Designation'),
    ('RECEPTIONIST', 'RECEPTIONIST Designation'),
    ('LIBRARIAN', 'LIBRARIAN Designation'),
    ('COUNSELOR', 'SCHOOL_COUNSELOR Designation'),
    ('SYSTEM_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR Designation'),
    ('SPORTS_TEACHER', 'SPORTS_DIRECTOR Designation'),
    ('TRANSPORT_MANAGER', 'TRANSPORT_MANAGER Designation')
ON DUPLICATE KEY UPDATE description=VALUES(description);

INSERT INTO fee_discounts (name, discount_code, discount_type, percentage, amount, use_count, expiry_date, description)
VALUES
    ('Sibling Discount', 'sibling-disc', 'FIX_AMOUNT', NULL, 300.00, 10, NULL, NULL),
    ('Handicapped Discount', 'handicap-disc', 'FIX_AMOUNT', NULL, 350.00, 10, '2026-01-15', NULL),
    ('Class Topper Discount', 'cls-top-disc', 'PERCENTAGE', 100.00, NULL, 20, NULL, NULL)
ON DUPLICATE KEY UPDATE discount_code=VALUES(discount_code);

INSERT INTO fee_groups (name, description)
VALUES
    -- Grade/Class Specific Packages
    ('Regular (Quarterly)', 'Standard quarterly academic fee package'),
    ('Lump Sum (Annual)', 'Discounted single-payment annual fee package'),

    -- Term & Monthly Schedules
    ('Quarter 1 (Apr - Jun)', 'First Quarter Fees due in April'),
    ('Quarter 2 (Jul - Sep)', 'Second Quarter Fees due in July'),
    ('Quarter 3 (Oct - Dec)', 'Third Quarter Fees due in October'),
    ('Quarter 4 (Jan - Mar)', 'Fourth Quarter Fees due in January'),
    ('March Settlement Fees', 'End-of-year balance and clearance fees'),

    -- Optional & Ancillary Services
    ('Transport / Bus Fees', 'Distance-based slab fees for school transport'),
    ('Hostel & Boarding', 'Residential and mess charges'),

    -- One-Time & Miscellaneous
    ('New Admission Package', 'One-time charges for newly admitted students'),
    ('Examination & Assessment', 'Annual and board examination charges'),
    ('Concessions & Discounts', 'Fee waivers, staff child discount, sibling discount')
ON DUPLICATE KEY UPDATE
    description = VALUES(description);

INSERT INTO fee_types (fee_group, name, code, description)
VALUES
    -- One-Time / Admission Fees
    ('New Admission Package', 'Prospectus & Registration Fee', 'REG-FEE', 'Non-refundable application registration fee'),
    ('New Admission Package', 'Admission Fee', 'ADM-FEE', 'One-time admission processing fee'),
    ('New Admission Package', 'Caution Money (Refundable)', 'CAUTION-DEP', 'Refundable security deposit collected at admission'),

    -- Core Academic Fees
    ('Regular (Quarterly)', 'Tuition Fee', 'TUIT-FEE', 'Core academic instruction fee'),
    ('Regular (Quarterly)', 'Development / Infrastructure Fee', 'DEV-FEE', 'School infrastructure and campus upkeep fee'),
    ('Regular (Quarterly)', 'Computer & STEM Lab Fee', 'LAB-STEM', 'IT, computer lab, and digital learning tools fee'),
    ('Regular (Quarterly)', 'Sports & Co-Curricular Fee', 'SPORTS-FEE', 'Sports equipment, annual day, and activity charges'),

    -- Examinations
    ('Examination & Assessment', 'Term Exam Fee', 'EXAM-TERM', 'Mid-term and final examination charges'),
    ('Examination & Assessment', 'CBSE / Board Registration Fee', 'EXAM-BOARD', 'External board registration and assessment fee'),

    -- Optional / Service Charges
    ('Transport / Bus Fees', 'Bus Fee - Zone A (0-5 km)', 'BUS-ZONE-A', 'Slab 1 transport charges'),
    ('Transport / Bus Fees', 'Bus Fee - Zone B (5-10 km)', 'BUS-ZONE-B', 'Slab 2 transport charges'),
    ('Transport / Bus Fees', 'Bus Fee - Zone C (10+ km)', 'BUS-ZONE-C', 'Slab 3 transport charges'),

    -- Discounts & Concessions (Mapped with negative/discount logic in business tier)
    ('Concessions & Discounts', 'Sibling Concession', 'DISC-SIBLING', 'Fee discount applicable for second child'),
    ('Concessions & Discounts', 'Staff Ward Discount', 'DISC-STAFF', 'Fee concession for children of school employees'),
    ('Concessions & Discounts', 'Merit Scholarship', 'DISC-MERIT', 'Academic excellence scholarship discount')
ON DUPLICATE KEY UPDATE
    fee_group = VALUES(fee_group),
    name = VALUES(name),
    description = VALUES(description);

INSERT INTO leave_types (name) VALUES
('Medical Leave'),('Casual Leave'),('Maternity Leave'),('Sick Leave'),
('Mandatory Leave'),('Half Day Leave'),('Holiday'),
('Paternity Leave'),('Study Leave'),('Bereavement Leave');

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

SET FOREIGN_KEY_CHECKS = 1;