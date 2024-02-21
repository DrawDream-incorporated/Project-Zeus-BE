USE kon;

CREATE TABLE users_name(
 uno INT NOT NULL, 
 first_name VARCHAR(25),
 last_name VARCHAR(25),
 FOREIGN KEY (uno) REFERENCES users(uno)
) CHARSET=utf8mb4;

INSERT INTO users_name 
(uno, first_name, last_name)
VALUES
(1, 'John', 'Doe'),
(2, 'Jane', 'Smith'),
(3, 'Alex', 'Johnson'),
(4, 'Emily', 'Davis'),
(5, 'Michael', 'Brown');