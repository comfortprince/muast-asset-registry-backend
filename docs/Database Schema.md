```sql
-- =============================================
-- MUAST ASSET REGISTER - DATABASE SCHEMA v0.2.0
-- =============================================

-- =============================================
-- 1. AUTHENTICATION & USER MANAGEMENT
-- =============================================

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    must_change_password BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP NULL,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
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
    permission_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (role_id, permission_name),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- =============================================
-- 2. LOCATION MANAGEMENT
-- =============================================

CREATE TABLE campuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    address TEXT
);

CREATE TABLE offices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    campus_id BIGINT NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    UNIQUE KEY uk_campus_office (campus_id, code),
    FOREIGN KEY (campus_id) REFERENCES campuses(id) ON DELETE CASCADE
);

-- =============================================
-- 3. ASSET CATALOG
-- =============================================

CREATE TABLE asset_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- =============================================
-- 4. ASSETS
-- =============================================

CREATE TABLE assets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) UNIQUE NOT NULL,
    asset_category_id BIGINT NOT NULL,
    current_status_id BIGINT NOT NULL, -- DEFAULT 'AVAILABLE'
    brand VARCHAR(50),
    serial_number VARCHAR(100) UNIQUE,
    purchase_date DATE,
    purchase_cost DECIMAL(10, 2),
    specs JSON,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (asset_category_id) REFERENCES asset_categories(id),
    FOREIGN KEY (current_status_id) REFERENCES asset_statuses(id)
);

CREATE TABLE asset_status_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    status_id VARCHAR(30) NOT NULL,
    reason TEXT,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES asset_statuses(id)
);

CREATE TABLE asset_statuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE asset_location_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    office_id BIGINT NOT NULL,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    FOREIGN KEY (office_id) REFERENCES offices(id)
);

CREATE TABLE asset_assignment_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role_at_assignment VARCHAR(100),
    notes TEXT,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- 5. TEMPORARY LOANS
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
-- 6. AUDIT TRAIL
-- =============================================

CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    entity_name VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL,
    old_values JSON,
    new_values JSON,
    occurred_at TIMESTAMP NOT NULL,
    ip_address VARCHAR(45),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- INDEXES
-- =============================================

CREATE INDEX idx_asset_status_history ON asset_status_history (asset_id, valid_from);
CREATE INDEX idx_asset_location_history ON asset_location_history (asset_id, valid_from);
CREATE INDEX idx_asset_assignment_history ON asset_assignment_history (asset_id, valid_from);
CREATE INDEX idx_temporary_loans_active ON temporary_loans (asset_id, actual_return_date);
CREATE INDEX idx_audit_entity ON audit_logs (entity_name, entity_id);
CREATE INDEX idx_audit_occurred ON audit_logs (occurred_at);
```
