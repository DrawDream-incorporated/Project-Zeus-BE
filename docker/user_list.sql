./user_list.sql
CREATE TABLE user_list (
 user_no INT NOT NULL AUTO_INCREMENT, 
 user_name VARCHAR(20) ,
 user_Pw    VARCHAR(20) ,
 user_email   VARCHAR(50) ,
 user_address    VARCHAR(100),  
 user_postalcode    VARCHAR(50),
 user_status    INT DEFAULT 0,
 user_delete_flag BOOLEAN DEFAULT false,
 update_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 create_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY(user_email),
  FOREIGN KEY(user_no) REFERENCES user_history(user_no)
) CHARSET=utf8mb4;
