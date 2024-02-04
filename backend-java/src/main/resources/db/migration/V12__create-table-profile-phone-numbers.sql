CREATE TABLE IF NOT EXISTS profile_phone_numbers (
    profile_user_id int8 NOT NULL,
    phone_numbers varchar(16) NULL,
    CONSTRAINT profile_phone_numbers_pkey PRIMARY KEY (profile_user_id, phone_numbers)
);

ALTER TABLE profile_phone_numbers ADD CONSTRAINT fk_profile_phone_numbers_profile_user_id FOREIGN KEY (profile_user_id)
REFERENCES profile(user_id);