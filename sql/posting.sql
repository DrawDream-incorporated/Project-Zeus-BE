CREATE TABLE user (
                      user_id VARCHAR(255) NOT NULL UNIQUE,
                      user_name VARCHAR(20) NOT NULL,
                      user_email VARCHAR(256) NOT NULL UNIQUE,
                      password VARCHAR(72) NOT NULL,
                      first_name VARCHAR(50) NOT NULL,
                      last_name VARCHAR(50) NOT NULL,
                      preferred_name VARCHAR(50) NOT NULL,
                      created_at DATETIME NOT NULL,
                      updated_at DATETIME NOT NULL,
                      is_active BOOLEAN NOT NULL,
                      PRIMARY KEY (user_id)
);

CREATE TABLE topic (
                       topic_id VARCHAR(255) PRIMARY KEY,
                       topic_name VARCHAR(255) NOT NULL
);

CREATE TABLE post (
                      post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id VARCHAR(255) NOT NULL,
                      topic_id VARCHAR(255) NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      content LONGTEXT NOT NULL,
                      tags VARCHAR(255),
                      created_at DATETIME NOT NULL,
                      updated_at DATETIME NOT NULL,
                      views INT DEFAULT 0 NOT NULL,
                      vote_like INT DEFAULT 0 NOT NULL,
                      vote_dislike INT DEFAULT 0 NOT NULL,
                      delete_flg INT DEFAULT 0 NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES user(user_id),
                      FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
);

CREATE TABLE vote (
                      user_id VARCHAR(255) NOT NULL,
                      post_id BIGINT NOT NULL,
                      vote_flag INT NOT NULL,
                      created_at DATETIME NOT NULL,
                      PRIMARY KEY (user_id, post_id),
                      FOREIGN KEY (user_id) REFERENCES user(user_id),
                      FOREIGN KEY (post_id) REFERENCES post(post_id)
);
