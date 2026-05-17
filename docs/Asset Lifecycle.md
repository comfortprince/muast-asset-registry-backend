Here's the asset lifecycle through your endpoints:

## Full Asset Lifecycle

### 1. Asset Created (via GRV or Legacy Import)

```
Asset: CSC-LAP-003
Status: AVAILABLE
Location: IT Office (CSC)
Assignment: None
```

### 2. Assign to User

```
POST /api/assets/CSC-LAP-003/assign
{
  "username": "user.tendai",
  "roleAtAssignment": "Lecturer",
  "notes": "New semester laptop"
}

Result:
  Status: AVAILABLE → ASSIGNED
  Assignment: Created (user.tendai, role=Lecturer)
  Location: Unchanged (still IT Office)
```

### 3. Transfer to Office

```
POST /api/assets/CSC-LAP-003/transfer
{
  "officeId": 6
}

Result:
  Status: ASSIGNED (unchanged)
  Assignment: Unchanged (still user.tendai)
  Location: IT Office → Computer Lab 1
```

### 4. Check-in (Return to Storage)

```
POST /api/assets/CSC-LAP-003/checkin

Result:
  Status: ASSIGNED → AVAILABLE
  Assignment: Closed (returned_at = now)
  Location: Unchanged (still Computer Lab 1)
```

### 5. Assign to Someone Else

```
POST /api/assets/CSC-LAP-003/assign
{
  "username": "it.sarah",
  "roleAtAssignment": "IT Support"
}

Result:
  Status: AVAILABLE → ASSIGNED
  Assignment: New (it.sarah)
  Previous assignment already closed
```

### 6. Send for Repair

```
POST /api/assets/CSC-LAP-003/repair

Result:
  Status: ASSIGNED → IN_REPAIR
  Assignment: Still active (it.sarah still responsible)
```

### 7. Mark as Lost

```
POST /api/assets/CSC-LAP-003/lost
{
  "notes": "Disappeared during office move"
}

Result:
  Status: IN_REPAIR → LOST
  Assignment: Closed (returned_at = now)
  Asset notes updated
```

### 8. Or Decommission Instead

```
POST /api/assets/CSC-DSK-001/decommission
{
  "notes": "End of life — 5 years old"
}

Result:
  Status: ASSIGNED → DECOMMISSIONED
  Assignment: Closed
  Location: Closed
```

## Lifecycle State Diagram

```
                    ┌─────────────┐
                    │  AVAILABLE  │ ← After checkin
                    └──────┬──────┘
                           │ assign
                    ┌──────▼──────┐
           ┌───────│  ASSIGNED   │
           │       └──────┬──────┘
           │              │ repair
           │       ┌──────▼──────┐
           │       │  IN_REPAIR  │
           │       └──────┬──────┘
           │              │ checkin
           │       ┌──────▼──────┐
           │       │  AVAILABLE  │ (back)
           │       └─────────────┘
           │
           ├──────────────────────┐ lost
           │               ┌──────▼──────┐
           │               │    LOST     │
           │               └─────────────┘
           │
           └──────────────────────┐ decommission
                           ┌──────▼──────┐
                           │DECOMMISSIONED│
                           └─────────────┘
```

## Gaps in Current Implementation

| Issue | What Happens Now |
|-------|-----------------|
| **Check-in doesn't update location** | After checkin, asset stays at user's office — should move to IT Storage |
| **Repair keeps assignment active** | it.sarah is still assigned even though the laptop is in the shop |
| **No status validation on transfer/lost/decommission** | You can transfer a LOST asset — should be blocked |
| **No service entry on repair** | `sendForRepair` just changes status — no record of vendor or cost |

### Quick Fix Suggestions

**Check-in should update location:**
```java
@Transactional
public Asset checkinAsset(String assetCode) {
    // ... close assignment ...

    // Move to IT Storage (office ID 8 = Stores/Storage)
    assetLocationRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
        .ifPresent(current -> {
            current.setIsCurrent(false);
            assetLocationRepository.save(current);
        });
    
    Office storage = officeRepository.findById(8L)  // Stores
        .orElseThrow();
    
    AssetLocation location = AssetLocation.builder()
        .asset(asset)
        .office(storage)
        .isCurrent(true)
        .build();
    assetLocationRepository.save(location);

    asset.setStatus(AssetStatus.AVAILABLE);
    return assetRepository.save(asset);
}
```

Want to fix these gaps now or continue to the next module?