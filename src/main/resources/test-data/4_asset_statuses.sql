-- =============================================
-- SEED ASSET STATUSES
-- =============================================

INSERT INTO asset_statuses (name, description) VALUES
('AVAILABLE', 'Asset is in storage and ready for assignment'),
('ASSIGNED', 'Currently assigned to a user'),
('ON_LOAN', 'Temporarily loaned out'),
('IN_REPAIR', 'Undergoing maintenance or repair'),
('DECOMMISSIONED', 'Retired from service'),
('LOST', 'Reported as lost or missing');