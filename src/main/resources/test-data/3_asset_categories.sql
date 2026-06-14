-- =============================================
-- FILE 3: ASSET CATEGORIES TEST DATA
-- =============================================
-- Description: Test data for asset categories
-- Order: Run this THIRD (after File 2)
-- Tables: asset_categories
-- Dependencies: None
-- =============================================

INSERT INTO asset_categories (name, description) VALUES
('Laptops', 'Portable laptop computers'),
('Desktops', 'Desktop computers and workstations'),
('Printers', 'Network and desktop printers'),
('Projectors', 'Video projectors for presentations'),
('Network Equipment', 'Switches, routers, and network devices'),
('Monitors', 'Computer display monitors'),
('Keyboards', 'Computer keyboards'),
('Mice', 'Computer mice');

-- =============================================
-- SUMMARY
-- =============================================

/*
┌────┬─────────────────────┬────────────────────────────────────────┐
│ ID │ Name                │ Description                            │
├────┼─────────────────────┼────────────────────────────────────────┤
│  1 │ Laptops             │ Portable laptop computers              │
│  2 │ Desktops            │ Desktop computers and workstations     │
│  3 │ Printers            │ Network and desktop printers           │
│  4 │ Projectors          │ Video projectors for presentations     │
│  5 │ Network Equipment   │ Switches, routers, and network devices │
│  6 │ Monitors            │ Computer display monitors              │
│  7 │ Keyboards           │ Computer keyboards                     │
│  8 │ Mice                │ Computer mice                          │
└────┴─────────────────────┴────────────────────────────────────────┘

Total: 8 asset categories
*/