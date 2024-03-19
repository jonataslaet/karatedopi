ALTER TABLE address DROP CONSTRAINT IF EXISTS fk_address_city_id;

ALTER TABLE address ALTER COLUMN street TYPE varchar(39);

ALTER TABLE address ADD CONSTRAINT fk_address_city_id FOREIGN KEY (city_id) REFERENCES city(id);
