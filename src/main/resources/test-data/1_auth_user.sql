-- =============================================
-- FILE 1: AUTHENTICATION & USER MANAGEMENT TEST DATA
-- =============================================
-- Description: Test data for users, roles, and permissions
-- Order: Run this FIRST
-- Tables: roles, users, user_roles, role_permissions
-- Note: Permissions are hardcoded in the Permission enum
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
-- 2. ROLE-PERMISSION ASSIGNMENTS
-- =============================================

-- 2.1 ADMIN (Role ID 4) — All permissions
INSERT INTO role_permissions (role_id, permission_name) VALUES
(4, 'CREATE_USERS'),
(4, 'EDIT_USERS'),
(4, 'READ_USERS'),
(4, 'MANAGE_ROLES'),
(4, 'READ_ROLES'),
(4, 'MANAGE_LOCATIONS'),
(4, 'READ_LOCATIONS'),
(4, 'MANAGE_ASSETS'),
(4, 'READ_ASSETS'),
(4, 'MANAGE_ASSET_CATEGORIES'),
(4, 'READ_ASSET_CATEGORIES');

-- 2.2 IT_STAFF (Role ID 2) — Asset management
INSERT INTO role_permissions (role_id, permission_name) VALUES
(2, 'READ_USERS'),
(2, 'MANAGE_ASSETS'),
(2, 'READ_ASSETS'),
(2, 'READ_LOCATIONS'),
(2, 'READ_ASSET_CATEGORIES');

-- 2.3 VIEWER (Role ID 3) — Read-only
INSERT INTO role_permissions (role_id, permission_name) VALUES
(3, 'READ_USERS'),
(3, 'READ_ASSETS'),
(3, 'READ_LOCATIONS'),
(3, 'READ_ASSET_CATEGORIES');

-- 2.4 USER (Role ID 1) — No permissions

-- =============================================
-- 3. USERS (30 users)
-- =============================================

INSERT INTO users (
    username, email, password, first_name, last_name,
    must_change_password, enabled, account_non_locked,
    account_non_expired, credentials_non_expired, created_at, last_login
) VALUES

-- ========================
-- ADMIN users (3)
-- ========================
('admin.john',   'john.admin@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'John',   'Admin',         FALSE, TRUE, TRUE, TRUE, TRUE, NOW(), NOW()),
('admin.jane',   'jane.admin@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Jane',   'Administrator', FALSE, TRUE, TRUE, TRUE, TRUE, NOW(), NOW()),
('admin.tom',    'tom.admin@muast.ac.zw',    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tom',    'Moyo',         FALSE, TRUE, TRUE, TRUE, TRUE, NOW(), NOW()),

-- ========================
-- IT_STAFF users (10)
-- ========================
('it.mary',      'mary.it@muast.ac.zw',      '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Mary',   'Moyo',         TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.peter',     'peter.it@muast.ac.zw',     '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Peter',  'Ncube',        TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.sarah',     'sarah.it@muast.ac.zw',     '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Sarah',  'Dube',         TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.james',     'james.it@muast.ac.zw',     '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'James',  'Chitumba',     TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.patience',  'patience.it@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Patience','Sibanda',     TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.brian',     'brian.it@muast.ac.zw',     '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Brian',  'Makoni',       TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.rutendo',   'rutendo.it@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Rutendo','Gumbo',       TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.tinashe',   'tinashe.it@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tinashe','Mutasa',      TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.farai',     'farai.it@muast.ac.zw',     '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Farai',  'Zhou',         TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('it.kudzai',    'kudzai.it@muast.ac.zw',    '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Kudzai', 'Chikosi',      TRUE,  TRUE, TRUE, TRUE, TRUE, NOW(), NULL),

-- ========================
-- VIEWER users (10)
-- ========================
('viewer.david',   'david.view@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'David',   'Sibanda',   TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.linda',   'linda.view@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Linda',   'Moyo',      TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.charles', 'charles.view@muast.ac.zw', '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Charles', 'Ndlovu',    TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.grace',   'grace.view@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Grace',   'Chauke',    TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.tapiwa',  'tapiwa.view@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tapiwa',  'Mpofu',     TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.nyasha',  'nyasha.view@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Nyasha',  'Banda',     TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.fungai',  'fungai.view@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Fungai',  'Dlamini',   TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.tsitsi',  'tsitsi.view@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tsitsi',  'Hove',      TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.takunda', 'takunda.view@muast.ac.zw', '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Takunda','Gonzo',     TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('viewer.ruvheneko','ruvheneko.view@muast.ac.zw','$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS','Ruvheneko','Sithole', TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),

-- ========================
-- USER (basic) users (7)
-- ========================
('user.tendai',  'tendai.user@muast.ac.zw',  '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tendai',  'Makoni', TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.chipo',   'chipo.user@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Chipo',   'Mutasa', TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.tawanda', 'tawanda.user@muast.ac.zw', '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tawanda', 'Gumbo',  TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.tafadzwa','tafadzwa.user@muast.ac.zw','$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Tafadzwa','Zhou',   TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.ruvarashe','ruvarashe.user@muast.ac.zw','$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS','Ruvarashe','Chiota',TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.kudakwashe','kudakwashe.user@muast.ac.zw','$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS','Kudakwashe','Mbedzi',TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL),
('user.anesu',   'anesu.user@muast.ac.zw',   '$2a$12$yFk88RqKP/XfsDgf32MG6OU.qubhhYwiAXSwqRML5DEEvAI7owLBS', 'Anesu',   'Shumba', TRUE, TRUE, TRUE, TRUE, TRUE, NOW(), NULL);

-- =============================================
-- 4. USER-ROLE ASSIGNMENTS
-- =============================================

-- ADMIN users (Role ID 4)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 4 FROM users u
WHERE u.username IN ('admin.john', 'admin.jane', 'admin.tom');

-- IT_STAFF users (Role ID 2)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 2 FROM users u
WHERE u.username IN (
    'it.mary', 'it.peter', 'it.sarah', 'it.james', 'it.patience',
    'it.brian', 'it.rutendo', 'it.tinashe', 'it.farai', 'it.kudzai'
);

-- VIEWER users (Role ID 3)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 3 FROM users u
WHERE u.username IN (
    'viewer.david', 'viewer.linda', 'viewer.charles', 'viewer.grace',
    'viewer.tapiwa', 'viewer.nyasha', 'viewer.fungai', 'viewer.tsitsi',
    'viewer.takunda', 'viewer.ruvheneko'
);

-- USER (basic) users (Role ID 1)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 1 FROM users u
WHERE u.username IN (
    'user.tendai', 'user.chipo', 'user.tawanda', 'user.tafadzwa',
    'user.ruvarashe', 'user.kudakwashe', 'user.anesu'
);

-- =============================================
-- SUMMARY
-- =============================================

/*
┌──────────────────┬──────────┬───────┐
│ Role             │ Count    │ Perms │
├──────────────────┼──────────┼───────┤
│ ADMIN            │     3    │   11  │
│ IT_STAFF         │    10    │    5  │
│ VIEWER           │    10    │    4  │
│ USER             │     7    │    0  │
├──────────────────┼──────────┼───────┤
│ TOTAL            │    30    │       │
└──────────────────┴──────────┴───────┘

Credentials: All users = password123

Permissions:
  ADMIN   (11): CREATE_USERS, EDIT_USERS, READ_USERS, MANAGE_ROLES, READ_ROLES,
                 MANAGE_LOCATIONS, READ_LOCATIONS, MANAGE_ASSETS, READ_ASSETS,
                 MANAGE_ASSET_CATEGORIES, READ_ASSET_CATEGORIES

  IT_STAFF (5): READ_USERS, MANAGE_ASSETS, READ_ASSETS,
                 READ_LOCATIONS, READ_ASSET_CATEGORIES

  VIEWER   (4): READ_USERS, READ_ASSETS, READ_LOCATIONS, READ_ASSET_CATEGORIES

  USER     (0): None
*/