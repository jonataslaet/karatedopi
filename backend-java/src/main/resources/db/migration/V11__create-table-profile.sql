CREATE TABLE IF NOT EXISTS profile (
    user_id int8 NOT NULL,
    birthday date NOT NULL,
    address_id int8 NOT NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    blood_type varchar(255) NOT NULL,
    itin varchar(11) NOT NULL,
    father varchar(64) NOT NULL,
    fullname varchar(64) NOT NULL,
    mother varchar(64) NOT NULL,
    nit varchar(10) NOT NULL,
    current_belt varchar(9) NULL,
    CONSTRAINT profile_pkey PRIMARY KEY (user_id)
);

ALTER TABLE profile ADD CONSTRAINT fk_profile_address_id FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE profile ADD CONSTRAINT fk_profile_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id);