CREATE TABLE IF NOT EXISTS federation_phone_numbers (
    federation_id int8 NOT NULL,
    phone_numbers varchar(16) NULL,
    CONSTRAINT federation_phone_numbers_pkey PRIMARY KEY (federation_id, phone_numbers)
);

ALTER TABLE federation_phone_numbers ADD CONSTRAINT fk_federation_phone_numbers_federation_id FOREIGN KEY (federation_id) REFERENCES federation(id);