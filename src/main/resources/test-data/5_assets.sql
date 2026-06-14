-- =============================================
-- FILE 4: ASSETS TEST DATA (Legacy — No GRV)
-- =============================================
-- Description: Test data for individually tracked assets with history
-- Order: Run this FOURTH (after File 3)
-- Tables: assets, asset_status_history, asset_location_history, asset_assignment_history
-- Dependencies: asset_categories, campuses, offices, users, asset_statuses
-- =============================================

-- =============================================
-- 1. LAPTOPS (10 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-001', ac.id, 'Dell', 'LAT5420-001', ast.id, '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Assigned to IT Manager'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-002', ac.id, 'Dell', 'LAT5420-002', ast.id, '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Assigned to Admin Officer'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-003', ac.id, 'Dell', 'LAT5420-003', ast.id, '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Spare laptop in IT storage'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-004', ac.id, 'Dell', 'LAT5420-004', ast.id, '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Screen repair needed'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'IN_REPAIR';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-LAP-001', ac.id, 'Dell', 'LAT5420-005', ast.id, '2024-06-20', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Marondera campus IT office'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-LAP-002', ac.id, 'Dell', 'LAT5420-006', ast.id, '2024-06-20', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Spare for Marondera campus'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'AIP-LAP-001', ac.id, 'Dell', 'LAT5420-007', ast.id, '2024-09-10', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'AIP campus admin'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-005', ac.id, 'HP', 'ELITE840-001', ast.id, '2024-03-15', 850.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'Reported lost'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'LOST';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-006', ac.id, 'HP', 'ELITE840-002', ast.id, '2024-03-15', 850.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'Available for new staff'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-LAP-007', ac.id, 'Lenovo', 'X1CARBON-001', ast.id, '2025-01-10', 1200.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "1TB SSD"}', 'Executive laptop'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Laptops' AND ast.name = 'AVAILABLE';

-- =============================================
-- 2. PROJECTORS (5 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-PRJ-001', ac.id, 'Epson', 'EB1795-001', ast.id, '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Lecture Hall 1 projector'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Projectors' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-PRJ-002', ac.id, 'Epson', 'EB1795-002', ast.id, '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Currently on loan'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Projectors' AND ast.name = 'ON_LOAN';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-PRJ-001', ac.id, 'Epson', 'EB1795-003', ast.id, '2024-06-20', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Marondera library projector'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Projectors' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'AIP-PRJ-001', ac.id, 'BenQ', 'LU930-001', ast.id, '2024-09-10', 1100.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "DLP"}', 'AIP innovation hub'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Projectors' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-PRJ-003', ac.id, 'Epson', 'EB1795-004', ast.id, '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Lamp replacement needed'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Projectors' AND ast.name = 'IN_REPAIR';

-- =============================================
-- 3. PRINTERS (3 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-PRT-001', ac.id, 'HP', 'M404-001', ast.id, '2024-05-05', 450.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'Finance Department printer'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Printers' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-PRT-002', ac.id, 'HP', 'M404-002', ast.id, '2024-05-05', 450.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'IT Office backup printer'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Printers' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-PRT-001', ac.id, 'HP', 'M404-003', ast.id, '2024-06-20', 450.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'Marondera admin printer'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Printers' AND ast.name = 'ASSIGNED';

-- =============================================
-- 4. DESKTOP COMPUTERS (4 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-DSK-001', ac.id, 'Dell', 'OPT7060-001', ast.id, '2024-03-15', 900.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'IT Manager workstation'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Desktops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-DSK-002', ac.id, 'Dell', 'OPT7060-002', ast.id, '2024-03-15', 900.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'Admin Office workstation'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Desktops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-DSK-001', ac.id, 'Dell', 'OPT7060-003', ast.id, '2024-06-20', 900.00, '{"cpu": "Intel Core i5", "ram": "8GB", "storage": "256GB SSD"}', 'Marondera admin workstation'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Desktops' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'AIP-DSK-001', ac.id, 'Lenovo', 'M90T-001', ast.id, '2024-09-10', 950.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'AIP innovation hub workstation'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Desktops' AND ast.name = 'ASSIGNED';

-- =============================================
-- 5. NETWORK EQUIPMENT (3 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-SWT-001', ac.id, 'Cisco', 'CAT9300-001', ast.id, '2024-09-12', 2500.00, '{"ports": 48, "speed": "10Gbps", "poe": true}', 'Server room switch'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Network Equipment' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-SWT-002', ac.id, 'Cisco', 'CAT9200-001', ast.id, '2024-09-12', 1500.00, '{"ports": 24, "speed": "1Gbps", "poe": true}', 'Computer Lab A switch'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Network Equipment' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-RTR-001', ac.id, 'Cisco', 'ISR4321-001', ast.id, '2024-09-12', 1800.00, '{"throughput": "100Mbps", "vpn": true}', 'Main campus router'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Network Equipment' AND ast.name = 'ASSIGNED';

-- =============================================
-- 6. MONITORS (3 units)
-- =============================================

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-MON-001', ac.id, 'Dell', 'U2723QE-001', ast.id, '2024-03-15', 500.00, '{"size": "27 inch", "resolution": "4K", "panel": "IPS"}', 'IT Manager second monitor'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Monitors' AND ast.name = 'ASSIGNED';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'CSC-MON-002', ac.id, 'Dell', 'P2422H-001', ast.id, '2024-03-15', 200.00, '{"size": "24 inch", "resolution": "1080p", "panel": "IPS"}', 'Spare monitor in storage'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Monitors' AND ast.name = 'AVAILABLE';

INSERT INTO assets (code, asset_category_id, brand, serial_number, current_status_id, purchase_date, purchase_cost, specs, notes)
SELECT 'MAR-MON-001', ac.id, 'LG', '27UP850-001', ast.id, '2024-06-20', 450.00, '{"size": "27 inch", "resolution": "4K", "panel": "IPS"}', 'Marondera library display'
FROM asset_categories ac, asset_statuses ast WHERE ac.name = 'Monitors' AND ast.name = 'ASSIGNED';

-- =============================================
-- 7. ASSET STATUS HISTORY
-- =============================================

INSERT INTO asset_status_history (asset_id, status_id, reason, valid_from, valid_to)
SELECT a.id, ast.id, 'Asset registered', '2024-03-15 10:00:00', '9000-01-01 00:00:00'
FROM assets a
JOIN asset_statuses ast ON a.current_status_id = ast.id;

-- =============================================
-- 8. ASSET LOCATION HISTORY (current locations)
-- =============================================

INSERT INTO asset_location_history (asset_id, office_id, valid_from, valid_to)
SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00'
FROM assets a, offices o WHERE a.code = 'CSC-LAP-001' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-003' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-004' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-006' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2025-01-10 08:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-007' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-DSK-001' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-05-05 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-PRT-002' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-002' AND o.code = 'CSC_REGISTRAR'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-DSK-002' AND o.code = 'CSC_REGISTRAR'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-MON-002' AND o.code = 'CSC_IT_STORES'
UNION ALL SELECT a.id, o.id, '2024-05-05 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-PRT-001' AND o.code = 'CSC_FINANCE'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-PRJ-001' AND o.code = 'CSC_LECTURE_HALL_1'
UNION ALL SELECT a.id, o.id, '2024-09-12 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-SWT-002' AND o.code = 'CSC_COMP_LAB_A'
UNION ALL SELECT a.id, o.id, '2024-09-12 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-SWT-001' AND o.code = 'CSC_SERVER_ROOM'
UNION ALL SELECT a.id, o.id, '2024-09-12 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-RTR-001' AND o.code = 'CSC_SERVER_ROOM'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-LAP-001' AND o.code = 'MAR_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-LAP-002' AND o.code = 'MAR_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-PRJ-001' AND o.code = 'MAR_LIBRARY'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-PRT-001' AND o.code = 'MAR_CAMPUS_DIRECTOR'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-DSK-001' AND o.code = 'MAR_CAMPUS_DIRECTOR'
UNION ALL SELECT a.id, o.id, '2024-06-20 09:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'MAR-MON-001' AND o.code = 'MAR_LIBRARY'
UNION ALL SELECT a.id, o.id, '2024-09-10 08:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'AIP-LAP-001' AND o.code = 'AIP_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-09-10 08:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'AIP-PRJ-001' AND o.code = 'AIP_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-09-10 08:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'AIP-DSK-001' AND o.code = 'AIP_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-LAP-005' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-PRJ-003' AND o.code = 'CSC_IT_DEPT'
UNION ALL SELECT a.id, o.id, '2024-03-15 10:00:00', '9000-01-01 00:00:00' FROM assets a, offices o WHERE a.code = 'CSC-PRJ-002' AND o.code = 'CSC_LECTURE_HALL_1';

-- =============================================
-- 9. ASSET ASSIGNMENT HISTORY (current assignments)
-- =============================================

INSERT INTO asset_assignment_history (asset_id, user_id, role_at_assignment, notes, valid_from, valid_to)
SELECT a.id, u.id, 'IT Manager', 'Primary work laptop', '2024-03-20 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'CSC-LAP-001' AND u.username = 'it.mary'
UNION ALL
SELECT a.id, u.id, 'IT Manager', 'Office workstation', '2024-03-20 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'CSC-DSK-001' AND u.username = 'it.mary'
UNION ALL
SELECT a.id, u.id, 'Administrator', 'Admin department laptop', '2024-04-01 09:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'CSC-LAP-002' AND u.username = 'admin.jane'
UNION ALL
SELECT a.id, u.id, 'IT Support', 'Marondera campus support', '2024-07-01 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'MAR-LAP-001' AND u.username = 'it.peter'
UNION ALL
SELECT a.id, u.id, 'IT Support', 'AIP campus support', '2024-09-15 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'AIP-LAP-001' AND u.username = 'it.sarah'
UNION ALL
SELECT a.id, u.id, 'Senior Admin', 'Admin workstation', '2024-04-01 09:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'CSC-DSK-002' AND u.username = 'admin.john'
UNION ALL
SELECT a.id, u.id, 'Campus Admin', 'Marondera admin workstation', '2024-07-01 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'MAR-DSK-001' AND u.username = 'viewer.david'
UNION ALL
SELECT a.id, u.id, 'Innovation Lead', 'AIP innovation hub', '2024-09-15 08:00:00', '9000-01-01 00:00:00'
FROM assets a, users u WHERE a.code = 'AIP-DSK-001' AND u.username = 'viewer.linda';

-- =============================================
-- 10. HISTORICAL ASSIGNMENT
-- =============================================

INSERT INTO asset_assignment_history (asset_id, user_id, role_at_assignment, notes, valid_from, valid_to)
SELECT a.id, u.id, 'Lecturer', 'Returned for screen repair', '2024-06-01 08:00:00', '2025-01-15 17:00:00'
FROM assets a, users u WHERE a.code = 'CSC-LAP-004' AND u.username = 'user.tendai';

-- =============================================
-- SUMMARY
-- =============================================

/*
┌─────────────────────┬───────┬──────────────────────────────────────────┐
│ Category            │ Count │ Statuses                                 │
├─────────────────────┼───────┼──────────────────────────────────────────┤
│ Laptops             │    10 │ 4 ASSIGNED, 3 AVAILABLE, 1 IN_REPAIR,   │
│                     │       │ 1 LOST, 1 AVAILABLE                      │
│ Projectors          │     5 │ 2 AVAILABLE, 1 ON_LOAN, 1 IN_REPAIR,    │
│                     │       │ 1 AVAILABLE                              │
│ Printers            │     3 │ 2 ASSIGNED, 1 AVAILABLE                  │
│ Desktops            │     4 │ 4 ASSIGNED                               │
│ Network Equipment   │     3 │ 3 ASSIGNED                               │
│ Monitors            │     3 │ 2 ASSIGNED, 1 AVAILABLE                  │
├─────────────────────┼───────┼──────────────────────────────────────────┤
│ TOTAL               │    28 │                                          │
└─────────────────────┴───────┴──────────────────────────────────────────┘

Locations: CSC (19), MAR (6), AIP (3)
Assignments: 8 current, 1 historical
History tables: status (28), location (28), assignment (9)
GRV Link: All NULL (legacy assets)
*/