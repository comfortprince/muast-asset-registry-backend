-- =============================================
-- FILE 5: ASSETS TEST DATA (Legacy — No GRV)
-- =============================================
-- Description: Test data for individually tracked assets, locations, and assignments
-- Order: Run this FIFTH (after File 4 or skip GRV)
-- Tables: assets, asset_locations, asset_assignments
-- Dependencies: asset_types, campuses, offices, users
-- Note: All assets are legacy — grv_entry_id is NULL
-- =============================================

-- =============================================
-- 1. LAPTOPS (10 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, specs, notes) VALUES
('CSC-LAP-001', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-001', 'ASSIGNED', '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Assigned to IT Manager'),
('CSC-LAP-002', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-002', 'ASSIGNED', '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Assigned to Admin Officer'),
('CSC-LAP-003', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-003', 'AVAILABLE', '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Spare laptop in IT storage'),
('CSC-LAP-004', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-004', 'IN_REPAIR', '2024-03-15', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Screen repair needed'),
('MAR-LAP-001', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-005', 'ASSIGNED', '2024-06-20', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Marondera campus IT office'),
('MAR-LAP-002', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-006', 'AVAILABLE', '2024-06-20', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'Spare for Marondera campus'),
('AIP-LAP-001', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'LAT5420-007', 'ASSIGNED', '2024-09-10', 750.00, '{"cpu": "Intel Core i5", "ram": "16GB", "storage": "512GB SSD"}', 'AIP campus admin'),
('CSC-LAP-005', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'HP', 'ELITE840-001', 'LOST', '2024-03-15', 850.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'Reported lost — investigation pending'),
('CSC-LAP-006', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'HP', 'ELITE840-002', 'AVAILABLE', '2024-03-15', 850.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD"}', 'Available for new staff'),
('CSC-LAP-007', (SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Lenovo', 'X1CARBON-001', 'AVAILABLE', '2025-01-10', 1200.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "1TB SSD"}', 'Executive laptop — unassigned');

-- =============================================
-- 2. PROJECTORS (5 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, specs, notes) VALUES
('CSC-PRJ-001', (SELECT id FROM asset_types WHERE name = 'PROJECTOR'), 'Epson', 'EB1795-001', 'AVAILABLE', '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Lecture Hall A projector'),
('CSC-PRJ-002', (SELECT id FROM asset_types WHERE name = 'PROJECTOR'), 'Epson', 'EB1795-002', 'ON_LOAN', '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Currently on loan to Dr. Moyo'),
('MAR-PRJ-001', (SELECT id FROM asset_types WHERE name = 'PROJECTOR'), 'Epson', 'EB1795-003', 'AVAILABLE', '2024-06-20', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Marondera library projector'),
('AIP-PRJ-001', (SELECT id FROM asset_types WHERE name = 'PROJECTOR'), 'BenQ', 'LU930-001', 'AVAILABLE', '2024-09-10', 1100.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "DLP"}', 'AIP innovation hub'),
('CSC-PRJ-003', (SELECT id FROM asset_types WHERE name = 'PROJECTOR'), 'Epson', 'EB1795-004', 'IN_REPAIR', '2024-03-15', 1200.00, '{"lumens": "6000", "resolution": "1920x1200", "technology": "3LCD"}', 'Lamp replacement needed');

-- =============================================
-- 3. PRINTERS (3 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, total_consumable_cost, specs, notes) VALUES
('CSC-PRT-001', (SELECT id FROM asset_types WHERE name = 'PRINTER'), 'HP', 'M404-001', 'ASSIGNED', '2024-05-05', 450.00, 340.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'Finance Department printer — 4 toners used'),
('CSC-PRT-002', (SELECT id FROM asset_types WHERE name = 'PRINTER'), 'HP', 'M404-002', 'AVAILABLE', '2024-05-05', 450.00, 0.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'IT Office backup printer'),
('MAR-PRT-001', (SELECT id FROM asset_types WHERE name = 'PRINTER'), 'HP', 'M404-003', 'ASSIGNED', '2024-06-20', 450.00, 170.00, '{"type": "Laser", "speed": "40ppm", "duplex": true, "color": false}', 'Marondera admin — 2 toners used');

-- =============================================
-- 4. DESKTOP COMPUTERS (4 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, specs, notes) VALUES
('CSC-DSK-001', (SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Dell', 'OPT7060-001', 'ASSIGNED', '2024-03-15', 900.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD", "monitor": "Dell 27 inch 4K"}', 'IT Manager workstation'),
('CSC-DSK-002', (SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Dell', 'OPT7060-002', 'ASSIGNED', '2024-03-15', 900.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD", "monitor": "Dell 24 inch"}', 'Admin Office workstation'),
('MAR-DSK-001', (SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Dell', 'OPT7060-003', 'ASSIGNED', '2024-06-20', 900.00, '{"cpu": "Intel Core i5", "ram": "8GB", "storage": "256GB SSD", "monitor": "Dell 24 inch"}', 'Marondera admin workstation'),
('AIP-DSK-001', (SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Lenovo', 'M90T-001', 'ASSIGNED', '2024-09-10', 950.00, '{"cpu": "Intel Core i7", "ram": "16GB", "storage": "512GB SSD", "monitor": "Lenovo 27 inch"}', 'AIP innovation hub workstation');

-- =============================================
-- 5. NETWORK EQUIPMENT (3 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, specs, notes) VALUES
('CSC-SWT-001', (SELECT id FROM asset_types WHERE name = 'SWITCH'), 'Cisco', 'CAT9300-001', 'ASSIGNED', '2024-09-12', 2500.00, '{"ports": 48, "speed": "10Gbps", "poe": true}', 'Main server room switch'),
('CSC-SWT-002', (SELECT id FROM asset_types WHERE name = 'SWITCH'), 'Cisco', 'CAT9200-001', 'ASSIGNED', '2024-09-12', 1500.00, '{"ports": 24, "speed": "1Gbps", "poe": true}', 'Computer Lab 1 switch'),
('CSC-RTR-001', (SELECT id FROM asset_types WHERE name = 'ROUTER'), 'Cisco', 'ISR4321-001', 'ASSIGNED', '2024-09-12', 1800.00, '{"throughput": "100Mbps", "vpn": true}', 'Main campus router');

-- =============================================
-- 6. MONITORS (3 units)
-- =============================================

INSERT INTO assets (asset_code, asset_type_id, brand, serial_number, status, purchase_date, purchase_cost, specs, notes) VALUES
('CSC-MON-001', (SELECT id FROM asset_types WHERE name = 'MONITOR'), 'Dell', 'U2723QE-001', 'ASSIGNED', '2024-03-15', 500.00, '{"size": "27 inch", "resolution": "4K", "panel": "IPS"}', 'IT Manager second monitor'),
('CSC-MON-002', (SELECT id FROM asset_types WHERE name = 'MONITOR'), 'Dell', 'P2422H-001', 'AVAILABLE', '2024-03-15', 200.00, '{"size": "24 inch", "resolution": "1080p", "panel": "IPS"}', 'Spare monitor in storage'),
('MAR-MON-001', (SELECT id FROM asset_types WHERE name = 'MONITOR'), 'LG', '27UP850-001', 'ASSIGNED', '2024-06-20', 450.00, '{"size": "27 inch", "resolution": "4K", "panel": "IPS"}', 'Marondera library display');

-- =============================================
-- 7. ASSET LOCATIONS
-- =============================================

-- CSC IT Office
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-001'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-003'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-004'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-006'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-007'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2025-01-10 08:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-DSK-001'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-PRT-002'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-05-05 09:00:00');

-- CSC Admin Office
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-002'), (SELECT id FROM offices WHERE name = 'ADMIN_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-DSK-002'), (SELECT id FROM offices WHERE name = 'ADMIN_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-MON-002'), (SELECT id FROM offices WHERE name = 'STORES' AND campus_id = 1), TRUE, '2024-03-15 10:00:00');

-- CSC Finance Department
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-PRT-001'), (SELECT id FROM offices WHERE name = 'FINANCE_DEPT' AND campus_id = 1), TRUE, '2024-05-05 09:00:00');

-- CSC Lecture Hall A
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-PRJ-001'), (SELECT id FROM offices WHERE name = 'LECTURE_HALL_A' AND campus_id = 1), TRUE, '2024-03-15 10:00:00');

-- CSC Computer Lab 1
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-SWT-002'), (SELECT id FROM offices WHERE name = 'COMP_LAB_1' AND campus_id = 1), TRUE, '2024-09-12 10:00:00');

-- CSC Server Room (using Stores as proxy — add SERVER_ROOM if needed)
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-SWT-001'), (SELECT id FROM offices WHERE name = 'STORES' AND campus_id = 1), TRUE, '2024-09-12 10:00:00'),
((SELECT id FROM assets WHERE asset_code = 'CSC-RTR-001'), (SELECT id FROM offices WHERE name = 'STORES' AND campus_id = 1), TRUE, '2024-09-12 10:00:00');

-- MAR Campus
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'MAR-LAP-001'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 2), TRUE, '2024-06-20 09:00:00'),
((SELECT id FROM assets WHERE asset_code = 'MAR-LAP-002'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 2), TRUE, '2024-06-20 09:00:00'),
((SELECT id FROM assets WHERE asset_code = 'MAR-PRJ-001'), (SELECT id FROM offices WHERE name = 'LIBRARY' AND campus_id = 2), TRUE, '2024-06-20 09:00:00'),
((SELECT id FROM assets WHERE asset_code = 'MAR-PRT-001'), (SELECT id FROM offices WHERE name = 'ADMIN_OFFICE' AND campus_id = 2), TRUE, '2024-06-20 09:00:00'),
((SELECT id FROM assets WHERE asset_code = 'MAR-DSK-001'), (SELECT id FROM offices WHERE name = 'ADMIN_OFFICE' AND campus_id = 2), TRUE, '2024-06-20 09:00:00'),
((SELECT id FROM assets WHERE asset_code = 'MAR-MON-001'), (SELECT id FROM offices WHERE name = 'LIBRARY' AND campus_id = 2), TRUE, '2024-06-20 09:00:00');

-- AIP Campus
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'AIP-LAP-001'), (SELECT id FROM offices WHERE name = 'ADMIN_OFFICE' AND campus_id = 3), TRUE, '2024-09-10 08:00:00'),
((SELECT id FROM assets WHERE asset_code = 'AIP-PRJ-001'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 3), TRUE, '2024-09-10 08:00:00'),
((SELECT id FROM assets WHERE asset_code = 'AIP-DSK-001'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 3), TRUE, '2024-09-10 08:00:00');

-- Lost/stolen assets — last known location
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-005'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00');

-- In repair — assigned to IT Office temporarily
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-PRJ-003'), (SELECT id FROM offices WHERE name = 'IT_OFFICE' AND campus_id = 1), TRUE, '2024-03-15 10:00:00');

-- On loan projector — last known at Lecture Hall
INSERT INTO asset_locations (asset_id, office_id, is_current, assigned_at) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-PRJ-002'), (SELECT id FROM offices WHERE name = 'LECTURE_HALL_A' AND campus_id = 1), TRUE, '2024-03-15 10:00:00');

-- =============================================
-- 8. ASSET ASSIGNMENTS
-- =============================================

-- IT Manager has CSC-LAP-001 and CSC-DSK-001
INSERT INTO asset_assignments (asset_id, user_id, role_at_assignment, is_current, assigned_at, notes) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-001'), (SELECT id FROM users WHERE username = 'it.mary'), 'IT Manager', TRUE, '2024-03-20 08:00:00', 'Primary work laptop'),
((SELECT id FROM assets WHERE asset_code = 'CSC-DSK-001'), (SELECT id FROM users WHERE username = 'it.mary'), 'IT Manager', TRUE, '2024-03-20 08:00:00', 'Office workstation');

-- Admin Officer has CSC-LAP-002
INSERT INTO asset_assignments (asset_id, user_id, role_at_assignment, is_current, assigned_at, notes) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-002'), (SELECT id FROM users WHERE username = 'admin.jane'), 'Administrator', TRUE, '2024-04-01 09:00:00', 'Admin department laptop');

-- IT Staff have other laptops
INSERT INTO asset_assignments (asset_id, user_id, role_at_assignment, is_current, assigned_at, notes) VALUES
((SELECT id FROM assets WHERE asset_code = 'MAR-LAP-001'), (SELECT id FROM users WHERE username = 'it.peter'), 'IT Support', TRUE, '2024-07-01 08:00:00', 'Marondera campus support'),
((SELECT id FROM assets WHERE asset_code = 'AIP-LAP-001'), (SELECT id FROM users WHERE username = 'it.sarah'), 'IT Support', TRUE, '2024-09-15 08:00:00', 'AIP campus support');

-- Desktop assignments
INSERT INTO asset_assignments (asset_id, user_id, role_at_assignment, is_current, assigned_at, notes) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-DSK-002'), (SELECT id FROM users WHERE username = 'admin.john'), 'Senior Admin', TRUE, '2024-04-01 09:00:00', 'Admin workstation'),
((SELECT id FROM assets WHERE asset_code = 'MAR-DSK-001'), (SELECT id FROM users WHERE username = 'viewer.david'), 'Campus Admin', TRUE, '2024-07-01 08:00:00', 'Marondera admin workstation'),
((SELECT id FROM assets WHERE asset_code = 'AIP-DSK-001'), (SELECT id FROM users WHERE username = 'viewer.linda'), 'Innovation Lead', TRUE, '2024-09-15 08:00:00', 'AIP innovation hub');

-- Historical assignment — CSC-LAP-004 was previously assigned before repair
INSERT INTO asset_assignments (asset_id, user_id, role_at_assignment, is_current, assigned_at, returned_at, notes) VALUES
((SELECT id FROM assets WHERE asset_code = 'CSC-LAP-004'), (SELECT id FROM users WHERE username = 'user.tendai'), 'Lecturer', FALSE, '2024-06-01 08:00:00', '2025-01-15 17:00:00', 'Returned for screen repair');

-- =============================================
-- SUMMARY
-- =============================================

/*
┌──────────────┬───────┬──────────────────────────────────────────┐
│ Asset Type   │ Count │ Statuses                                 │
├──────────────┼───────┼──────────────────────────────────────────┤
│ Laptop       │    10 │ 4 ASSIGNED, 3 AVAILABLE, 1 IN_REPAIR,   │
│              │       │ 1 LOST, 1 AVAILABLE                      │
│ Projector    │     5 │ 2 AVAILABLE, 1 ON_LOAN, 1 IN_REPAIR,    │
│              │       │ 1 AVAILABLE                              │
│ Printer      │     3 │ 2 ASSIGNED, 1 AVAILABLE                  │
│ Desktop      │     4 │ 4 ASSIGNED                               │
│ Switch       │     2 │ 2 ASSIGNED                               │
│ Router       │     1 │ 1 ASSIGNED                               │
│ Monitor      │     3 │ 2 ASSIGNED, 1 AVAILABLE                  │
├──────────────┼───────┼──────────────────────────────────────────┤
│ TOTAL        │    28 │                                          │
└──────────────┴───────┴──────────────────────────────────────────┘

Locations: CSC (19), MAR (6), AIP (3)
Assignments: 9 current, 1 historical
GRV Link: All NULL (legacy assets)
*/