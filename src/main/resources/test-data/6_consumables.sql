-- =============================================
-- FILE 5: CONSUMABLES TEST DATA
-- =============================================
-- Description: Test data for quantity-tracked consumables
-- Order: Run this FIFTH (after File 4)
-- Tables: consumables, consumable_stock, consumable_transactions
-- Dependencies: asset_types, offices, assets
-- =============================================

-- =============================================
-- 1. CONSUMABLES (product definitions)
-- =============================================

-- Toners (unique on asset_type_id + brand)
INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'HP', 'HP CF277X Black Toner', '{"yield": "3100 pages", "compatible": "HP LaserJet M404"}', 'Standard black toner'
FROM asset_types at WHERE at.code = 'TONER';

INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'HP Economy', 'HP CF277A Black Toner', '{"yield": "1500 pages", "compatible": "HP LaserJet M404"}', 'Economy black toner'
FROM asset_types at WHERE at.code = 'TONER';

INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Brother', 'Brother TN-820 Black Toner', '{"yield": "3000 pages", "compatible": "Brother HL-L6400DW"}', 'Brother printer toner'
FROM asset_types at WHERE at.code = 'TONER';

-- Paper
INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Typek', 'Typek A4 Copy Paper', '{"size": "A4", "gsm": "80", "sheets": "500"}', 'Standard office paper'
FROM asset_types at WHERE at.code = 'PAPER';

-- Keyboards
INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Dell', 'Dell USB Wired Keyboard', '{"type": "USB", "layout": "QWERTY"}', 'Standard office keyboard'
FROM asset_types at WHERE at.code = 'KEYBOARD';

INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Logitech', 'Logitech K120 Keyboard', '{"type": "USB", "layout": "QWERTY"}', 'Budget keyboard'
FROM asset_types at WHERE at.code = 'KEYBOARD';

-- Mice
INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Dell', 'Dell USB Optical Mouse', '{"type": "USB", "dpi": "1000"}', 'Standard office mouse'
FROM asset_types at WHERE at.code = 'MOUSE';

INSERT INTO consumables (asset_type_id, brand, name, specs, notes)
SELECT at.id, 'Logitech', 'Logitech M90 Mouse', '{"type": "USB", "dpi": "1000"}', 'Budget mouse'
FROM asset_types at WHERE at.code = 'MOUSE';

-- =============================================
-- 2. CONSUMABLE STOCK (per office quantities)
-- =============================================

-- HP CF277X Black Toner
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 15 FROM consumables c, offices o
WHERE c.brand = 'HP' AND c.name = 'HP CF277X Black Toner' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 3 FROM consumables c, offices o
WHERE c.brand = 'HP' AND c.name = 'HP CF277X Black Toner' AND o.code = 'CSC_FINANCE';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 2 FROM consumables c, offices o
WHERE c.brand = 'HP' AND c.name = 'HP CF277X Black Toner' AND o.code = 'MAR_IT_DEPT';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 1 FROM consumables c, offices o
WHERE c.brand = 'HP' AND c.name = 'HP CF277X Black Toner' AND o.code = 'AIP_IT_DEPT';

-- HP CF277A Economy Toner
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 8 FROM consumables c, offices o
WHERE c.brand = 'HP Economy' AND c.name = 'HP CF277A Black Toner' AND o.code = 'CSC_IT_STORES';

-- Brother TN-820
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 5 FROM consumables c, offices o
WHERE c.brand = 'Brother' AND c.name = 'Brother TN-820 Black Toner' AND o.code = 'CSC_IT_STORES';

-- Typek A4 Paper
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 200 FROM consumables c, offices o
WHERE c.brand = 'Typek' AND c.name = 'Typek A4 Copy Paper' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 50 FROM consumables c, offices o
WHERE c.brand = 'Typek' AND c.name = 'Typek A4 Copy Paper' AND o.code = 'CSC_REGISTRAR';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 30 FROM consumables c, offices o
WHERE c.brand = 'Typek' AND c.name = 'Typek A4 Copy Paper' AND o.code = 'CSC_FINANCE';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 20 FROM consumables c, offices o
WHERE c.brand = 'Typek' AND c.name = 'Typek A4 Copy Paper' AND o.code = 'MAR_CAMPUS_DIRECTOR';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 15 FROM consumables c, offices o
WHERE c.brand = 'Typek' AND c.name = 'Typek A4 Copy Paper' AND o.code = 'AIP_IT_DEPT';

-- Keyboards
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 25 FROM consumables c, offices o
WHERE c.brand = 'Dell' AND c.name = 'Dell USB Wired Keyboard' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 10 FROM consumables c, offices o
WHERE c.brand = 'Logitech' AND c.name = 'Logitech K120 Keyboard' AND o.code = 'CSC_IT_STORES';

-- Mice
INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 30 FROM consumables c, offices o
WHERE c.brand = 'Dell' AND c.name = 'Dell USB Optical Mouse' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_stock (consumable_id, office_id, quantity)
SELECT c.id, o.id, 15 FROM consumables c, offices o
WHERE c.brand = 'Logitech' AND c.name = 'Logitech M90 Mouse' AND o.code = 'CSC_IT_STORES';

-- =============================================
-- 3. CONSUMABLE TRANSACTIONS
-- =============================================

-- Initial stock received
INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 20, 85.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock from supplier'
FROM consumables c, offices o WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 10, 65.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock from supplier'
FROM consumables c, offices o WHERE c.name = 'HP CF277A Black Toner' AND c.brand = 'HP Economy' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 8, 75.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock from supplier'
FROM consumables c, offices o WHERE c.name = 'Brother TN-820 Black Toner' AND c.brand = 'Brother' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 250, 5.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock — 50 reams'
FROM consumables c, offices o WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 30, 8.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock'
FROM consumables c, offices o WHERE c.name = 'Dell USB Wired Keyboard' AND c.brand = 'Dell' AND o.code = 'CSC_IT_STORES';

INSERT INTO consumable_transactions (consumable_id, quantity, unit_cost, transaction_type, destination_office_id, transaction_date, notes)
SELECT c.id, 40, 5.00, 'RECEIVED', o.id, '2024-03-01 09:00:00', 'Initial stock'
FROM consumables c, offices o WHERE c.name = 'Dell USB Optical Mouse' AND c.brand = 'Dell' AND o.code = 'CSC_IT_STORES';

-- Toner transferred to departments
INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 3, 'TRANSFERRED', o1.id, o2.id, '2024-04-15 10:00:00', 'Restock Finance printer'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'CSC_FINANCE';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 2, 'TRANSFERRED', o1.id, o2.id, '2024-05-10 11:00:00', 'MAR campus restock'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'MAR_IT_DEPT';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 1, 'TRANSFERRED', o1.id, o2.id, '2024-06-20 14:00:00', 'AIP campus restock'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'AIP_IT_DEPT';

-- Toner consumed by printers
INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, asset_id, transaction_date, notes)
SELECT c.id, 1, 'CONSUMED', o.id, a.id, '2024-05-01 08:00:00', 'Toner depleted — replaced'
FROM consumables c, offices o, assets a
WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o.code = 'CSC_FINANCE' AND a.code = 'CSC-PRT-001';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, asset_id, transaction_date, notes)
SELECT c.id, 1, 'CONSUMED', o.id, a.id, '2024-08-15 09:00:00', 'Toner depleted — replaced'
FROM consumables c, offices o, assets a
WHERE c.name = 'HP CF277X Black Toner' AND c.brand = 'HP' AND o.code = 'CSC_FINANCE' AND a.code = 'CSC-PRT-001';

-- Paper transferred to departments
INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 50, 'TRANSFERRED', o1.id, o2.id, '2024-03-15 11:00:00', 'Monthly paper allocation'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'CSC_REGISTRAR';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 30, 'TRANSFERRED', o1.id, o2.id, '2024-03-15 11:00:00', 'Monthly paper allocation'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'CSC_FINANCE';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 20, 'TRANSFERRED', o1.id, o2.id, '2024-06-20 14:00:00', 'MAR campus paper supply'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'MAR_CAMPUS_DIRECTOR';

INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, destination_office_id, transaction_date, notes)
SELECT c.id, 15, 'TRANSFERRED', o1.id, o2.id, '2024-09-10 10:00:00', 'AIP campus paper supply'
FROM consumables c, offices o1, offices o2
WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o1.code = 'CSC_IT_STORES' AND o2.code = 'AIP_IT_DEPT';

-- Stock adjustment (damage)
INSERT INTO consumable_transactions (consumable_id, quantity, transaction_type, source_office_id, transaction_date, notes)
SELECT c.id, -2, 'ADJUSTED', o.id, '2024-07-01 16:00:00', 'Damaged stock — water leak in storage'
FROM consumables c, offices o WHERE c.name = 'Typek A4 Copy Paper' AND c.brand = 'Typek' AND o.code = 'CSC_IT_STORES';

-- =============================================
-- SUMMARY
-- =============================================

/*
┌──────────────────────────────────┬──────────────────────────────────────┐
│ Consumable                       │ Stock Locations                      │
├──────────────────────────────────┼──────────────────────────────────────┤
│ HP CF277X Black Toner            │ CSC IT Stores: 15                    │
│                                  │ CSC Finance: 3                       │
│                                  │ MAR IT Dept: 2                       │
│                                  │ AIP IT Dept: 1                       │
├──────────────────────────────────┼──────────────────────────────────────┤
│ HP CF277A Black Toner (Economy)  │ CSC IT Stores: 8                     │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Brother TN-820 Black Toner       │ CSC IT Stores: 5                     │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Typek A4 Copy Paper              │ CSC IT Stores: 198 (-2 damaged)      │
│                                  │ CSC Registrar: 50                    │
│                                  │ CSC Finance: 30                      │
│                                  │ MAR Campus Director: 20              │
│                                  │ AIP IT Dept: 15                      │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Dell USB Wired Keyboard          │ CSC IT Stores: 25                    │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Logitech K120 Keyboard           │ CSC IT Stores: 10                    │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Dell USB Optical Mouse           │ CSC IT Stores: 30                    │
├──────────────────────────────────┼──────────────────────────────────────┤
│ Logitech M90 Mouse               │ CSC IT Stores: 15                    │
└──────────────────────────────────┴──────────────────────────────────────┘

Transactions: 16 (6 RECEIVED, 6 TRANSFERRED, 2 CONSUMED, 1 ADJUSTED)
Consumables: 8 products
Stock locations: 17
*/