./user_history.sql
CREATE TABLE user_history (
 user_no INT NOT NULL ,
 present_login  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 previous_login  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
 PRIMARY KEY(user_no)
) CHARSET=utf8mb4;