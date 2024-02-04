CREATE TABLE IF NOT EXISTS city (
    id bigserial,
    state_id int8 NOT NULL,
    name varchar(33) NOT NULL,
    CONSTRAINT city_pkey PRIMARY KEY (id)
);

ALTER TABLE city ADD CONSTRAINT fk_city_state_id FOREIGN KEY (state_id) REFERENCES state(id);