CREATE TABLE user (
    email VARCHAR(30) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);


CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer VARCHAR(20) NOT NULL,
    task VARCHAR(200) NOT NULL,
    password VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    writer_email VARCHAR(30) NOT NULL,
    FOREIGN KEY (writer_email) REFERENCES user(email)
);

