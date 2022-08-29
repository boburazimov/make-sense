-- INSERT NOT EXISTS INTO table roles(name)
-- values ('ROLE_USER'),
--        ('ROLE_ADMIN'),
--        ('ROLE_DIRECTOR');

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_DIRECTOR')
    ON DUPLICATE KEY UPDATE name = VALUES(name);