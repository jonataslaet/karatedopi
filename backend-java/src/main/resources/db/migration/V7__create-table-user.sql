CREATE TABLE IF NOT EXISTS tb_user (
    id bigserial,
    email varchar(255) NOT NULL,
    "password" varchar(128) NOT NULL,
    firstname varchar(16) NOT NULL,
    lastname varchar(16) NOT NULL,
    status varchar(18) NOT NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id)
);