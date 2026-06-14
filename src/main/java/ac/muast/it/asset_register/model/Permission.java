// model/Permission.java
package ac.muast.it.asset_register.model;

public enum Permission {
    // User Management
    MANAGE_USERS("Manage Users", "Create, update, disable, and assign roles to users", Module.USER_MANAGEMENT),
    READ_USERS("Read Users", "View user information", Module.USER_MANAGEMENT),

    // Role Management
    MANAGE_ROLES("Manage Roles", "Create, update, delete roles and assign permissions", Module.ROLE_MANAGEMENT),
    READ_ROLES("Read Roles", "View roles and their permissions", Module.ROLE_MANAGEMENT),

    // Asset Management
    MANAGE_ASSETS("Manage Assets", "Create, update, dispose, assign, check-in, and transfer assets", Module.ASSET_MANAGEMENT),
    READ_ASSETS("Read Assets", "View assets", Module.ASSET_MANAGEMENT),

    // Location Management
    MANAGE_LOCATIONS("Manage Locations", "Create, update, and delete campuses and offices", Module.LOCATION_MANAGEMENT),
    READ_LOCATIONS("Read Locations", "View campuses and offices", Module.LOCATION_MANAGEMENT),

    // Catalog Management
    MANAGE_ASSET_CATALOG("Manage Asset Catalog", "Create, update, and delete categories and asset types", Module.CATALOG_MANAGEMENT),
    READ_ASSET_CATALOG("Read Asset Catalog", "View categories and asset types", Module.CATALOG_MANAGEMENT),

    // Reports
    VIEW_REPORTS("View Reports", "View system reports", Module.REPORTS),
    EXPORT_REPORTS("Export Reports", "Export reports", Module.REPORTS),

    // System
    VIEW_AUDIT_LOGS("View Audit Logs", "View audit logs", Module.SYSTEM);

    private final String displayName;
    private final String description;
    private final Module module;

    Permission(String displayName, String description, Module module) {
        this.displayName = displayName;
        this.description = description;
        this.module = module;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public Module getModule() { return module; }

    public enum Module {
        USER_MANAGEMENT,
        ROLE_MANAGEMENT,
        ASSET_MANAGEMENT,
        LOCATION_MANAGEMENT,
        CATALOG_MANAGEMENT,
        REPORTS,
        SYSTEM
    }
}