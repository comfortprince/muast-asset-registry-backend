**(MUAST – IT Department)**

**Date:** May 28, 2026
**Prepared By:** [Your Name/Title]
**Version:** 1.0

## 1. Executive Summary

### Current Situation
MUAST currently manages its IT assets across three campuses (CSC, MAR, AIP) using a combination of manual logbooks and spreadsheets. Key documents like Goods Received Vouchers (GRVs), assignment ledgers, and loan registers are maintained on paper.

### Key Problem
This manual approach presents significant operational and audit risks. As the university grows, the lack of a centralized system leads to **poor asset visibility**, **weak accountability**, **inefficient maintenance tracking**, and **difficulty in audit compliance**. There is no single, reliable source of truth for the location, status, or history of an institutional asset.

### Proposed Solution
This proposal recommends implementing a **digital, web-based IT Asset Management System**.

### Expected Benefits
- **Improved Asset Visibility:** Real-time view of all assets, their location, and status.
- **Stronger Accountability:** Clear tracking of asset assignment to individuals or departments.
- **Reduced Asset Loss:** Digital record-keeping reduces the risk of undetected loss.
- **Better Maintenance Planning:** Track service history and schedule repairs.
- **Audit Readiness:** Streamlined reporting and a clear audit trail for every asset.

## 2. Problem Statement

The IT Department faces the following challenges with the current asset management process:

- **Lack of Visibility:** There is no centralized system to see the total inventory, where assets are located, or who is responsible for them. The status of an asset (e.g., in repair, in storage, lost) is not easily determined.
- **Weak Accountability:** The assignment of assets is tracked manually, making it difficult to resolve accountability issues when assets are lost or damaged. The historical trail of asset ownership is fragmented.
- **Inefficient Tracking:** The lifecycle of different asset types (e.g., projectors with daily loans, toners as consumables) is tracked inconsistently which might lead to stock-out or loss. Maintenance and repair history is not easily linked to the asset record.
- **Audit Risk:** The University’s growth will magnify these inefficiencies, putting the institution at risk for asset misappropriation and non-compliance during audits. Generating a complete inventory report is a laborious and error-prone manual process.


## 3. Current State Analysis

The IT Department currently manages assets using **disparate manual documents and logbooks**, each serving a specific purpose but operating in isolation. This fragmentation creates data redundancy, reconciliation challenges, and a lack of a single source of truth.

### 3.1 Inventory of Existing Documents

| #     | Document Name                    | Purpose                                                                      | Key Fields                                                                                | Limitation                                                                   |
| :---- | :------------------------------- | :--------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------- |
| **1** | **Goods Received Voucher (GRV)** | Records all incoming goods (assets, consumables, services)                   | Item, quantity, description, price, storekeeper, remarks                                  | No assignment tracking.                                                      |
| **2** | **Purchases/Assignment Book**    | Tracks asset assignment to users                                             | Date, item description, office, receiving party, issuing party                            | No link back to GRV or asset register                                        |
| **3** | **Asset Register**               | Tracks asset assignment per office                                           | User, office, item, brand, specs, serial number                                           | Manual entries; no historical trail of transfers                             |
| **4** | **Asset Record (from GRV)**      | Master list of assets by type (projector, computer, printer, network device) | Asset code, asset description, serial number, asset type, purchase date                   | Contains no assignment information; disconnected from who has what           |
| **5** | **Projector Loan Log**           | Tracks daily loans of projectors to lecturers                                | Date, item description (asset code + accessories), name, sign in/out signatures           | Separate from main asset register; no link to asset status                   |
| **6** | **GIS Lab Asset List**           | Tracks equipment in GIS lab (keyboards, desktops, monitors)                  | Serial number, brand                                                                      | Minimal fields; no assignment or location tracking beyond the lab            |
| **7** | **Consumables Log (Toners)**     | Tracks toner cartridge distribution                                          | Item description, printer type, printer S/N, quantity, department, estimated amount, date | Quantity-based; difficult to track low stock or reconcile with printer usage |


### 3.2 Document Details

#### 3.2.1 Goods Received Voucher (GRV)
- **Purpose:** Entry point for ALL items entering IT Department.
- **Sample Data:**

| Item               | Qty | Description       | Price     | Storekeeper | Remarks           |
| ------------------ | --- | ----------------- | --------- | ----------- | ----------------- |
| Dell Latitude 5420 | 5   | Core i5, 16GB RAM | $750 each | Sithole     | In good condition |
| HP CF277X Toner    | 10  | For LaserJet M404 | $85 each  | T. Sibanda  | In storage        |
| Projector Repair   | 1   | Epson EB-1795F    | $120      | Sithole     | Completed         |

- **Pain Points:** No easy way to track what happened to these items after receipt. Cannot easily see which assets from this GRV are still in storage vs. assigned.

#### 3.2.2 Asset Record (Master Asset List)
- **Purpose:** Master list of assets by type.
- **Sample Data:**

| Asset Code  | Asset Description        | Serial Number | Asset Type | Purchase Date |
| ----------- | ------------------------ | ------------- | ---------- | ------------- |
| CSC-PRJ-001 | Epson EB-1795F Projector | EB1795F123    | Projector  | 2024-03-15    |
| CSC-DSK-001 | Dell Optiplex 7060       | OP7060ABC     | Computer   | 2024-03-15    |
| CSC-PRT-001 | HP LaserJet M404dn       | M404XYZ       | Printer    | 2024-05-05    |
| CSC-NWK-001 | Cisco Catalyst 2960-X    | CISCO2960     | Network    | 2024-09-12    |

- **Pain Points:** No assignment information. To find who has CSC-DSK-001, you must manually cross-reference with the Asset Register or Assignment Book.

#### 3.2.3 Asset Register (By Office)
- **Purpose:** Tracks which assets are assigned to which offices/users.
- **Sample Data:**

| User       | Office       | Item    | Brand | Specs               | Serial Number |
| ---------- | ------------ | ------- | ----- | ------------------- | ------------- |
| John Doe   | IT Office 1  | Desktop | Dell  | i5, 8GB, 256GB SSD  | OP7060ABC     |
| Mary Smith | Admin Office | Laptop  | Dell  | i5, 16GB, 512GB SSD | LAT5420ABC    |

- **Pain Points:** Manual entry; no historical record of previous assignments. If John transfers to another office, the previous record is likely overwritten or lost.

#### 3.2.4 Projector Loan Log
- **Purpose:** Tracks daily loans of projectors to lecturers.
- **Sample Data:**

| Date       | Item Description                         | Name (Lecturer) | Sign Out    | Sign In     |
| ---------- | ---------------------------------------- | --------------- | ----------- | ----------- |
| 2025-04-30 | CSC-PRJ-001 + power cable, adapter, hdmi | Dr. J. Moyo     | [Signature] | [Signature] |

- **Pain Points:** Separate from main asset register. No easy way to see asset status (e.g., currently on loan) without manually checking this log.

#### 3.2.5 GIS Lab Asset List
- **Purpose:** Tracks equipment in the GIS laboratory.
- **Sample Data:**

| Serial Number | Brand    |
| ------------- | -------- |
| KB12345       | Dell     |
| MS67890       | Logitech |
| MON001        | HP       |

- **Pain Points:** Minimal fields (no asset codes, no assignment tracking). Only indicates equipment exists in the lab, not who is responsible or the condition of items.

#### 3.2.6 Consumables Log (Toners)
- **Purpose:** Tracks toner cartridge distribution to departments.
- **Sample Data:**

| Item Description | Printer Type  | Printer S/N | Quantity | Department | Estimated Amount | Date       |
| ---------------- | ------------- | ----------- | -------- | ---------- | ---------------- | ---------- |
| HP CF277X Toner  | LaserJet M404 | M404XYZ     | 3        | Finance    | $255             | 2025-04-20 |

- **Pain Points:** Quantity tracking only; no low-stock alerts. Difficult to reconcile total cartridges received (from GRV) vs. distributed (from this log). Manual calculation of remaining stock.

#### 3.2.7 Purchases/Assignment Book
- **Purpose:** Records the transaction of assigning an asset.
- **Sample Data:**

| Date       | Item Description          | Office    | Receiving Party | Issuing Party |
| ---------- | ------------------------- | --------- | --------------- | ------------- |
| 2025-04-10 | CSC-DSK-001 Dell Optiplex | IT Office | John Doe        | T. Sibanda    |

- **Pain Points:** Disconnected from the Asset Register. No way to see full history of an asset without flipping through pages. No status tracking (e.g., returned, transferred).

### 3.3 Identified Gaps & Inefficiencies

| Gap                           | Description                                                            | Impact                                                       |
| :---------------------------- | :--------------------------------------------------------------------- | :----------------------------------------------------------- |
| **No Single Source of Truth** | Asset information is scattered across 7+ documents                     | Time wasted reconciling records; conflicting information     |
| **No Asset Lifecycle View**   | No document links GRV → Assignment → Maintenance → Disposal            | Cannot answer: "What is the full history of this asset?"     |
| **Manual Data Redundancy**    | Same asset information entered in multiple books                       | Increased risk of data entry errors; wastes time             |
| **No Historical Tracking**    | Assignment history is overwritten when an asset is reassigned          | Cannot audit past asset custody                              |
| **No Real-Time Visibility**   | Asset status (on loan, in repair, in storage) is not centrally tracked | Lost assets not flagged                                      |
| **Inventory Blind Spots**     | Consumables (toners) tracked manually; no low-stock alerts             | Risk of stock-outs; reactive instead of proactive management |
| **Audit Difficulty**          | Full inventory requires manual review of multiple documents            | Audit preparation takes days/weeks instead of minutes        |
| **No Accountability**         | Missing signatures or incomplete records weaken accountability         | Asset loss is hard to trace and assign responsibility        |


### 3.5 Summary of Findings

The IT Department operates with a **paper-based asset management ecosystem**. While each document serves a specific purpose, they collectively create a system that is **inefficient, error-prone, and largely unscalable**. The lack of integration between the GRV, Asset Record, Asset Register, and various logs means that **no single person or system can answer basic questions** like:
- *"How many laptops do we own, and where are they?"*
- *"Who has been responsible for Projector CSC-PRJ-001 over the last two years?"*
- *"How many toner cartridges are currently in stock?"*
- *"What is the complete maintenance history of CSC-PRT-001?"*

As MUAST continues to grow, this fragmentation will transition from an inconvenience to an audit risk. A digital, unified Asset Management System is required to eliminate these silos and provide a single, reliable source of truth.

## 4. Proposed Solution

We propose the implementation of a **digital, web-based IT Asset Management System**. The system will serve as the **Single Source of Truth (SSOT)** for all IT assets, streamlining operations and providing end-to-end lifecycle management.

The system will digitize the current workflow, creating a unified platform to:
- **Register** all IT assets directly upon receipt (from the GRV).
- **Track** asset assignment, from permanent allocation to temporary loans.
- **Manage** inventory and consumables, like toners, with quantity tracking.
- **Monitor** the lifecycle, linking maintenance and service records directly to the asset.

This is not about replacing processes but about enhancing them to be more efficient, accurate, and auditable.

## 5. Core Features & Scope (Phased Approach)

The project is broken into the following phases:

### Phase 1: MVP

- **Asset Registration:** A database to uniquely identify and describe every asset.
- **Asset Categorization:** Organize assets by category (e.g., Computer) and type (e.g., Desktop, Laptop).
- **Location Management:** Hierarchical tracking by Campus (CSC, MAR, AIP) and Office.
- **User & Role Management:** Secure login with university credentials and role-based access (Admin, IT Staff).
- **Assignment Tracking:** Ability to assign assets to a user or a location (deploy).
- **Search & Listing:** Basic search by asset code, serial number, or user.

### Phase 2: Lifecycle & Inventory

- **GRV Digitization:** A module to record incoming goods, which will then auto-create asset records.
- **Inventory Management:** Track consumables (e.g., toner cartridges) by quantity, manage low-stock alerts, and record transactions to departments.
- **Maintenance Logs:** Record and track repair and service history, linking services to specific assets.
- **Asset History:** A complete, auditable timeline of an asset's assignment and maintenance events.

### Phase 3: Advanced Tracking & Reporting

- **Temporary Loans Module:** A dedicated system to manage the daily checkout of assets like projectors, with digital signatures and accessories tracking.
- **Reporting & Analytics Dashboards:** Provide operational insights into asset distribution, status, and maintenance schedules.
- **Proactive Alerts:** Automated notifications for low stock, upcoming warranty expirations, or overdue loan returns.

## 6. Users & Core Roles

The system will cater to three core user profiles, each with specific access levels.

| Role         | Responsibility                                                                              | Example Actions                                                            |
| :----------- | :------------------------------------------------------------------------------------------ | :------------------------------------------------------------------------- |
| **IT Admin** | Full system control, user management, system configuration.                                 | Manage users, assign roles, configure asset catalogs.                      |
| **IT Staff** | Day-to-day operation: entering GRVs, creating assets, assignments, and logging maintenance. | Record new assets, assign to users, log a repair, return asset to storage. |
| **Viewer**   | Read-only access to asset information, primarily for reporting and audit visibility.        | Generate an asset by location report, view inventory status.               |


## 7. Authorization Model

The system will implement a **Role-Based Access Control (RBAC)** model to ensure security and clarity.

### Core Permissions (Examples)

| Permission Name | Description                                                   |
| :-------------- | :------------------------------------------------------------ |
| `ASSET_CREATE`  | Ability to add new asset records.                             |
| `ASSET_VIEW`    | Ability to view asset details and listings.                   |
| `ASSET_ASSIGN`  | Ability to assign/transfer asset ownership or location.       |
| `ASSET_UPDATE`  | Ability to edit asset properties and details.                 |
| `ASSET_DISPOSE` | Ability to change asset status to 'Decommissioned' or 'Lost'. |
| `USER_MANAGE`   | Ability to create, edit, and deactivate user accounts.        |

### Role-to-Permission Mapping

| Role         | Key Permissions                                                 |
| :----------- | :-------------------------------------------------------------- |
| **ADMIN**    | All permissions (ASSET_*, LOCATION_*, USER_MANAGE, etc.)        |
| **IT_STAFF** | ASSET_CREATE, ASSET_VIEW, ASSET_ASSIGN, ASSET_UPDATE, MAINT_LOG |
| **VIEWER**   | ASSET_VIEW, REPORT_GENERATE                                     |


## 8. System Overview (High-Level Design)

### 8.1 Architecture

The system will be a modern, three-tier web application accessible from any standard web browser on the MUAST network or VPN.

**Technology Stack Summary:**

| Tier               | Technology            | Purpose                                                   |
| :----------------- | :-------------------- | :-------------------------------------------------------- |
| **Frontend**       | Flutter (Web)         | Cross-platform UI framework for consistent web experience |
| **Backend**        | Spring Boot (Java)    | REST API, business logic, security, and audit             |
| **Database**       | MySQL                 | Relational data persistence with ACID compliance          |
| **Authentication** | Spring Security + JWT | Secure user authentication and session management         |
### 8.2 Sitemap

The application will be organized into the following logical modules and pages.

```
MUAST Asset Management System
│
├── Public (No Auth Required)
│   ├── Home (Landing Page)
│   └── Login
│
├── Authenticated (Auth Required)
│   │
│   ├── Dashboard (Home after login)
│   │
│   ├── GRV Module
│   │   ├── GRV List (/grv)
│   │   ├── Create GRV Entry (/grv/create)
│   │   ├── View GRV Entry (/grv/:id)
│   │   └── Edit GRV Entry (/grv/edit/:id)
│   │
│   ├── Asset Management
│   │   ├── Assets List (/assets)
│   │   ├── Create Asset (/assets/create)
│   │   ├── Asset Detail (/assets/:id)
│   │   ├── Edit Asset (/assets/edit/:id)
│   │   └── Export (/assets/export)
│   │
│   ├── Inventory Management (Toners, Consumables)
│   │   ├── Inventory List (/inventory)
│   │   ├── Inventory Item Detail (/inventory/:id)
│   │   ├── Send to Department (/inventory/:id/send)
│   │   ├── Transaction History (/inventory/:id/transactions)
│   │   └── Low Stock Alerts (/inventory/alerts)
│   │
│   ├── Temporary Loans (Projectors)
│   │   ├── Active Loans (/loans/active)
│   │   ├── Loan History (/loans/history)
│   │   ├── Create Loan (/loans/create)
│   │   ├── Loan Detail (/loans/:id)
│   │   └── Return Asset (/loans/:id/return)
│   │
│   ├── Services (Repairs & Maintenance)
│   │   ├── Services List (/services)
│   │   ├── Create Service (/services/create)
│   │   ├── Service Detail (/services/:id)
│   │   └── Link to Asset (/services/:id/link-asset)
│   │
│   ├── Location Management
│   │   ├── Campuses List (/campuses)
│   │   ├── Create Campus (/campuses/create)
│   │   ├── Edit Campus (/campuses/edit/:id)
│   │   ├── Offices by Campus (/campuses/:id/offices)
│   │   ├── Create Office (/offices/create)
│   │   └── Edit Office (/offices/edit/:id)
│   │
│   ├── Asset Catalog
│   │   ├── Categories List (/categories)
│   │   ├── Create Category (/categories/create)
│   │   ├── Edit Category (/categories/edit/:id)
│   │   ├── Asset Types by Category (/categories/:id/asset-types)
│   │   ├── Create Asset Type (/asset-types/create)
│   │   ├── Edit Asset Type (/asset-types/edit/:id)
│   │   ├── Models List (/models)
│   │   ├── Create Model (/models/create)
│   │   └── Edit Model (/models/edit/:id)
│   │
│   ├── User Management (Admin only)
│   │   ├── Users List (/users)
│   │   ├── Create User (/users/create)
│   │   ├── Edit User (/users/edit/:id)
│   │   └── Force Password Change (/users/force-password-change)
│   │
│   ├── Role Management (Admin only)
│   │   ├── Roles List (/roles)
│   │   ├── Create Role (/roles/create)
│   │   └── Edit Role (/roles/edit/:id)
│   │
│   └── Reports
│       ├── Assets by Location (/reports/assets-by-location)
│       ├── Assets by User (/reports/assets-by-user)
│       ├── Assets by Status (/reports/assets-by-status)
│       ├── Inventory Report (/reports/inventory)
│       ├── Loan History Report (/reports/loans)
│       ├── Service History Report (/reports/services)
│       ├── GRV Summary (/reports/grv-summary)
│       └── Full Inventory Export (/reports/full-export)
│
└── Actions (No dedicated pages)
    ├── Logout
    ├── Change Password
    └── Profile
```

### 8.3 Routes (Flutter / Frontend)

```dart
// lib/routes/app_routes.dart

class AppRoutes {
  // Public routes
  static const String home = '/';
  static const String login = '/login';

  // Authenticated routes
  static const String dashboard = '/dashboard';

  // GRV Module
  static const String grvList = '/grv';
  static const String grvCreate = '/grv/create';
  static const String grvDetail = '/grv/:id';
  static const String grvEdit = '/grv/edit/:id';

  // Asset Management
  static const String assetsList = '/assets';
  static const String assetCreate = '/assets/create';
  static const String assetDetail = '/assets/:id';
  static const String assetEdit = '/assets/edit/:id';
  static const String assetBulkImport = '/assets/bulk-import';
  static const String assetExport = '/assets/export';

  // Inventory Management
  static const String inventoryList = '/inventory';
  static const String inventoryDetail = '/inventory/:id';
  static const String inventorySend = '/inventory/:id/send';
  static const String inventoryTransactions = '/inventory/:id/transactions';
  static const String inventoryAlerts = '/inventory/alerts';

  // Temporary Loans
  static const String loansActive = '/loans/active';
  static const String loansHistory = '/loans/history';
  static const String loanCreate = '/loans/create';
  static const String loanDetail = '/loans/:id';
  static const String loanReturn = '/loans/:id/return';

  // Services
  static const String servicesList = '/services';
  static const String serviceCreate = '/services/create';
  static const String serviceDetail = '/services/:id';
  static const String serviceLinkAsset = '/services/:id/link-asset';

  // Location Management
  static const String campusesList = '/campuses';
  static const String campusCreate = '/campuses/create';
  static const String campusEdit = '/campuses/edit/:id';
  static const String officesByCampus = '/campuses/:id/offices';
  static const String officeCreate = '/offices/create';
  static const String officeEdit = '/offices/edit/:id';

  // Asset Catalog
  static const String categoriesList = '/categories';
  static const String categoryCreate = '/categories/create';
  static const String categoryEdit = '/categories/edit/:id';
  static const String assetTypesByCategory = '/categories/:id/asset-types';
  static const String assetTypeCreate = '/asset-types/create';
  static const String assetTypeEdit = '/asset-types/edit/:id';
  static const String modelsList = '/models';
  static const String modelCreate = '/models/create';
  static const String modelEdit = '/models/edit/:id';

  // User Management
  static const String usersList = '/users';
  static const String userCreate = '/users/create';
  static const String userEdit = '/users/edit/:id';
  static const String forcePasswordChange = '/users/force-password-change';

  // Role Management
  static const String rolesList = '/roles';
  static const String roleCreate = '/roles/create';
  static const String roleEdit = '/roles/edit/:id';

  // Reports
  static const String reportAssetsByLocation = '/reports/assets-by-location';
  static const String reportAssetsByUser = '/reports/assets-by-user';
  static const String reportAssetsByStatus = '/reports/assets-by-status';
  static const String reportInventory = '/reports/inventory';
  static const String reportLoans = '/reports/loans';
  static const String reportServices = '/reports/services';
  static const String reportGrvSummary = '/reports/grv-summary';
  static const String reportFullExport = '/reports/full-export';
}
```

---

### 8.4 Database Relationship Diagram & Schema

```sql
-- =============================================
-- MUAST ASSET MANAGEMENT SYSTEM - DATABASE SCHEMA
-- =============================================

-- =============================================
-- 1. AUTHENTICATION & USER MANAGEMENT
-- =============================================

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    must_change_password BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    description TEXT,
    module VARCHAR(50)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- =============================================
-- 2. LOCATION MANAGEMENT
-- =============================================

CREATE TABLE campuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) UNIQUE NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    address TEXT
);

CREATE TABLE offices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    campus_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    UNIQUE KEY uk_campus_office (campus_id, name),
    FOREIGN KEY (campus_id) REFERENCES campuses(id) ON DELETE CASCADE
);

-- =============================================
-- 3. ASSET CATALOG
-- =============================================

CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE asset_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    description TEXT,
    track_individual BOOLEAN DEFAULT TRUE,
    track_quantity BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_category_asset_type (category_id, name),
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- =============================================
-- 4. GRV MODULE (Goods Received)
-- =============================================

CREATE TABLE grv_entries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(200) NOT NULL,
    quantity_received INT NOT NULL,
    description TEXT,
    unit_price DECIMAL(10, 2),
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity_received * unit_price) STORED,
    storekeeper_remarks TEXT,
    storekeeper_id BIGINT,
    received_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (storekeeper_id) REFERENCES users(id)
);

CREATE TABLE grv_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grv_entry_id BIGINT NOT NULL,
    asset_type_id BIGINT,
    brand VARCHAR(50),
    serial_number VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grv_entry_id) REFERENCES grv_entries(id) ON DELETE CASCADE,
    FOREIGN KEY (asset_type_id) REFERENCES asset_types(id)
);

-- =============================================
-- 5. ASSETS TABLE (Individual Tracked Assets)
-- =============================================

CREATE TABLE assets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_code VARCHAR(50) UNIQUE NOT NULL,
    grv_item_id BIGINT,
    asset_type_id BIGINT NOT NULL,
    brand VARCHAR(50),
    serial_number VARCHAR(100) UNIQUE,
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    purchase_date DATE,
    purchase_cost DECIMAL(10, 2),
    replacement_threshold DECIMAL(10, 2),
    total_consumable_cost DECIMAL(10, 2) DEFAULT 0.00,
    specs JSON,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (grv_item_id) REFERENCES grv_items(id),
    FOREIGN KEY (asset_type_id) REFERENCES asset_types(id)
);

CREATE TABLE asset_locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    campus_id BIGINT NOT NULL,
    office_id BIGINT NOT NULL,
    is_current BOOLEAN DEFAULT TRUE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_asset_current_location (asset_id, is_current),
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    FOREIGN KEY (campus_id) REFERENCES campuses(id),
    FOREIGN KEY (office_id) REFERENCES offices(id)
);

CREATE TABLE asset_assignments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    user_id BIGINT,
    role_at_assignment VARCHAR(100),
    is_current BOOLEAN DEFAULT TRUE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP NULL,
    notes TEXT,
    UNIQUE KEY uk_asset_current_assignment (asset_id, is_current),
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- 6. INVENTORY MODULE (Quantity-tracked items)
-- =============================================

CREATE TABLE inventory_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_type_id BIGINT NOT NULL,
    brand VARCHAR(50),
    name VARCHAR(200) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    location_id BIGINT,
    linked_asset_id BIGINT
    specs JSON,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_type_id) REFERENCES asset_types(id),
    FOREIGN KEY (location_id) REFERENCES offices(id),
    FOREIGN KEY (linked_asset_id) REFERENCES assets(id),
    UNIQUE KEY uk_product_location (asset_type_id, brand, location_id)
);

CREATE TABLE inventory_grv_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inventory_item_id BIGINT NOT NULL,
    grv_item_id BIGINT NOT NULL,
    quantity_contributed INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_item_id) REFERENCES inventory_items(id) ON DELETE CASCADE,
    FOREIGN KEY (grv_item_id) REFERENCES grv_items(id) ON DELETE CASCADE
);

CREATE TABLE inventory_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inventory_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    from_location_id BIGINT,
    to_location_id BIGINT,
    transaction_date DATE NOT NULL,
    issued_by_id BIGINT,
    received_by_id BIGINT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_item_id) REFERENCES inventory_items(id) ON DELETE CASCADE,
    FOREIGN KEY (from_location_id) REFERENCES offices(id),
    FOREIGN KEY (to_location_id) REFERENCES offices(id),
    FOREIGN KEY (issued_by_id) REFERENCES users(id),
    FOREIGN KEY (received_by_id) REFERENCES users(id)
);

-- =============================================
-- 7. TEMPORARY LOANS MODULE
-- =============================================

CREATE TABLE temporary_loans (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    loaned_to_id BIGINT NOT NULL,
    loaned_by_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    expected_return_date DATE,
    actual_return_date DATE,
    accessories TEXT,
    sign_out_signature TEXT,
    sign_in_signature TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_id) REFERENCES assets(id),
    FOREIGN KEY (loaned_to_id) REFERENCES users(id),
    FOREIGN KEY (loaned_by_id) REFERENCES users(id)
);

-- =============================================
-- 8. SERVICES MODULE (Repairs & Maintenance)
-- =============================================

CREATE TABLE service_entries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grv_item_id BIGINT,
    asset_id BIGINT,
    service_type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    vendor VARCHAR(100),
    cost DECIMAL(10, 2),
    date_performed DATE NOT NULL,
    performed_by_id BIGINT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grv_item_id) REFERENCES grv_items(id),
    FOREIGN KEY (asset_id) REFERENCES assets(id),
    FOREIGN KEY (performed_by_id) REFERENCES users(id)
);

-- =============================================
-- 9. AUDIT TRAIL
-- =============================================

CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT NOT NULL,
    old_values JSON,
    new_values JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- INDEXES FOR PERFORMANCE
-- =============================================

CREATE INDEX idx_assets_asset_code ON assets(asset_code);
CREATE INDEX idx_assets_serial_number ON assets(serial_number);
CREATE INDEX idx_assets_status ON assets(status);
CREATE INDEX idx_assets_brand ON assets(brand);
CREATE INDEX idx_asset_locations_current ON asset_locations(asset_id, is_current);
CREATE INDEX idx_asset_assignments_current ON asset_assignments(asset_id, is_current);
CREATE INDEX idx_inventory_items_quantity ON inventory_items(quantity);
CREATE INDEX idx_inventory_items_brand ON inventory_items(brand);
CREATE INDEX idx_temporary_loans_active ON temporary_loans(asset_id, actual_return_date);
CREATE INDEX idx_service_entries_asset ON service_entries(asset_id);
CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_logs_created ON audit_logs(created_at);
```

### 8.6 Entity Descriptions

| Entity                    | Description                                              | Key Fields                                             | Tracks                                 |
| :------------------------ | :------------------------------------------------------- | :----------------------------------------------------- | :------------------------------------- |
| **GRV Entry**             | Records incoming goods or services                       | item name, quantity, price, storekeeper, received date | What came in, when, and from whom      |
| **GRV Item**              | Line items within a GRV entry                            | asset type, model, serial number, status               | Individual items within a bulk receipt |
| **Asset**                 | Individually tracked hardware                            | asset code, serial number, status, specs               | Lifecycle of a single physical asset   |
| **Inventory Item**        | Quantity-tracked consumables                             | name, quantity, min stock level, location              | Stock levels of toners, cables, etc.   |
| **Inventory Transaction** | Movement of consumables                                  | quantity, type (SENT/RECEIVED), from/to location       | Who requested/issued consumables       |
| **Assignment**            | Asset assigned to a user                                 | user, assigned date, returned date                     | Who has/had an asset                   |
| **Asset Location**        | Physical location of an asset                            | campus, office, assigned date                          | Where an asset is located              |
| **Temporary Loan**        | Short-term loan (projectors)                             | loaned to, dates, accessories, signatures              | Daily checkouts                        |
| **Service Entry**         | Repair/maintenance record                                | service type, vendor, cost, date performed             | Asset service history                  |
| **Asset Component**       | Linking components (monitor/keyboard/mouse) to a desktop | parent asset, component asset, component type          | Workstation composition                |

## 9. Implementation Approach

The project will be delivered using an **iterative, phased approach** to ensure early value and continuous feedback.

1.  **Planning & Analysis (2 weeks):**
    - Finalize technical specifications and environments.
2.  **Phase 1 Development (6 weeks):**
    - Build and test the core asset tracking features (MVP).
3.  **Phase 1 Deployment (1 week):**
    - Deploy the MVP for the IT department as a pilot. Begin initial data entry.
4.  **Phase 2 Development (4 weeks):**
    - Develop and integrate GRV, Inventory, and Lifecycle features.
5.  **Phase 2 Rollout (1 week):**
    - Full rollout to all relevant departments, with training.
6.  **Phase 3 & Ongoing (TBD):**
    - Development of advanced reporting and integrations based on initial feedback.

## 10.  Testing & Validation Plan

- **Unit & Integration Testing:** Continuous testing by the development team during each sprint.
- **User Acceptance Testing (UAT):** IT staff will test the MVP and subsequent phases in a staging environment before general release.
- **Pilot Rollout:** Phase 1 will be a live pilot for the IT department to validate the workflow and data model.
- **Feedback Loop:** A structured feedback process will be used to guide future development phases.

## 11. Expected Benefits

| Benefit Category | Description |
| :--- | :--- |
| **Operational Efficiency** | Eliminate manual logbooks, reduce time spent searching for asset information and preparing reports. |
| **Asset Visibility & Control** | Gain real-time insight into asset location, status, and assignment, reducing the risk of loss. |
| **Accountability** | Clear, auditable history of who has/had an asset, improving responsibility. |
| **Data-Driven Decisions** | Generate reports on asset utilization, maintenance costs, and inventory needs to inform purchasing decisions. |
| **Audit Compliance** | Simplify internal and external audits with comprehensive, on-demand reporting and a clear audit trail. |
| **Reduced Redundancy** | Avoid over-purchasing consumables or hardware through accurate inventory tracking. |

## 12. Risks & Mitigation

| Risk                                          | Probability | Impact | Mitigation Strategy                                                                                                            |
| :-------------------------------------------- | :---------- | :----- | :----------------------------------------------------------------------------------------------------------------------------- |
| **Data Migration Issues (from spreadsheets)** | Medium      | High   | Phase 1 will prioritize a clean data entry process for core assets, rather than a potentially unreliable automated migration.  |
| **Resistance to New System**                  | Medium      | Medium | Provide targeted training, involve IT staff in UAT, and implement a phased rollout to build confidence and gather advocates.   |
| **Scope Creep**                               | High        | Medium | Adhere to the defined phased approach. Non-critical features will be pushed to a later phase, not added to the current sprint. |

## 13. Future Considerations

While not part of the immediate proposal, the system is designed to be extensible.

- **Integration with Procurement:** Automatically create assets from purchase orders.
- **Mobile Scanning (QR/Barcode):** For quick asset lookup, inventory audits, and receipt of goods.
- **Integration with University ID System:** Automatically pull user information for assignment.

## 14. Conclusion & Recommendation

The current manual asset management process cannot sustain MUAST's growth trajectory. It presents a clear risk to financial accountability and operational efficiency.

The proposed digital IT Asset Management System provides a practical, phased solution to this problem. By modernizing our asset tracking, we will gain **real-time visibility**, **improve accountability**, and **ensure audit readiness**. The approach focuses on delivering immediate value with the core MVP, followed by advanced features in subsequent phases.

**Approval is recommended to proceed with Phase 1 development of the IT Asset Management System.**
