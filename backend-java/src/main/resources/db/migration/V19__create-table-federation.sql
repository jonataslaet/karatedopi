CREATE TABLE IF NOT EXISTS federation (
    id bigserial,
    business_name varchar(64) NOT NULL,
    trade_name varchar(32) NOT NULL,
    federation_abbreviation varchar(8) NOT NULL,
    foundation_date date NOT NULL,
    ein varchar(18) NOT NULL,
    email varchar(255) NULL,
    status varchar(18) NOT NULL,
    address_id int8 NOT NULL,
    profile_user_id int8 NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT federation_pkey PRIMARY KEY (id)
);

ALTER TABLE federation ADD CONSTRAINT fk_federation_address_id FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE federation ADD CONSTRAINT fk_federation_profile_user_id FOREIGN KEY (profile_user_id)
REFERENCES profile(user_id);