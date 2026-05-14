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

-- Reset auto-increment to start after our inserted IDs
ALTER TABLE roles AUTO_INCREMENT = 5;

-- =============================================
-- 2. PERMISSIONS
-- =============================================

INSERT INTO permissions (name, display_name, description, module) VALUES
-- User Management (USER_*)
('USER_CREATE', 'Create Users', 'Ability to create new users', 'USER_MANAGEMENT'),
('USER_READ', 'Read Users', 'Ability to view user information', 'USER_MANAGEMENT'),
('USER_UPDATE', 'Update Users', 'Ability to update user information', 'USER_MANAGEMENT'),
('USER_DELETE', 'Delete Users', 'Ability to delete users', 'USER_MANAGEMENT'),
('USER_ASSIGN_ROLES', 'Assign Roles', 'Ability to assign roles to users', 'USER_MANAGEMENT'),

-- Role Management (*_ROLE)
('CREATE_ROLE', 'Create Roles', 'Ability to create new roles', 'ROLE_MANAGEMENT'),
('READ_ROLE', 'Read Roles', 'Ability to view roles', 'ROLE_MANAGEMENT'),
('UPDATE_ROLE', 'Update Roles', 'Ability to update roles', 'ROLE_MANAGEMENT'),
('DELETE_ROLE', 'Delete Roles', 'Ability to delete roles', 'ROLE_MANAGEMENT'),
('ASSIGN_ROLE_PERMISSION', 'Assign Permissions', 'Ability to assign permissions to roles', 'ROLE_MANAGEMENT'),

-- Asset Management (ASSET_*)
('ASSET_CREATE', 'Create Assets', 'Ability to create new assets', 'ASSET_MANAGEMENT'),
('ASSET_READ', 'Read Assets', 'Ability to view assets', 'ASSET_MANAGEMENT'),
('ASSET_UPDATE', 'Update Assets', 'Ability to update asset information', 'ASSET_MANAGEMENT'),
('ASSET_DELETE', 'Delete Assets', 'Ability to delete assets', 'ASSET_MANAGEMENT'),
('ASSET_ASSIGN', 'Assign Assets', 'Ability to assign assets to users', 'ASSET_MANAGEMENT'),
('ASSET_CHECKIN', 'Check-in Assets', 'Ability to check-in returned assets', 'ASSET_MANAGEMENT'),
('ASSET_TRANSFER', 'Transfer Assets', 'Ability to transfer assets between locations', 'ASSET_MANAGEMENT'),

-- GRV Management (GRV_*)
('GRV_CREATE', 'Create GRV', 'Ability to create Goods Received Vouchers', 'GRV_MANAGEMENT'),
('GRV_READ', 'Read GRV', 'Ability to view GRV entries', 'GRV_MANAGEMENT'),
('GRV_UPDATE', 'Update GRV', 'Ability to update GRV entries', 'GRV_MANAGEMENT'),
('GRV_DELETE', 'Delete GRV', 'Ability to delete GRV entries', 'GRV_MANAGEMENT'),
('GRV_APPROVE', 'Approve GRV', 'Ability to approve GRV entries', 'GRV_MANAGEMENT'),

-- Location Management (LOCATION_*)
('LOCATION_CREATE', 'Create Locations', 'Ability to create campuses/offices', 'LOCATION_MANAGEMENT'),
('LOCATION_READ', 'Read Locations', 'Ability to view locations', 'LOCATION_MANAGEMENT'),
('LOCATION_UPDATE', 'Update Locations', 'Ability to update location information', 'LOCATION_MANAGEMENT'),
('LOCATION_DELETE', 'Delete Locations', 'Ability to delete locations', 'LOCATION_MANAGEMENT'),

-- Category Management (CATEGORY_*)
('CATEGORY_CREATE', 'Create Categories', 'Ability to create asset categories', 'CATALOG_MANAGEMENT'),
('CATEGORY_READ', 'Read Categories', 'Ability to view categories', 'CATALOG_MANAGEMENT'),
('CATEGORY_UPDATE', 'Update Categories', 'Ability to update categories', 'CATALOG_MANAGEMENT'),
('CATEGORY_DELETE', 'Delete Categories', 'Ability to delete categories', 'CATALOG_MANAGEMENT'),

-- Reports
('REPORT_VIEW', 'View Reports', 'Ability to view system reports', 'REPORTS'),
('REPORT_EXPORT', 'Export Reports', 'Ability to export reports', 'REPORTS'),
('REPORT_SCHEDULE', 'Schedule Reports', 'Ability to schedule automated reports', 'REPORTS'),

-- System Configuration
('SYSTEM_CONFIG', 'Configure System', 'Ability to modify system configuration', 'SYSTEM'),
('AUDIT_LOG_VIEW', 'View Audit Logs', 'Ability to view audit logs', 'SYSTEM'),
('BACKUP_RESTORE', 'Backup & Restore', 'Ability to backup and restore data', 'SYSTEM');

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
    'USER_READ', 
    'ASSET_CREATE', 'ASSET_READ', 'ASSET_UPDATE', 'ASSET_DELETE', 
    'ASSET_ASSIGN', 'ASSET_CHECKIN', 'ASSET_TRANSFER',
    'LOCATION_READ', 
    'CATEGORY_READ',
    'GRV_READ', 'GRV_CREATE', 'GRV_UPDATE',
    'REPORT_VIEW', 'REPORT_EXPORT'
);

-- 3.3 VIEWER (Role ID 3) - Read-only access
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, p.id 
FROM permissions p 
WHERE p.name IN (
    'ASSET_READ',
    'LOCATION_READ',
    'CATEGORY_READ',
    'REPORT_VIEW'
);

-- 3.4 USER (Role ID 1) - Basic user with no access (NO permissions)
-- No permissions inserted for USER role

-- =============================================
-- 4. USERS
-- =============================================
-- Note: Using password field (not password_hash) as per User model
-- Fields match User.java: username, email, password, firstName, lastName, 
-- mustChangePassword, enabled, accountNonLocked, createdAt, lastLogin

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