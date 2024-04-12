CREATE TABLE User (
    user_id CHAR(36) NOT NULL UNIQUE COMMENT 'UUID',-- Using CHAR(36) for UUID
    username VARCHAR(20) NOT NULL UNIQUE COMMENT 'It is equal to ID in Konglish',
    email VARCHAR(256) PRIMARY KEY,
    password VARCHAR(72) NOT NULL COLLATE utf8mb4_0900_as_cs COMMENT 'We use case sensitive and Accent Sensitive',  -- bcrypt, utf_unicode
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    preferred_name VARCHAR(50) COMMENT 'Displayed Name for the user',
    created_at DATETIME NOT NULL COMMENT 'UTC timzezone', -- UTC
    updated_at DATETIME NOT NULL COMMENT 'UTC timzezone', -- UTC
    is_active BOOLEAN NOT NULL
);
