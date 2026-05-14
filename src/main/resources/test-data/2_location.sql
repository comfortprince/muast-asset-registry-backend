-- =============================================
-- FILE 2: LOCATION MANAGEMENT TEST DATA
-- =============================================
-- Description: Test data for campuses and offices
-- Order: Run this SECOND (after File 1)
-- Tables: campuses, offices
-- Dependencies: None
-- =============================================

-- =============================================
-- 1. CAMPUSES (3 campuses as specified)
-- =============================================

INSERT INTO campuses (name, display_name, address) VALUES
('CSC', 'Cold Storage Commission', '123 Technology Drive, Harare, Zimbabwe'),
('AIP', 'Agro Industrial Park', '45 Innovation Hub, Harare, Zimbabwe'),
('MAR', 'Marondera Campus', '78 Agriculture Road, Marondera, Zimbabwe');

-- =============================================
-- 2. OFFICES FOR CSC CAMPUS
-- =============================================

-- CSC Campus Offices (Campus name: 'CSC')
INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'IT_DEPT', 'IT Department', 'Main IT department office for technical staff', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'REGISTRAR', 'Registrar Office', 'Office of the Registrar for student records', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'FINANCE', 'Finance Office', 'Finance department for payments and budgeting', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_CS', 'Head of Computer Science', 'Computer Science Department head office', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_IT', 'Head of Information Technology', 'IT Department head office', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LIBRARY', 'Main Library', 'University main library and resource center', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LAB_MAIN', 'Main Computer Lab', 'Primary computer laboratory with 100 workstations', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LAB_RESEARCH', 'Research Lab', 'Specialized research computer laboratory', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'SERVER_ROOM', 'Main Server Room', 'Primary data center and server room', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'NETWORK_OPS', 'Network Operations Center', 'Network monitoring and operations center', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HR_DEPT', 'Human Resources', 'Human Resources department office', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'PROCUREMENT', 'Procurement Office', 'Procurement and supplies department', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'ESTATES', 'Estates Office', 'University estates and facilities management', TRUE
FROM campuses c WHERE c.name = 'CSC';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'SECURITY', 'Security Office', 'Campus security and access control', TRUE
FROM campuses c WHERE c.name = 'CSC';

-- =============================================
-- 3. OFFICES FOR AIP CAMPUS
-- =============================================

-- AIP Campus Offices (Campus name: 'AIP')
INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'IT_DEPT', 'IT Department', 'AIP campus IT support office', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'INNOVATION_HUB', 'Innovation Hub', 'Startup incubation and innovation center', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'BUSINESS_DEV', 'Business Development', 'Partnerships and business development office', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_ENTRE', 'Head of Entrepreneurship', 'Entrepreneurship Department office', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_INNOVATION', 'Head of Innovation', 'Innovation Department office', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LIBRARY', 'AIP Library', 'Innovation and business library', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LAB_STARTUP', 'Startup Lab', 'Computer lab for startup incubation', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'COWORKING', 'Co-working Space', 'Shared workspace for innovators', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'ADMIN', 'Administration Office', 'AIP campus administration', TRUE
FROM campuses c WHERE c.name = 'AIP';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HR_DEPT', 'Human Resources', 'Human Resources department', TRUE
FROM campuses c WHERE c.name = 'AIP';

-- =============================================
-- 4. OFFICES FOR MAR CAMPUS (Marondera)
-- =============================================

-- MAR Campus Offices (Campus name: 'MAR')
INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'IT_DEPT', 'IT Department', 'Marondera campus IT support office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'AGRIC_DEPT', 'Agriculture Department', 'Main agriculture department office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_AGRIC', 'Head of Agriculture', 'Agriculture Department head office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'HOD_VET', 'Head of Veterinary', 'Veterinary Sciences head office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'FARM_MANAGER', 'Farm Manager Office', 'University farm management office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LIBRARY', 'Marondera Library', 'Agriculture and science library', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LAB_AGRIC', 'Agriculture Lab', 'Agricultural science laboratory', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'LAB_COMPUTER', 'Computer Lab', 'General purpose computer laboratory', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'ADMIN', 'Administration Office', 'Marondera campus administration', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'STUDENT_AFFAIRS', 'Student Affairs', 'Student services and affairs office', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'ACCOMMODATION', 'Accommodation Office', 'Student housing and accommodation', TRUE
FROM campuses c WHERE c.name = 'MAR';

INSERT INTO offices (campus_id, name, display_name, description, is_active) 
SELECT c.id, 'SPORTS', 'Sports Office', 'Sports department and recreation', TRUE
FROM campuses c WHERE c.name = 'MAR';