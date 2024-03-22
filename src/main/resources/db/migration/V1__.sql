CREATE TABLE message
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    date            date                  NULL,
    title           VARCHAR(255)          NULL,
    message_body    VARCHAR(255)          NULL,
    private_message BIT(1)                NOT NULL,
    user_id         BIGINT                NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_name  VARCHAR(255)          NULL,
    image      BLOB                  NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    email      VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_user_name UNIQUE (user_name);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);