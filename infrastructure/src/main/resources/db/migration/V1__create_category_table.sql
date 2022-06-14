CREATE TABLE category(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_at DATETIME(6) NOT NULL,
    update_at DATETIME(6) NOT NULL,
    delete_at DATETIME(6) NULL
);
