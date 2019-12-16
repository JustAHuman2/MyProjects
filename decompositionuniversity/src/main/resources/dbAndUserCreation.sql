DROP database IF EXISTS university;
DROP USER IF EXISTS master;

CREATE database university;
CREATE USER master;
GRANT ALL PRIVILEGES ON database university to master;