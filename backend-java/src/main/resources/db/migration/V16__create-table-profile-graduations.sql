CREATE TABLE IF NOT EXISTS profile_graduation (
    profile_user_id int8 NULL,
    graduation_id int8 NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT profile_graduation_pkey
    PRIMARY KEY (profile_user_id, graduation_id)
);

ALTER TABLE profile_graduation ADD CONSTRAINT fk_profile_graduation_profile_user_id
FOREIGN KEY (profile_user_id) REFERENCES profile(user_id);
ALTER TABLE profile_graduation ADD CONSTRAINT fk_profile_graduation_graduation_id
FOREIGN KEY (graduation_id) REFERENCES graduation(id);