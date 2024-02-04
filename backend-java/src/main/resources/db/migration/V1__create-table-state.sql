CREATE TABLE IF NOT EXISTS state (
    id bigserial,
    name varchar(19) NOT NULL,
    state_abbreviation varchar(2) NOT NULL,
    CONSTRAINT state_pkey PRIMARY KEY (id)
);