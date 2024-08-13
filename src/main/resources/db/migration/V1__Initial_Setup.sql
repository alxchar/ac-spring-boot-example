CREATE TABLE customer
(
    id    BIGSERIAL PRIMARY KEY,
    name  TEXT NOT NULL,
    email TEXT NOT NULL,
    age   INT  NOT NULL,
    CONSTRAINT customer_email_unique UNIQUE (email)
);