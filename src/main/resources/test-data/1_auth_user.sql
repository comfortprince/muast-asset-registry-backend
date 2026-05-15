-- =============================================
-- FILE 1: AUTHENTICATION & USER MANAGEMENT TEST DATA
-- =============================================
-- Description: Test data for users, roles, and permissions
-- Order: Run this FIRST
-- Tables: roles, permissions, users, user_roles, role_permissions
-- 
-- Password hash: $2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS
-- Plain text password: "password123"
-- =============================================

-- =============================================
-- 1. ROLES
-- =============================================

INSERT INTO roles (id, name, description) VALUES
(1, 'USER', 'Basic user with no access'),
(2, 'IT_STAFF', 'IT staff with asset management permissions'),
(3, 'VIEWER', 'View only access'),
(4, 'ADMIN', 'Full system access');

ALTER TABLE roles AUTO_INCREMENT = 5;

-- =============================================
-- 2. PERMISSIONS
-- =============================================

INSERT INTO permissions (name, display_name, description, module) VALUES
-- User Management
('MANAGE_USERS', 'Manage Users', 'Ability to create, update, disable, and assign roles to users', 'USER_MANAGEMENT'),
('READ_USERS', 'Read Users', 'Ability to view user information', 'USER_MANAGEMENT'),

-- Role Management
('MANAGE_ROLES', 'Manage Roles', 'Ability to create, update, delete roles and assign permissions', 'ROLE_MANAGEMENT'),
('READ_ROLES', 'Read Roles', 'Ability to view roles and their permissions', 'ROLE_MANAGEMENT'),

-- Asset Management
('MANAGE_ASSETS', 'Manage Assets', 'Ability to create, update, dispose, assign, check-in, and transfer assets', 'ASSET_MANAGEMENT'),
('READ_ASSETS', 'Read Assets', 'Ability to view assets', 'ASSET_MANAGEMENT'),

-- Location Management
('MANAGE_LOCATIONS', 'Manage Locations', 'Ability to create, update, and delete campuses and offices', 'LOCATION_MANAGEMENT'),
('READ_LOCATIONS', 'Read Locations', 'Ability to view campuses and offices', 'LOCATION_MANAGEMENT'),

-- Catalog Management
('MANAGE_ASSET_CATALOG', 'Manage Asset Catalog', 'Ability to create, update, and delete categories and asset types', 'CATALOG_MANAGEMENT'),
('READ_ASSET_CATALOG', 'Read Asset Catalog', 'Ability to view categories and asset types', 'CATALOG_MANAGEMENT'),

-- Reports
('VIEW_REPORTS', 'View Reports', 'Ability to view system reports', 'REPORTS'),
('EXPORT_REPORTS', 'Export Reports', 'Ability to export reports', 'REPORTS'),

-- System
('VIEW_AUDIT_LOGS', 'View Audit Logs', 'Ability to view audit logs', 'SYSTEM');

-- =============================================
-- 3. ROLE-PERMISSION ASSIGNMENTS
-- =============================================

-- 3.1 ADMIN (Role ID 4) - Full system access (ALL permissions)
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, p.id 
FROM permissions p;

-- 3.2 IT_STAFF (Role ID 2) - Asset management permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, p.id 
FROM permissions p 
WHERE p.name IN (
    'READ_USERS',
    'MANAGE_ASSETS', 'READ_ASSETS',
    'READ_LOCATIONS',
    'READ_ASSET_CATALOG',
    'VIEW_REPORTS', 'EXPORT_REPORTS'
);

-- 3.3 VIEWER (Role ID 3) - Read-only access
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, p.id 
FROM permissions p 
WHERE p.name IN (
    'READ_USERS',
    'READ_ASSETS',
    'READ_LOCATIONS',
    'READ_ASSET_CATALOG',
    'VIEW_REPORTS'
);

-- 3.4 USER (Role ID 1) - Basic user with no access (NO permissions)

-- =============================================
-- 4. USERS
-- =============================================

INSERT INTO users (
    username, 
    email, 
    password, 
    first_name, 
    last_name, 
    must_change_password, 
    enabled, 
    account_non_locked,
    account_non_expired,
    credentials_non_expired,
    created_at, 
    last_login
) VALUES
-- ADMIN users
(
    'admin.john', 
    'john.admin@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'John', 
    'Admin', 
    FALSE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NOW()
),
(
    'admin.jane', 
    'jane.admin@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Jane', 
    'Administrator', 
    FALSE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE,
    NOW(), 
    NOW()
),

-- IT_STAFF users
(
    'it.mary', 
    'mary.it@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Mary', 
    'Moyo', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),
(
    'it.peter', 
    'peter.it@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Peter', 
    'Ncube', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),
(
    'it.sarah', 
    'sarah.it@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Sarah', 
    'Dube', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),

-- VIEWER users
(
    'viewer.david', 
    'david.view@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'David', 
    'Sibanda', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),
(
    'viewer.linda', 
    'linda.view@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Linda', 
    'Moyo', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),

-- USER (basic) users - no permissions
(
    'user.tendai', 
    'tendai.user@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Tendai', 
    'Makoni', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),
(
    'user.chipo', 
    'chipo.user@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Chipo', 
    'Mutasa', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
),
(
    'user.tawanda', 
    'tawanda.user@muast.ac.zw', 
    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 
    'Tawanda', 
    'Gumbo', 
    TRUE, 
    TRUE, 
    TRUE,
    TRUE, 
    TRUE, 
    NOW(), 
    NULL
);

-- =============================================
-- 5. USER-ROLE ASSIGNMENTS
-- =============================================

-- ADMIN users (Role ID 4)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 4
FROM users u 
WHERE u.username IN ('admin.john', 'admin.jane');

-- IT_STAFF users (Role ID 2)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 2
FROM users u 
WHERE u.username IN ('it.mary', 'it.peter', 'it.sarah');

-- VIEWER users (Role ID 3)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 3
FROM users u 
WHERE u.username IN ('viewer.david', 'viewer.linda');

-- USER (basic) users (Role ID 1)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 1
FROM users u 
WHERE u.username IN ('user.tendai', 'user.chipo', 'user.tawanda');

-- =============================================
-- SUMMARY: USER-ROLE-PERMISSION MATRIX
-- =============================================

/*
┌─────────────────┬───────────┬──────────────────────────────────────────────────────────┐
│ User            │ Role      │ Permissions                                              │
├─────────────────┼───────────┼──────────────────────────────────────────────────────────┤
│ admin.john      │ ADMIN     │ ALL permissions                                          │
│ admin.jane      │ ADMIN     │ ALL permissions                                          │
├─────────────────┼───────────┼──────────────────────────────────────────────────────────┤
│ it.mary         │ IT_STAFF  │ READ_USERS, MANAGE_ASSETS, READ_ASSETS,                  │
│ it.peter        │ IT_STAFF  │ READ_LOCATIONS, READ_ASSET_CATALOG,                      │
│ it.sarah        │ IT_STAFF  │ VIEW_REPORTS, EXPORT_REPORTS                             │
├─────────────────┼───────────┼──────────────────────────────────────────────────────────┤
│ viewer.david    │ VIEWER    │ READ_USERS, READ_ASSETS, READ_LOCATIONS,                 │
│ viewer.linda    │ VIEWER    │ READ_ASSET_CATALOG, VIEW_REPORTS                         │
├─────────────────┼───────────┼──────────────────────────────────────────────────────────┤
│ user.tendai     │ USER      │ None                                                     │
│ user.chipo      │ USER      │ None                                                     │
│ user.tawanda    │ USER      │ None                                                     │
└─────────────────┴───────────┴──────────────────────────────────────────────────────────┘

Credentials:
  All users: secret123

Permissions List:
  ADMIN    (13): MANAGE_USERS, READ_USERS, MANAGE_ROLES, READ_ROLES,
                  MANAGE_ASSETS, READ_ASSETS, MANAGE_LOCATIONS, READ_LOCATIONS,
                  MANAGE_ASSET_CATALOG, READ_ASSET_CATALOG,
                  VIEW_REPORTS, EXPORT_REPORTS, VIEW_AUDIT_LOGS

  IT_STAFF  (7): READ_USERS, MANAGE_ASSETS, READ_ASSETS,
                  READ_LOCATIONS, READ_ASSET_CATALOG,
                  VIEW_REPORTS, EXPORT_REPORTS

  VIEWER    (5): READ_USERS, READ_ASSETS, READ_LOCATIONS,
                  READ_ASSET_CATALOG, VIEW_REPORTS

  USER      (0): None
*/