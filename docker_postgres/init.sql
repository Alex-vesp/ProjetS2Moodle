CREATE TABLE roles(
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(20)
);
INSERT INTO roles(name) VALUES('ROLE_STUDENT');
INSERT INTO roles(name) VALUES('ROLE_TEACHER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
