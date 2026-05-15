# MUAST Asset Registry — Test Data Checklist

## File Order (Must Run Sequentially)

| # | File | Tables Populated | Dependencies |
|---|------|-----------------|--------------|
| 1 | `1_auth_users.sql` | roles, permissions, role_permissions, users, user_roles | None |
| 2 | `2_locations.sql` | campuses, offices | None |
| 3 | `3_asset_catalog.sql` | categories, asset_types | None |
| 4 | `4_grv.sql` | grv_entries, grv_items | users, asset_types |
| 5 | `5_assets.sql` | assets, asset_locations, asset_assignments | grv_items, asset_types, campuses, offices, users |
| 6 | `6_inventory.sql` | inventory_items, inventory_grv_links, inventory_transactions | asset_types, offices, assets, grv_items, users |
| 7 | `7_loans.sql` | temporary_loans | assets, users |
| 8 | `8_services.sql` | service_entries | assets, grv_items, users |
| 9 | `9_audit_logs.sql` | audit_logs | users |

---

## 1. Auth & Users (`1_auth_users.sql`) ✅ DONE

- [ ] 4 Roles: `USER`, `IT_STAFF`, `VIEWER`, `ADMIN`
- [ ] 13 Permissions (ACTION_ENTITY convention)
- [ ] ADMIN role → all 13 permissions
- [ ] IT_STAFF role → 7 permissions
- [ ] VIEWER role → 5 permissions
- [ ] USER role → no permissions
- [ ] 10 Users (all use password: `password123`)
  - [ ] 2 ADMIN: `admin.john`, `admin.jane`
  - [ ] 3 IT_STAFF: `it.mary`, `it.peter`, `it.sarah`
  - [ ] 2 VIEWER: `viewer.david`, `viewer.linda`
  - [ ] 3 USER: `user.tendai`, `user.chipo`, `user.tawanda`

---

## 2. Locations (`2_locations.sql`)

### Campuses
- [ ] CSC — Main Campus (CSC)
- [ ] MAR — Marondera Campus
- [ ] AIP — AIP Campus

### Offices
- [ ] **CSC Offices:**
  - [ ] IT Office
  - [ ] Admin Office
  - [ ] Finance Department
  - [ ] Computer Lab 1
  - [ ] Computer Lab 2
  - [ ] Library
  - [ ] Lecture Hall A
  - [ ] Stores/Storage Room
- [ ] **MAR Offices:**
  - [ ] IT Office
  - [ ] Admin Office
  - [ ] Library
- [ ] **AIP Offices:**
  - [ ] IT Office
  - [ ] Admin Office

---

## 3. Asset Catalog (`3_asset_catalog.sql`)

### Categories
- [ ] COMPUTER — Computers & Laptops
- [ ] PRINTER — Printers & Scanners
- [ ] PROJECTOR — Projectors & Displays
- [ ] NETWORK — Networking Equipment
- [ ] CONSUMABLE — Consumables & Supplies
- [ ] PERIPHERAL — Peripherals & Accessories

### Asset Types
- [ ] **COMPUTER category:**
  - [ ] DESKTOP — Desktop Computer (track_individual=true)
  - [ ] LAPTOP — Laptop (track_individual=true)
- [ ] **PRINTER category:**
  - [ ] PRINTER — Printer (track_individual=true)
- [ ] **PROJECTOR category:**
  - [ ] PROJECTOR — Projector (track_individual=true)
- [ ] **NETWORK category:**
  - [ ] SWITCH — Network Switch (track_individual=true)
  - [ ] ROUTER — Router (track_individual=true)
- [ ] **CONSUMABLE category:**
  - [ ] TONER — Toner Cartridge (track_quantity=true)
  - [ ] PAPER — Paper Ream (track_quantity=true)
- [ ] **PERIPHERAL category:**
  - [ ] MONITOR — Monitor (track_individual=true)
  - [ ] KEYBOARD — Keyboard (track_quantity=true)
  - [ ] MOUSE — Mouse (track_quantity=true)

---

## 4. GRV — Goods Received (`4_grv.sql`)

- [ ] **GRV #1:** 10 Dell Latitude 5420 Laptops @ $750 each
  - [ ] 10 individual GRV items with serial numbers
- [ ] **GRV #2:** 20 HP CF277X Toner Cartridges @ $85 each
  - [ ] 1 quantity-tracked GRV item
- [ ] **GRV #3:** 5 Epson EB-1795F Projectors @ $1,200 each
  - [ ] 5 individual GRV items with serial numbers
- [ ] **GRV #4:** 3 HP LaserJet M404dn Printers @ $450 each
  - [ ] 3 individual GRV items with serial numbers

---

## 5. Assets — Individual Tracked (`5_assets.sql`)

### Laptops (from GRV #1)
- [ ] CSC-DSK-001 → Dell Latitude 5420, serial: LAT5420-001, status: ASSIGNED
- [ ] CSC-DSK-002 → Dell Latitude 5420, serial: LAT5420-002, status: ASSIGNED
- [ ] CSC-DSK-003 → Dell Latitude 5420, serial: LAT5420-003, status: AVAILABLE
- [ ] CSC-DSK-004 → Dell Latitude 5420, serial: LAT5420-004, status: IN_REPAIR
- [ ] MAR-DSK-001 → Dell Latitude 5420, serial: LAT5420-005, status: ASSIGNED
- [ ] MAR-DSK-002 → Dell Latitude 5420, serial: LAT5420-006, status: AVAILABLE
- [ ] AIP-DSK-001 → Dell Latitude 5420, serial: LAT5420-007, status: ASSIGNED
- [ ] CSC-DSK-005 → Dell Latitude 5420, serial: LAT5420-008, status: LOST
- [ ] CSC-DSK-006 → Dell Latitude 5420, serial: LAT5420-009, status: AVAILABLE
- [ ] CSC-DSK-007 → Dell Latitude 5420, serial: LAT5420-010, status: AVAILABLE

### Projectors (from GRV #3)
- [ ] CSC-PRJ-001 → Epson EB-1795F, serial: EB1795-001, status: AVAILABLE
- [ ] CSC-PRJ-002 → Epson EB-1795F, serial: EB1795-002, status: ON_LOAN
- [ ] MAR-PRJ-001 → Epson EB-1795F, serial: EB1795-003, status: AVAILABLE
- [ ] AIP-PRJ-001 → Epson EB-1795F, serial: EB1795-004, status: AVAILABLE
- [ ] CSC-PRJ-003 → Epson EB-1795F, serial: EB1795-005, status: IN_REPAIR

### Printers (from GRV #4)
- [ ] CSC-PRT-001 → HP LaserJet M404dn, serial: M404-001, status: ASSIGNED, purchase_cost: $450
- [ ] CSC-PRT-002 → HP LaserJet M404dn, serial: M404-002, status: AVAILABLE, purchase_cost: $450
- [ ] MAR-PRT-001 → HP LaserJet M404dn, serial: M404-003, status: ASSIGNED, purchase_cost: $450

### Asset Locations
- [ ] Each asset assigned to appropriate campus + office

### Asset Assignments
- [ ] ASSIGNED assets linked to IT_STAFF or ADMIN users
- [ ] At least 2 assets with assignment history (previous + current)

---

## 6. Inventory — Quantity Tracked (`6_inventory.sql`)

### Inventory Items
- [ ] HP CF277X Toner @ IT Storage (CSC) — qty: 15
- [ ] HP CF277X Toner @ Finance Dept (CSC) — qty: 3
- [ ] HP CF277X Toner @ Admin Office (CSC) — qty: 2
- [ ] HP CF277X Toner @ MAR IT Office — qty: 0 (empty — needs restock)
- [ ] A4 Paper Ream @ IT Storage (CSC) — qty: 50
- [ ] A4 Paper Ream @ Admin Office (CSC) — qty: 10
- [ ] Keyboard (generic) @ IT Storage (CSC) — qty: 20
- [ ] Mouse (generic) @ IT Storage (CSC) — qty: 25

### Inventory Transactions
- [ ] RECEIVED: 20 toners from GRV #2
- [ ] SENT: 5 toners to Finance Dept
- [ ] SENT: 3 toners to Admin Office
- [ ] SENT: 2 toners to MAR IT Office
- [ ] RETURNED: 2 toners from MAR (wrong model)
- [ ] RECEIVED: 100 paper reams
- [ ] SENT: 10 paper reams to Admin Office

### Inventory-GRV Links
- [ ] Toner inventory linked to GRV #2

### Printer Consumable Costs
- [ ] CSC-PRT-001: total_consumable_cost = $340 (4 toners used)
- [ ] CSC-PRT-002: total_consumable_cost = $0
- [ ] MAR-PRT-001: total_consumable_cost = $170 (2 toners used)

---

## 7. Temporary Loans (`7_loans.sql`)

- [ ] CSC-PRJ-001 loaned to `it.mary` → returned same day
- [ ] CSC-PRJ-002 currently on loan to `viewer.david` (overdue by 2 days)
- [ ] CSC-PRJ-001 loaned to `user.tendai` → returned after 3 days
- [ ] CSC-PRJ-003 loaned to `admin.jane` → still active (due tomorrow)

### Loan Details
- [ ] Accessories tracked: power cable, HDMI adapter, remote
- [ ] Sign-out/sign-in signatures recorded

---

## 8. Service Entries (`8_services.sql`)

- [ ] CSC-DSK-004 (Laptop): Screen repair, $120, vendor "TechFix Solutions"
- [ ] CSC-PRJ-003 (Projector): Lamp replacement, $250, vendor "AV Services Ltd"
- [ ] CSC-PRT-001 (Printer): Maintenance service, $75, vendor "HP Support"
- [ ] CSC-DSK-001 (Laptop): Keyboard replacement, $90, vendor "TechFix Solutions"

---

## 9. Audit Logs (`9_audit_logs.sql`)

- [ ] CREATE: GRV Entry #1
- [ ] CREATE: Assets (multiple from GRV #1)
- [ ] ASSIGN: CSC-DSK-001 → admin.john
- [ ] SEND: 5 toners → Finance Dept
- [ ] CREATE: Temporary Loan CSC-PRJ-002
- [ ] UPDATE: CSC-DSK-004 status → IN_REPAIR
- [ ] LOGIN: Various user logins

---

## Status Summary

| File | Status |
|------|--------|
| `1_auth_users.sql` | ✅ Complete |
| `2_locations.sql` | ⬜ To do |
| `3_asset_catalog.sql` | ⬜ To do |
| `4_grv.sql` | ⬜ To do |
| `5_assets.sql` | ⬜ To do |
| `6_inventory.sql` | ⬜ To do |
| `7_loans.sql` | ⬜ To do |
| `8_services.sql` | ⬜ To do |
| `9_audit_logs.sql` | ⬜ To do |