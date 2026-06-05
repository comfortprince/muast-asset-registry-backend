-- =============================================
-- FILE 2: LOCATION MANAGEMENT TEST DATA
-- =============================================
-- Description: Test data for campuses and offices
-- Order: Run this SECOND (after File 1)
-- Tables: campuses, offices
-- Dependencies: None
-- =============================================

-- =============================================
-- 1. CAMPUSES (3 campuses)
-- =============================================

INSERT INTO campuses (code, name, description, address) VALUES
('CSC', 'CSC Campus', 'Main campus located along Longlands Road', 'Plot 15 Longlands Road, Marondera, Zimbabwe'),
('MAR', 'Marondera Campus', 'Agricultural research and training campus', 'Grasslands Research Station, Marondera, Zimbabwe'),
('AIP', 'Agro Industrial Park', 'Innovation and business incubation hub', '45 Industrial Way, Marondera, Zimbabwe');

-- =============================================
-- 2. OFFICES FOR CSC CAMPUS (20 offices)
-- =============================================

INSERT INTO offices (campus_id, code, name, description, is_active)
SELECT c.id, o.code, o.name, o.description, TRUE
FROM campuses c
CROSS JOIN (
    VALUES
    ROW('CSC_VC_OFFICE', 'Vice Chancellor''s Office', 'Office of the Vice Chancellor'),
    ROW('CSC_REGISTRAR', 'Registrar''s Office CSC', 'Academic records and student registration'),
    ROW('CSC_BURSAR', 'Bursar''s Office', 'Fees, tuition and student accounts'),
    ROW('CSC_HR', 'Human Resources', 'Staff recruitment and personnel records'),
    ROW('CSC_PROCUREMENT', 'Procurement Office', 'Tender processing and supplier management'),
    ROW('CSC_ACADEMIC_AFFAIRS', 'Academic Affairs', 'Curriculum, examinations and quality assurance'),
    ROW('CSC_STUDENT_AFFAIRS', 'Student Affairs', 'Student welfare, housing and activities'),
    ROW('CSC_ESTATES', 'Estates & Facilities', 'Building maintenance and campus grounds'),
    ROW('CSC_FINANCE', 'Finance Department', 'Payments, budgeting and payroll'),
    ROW('CSC_IT_DEPT', 'IT Department', 'Main IT support and infrastructure office'),
    ROW('CSC_IT_STORES', 'IT Stores', 'Secure storage for IT equipment and consumables'),
    ROW('CSC_SERVER_ROOM', 'Server Room', 'Primary data centre and network operations'),
    ROW('CSC_LIBRARY', 'Main Library', 'University library and study centre'),
    ROW('CSC_COMP_LAB_A', 'Computer Laboratory A', 'Primary computer lab — 80 workstations'),
    ROW('CSC_COMP_LAB_B', 'Computer Laboratory B', 'Secondary computer lab — 40 workstations'),
    ROW('CSC_SCIENCE_LAB', 'Science Laboratory', 'Physics and chemistry practical lab'),
    ROW('CSC_LECTURE_HALL_1', 'Lecture Hall 1', 'Large lecture hall with projector and PA system'),
    ROW('CSC_LECTURE_HALL_2', 'Lecture Hall 2', 'Medium lecture hall'),
    ROW('CSC_LECTURE_HALL_3', 'Lecture Hall 3', 'Small lecture hall — 60 seats'),
    ROW('CSC_AUDITORIUM', 'Main Auditorium', 'Large events and graduation venue')
) AS o(code, name, description)
WHERE c.code = 'CSC';

-- =============================================
-- 3. OFFICES FOR MAR CAMPUS (14 offices)
-- =============================================

INSERT INTO offices (campus_id, code, name, description, is_active)
SELECT c.id, o.code, o.name, o.description, TRUE
FROM campuses c
CROSS JOIN (
    VALUES
    ROW('MAR_CAMPUS_DIRECTOR', 'Campus Director''s Office', 'Overall campus administration'),
    ROW('MAR_REGISTRAR', 'Registrar''s Office MAR', 'Student records and enrolment'),
    ROW('MAR_HR', 'Human Resources', 'Campus staffing and personnel'),
    ROW('MAR_ESTATES', 'Estates & Facilities', 'Campus maintenance and farm infrastructure'),
    ROW('MAR_IT_DEPT', 'IT Department', 'Campus IT support and network maintenance'),
    ROW('MAR_COMP_LAB', 'Computer Laboratory', 'General purpose computer lab — 30 workstations'),
    ROW('MAR_SOIL_LAB', 'Soil Science Laboratory', 'Soil analysis and testing'),
    ROW('MAR_VET_LAB', 'Veterinary Laboratory', 'Animal health diagnostic lab'),
    ROW('MAR_AGRIC_DEPT', 'Agriculture Department', 'Crop and soil science faculty office'),
    ROW('MAR_VET_DEPT', 'Veterinary Sciences', 'Animal health and veterinary faculty office'),
    ROW('MAR_AGRI_ECON', 'Agricultural Economics', 'Farm business and agribusiness faculty'),
    ROW('MAR_LIBRARY', 'Campus Library', 'Agricultural sciences and general library'),
    ROW('MAR_LECTURE_HALL', 'Lecture Hall', 'Main lecture hall with AV equipment'),
    ROW('MAR_FARM_OFFICE', 'Farm Manager''s Office', 'University farm operations')
) AS o(code, name, description)
WHERE c.code = 'MAR';

-- =============================================
-- 4. OFFICES FOR AIP CAMPUS (10 offices)
-- =============================================

INSERT INTO offices (campus_id, code, name, description, is_active)
SELECT c.id, o.code, o.name, o.description, TRUE
FROM campuses c
CROSS JOIN (
    VALUES
    ROW('AIP_PARK_MANAGER', 'Park Manager''s Office', 'AIP administration and tenant relations'),
    ROW('AIP_RECEPTION', 'Reception', 'Front desk and visitor management'),
    ROW('AIP_IT_DEPT', 'IT Department', 'IT support for startups and co-working spaces'),
    ROW('AIP_COWORKING', 'Co-working Space', 'Shared workspace for entrepreneurs'),
    ROW('AIP_PRIVATE_OFFICES', 'Private Offices', 'Dedicated offices for resident startups'),
    ROW('AIP_CONFERENCE_ROOM', 'Conference Room', 'Meeting room with video conferencing'),
    ROW('AIP_INNOVATION_HUB', 'Innovation Hub', 'Startup incubation and prototyping lab'),
    ROW('AIP_BUSINESS_DEV', 'Business Development', 'Partnerships and enterprise support'),
    ROW('AIP_TRAINING_ROOM', 'Training Room', 'Workshop and seminar space — 40 seats'),
    ROW('AIP_CAFE', 'Cafeteria', 'Staff and tenant dining area')
) AS o(code, name, description)
WHERE c.code = 'AIP';

-- =============================================
-- SUMMARY
-- =============================================

/*
┌─────────┬────────────────────────────────┬───────┐
│ Campus  │ Offices                        │ Count │
├─────────┼────────────────────────────────┼───────┤
│ CSC     │ Vice Chancellor's Office       │   20  │
│         │ Registrar's Office             │       │
│         │ Bursar's Office                │       │
│         │ Human Resources                │       │
│         │ Procurement Office             │       │
│         │ Academic Affairs               │       │
│         │ Student Affairs                │       │
│         │ Estates & Facilities           │       │
│         │ Finance Department             │       │
│         │ IT Department                  │       │
│         │ IT Stores                      │       │
│         │ Server Room                    │       │
│         │ Main Library                   │       │
│         │ Computer Laboratory A          │       │
│         │ Computer Laboratory B          │       │
│         │ Science Laboratory             │       │
│         │ Lecture Hall 1                 │       │
│         │ Lecture Hall 2                 │       │
│         │ Lecture Hall 3                 │       │
│         │ Main Auditorium                │       │
├─────────┼────────────────────────────────┼───────┤
│ MAR     │ Campus Director's Office       │   14  │
│         │ Registrar's Office             │       │
│         │ Human Resources                │       │
│         │ Estates & Facilities           │       │
│         │ IT Department                  │       │
│         │ Computer Laboratory            │       │
│         │ Soil Science Laboratory        │       │
│         │ Veterinary Laboratory          │       │
│         │ Agriculture Department         │       │
│         │ Veterinary Sciences            │       │
│         │ Agricultural Economics         │       │
│         │ Campus Library                 │       │
│         │ Lecture Hall                   │       │
│         │ Farm Manager's Office          │       │
├─────────┼────────────────────────────────┼───────┤
│ AIP     │ Park Manager's Office          │   10  │
│         │ Reception                      │       │
│         │ IT Department                  │       │
│         │ Co-working Space               │       │
│         │ Private Offices                │       │
│         │ Conference Room                │       │
│         │ Innovation Hub                 │       │
│         │ Business Development           │       │
│         │ Training Room                  │       │
│         │ Cafeteria                      │       │
└─────────┴────────────────────────────────┴───────┘
Total: 3 campuses, 44 offices
*/