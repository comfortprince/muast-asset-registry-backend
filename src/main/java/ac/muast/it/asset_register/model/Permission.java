// model/Permission.java
package ac.muast.it.asset_register.model;

public enum Permission {
    // User Management
    CREATE_USERS("Create Users", "Create new user accounts", Module.USER_MANAGEMENT),
    EDIT_USERS("Edit Users", "Update, disable, and assign roles to users", Module.USER_MANAGEMENT),
    READ_USERS("Read Users", "View user information", Module.USER_MANAGEMENT),

    // Role Management
    MANAGE_ROLES("Manage Roles", "Create, update, delete roles and assign permissions", Module.ROLE_MANAGEMENT),
    READ_ROLES("Read Roles", "View roles and their permissions", Module.ROLE_MANAGEMENT),

    MANAGE_LOCATIONS("Manage Locations", "Create, update, and delete campuses and offices", Module.LOCATION_MANAGEMENT),
    READ_LOCATIONS("Read Locations", "View campuses and offices", Module.LOCATION_MANAGEMENT),

    MANAGE_ASSETS("Manage Assets", "Create, update, assign, transfer, check-in, repair, and decommission assets", Module.ASSET_MANAGEMENT),
    READ_ASSETS("Read Assets", "View assets", Module.ASSET_MANAGEMENT),

    MANAGE_ASSET_CATEGORIES("Manage Asset Categories", "Create, update, and delete asset categories", Module.CATEGORY_MANAGEMENT),
    READ_ASSET_CATEGORIES("Read Asset Categories", "View asset categories", Module.CATEGORY_MANAGEMENT);

    private final String name;
    private final String description;
    private final Module module;

    Permission(String name, String description, Module module) {
        this.name = name;
        this.description = description;
        this.module = module;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Module getModule() { return module; }

    public enum Module {
        USER_MANAGEMENT,
        ROLE_MANAGEMENT,
        LOCATION_MANAGEMENT,
        ASSET_MANAGEMENT,
        CATEGORY_MANAGEMENT
    }
}