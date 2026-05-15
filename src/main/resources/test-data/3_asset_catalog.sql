-- =============================================
-- FILE 3: ASSET CATALOG TEST DATA
-- =============================================
-- Description: Test data for categories and asset types
-- Order: Run this THIRD (after File 2)
-- Tables: categories, asset_types
-- Dependencies: None
-- Note: models table removed — brand is now a field on assets/inventory_items
-- =============================================

-- =============================================
-- 1. CATEGORIES
-- =============================================

INSERT INTO categories (name, display_name, description) VALUES
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
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'DESKTOP', 'Desktop Computer', 'Standard desktop computer for office use', TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'LAPTOP', 'Laptop Computer', 'Portable laptop computer', TRUE, FALSE);

-- Printer Category
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'PRINTER'), 'PRINTER', 'Printer', 'Network or desktop printer', TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'PRINTER'), 'SCANNER', 'Scanner', 'Document scanner', TRUE, FALSE);

-- Projector Category
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'PROJECTOR'), 'PROJECTOR', 'Projector', 'Video projector for presentations', TRUE, FALSE);

-- Network Category
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'NETWORK'), 'SWITCH', 'Network Switch', 'Network switching equipment', TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'NETWORK'), 'ROUTER', 'Network Router', 'Network routing equipment', TRUE, FALSE);

-- Consumable Category
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'CONSUMABLE'), 'TONER', 'Toner Cartridge', 'Printer toner cartridge', FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'CONSUMABLE'), 'PAPER', 'Paper Ream', 'A4 paper ream for printers', FALSE, TRUE);

-- Peripheral Category
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity) VALUES
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'MONITOR', 'Monitor', 'Computer display monitor', TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'KEYBOARD', 'Keyboard', 'Computer keyboard', FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'MOUSE', 'Mouse', 'Computer mouse', FALSE, TRUE);

-- =============================================
-- SUMMARY
-- =============================================

/*
┌─────────────────┬────────────────────┬───────────────────┬───────────────────┐
│ Category        │ Asset Types        │ Track Individual  │ Track Quantity    │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ COMPUTER        │ DESKTOP            │ ✅                │ ❌                │
│                 │ LAPTOP             │ ✅                │ ❌                │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ PRINTER         │ PRINTER            │ ✅                │ ❌                │
│                 │ SCANNER            │ ✅                │ ❌                │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ PROJECTOR       │ PROJECTOR          │ ✅                │ ❌                │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ NETWORK         │ SWITCH             │ ✅                │ ❌                │
│                 │ ROUTER             │ ✅                │ ❌                │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ CONSUMABLE      │ TONER              │ ❌                │ ✅                │
│                 │ PAPER              │ ❌                │ ✅                │
├─────────────────┼────────────────────┼───────────────────┼───────────────────┤
│ PERIPHERAL      │ MONITOR            │ ✅                │ ❌                │
│                 │ KEYBOARD           │ ❌                │ ✅                │
│                 │ MOUSE              │ ❌                │ ✅                │
└─────────────────┴────────────────────┴───────────────────┴───────────────────┘

Total: 6 categories, 12 asset types
Individual tracked: 7 (desktop, laptop, printer, scanner, projector, switch, router, monitor)
Quantity tracked: 5 (toner, paper, keyboard, mouse)

Note: Brand/specs are stored directly on assets and inventory_items,
      not in a separate models table.
*/