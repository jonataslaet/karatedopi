ALTER TABLE profile ADD COLUMN association_id int8 NULL;
ALTER TABLE profile ADD CONSTRAINT fk_profile_association_id FOREIGN KEY (association_id) REFERENCES association(id);