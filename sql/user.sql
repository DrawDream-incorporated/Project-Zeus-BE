CREATE TABLE User (
                      UserID CHAR(36) NOT NULL UNIQUE,  -- Using CHAR(36) for UUID
                      Username VARCHAR(20) NOT NULL UNIQUE,
                      Email VARCHAR(256) PRIMARY KEY,
                      Password VARCHAR(72) NOT NULL,  -- bcrypt, utf_unicode
                      FirstName VARCHAR(50),
                      LastName VARCHAR(50),
                      Preferred_name VARCHAR(50),
                      Created_at DATETIME NOT NULL, -- utc
                      Updated_at DATETIME NOT NULL, -- utc
                      IsActive BOOLEAN NOT NULL
);
