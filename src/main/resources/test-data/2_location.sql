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

INSERT INTO campuses (id, name, display_name, address) VALUES
(1, 'CSC', 'Main Campus', '123 Technology Drive, Harare, Zimbabwe'),
(2, 'MAR', 'Marondera Campus', '78 Agriculture Road, Marondera, Zimbabwe'),
(3, 'AIP', 'AIP Campus', '45 Innovation Hub, Harare, Zimbabwe');

ALTER TABLE campuses AUTO_INCREMENT = 4;

-- =============================================
-- 2. OFFICES FOR CSC CAMPUS (Campus ID 1)
-- =============================================

INSERT INTO offices (campus_id, name, display_name, description, is_active) VALUES
(1, 'IT_OFFICE', 'IT Office', 'Main IT department office', TRUE),
(1, 'ADMIN_OFFICE', 'Admin Office', 'Administration and management office', TRUE),
(1, 'FINANCE_DEPT', 'Finance Department', 'Finance and accounting department', TRUE),
(1, 'COMP_LAB_1', 'Computer Lab 1', 'Primary computer laboratory', TRUE),
(1, 'COMP_LAB_2', 'Computer Lab 2', 'Secondary computer laboratory', TRUE),
(1, 'LIBRARY', 'Library', 'Main university library', TRUE),
(1, 'LECTURE_HALL_A', 'Lecture Hall A', 'Main lecture hall with projector', TRUE),
(1, 'STORES', 'Stores/Storage Room', 'IT equipment and supplies storage', TRUE);

-- =============================================
-- 3. OFFICES FOR MAR CAMPUS (Campus ID 2)
-- =============================================

INSERT INTO offices (campus_id, name, display_name, description, is_active) VALUES
(2, 'IT_OFFICE', 'IT Office', 'Marondera campus IT support office', TRUE),
(2, 'ADMIN_OFFICE', 'Admin Office', 'Marondera campus administration', TRUE),
(2, 'LIBRARY', 'Library', 'Marondera campus library', TRUE);

-- =============================================
-- 4. OFFICES FOR AIP CAMPUS (Campus ID 3)
-- =============================================

INSERT INTO offices (campus_id, name, display_name, description, is_active) VALUES
(3, 'IT_OFFICE', 'IT Office', 'AIP campus IT support office', TRUE),
(3, 'ADMIN_OFFICE', 'Admin Office', 'AIP campus administration', TRUE);

-- =============================================
-- SUMMARY
-- =============================================

/*
┌─────────┬────────────────────┬──────────────────────┐
│ Campus  │ Offices            │ Count                │
├─────────┼────────────────────┼──────────────────────┤
│ CSC     │ IT Office          │ 8 offices            │
│         │ Admin Office       │                      │
│         │ Finance Department │                      │
│         │ Computer Lab 1     │                      │
│         │ Computer Lab 2     │                      │
│         │ Library            │                      │
│         │ Lecture Hall A     │                      │
│         │ Stores/Storage     │                      │
├─────────┼────────────────────┼──────────────────────┤
│ MAR     │ IT Office          │ 3 offices            │
│         │ Admin Office       │                      │
│         │ Library            │                      │
├─────────┼────────────────────┼──────────────────────┤
│ AIP     │ IT Office          │ 2 offices            │
│         │ Admin Office       │                      │
└─────────┴────────────────────┴──────────────────────┘
Total: 3 campuses, 13 offices
*/