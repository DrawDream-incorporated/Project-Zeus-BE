./user_history.sql
CREATE TABLE user_history (
 user_no INT NOT NULL ,
 current_login  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 previous_login  TIMESTAMP NULL,
 PRIMARY KEY(user_no)
) CHARSET=utf8mb4;