USE kon;

CREATE TABLE users(
 uno INT NOT NULL AUTO_INCREMENT, 
 full_name VARCHAR(50) ,
 nickname VARCHAR(50) ,
 pw VARCHAR(50) ,
 email VARCHAR(50) ,
 address VARCHAR(100),  
 postalcode VARCHAR(50),
 login_status boolean DEFAULT 0,
 login_method ENUM ('general','google') ,
 delete_flag BOOLEAN DEFAULT false,
 update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
 PRIMARY KEY(uno)
) CHARSET=utf8mb4;

 INSERT INTO users
 (full_name, nickname, pw, email, address, postalcode, login_method)
 VALUES
('John Doe','jd1234', 'password1' ,'john.doe@example.com', '123 Main Street' , '101 001' , 'general'),
('Jane Smith','js1234', 'password2' ,'jane.smith@example.com', '456 Elm Street' , '202 002' , 'general'),
('Alex Johnson','aj1234', 'password3' ,'alex.johnson@example.com', '789 Oak Street' , '303 003' , 'general'),
('Emily Davis','ed1234', 'password4' ,'emily.davis@example.com', '101 Pine Street' , '404 004' , 'general'),
('Michael Brown','mb1234', 'password5' ,'michael.brown@example.com', '202 Cedar Street' , '505 005' , 'general');
