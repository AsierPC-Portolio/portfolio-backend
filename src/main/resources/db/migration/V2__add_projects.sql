CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    country_code VARCHAR(10),
    link VARCHAR(255),
    start_date DATE,
    end_date DATE
);