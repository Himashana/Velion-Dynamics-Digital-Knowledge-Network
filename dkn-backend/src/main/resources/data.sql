-- This script initializes the database with a default administrator user.

INSERT INTO APP_USER (
    ADDRESS,
    CONTACT_NUMBER,
    DESIGNATION,
    EMAIL,
    NAME,
    OFFICE_LOCATION,
    PASSWORD,
    PERMISSION_LEVEL
)
SELECT
    'Gonulla, Gonawila, Sri Lanka',
    '0701517713',
    'Administrator',
    'himashanasuraweera5@gmail.com',
    'Himashana Suraweera',
    'Head Office, No 123, Kurunagale, Sri Lanka',
    '$2a$10$YAjQ3ly2GR8hoRzRDywvC.IIIwU0CC7jvXzsfZFhw8oYPg5U50Z6y',
    1
WHERE NOT EXISTS (
    SELECT 1 FROM APP_USER
);

-- OR request body format for /auth/register endpoint
-- {
--   "name": "Himashana Suraweera",
--   "address": "Gonulla, Gonawila",
--   "email": "himashanasuraweera5@gmail.com",
--   "password": "123456",
--   "contactNumber": "0701517713",
--   "permissionLevel": 1,
--   "performanceMetrics": {},
--   "designation": "Administrator",
--   "officeLocation": "Head Office, No 123, Kurunagale, Sri Lanka",
--   "responsibility": "Manage System Users"
-- }