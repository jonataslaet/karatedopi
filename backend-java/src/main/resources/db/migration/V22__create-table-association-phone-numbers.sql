CREATE TABLE IF NOT EXISTS association_phone_numbers (
    association_id int8 NOT NULL,
    phone_numbers varchar(16) NULL,
    CONSTRAINT association_phone_numbers_pkey PRIMARY KEY (association_id, phone_numbers)
);

ALTER TABLE association_phone_numbers ADD CONSTRAINT fk_association_phone_numbers_association_id FOREIGN KEY (association_id) REFERENCES association(id);