## Spring Data REST Auto-Generated URLs

These come free with your repositories:

### Assets
```
GET    /api/assets                  # List all assets (paginated)
GET    /api/assets/{id}             # Get single asset
POST   /api/assets                  # Create asset
PUT    /api/assets/{id}             # Update asset
PATCH  /api/assets/{id}             # Partial update
DELETE /api/assets/{id}             # Delete asset
GET    /api/assets/search           # Search methods
```

### Asset Locations
```
GET    /api/assetLocations
GET    /api/assetLocations/{id}
GET    /api/assetLocations/search
```

### Asset Assignments
```
GET    /api/assetAssignments
GET    /api/assetAssignments/{id}
GET    /api/assetAssignments/search
```

### Other Auto-Generated
```
GET    /api/users
GET    /api/roles
GET    /api/permissions
GET    /api/campuses
GET    /api/offices
GET    /api/categories
GET    /api/assetTypes
GET    /api/grvEntries
GET    /api/serviceEntries
GET    /api/temporaryLoans
GET    /api/inventoryItems
GET    /api/inventoryTransactions
```

---

## Custom Controller URLs (Need to Build)

### Asset Operations
```
POST   /api/assets/{assetCode}/assign        # Assign to user
POST   /api/assets/{assetCode}/transfer      # Transfer to new office
POST   /api/assets/{assetCode}/checkin       # Return to storage
POST   /api/assets/{assetCode}/repair        # Send for repair
POST   /api/assets/{assetCode}/lost          # Mark as lost
POST   /api/assets/{assetCode}/decommission  # Decommission asset
GET    /api/assets/{assetCode}/history       # Full audit history
```

### Loan Operations
```
POST   /api/loans                           # Create new loan
POST   /api/loans/{id}/return               # Return a loan
GET    /api/loans/active                    # All active loans
GET    /api/loans/overdue                   # Overdue loans
GET    /api/assets/{assetCode}/loans        # Loan history for an asset
```

### Inventory Operations
```
POST   /api/inventory/{id}/send             # Send to department
POST   /api/inventory/{id}/receive          # Receive from GRV
POST   /api/inventory/{id}/return           # Return to storage
POST   /api/inventory/{id}/adjust           # Manual stock adjustment
GET    /api/inventory/low-stock             # Items below threshold
GET    /api/inventory/{id}/transactions     # Transaction history
```

### GRV Operations
```
POST   /api/grv                             # Create GRV (bulk asset/inventory creation)
POST   /api/grv/{id}/approve                # Approve GRV
GET    /api/grv/{id}/items                  # Items in this GRV
```

### Report Operations
```
GET    /api/reports/assets-by-location      # Assets grouped by campus/office
GET    /api/reports/assets-by-user          # Assets assigned per user
GET    /api/reports/assets-by-status        # Asset count by status
GET    /api/reports/inventory-levels        # Current stock levels
GET    /api/reports/overdue-loans           # Loans past due date
GET    /api/reports/replacement-alerts      # Printers due for replacement
GET    /api/reports/audit-trail/{entityType}/{entityId}  # Audit history
```

---

## Summary

| Type | Count | Status |
|------|-------|--------|
| Auto-generated (Spring Data REST) | ~15 endpoints | ✅ Working |
| Asset operations | 7 endpoints | ⬜ To build |
| Loan operations | 4 endpoints | ⬜ To build |
| Inventory operations | 5 endpoints | ⬜ To build |
| GRV operations | 2 endpoints | ⬜ To build |
| Reports | 7 endpoints | ⬜ To build |
| **Total custom** | **25 endpoints** | |

Want to start with the Asset operations controller?