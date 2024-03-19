CREATE TABLE IF NOT EXISTS tb_tournament_participant (
    tournament_id int8 NULL,
    participant_id int8 NULL,
    created_on timestamp(6) NOT NULL,
    updated_on timestamp(6) NULL,
    CONSTRAINT tournament_participant_pkey
    PRIMARY KEY (tournament_id, participant_id)
);

ALTER TABLE tb_tournament_participant ADD CONSTRAINT fk_tb_tournament_participant_tournament_id FOREIGN KEY (tournament_id) REFERENCES tournament(id);
ALTER TABLE tb_tournament_participant ADD CONSTRAINT fk_tb_tournament_participant_participant_id FOREIGN KEY (participant_id) REFERENCES profile(user_id);
