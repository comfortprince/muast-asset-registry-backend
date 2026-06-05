-- =============================================
-- FILE 3: ASSET CLASSIFICATION TEST DATA
-- =============================================
-- Description: Test data for categories and asset types
-- Order: Run this THIRD (after File 2)
-- Tables: categories, asset_types
-- Dependencies: None
-- =============================================

-- =============================================
-- 1. CATEGORIES
-- =============================================

INSERT INTO categories (code, name, description) VALUES
('COMPUTER', 'Computers & Laptops', 'Desktop computers, laptops, and workstations'),
('PRINTER', 'Printers & Scanners', 'Printers, scanners, and multifunction devices'),
('PROJECTOR', 'Projectors & Displays', 'Projectors, screens, and display equipment'),
('NETWORK', 'Networking Equipment', 'Switches, routers, access points, and network devices'),
('CONSUMABLE', 'Consumables & Supplies', 'Toner cartridges, paper, and other consumable supplies'),
('PERIPHERAL', 'Peripherals & Accessories', 'Monitors, keyboards, mice, and other peripherals');

-- =============================================
-- 2. ASSET TYPES
-- =============================================

-- Computer Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'DESKTOP', 'Desktop Computer', 'Standard desktop computer for office use', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'COMPUTER'
UNION ALL
SELECT c.id, 'LAPTOP', 'Laptop Computer', 'Portable laptop computer', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'COMPUTER';

-- Printer Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'PRINTER', 'Printer', 'Network or desktop printer', TRUE, FALSE, TRUE
FROM categories c WHERE c.code = 'PRINTER'
UNION ALL
SELECT c.id, 'SCANNER', 'Scanner', 'Document scanner', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'PRINTER';

-- Projector Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'PROJECTOR', 'Projector', 'Video projector for presentations', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'PROJECTOR';

-- Network Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'SWITCH', 'Network Switch', 'Network switching equipment', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'NETWORK'
UNION ALL
SELECT c.id, 'ROUTER', 'Network Router', 'Network routing equipment', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'NETWORK';

-- Consumable Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'TONER', 'Toner Cartridge', 'Printer toner cartridge', FALSE, TRUE, FALSE
FROM categories c WHERE c.code = 'CONSUMABLE'
UNION ALL
SELECT c.id, 'PAPER', 'Paper Ream', 'A4 paper ream for printers', FALSE, TRUE, FALSE
FROM categories c WHERE c.code = 'CONSUMABLE';

-- Peripheral Category
INSERT INTO asset_types (category_id, code, name, description, track_individual, track_quantity, track_consumable_replacement)
SELECT c.id, 'MONITOR', 'Monitor', 'Computer display monitor', TRUE, FALSE, FALSE
FROM categories c WHERE c.code = 'PERIPHERAL'
UNION ALL
SELECT c.id, 'KEYBOARD', 'Keyboard', 'Computer keyboard', FALSE, TRUE, FALSE
FROM categories c WHERE c.code = 'PERIPHERAL'
UNION ALL
SELECT c.id, 'MOUSE', 'Mouse', 'Computer mouse', FALSE, TRUE, FALSE
FROM categories c WHERE c.code = 'PERIPHERAL';

-- =============================================
-- SUMMARY
-- =============================================

/*
┌─────────────────┬──────────┬────────────────────┬──────────┬──────────┬──────────────────────┐
│ Category        │ Code     │ Asset Type         │ Track Ind│ Track Qty│ Track Replacement    │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ COMPUTER        │ DESKTOP  │ Desktop Computer   │ ✅       │ ❌       │ ❌                   │
│                 │ LAPTOP   │ Laptop Computer    │ ✅       │ ❌       │ ❌                   │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ PRINTER         │ PRINTER  │ Printer            │ ✅       │ ❌       │ ✅ (toner tracking)  │
│                 │ SCANNER  │ Scanner            │ ✅       │ ❌       │ ❌                   │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ PROJECTOR       │ PROJECTOR│ Projector          │ ✅       │ ❌       │ ❌                   │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ NETWORK         │ SWITCH   │ Network Switch     │ ✅       │ ❌       │ ❌                   │
│                 │ ROUTER   │ Network Router     │ ✅       │ ❌       │ ❌                   │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ CONSUMABLE      │ TONER    │ Toner Cartridge    │ ❌       │ ✅       │ ❌                   │
│                 │ PAPER    │ Paper Ream         │ ❌       │ ✅       │ ❌                   │
├─────────────────┼──────────┼────────────────────┼──────────┼──────────┼──────────────────────┤
│ PERIPHERAL      │ MONITOR  │ Monitor            │ ✅       │ ❌       │ ❌                   │
│                 │ KEYBOARD │ Keyboard           │ ❌       │ ✅       │ ❌                   │
│                 │ MOUSE    │ Mouse              │ ❌       │ ✅       │ ❌                   │
└─────────────────┴──────────┴────────────────────┴──────────┴──────────┴──────────────────────┘

Total: 6 categories, 12 asset types
Individual tracked: 7 (desktop, laptop, printer, scanner, projector, switch, router, monitor)
Quantity tracked: 5 (toner, paper, keyboard, mouse)
Consumable replacement tracking: 1 (printer)
*/