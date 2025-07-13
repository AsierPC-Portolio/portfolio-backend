-- Script to insert many projects for testing filters and pagination

-- Script to insert many projects for testing filters and pagination
INSERT INTO projects (name, description, country_code, start_date, end_date)
SELECT 
  'Project ' || lpad(gs::text, 2, '0'),
  'Description for project ' || lpad(gs::text, 2, '0'),
  CASE (gs % 3)
    WHEN 0 THEN 'es'
    WHEN 1 THEN 'gb'
    WHEN 4 THEN 'fr'
    WHEN 5 then 'de'
    ELSE 'it'
  END,
  DATE '2023-01-01' + (gs % 30),
  DATE '2023-02-01' + (gs % 30)
FROM generate_series(1, 99) AS gs;
