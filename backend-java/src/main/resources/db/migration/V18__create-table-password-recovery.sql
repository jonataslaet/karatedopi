CREATE TABLE IF NOT EXISTS tb_password_recovery (
    id bigserial,
    token varchar(255) NOT NULL,
    expiration timestamp NOT NULL,
    email varchar(255) NOT NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT tb_password_recovery_pkey PRIMARY KEY (id)
);