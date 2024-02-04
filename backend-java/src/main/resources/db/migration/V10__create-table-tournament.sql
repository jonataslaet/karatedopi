CREATE TABLE IF NOT EXISTS tournament (
    id bigserial,
    address_id int8 NOT NULL,
    event_date timestamp(6) NOT NULL,
    name varchar(64) NOT NULL,
    status varchar(18) NOT NULL,
    CONSTRAINT tournament_pkey PRIMARY KEY (id)
);

ALTER TABLE tournament ADD CONSTRAINT fk_tournament_address_id FOREIGN KEY (address_id) REFERENCES address(id);