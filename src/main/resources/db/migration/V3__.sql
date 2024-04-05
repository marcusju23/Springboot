ALTER TABLE message
    ADD last_changed date NULL;

ALTER TABLE user
DROP
COLUMN github_id;

ALTER TABLE user
    ADD github_id INT NULL;