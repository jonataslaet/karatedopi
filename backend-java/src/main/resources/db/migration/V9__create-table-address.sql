CREATE TABLE IF NOT EXISTS address (
    id bigserial,
    city_id bigserial,
    neighbourhood varchar(32) NOT NULL,
    number varchar(16) NOT NULL,
    street varchar(39) NOT NULL,
    zip_code varchar(9) NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

ALTER TABLE address ADD CONSTRAINT fk_address_city_id FOREIGN KEY (city_id) REFERENCES city(id);