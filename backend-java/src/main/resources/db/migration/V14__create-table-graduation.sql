CREATE TABLE IF NOT EXISTS graduation (
    id bigserial,
    belt varchar(255) NOT NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT graduation_pkey PRIMARY KEY (id)
);