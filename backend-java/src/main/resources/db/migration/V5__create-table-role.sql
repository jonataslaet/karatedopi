CREATE TABLE IF NOT EXISTS tb_role (
    id bigserial,
    authority varchar(16) NOT NULL,
    CONSTRAINT tb_role_pkey PRIMARY KEY (id)
);