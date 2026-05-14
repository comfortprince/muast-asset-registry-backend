-- =============================================
-- FILE 3: ASSET CATALOG TEST DATA
-- =============================================
-- Description: Test data for categories, asset types, and models
-- Order: Run this THIRD (after File 2)
-- Tables: categories, asset_types, models
-- Dependencies: None
-- =============================================

-- =============================================
-- 1. CATEGORIES
-- =============================================

INSERT INTO categories (name, display_name, description) VALUES
('COMPUTER', 'Computers', 'Desktops, laptops, workstations, and servers'),
('NETWORK', 'Networking Equipment', 'Switches, routers, access points, and network devices'),
('PERIPHERAL', 'Peripherals', 'Printers, scanners, monitors, keyboards, mice'),
('SOFTWARE', 'Software', 'Operating systems, applications, and software licenses'),
('FURNITURE', 'Furniture', 'Desks, chairs, shelves, and office furniture'),
('MOBILE', 'Mobile Devices', 'Tablets, smartphones, and mobile computing devices'),
('SECURITY', 'Security Equipment', 'CCTV cameras, access control, and security systems'),
('AUDIO_VISUAL', 'Audio Visual', 'Projectors, screens, speakers, and AV equipment');

-- =============================================
-- 2. ASSET TYPES
-- =============================================

-- Computer Category (ID 1 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'DESKTOP', 'Desktop Computer', 'Standard desktop computer for office use', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'LAPTOP', 'Laptop Computer', 'Portable laptop computer', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'WORKSTATION', 'Workstation', 'High-performance workstation for specialized tasks', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'SERVER', 'Server', 'Server hardware for data center', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'COMPUTER'), 'THIN_CLIENT', 'Thin Client', 'Thin client terminal for virtual desktop', TRUE, FALSE, TRUE, FALSE);

-- Network Category (ID 2 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'NETWORK'), 'SWITCH', 'Network Switch', 'Network switching equipment', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'NETWORK'), 'ROUTER', 'Network Router', 'Network routing equipment', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'NETWORK'), 'ACCESS_POINT', 'Access Point', 'Wireless access point', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'NETWORK'), 'FIREWALL', 'Firewall', 'Network firewall appliance', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'NETWORK'), 'CABLE', 'Network Cable', 'Ethernet and fiber optic cables', FALSE, TRUE, FALSE, FALSE);

-- Peripheral Category (ID 3 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'PRINTER', 'Printer', 'Printing device for documents', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'SCANNER', 'Scanner', 'Document scanner', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'MONITOR', 'Monitor', 'Computer display monitor', TRUE, FALSE, TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'KEYBOARD', 'Keyboard', 'Computer keyboard', FALSE, TRUE, TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'MOUSE', 'Mouse', 'Computer mouse', FALSE, TRUE, TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'PERIPHERAL'), 'PROJECTOR', 'Projector', 'Video projector for presentations', TRUE, FALSE, TRUE, TRUE);

-- Software Category (ID 4 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'SOFTWARE'), 'OS', 'Operating System', 'Operating system license', FALSE, TRUE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'SOFTWARE'), 'OFFICE_SUITE', 'Office Suite', 'Office productivity software', FALSE, TRUE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'SOFTWARE'), 'ANTIVIRUS', 'Antivirus Software', 'Antivirus and security software', FALSE, TRUE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'SOFTWARE'), 'DEVELOPMENT', 'Development Tools', 'Software development tools and IDEs', FALSE, TRUE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'SOFTWARE'), 'DB_SOFTWARE', 'Database Software', 'Database management software', FALSE, TRUE, FALSE, FALSE);

-- Furniture Category (ID 5 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'FURNITURE'), 'DESK', 'Office Desk', 'Office desk with drawers', TRUE, FALSE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'FURNITURE'), 'CHAIR', 'Office Chair', 'Ergonomic office chair', TRUE, FALSE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'FURNITURE'), 'CABINET', 'Filing Cabinet', 'Filing and storage cabinet', TRUE, FALSE, FALSE, FALSE),
((SELECT id FROM categories WHERE name = 'FURNITURE'), 'SHELF', 'Bookshelf', 'Bookcase and shelving unit', TRUE, FALSE, FALSE, FALSE);

-- Mobile Category (ID 6 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'MOBILE'), 'TABLET', 'Tablet', 'Tablet computer', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'MOBILE'), 'SMARTPHONE', 'Smartphone', 'Mobile phone device', TRUE, FALSE, TRUE, TRUE);

-- Security Category (ID 7 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'SECURITY'), 'CAMERA', 'CCTV Camera', 'Surveillance camera', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'SECURITY'), 'RECORDER', 'DVR/NVR', 'Digital video recorder for CCTV', TRUE, FALSE, FALSE, TRUE),
((SELECT id FROM categories WHERE name = 'SECURITY'), 'ACCESS_CTRL', 'Access Controller', 'Door access control system', TRUE, FALSE, FALSE, TRUE);

-- Audio Visual Category (ID 8 expected)
INSERT INTO asset_types (category_id, name, display_name, description, track_individual, track_quantity, allow_temporary_loans, has_service_records) VALUES
((SELECT id FROM categories WHERE name = 'AUDIO_VISUAL'), 'PROJECTOR', 'AV Projector', 'Multimedia projector', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'AUDIO_VISUAL'), 'SPEAKER', 'Speaker System', 'Audio speaker system', TRUE, FALSE, TRUE, FALSE),
((SELECT id FROM categories WHERE name = 'AUDIO_VISUAL'), 'MICROPHONE', 'Microphone', 'Wireless microphone system', TRUE, FALSE, TRUE, TRUE),
((SELECT id FROM categories WHERE name = 'AUDIO_VISUAL'), 'INTERACTIVE_BOARD', 'Interactive Board', 'Smart board for presentations', TRUE, FALSE, TRUE, TRUE);

-- =============================================
-- 3. MODELS
-- =============================================

-- Laptop Models (Asset Type 'LAPTOP')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'Latitude 5440', '{"cpu": "Intel Core i7-1355U", "ram": "16GB DDR5", "storage": "512GB NVMe SSD", "display": "14 inch FHD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Dell', 'Latitude 3440', '{"cpu": "Intel Core i5-1335U", "ram": "8GB DDR5", "storage": "256GB NVMe SSD", "display": "14 inch HD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'HP', 'EliteBook 840 G10', '{"cpu": "Intel Core i7-1365U", "ram": "16GB DDR5", "storage": "512GB NVMe SSD", "display": "14 inch WUXGA", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'HP', 'ProBook 450 G10', '{"cpu": "Intel Core i5-1335U", "ram": "8GB DDR5", "storage": "256GB NVMe SSD", "display": "15.6 inch HD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Lenovo', 'ThinkPad X1 Carbon Gen 11', '{"cpu": "Intel Core i7-1365U", "ram": "16GB LPDDR5", "storage": "1TB NVMe SSD", "display": "14 inch 2.8K OLED", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Lenovo', 'ThinkPad E16', '{"cpu": "Intel Core i5-1335U", "ram": "8GB DDR5", "storage": "256GB NVMe SSD", "display": "16 inch WUXGA", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Apple', 'MacBook Air M2', '{"cpu": "Apple M2", "ram": "8GB Unified", "storage": "256GB SSD", "display": "13.6 inch Liquid Retina", "os": "macOS"}'),
((SELECT id FROM asset_types WHERE name = 'LAPTOP'), 'Apple', 'MacBook Pro M3', '{"cpu": "Apple M3 Pro", "ram": "18GB Unified", "storage": "512GB SSD", "display": "14.2 inch Liquid Retina XDR", "os": "macOS"}');

-- Desktop Models (Asset Type 'DESKTOP')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Dell', 'OptiPlex 7010 Plus', '{"cpu": "Intel Core i7-13700", "ram": "16GB DDR5", "storage": "512GB NVMe SSD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Dell', 'OptiPlex 3000', '{"cpu": "Intel Core i5-12500", "ram": "8GB DDR4", "storage": "256GB SSD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'HP', 'EliteDesk 800 G9', '{"cpu": "Intel Core i7-13700", "ram": "16GB DDR5", "storage": "512GB NVMe SSD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'HP', 'ProDesk 400 G9', '{"cpu": "Intel Core i5-13500", "ram": "8GB DDR5", "storage": "256GB NVMe SSD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Lenovo', 'ThinkCentre M90t Gen 4', '{"cpu": "Intel Core i7-13700", "ram": "16GB DDR5", "storage": "512GB NVMe SSD", "os": "Windows 11 Pro"}'),
((SELECT id FROM asset_types WHERE name = 'DESKTOP'), 'Lenovo', 'ThinkCentre M70s Gen 4', '{"cpu": "Intel Core i5-13500", "ram": "8GB DDR5", "storage": "256GB NVMe SSD", "os": "Windows 11 Pro"}');

-- Workstation Models (Asset Type 'WORKSTATION')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'WORKSTATION'), 'Dell', 'Precision 3660', '{"cpu": "Intel Xeon W-1390", "ram": "32GB DDR5", "storage": "1TB NVMe SSD", "gpu": "NVIDIA RTX A2000", "os": "Windows 11 Pro for Workstations"}'),
((SELECT id FROM asset_types WHERE name = 'WORKSTATION'), 'HP', 'Z4 G5', '{"cpu": "Intel Xeon w5-2455X", "ram": "32GB DDR5", "storage": "1TB NVMe SSD", "gpu": "NVIDIA RTX A4000", "os": "Windows 11 Pro for Workstations"}'),
((SELECT id FROM asset_types WHERE name = 'WORKSTATION'), 'Lenovo', 'ThinkStation P3', '{"cpu": "Intel Xeon W-1390", "ram": "32GB DDR5", "storage": "512GB NVMe SSD", "gpu": "NVIDIA RTX A2000", "os": "Windows 11 Pro for Workstations"}');

-- Server Models (Asset Type 'SERVER')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'SERVER'), 'Dell', 'PowerEdge R760', '{"cpu": "2x Intel Xeon Gold 6448Y", "ram": "128GB DDR5", "storage": "4x 1.92TB NVMe SSD", "raid": "PERC H755", "psu": "Dual 1400W"}'),
((SELECT id FROM asset_types WHERE name = 'SERVER'), 'HP', 'ProLiant DL380 Gen11', '{"cpu": "2x Intel Xeon Gold 6448Y", "ram": "128GB DDR5", "storage": "4x 1.92TB NVMe SSD", "raid": "MR408i-o", "psu": "Dual 1600W"}'),
((SELECT id FROM asset_types WHERE name = 'SERVER'), 'Lenovo', 'ThinkSystem SR650 V3', '{"cpu": "2x Intel Xeon Gold 6448Y", "ram": "128GB DDR5", "storage": "4x 1.92TB NVMe SSD", "raid": "ThinkSystem RAID 940-8i", "psu": "Dual 1600W"}');

-- Switch Models (Asset Type 'SWITCH')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'SWITCH'), 'Cisco', 'Catalyst 9300', '{"ports": 48, "speed": "1Gbps/10Gbps", "layer": "Layer 3", "poe": "PoE+", "redundant_psu": "Yes"}'),
((SELECT id FROM asset_types WHERE name = 'SWITCH'), 'Cisco', 'Catalyst 9200', '{"ports": 24, "speed": "1Gbps", "layer": "Layer 2/3", "poe": "PoE+", "redundant_psu": "Optional"}'),
((SELECT id FROM asset_types WHERE name = 'SWITCH'), 'TP-Link', 'TL-SG3428X', '{"ports": 24, "speed": "1Gbps", "layer": "Layer 2/3", "poe": "No", "redundant_psu": "No"}'),
((SELECT id FROM asset_types WHERE name = 'SWITCH'), 'Ubiquiti', 'USW-Pro-48-PoE', '{"ports": 48, "speed": "1Gbps/10Gbps", "layer": "Layer 3", "poe": "PoE+", "redundant_psu": "Yes"}');

-- Router Models (Asset Type 'ROUTER')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'ROUTER'), 'Cisco', 'ISR 4321', '{"throughput": "50Mbps-100Mbps", "slots": 4, "redundant_psu": "Yes", "security": "VPN/SSL"}'),
((SELECT id FROM asset_types WHERE name = 'ROUTER'), 'MikroTik', 'CCR2004-16G-2S+', '{"throughput": "40Gbps", "ports": "16x1Gbps, 2x10Gbps SFP+", "license": "RouterOS v7", "redundant_psu": "Yes"}'),
((SELECT id FROM asset_types WHERE name = 'ROUTER'), 'Ubiquiti', 'EdgeRouter 12', '{"throughput": "2.4Gbps", "ports": "10x1Gbps, 2xSFP", "redundant_psu": "No"}');

-- Access Point Models (Asset Type 'ACCESS_POINT')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'ACCESS_POINT'), 'Ubiquiti', 'UniFi U6 Pro', '{"speed": "2.4Gbps", "band": "Tri-band", "standard": "Wi-Fi 6", "poe": "PoE+", "range": "150m"}'),
((SELECT id FROM asset_types WHERE name = 'ACCESS_POINT'), 'Ubiquiti', 'UniFi U6 Lite', '{"speed": "1.5Gbps", "band": "Dual-band", "standard": "Wi-Fi 6", "poe": "PoE", "range": "120m"}'),
((SELECT id FROM asset_types WHERE name = 'ACCESS_POINT'), 'TP-Link', 'Omada EAP670', '{"speed": "3.6Gbps", "band": "Tri-band", "standard": "Wi-Fi 6", "poe": "PoE+", "range": "130m"}'),
((SELECT id FROM asset_types WHERE name = 'ACCESS_POINT'), 'Cisco', 'Meraki MR46', '{"speed": "3.6Gbps", "band": "Tri-band", "standard": "Wi-Fi 6", "poe": "PoE+", "range": "140m"}');

-- Firewall Models (Asset Type 'FIREWALL')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'FIREWALL'), 'Fortinet', 'FortiGate 100F', '{"throughput": "10Gbps", "connections": "4M", "vpn": "IPsec/SSL", "redundant_psu": "Yes"}'),
((SELECT id FROM asset_types WHERE name = 'FIREWALL'), 'Cisco', 'Firepower 1120', '{"throughput": "6.5Gbps", "connections": "2.5M", "vpn": "IPsec/SSL", "redundant_psu": "Optional"}'),
((SELECT id FROM asset_types WHERE name = 'FIREWALL'), 'SonicWall', 'TZ670', '{"throughput": "3.5Gbps", "connections": "1.5M", "vpn": "IPsec/SSL", "redundant_psu": "No"}');

-- Monitor Models (Asset Type 'MONITOR')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'MONITOR'), 'Dell', 'UltraSharp U2723QE', '{"size": "27 inch", "resolution": "3840x2160 4K", "panel": "IPS", "refresh": "60Hz", "ports": "USB-C HDMI DisplayPort"}'),
((SELECT id FROM asset_types WHERE name = 'MONITOR'), 'Dell', 'P2422H', '{"size": "24 inch", "resolution": "1920x1080", "panel": "IPS", "refresh": "60Hz", "ports": "DisplayPort VGA HDMI"}'),
((SELECT id FROM asset_types WHERE name = 'MONITOR'), 'LG', '27UP850N-W', '{"size": "27 inch", "resolution": "3840x2160 4K", "panel": "IPS", "refresh": "60Hz", "ports": "USB-C HDMI DisplayPort"}'),
((SELECT id FROM asset_types WHERE name = 'MONITOR'), 'Samsung', 'S27A800UJN', '{"size": "27 inch", "resolution": "3840x2160 4K", "panel": "IPS", "refresh": "60Hz", "ports": "USB-C HDMI DisplayPort"}');

-- Printer Models (Asset Type 'PRINTER')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'PRINTER'), 'HP', 'LaserJet Enterprise M607dn', '{"type": "Laser", "speed": "65ppm", "resolution": "1200x1200dpi", "duplex": "Yes", "color": "No"}'),
((SELECT id FROM asset_types WHERE name = 'PRINTER'), 'Brother', 'HL-L6400DW', '{"type": "Laser", "speed": "52ppm", "resolution": "1200x1200dpi", "duplex": "Yes", "color": "No"}'),
((SELECT id FROM asset_types WHERE name = 'PRINTER'), 'HP', 'Color LaserJet Enterprise M653dn', '{"type": "Laser", "speed": "55ppm", "resolution": "1200x1200dpi", "duplex": "Yes", "color": "Yes"}');

-- Tablet Models (Asset Type 'TABLET')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'TABLET'), 'Apple', 'iPad 10th Gen', '{"storage": "64GB", "display": "10.9 inch Liquid Retina", "cellular": "Wi-Fi only", "os": "iPadOS"}'),
((SELECT id FROM asset_types WHERE name = 'TABLET'), 'Apple', 'iPad Air 5th Gen', '{"storage": "256GB", "display": "10.9 inch Liquid Retina", "cellular": "5G Ready", "os": "iPadOS"}'),
((SELECT id FROM asset_types WHERE name = 'TABLET'), 'Samsung', 'Galaxy Tab S9 FE', '{"storage": "128GB", "display": "10.9 inch TFT LCD", "cellular": "5G Ready", "os": "Android"}');

-- Smartphone Models (Asset Type 'SMARTPHONE')
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'SMARTPHONE'), 'Apple', 'iPhone 14', '{"storage": "128GB", "display": "6.1 inch Super Retina XDR", "camera": "Dual 12MP", "os": "iOS"}'),
((SELECT id FROM asset_types WHERE name = 'SMARTPHONE'), 'Apple', 'iPhone 13', '{"storage": "128GB", "display": "6.1 inch Super Retina XDR", "camera": "Dual 12MP", "os": "iOS"}'),
((SELECT id FROM asset_types WHERE name = 'SMARTPHONE'), 'Samsung', 'Galaxy S23', '{"storage": "256GB", "display": "6.1 inch Dynamic AMOLED", "camera": "Triple 50MP", "os": "Android"}');

-- Projector Models (Asset Type 'PROJECTOR' from PERIPHERAL)
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'PROJECTOR' AND category_id = (SELECT id FROM categories WHERE name = 'PERIPHERAL')), 'Epson', 'PowerLite L630U', '{"lumens": "6200", "resolution": "1920x1200", "technology": "3LCD", "lamp_life": "20000h", "wireless": "Yes"}'),
((SELECT id FROM asset_types WHERE name = 'PROJECTOR' AND category_id = (SELECT id FROM categories WHERE name = 'PERIPHERAL')), 'BenQ', 'LU930', '{"lumens": "6000", "resolution": "1920x1200", "technology": "DLP", "lamp_life": "20000h", "wireless": "Yes"}');

-- AV Projector Models (Asset Type 'PROJECTOR' from AUDIO_VISUAL)
INSERT INTO models (asset_type_id, brand, model_number, specs) VALUES
((SELECT id FROM asset_types WHERE name = 'PROJECTOR' AND category_id = (SELECT id FROM categories WHERE name = 'AUDIO_VISUAL')), 'Epson', 'PowerLite L630U', '{"lumens": "6200", "resolution": "1920x1200", "technology": "3LCD", "lamp_life": "20000h", "wireless": "Yes"}'),
((SELECT id FROM asset_types WHERE name = 'PROJECTOR' AND category_id = (SELECT id FROM categories WHERE name = 'AUDIO_VISUAL')), 'BenQ', 'LU930', '{"lumens": "6000", "resolution": "1920x1200", "technology": "DLP", "lamp_life": "20000h", "wireless": "Yes"}');
