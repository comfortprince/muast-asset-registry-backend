```markdown
**(MUAST – IT Department)**

**Date:** June 14, 2026
**Prepared By:** [Your Name/Title]
**Version:** 0.2.0 (MVP)

## 1. Executive Summary

### Current Situation
MUAST currently manages its IT assets across three campuses (CSC, MAR, AIP) using a combination of manual logbooks and spreadsheets. Key documents like assignment ledgers, loan registers, and asset records are maintained on paper.

### Key Problem
This manual approach presents significant operational and audit risks. The lack of a centralized system leads to **poor asset visibility**, **weak accountability**, and **difficulty in audit compliance**. There is no single, reliable source of truth for the location, status, or history of an institutional asset.

### Proposed Solution
This document describes the **MUAST Asset Register** — a digital, web-based system for tracking IT assets across all campuses.

### Expected Benefits
- **Improved Asset Visibility:** Real-time view of all assets, their location, and status.
- **Stronger Accountability:** Clear tracking of asset assignment to individuals or departments.
- **Reduced Asset Loss:** Digital record-keeping reduces the risk of undetected loss.
- **Audit Readiness:** Complete history trail for every asset with status, location, and assignment timelines.

## 2. Problem Statement

The IT Department faces the following challenges with the current asset management process:

- **Lack of Visibility:** There is no centralized system to see the total inventory, where assets are located, or who is responsible for them.
- **Weak Accountability:** The assignment of assets is tracked manually, making it difficult to resolve accountability issues when assets are lost or damaged.
- **Inefficient Tracking:** The lifecycle of assets (assignment, loan, repair, decommission) is tracked inconsistently across multiple documents.
- **Audit Risk:** Generating a complete inventory report is a laborious and error-prone manual process.

## 3. Current State Analysis

The IT Department manages assets using **disparate manual documents and logbooks**, each operating in isolation.

### 3.1 Inventory of Existing Documents

| # | Document Name | Purpose | Limitation |
|---|--------------|---------|------------|
| 1 | **Asset Register** | Tracks asset assignment per office | Manual entries; no historical trail of transfers |
| 2 | **Asset Record** | Master list of assets by type | Contains no assignment information |
| 3 | **Purchases/Assignment Book** | Tracks asset assignment to users | No link back to asset register |
| 4 | **Projector Loan Log** | Tracks daily loans of projectors | Separate from main asset register |
| 5 | **GIS Lab Asset List** | Tracks equipment in GIS lab | Minimal fields; no assignment or location tracking |
| 6 | **Consumables Log (Toners)** | Tracks toner cartridge distribution | Quantity-based; difficult to reconcile |

### 3.2 Identified Gaps

| Gap | Impact |
|-----|--------|
| **No Single Source of Truth** | Time wasted reconciling records across 6+ documents |
| **No Asset Lifecycle View** | Cannot answer: "What is the full history of this asset?" |
| **No Historical Tracking** | Assignment history is overwritten when an asset is reassigned |
| **No Real-Time Visibility** | Asset status (on loan, in repair, in storage) is not centrally tracked |
| **Audit Difficulty** | Full inventory requires manual review of multiple documents |

## 4. Proposed Solution

The **MUAST Asset Register** serves as the **Single Source of Truth (SSOT)** for all IT assets. It digitizes the current workflow, creating a unified platform to:

- **Register** all IT assets with unique asset codes.
- **Track** asset assignment, from permanent allocation to temporary loans.
- **Monitor** the lifecycle with complete history of status, location, and assignment changes.
- **Audit** every action through a comprehensive audit trail.

## 5. Core Features (MVP)

### Phase 1: MVP (v0.2.0)

- **Asset Registration:** Unique asset codes, categorization by type, brand, serial number.
- **Location Management:** Hierarchical tracking by Campus (CSC, MAR, AIP) and Office.
- **User & Role Management:** Secure login with role-based access control (Admin, IT Staff, Viewer).
- **Assignment Tracking:** Assign assets to users with role context (e.g., "Lecturer").
- **Status Lifecycle:** Track assets through AVAILABLE → ASSIGNED → ON_LOAN → IN_REPAIR → LOST → DECOMMISSIONED.
- **Temporary Loans:** Dedicated loan tracking with signatures, accessories, and due dates.
- **Complete History:** Temporal tables for status, location, and assignment — every change is preserved.
- **Audit Trail:** Every action logged with old/new values, user, and timestamp.
- **Search & Filtering:** Find assets by code, serial number, brand, status, or category.

### Future Phases

| Phase | Features |
|-------|----------|
| Phase 2 | GRV digitization, consumables/inventory tracking, maintenance logs |
| Phase 3 | Advanced reporting, QR/barcode scanning, procurement integration |

## 6. Users & Core Roles

| Role | Responsibility | Example Actions |
|------|---------------|-----------------|
| **Admin** | Full system control, user management | Manage users, assign roles, configure categories |
| **IT Staff** | Day-to-day asset operations | Create assets, assign to users, record loans, check-in |
| **Viewer** | Read-only access | View asset listings, run reports |

## 7. Authorization Model

The system implements **Role-Based Access Control (RBAC)** with permissions defined as code-level enums.

### Core Permissions

| Permission | Description |
|------------|-------------|
| `CREATE_USERS` | Create new user accounts |
| `EDIT_USERS` | Update and disable users |
| `READ_USERS` | View user information |
| `MANAGE_ROLES` | Create and update roles |
| `READ_ROLES` | View roles and permissions |
| `MANAGE_ASSETS` | Create, update, assign, transfer, decommission assets |
| `READ_ASSETS` | View assets |
| `MANAGE_LOCATIONS` | Manage campuses and offices |
| `READ_LOCATIONS` | View locations |
| `MANAGE_ASSET_CATALOG` | Manage asset categories |
| `READ_ASSET_CATALOG` | View categories |
| `VIEW_REPORTS` | View reports |
| `VIEW_AUDIT_LOGS` | View audit trail |

### Role-to-Permission Mapping

| Role | Permissions |
|------|------------|
| **Admin** | All permissions |
| **IT Staff** | READ_USERS, MANAGE_ASSETS, READ_ASSETS, READ_LOCATIONS, READ_ASSET_CATALOG, VIEW_REPORTS, EXPORT_REPORTS |
| **Viewer** | READ_USERS, READ_ASSETS, READ_LOCATIONS, READ_ASSET_CATALOG, VIEW_REPORTS |

## 8. System Overview

### 8.1 Architecture

| Tier | Technology | Purpose |
|------|-----------|---------|
| **Frontend** | Flutter (Web) | Cross-platform UI |
| **Backend** | Spring Boot (Java) | REST API, business logic, security, audit |
| **Database** | MySQL | Relational data with ACID compliance |
| **Authentication** | Spring Security + JWT | Secure login and session management |

### 8.2 Sitemap

```
MUAST Asset Register
│
├── Public
│   ├── Home (Landing Page)
│   └── Login
│
├── Authenticated
│   ├── Dashboard
│   ├── Asset Management
│   │   ├── Assets List
│   │   ├── Asset Detail
│   │   └── Asset Actions (Assign, Transfer, Check-in, Repair, Loan, Lost, Decommission)
│   ├── Location Management
│   │   ├── Campuses
│   │   └── Offices
│   ├── Asset Categories
│   ├── User Management (Admin)
│   ├── Role Management (Admin)
│   ├── Temporary Loans
│   └── Reports
```

### 8.3 Database Schema

```sql
-- 13 tables total

users                  -- Authentication and user profiles
roles                  -- Role definitions
user_roles             -- User-to-role mapping
role_permissions       -- Role-to-permission mapping (stores Permission enum names)

campuses               -- CSC, MAR, AIP
offices                -- Offices within campuses

asset_categories       -- Laptops, Desktops, Projectors, Printers, etc.

assets                 -- Core asset records with current_status
asset_status_history   -- Temporal history of status changes
asset_location_history -- Temporal history of location changes
asset_assignment_history -- Temporal history of assignments

temporary_loans        -- Loan-specific metadata (signatures, accessories, due dates)
audit_logs             -- System-wide audit trail
```

### 8.4 Asset Lifecycle

```
                    ┌─────────────┐
                    │  AVAILABLE  │ ← After checkin / new asset
                    └──────┬──────┘
                           │ assign
                    ┌──────▼──────┐
                    │  ASSIGNED   │
                    └──────┬──────┘
                           │ loan
                    ┌──────▼──────┐
                    │  ON_LOAN    │
                    └──────┬──────┘
                           │ return
                    ┌──────▼──────┐
                    │  ASSIGNED   │ (back)
                    └──────┬──────┘
                           │ repair / lost / decommission
              ┌────────────┼────────────┐
       ┌──────▼──────┐ ┌───▼───┐ ┌──────▼──────────┐
       │  IN_REPAIR  │ │ LOST  │ │ DECOMMISSIONED  │
       └─────────────┘ └───────┘ └─────────────────┘
```

## 9. Implementation Approach

1. **Planning & Analysis (2 weeks):** Finalize specifications and environment setup.
2. **MVP Development (6 weeks):** Build core asset tracking with RBAC and audit logging.
3. **MVP Deployment (1 week):** Deploy for IT department pilot with initial data entry.
4. **Future Phases (TBD):** GRV, inventory, maintenance logs, reporting.

## 10. Expected Benefits

| Benefit | Description |
|---------|-------------|
| **Operational Efficiency** | Eliminate manual logbooks; instant search and reporting |
| **Asset Visibility** | Real-time insight into location, status, and assignment |
| **Accountability** | Complete auditable history of every asset |
| **Audit Compliance** | On-demand reports with full audit trail |

## 11. Risks & Mitigation

| Risk | Probability | Impact | Mitigation |
|------|:----------:|:------:|------------|
| **Data Migration Issues** | Medium | High | Prioritize clean data entry over automated migration |
| **Resistance to New System** | Medium | Medium | Phased rollout with IT staff training |
| **Scope Creep** | High | Medium | Adhere to phased approach; defer non-MVP features |

## 12. Conclusion

The current manual asset management process cannot sustain MUAST's growth trajectory. The **MUAST Asset Register** provides a practical, focused solution to this problem. By modernizing asset tracking, we gain **real-time visibility**, **improve accountability**, and **ensure audit readiness**.
