CREATE TABLE IF NOT EXISTS tb_tournament_participant (
    tournament_id bigserial,
    participant_id bigserial
);

ALTER TABLE tb_tournament_participant ADD CONSTRAINT fk_tb_tournament_participant_tournament_id FOREIGN KEY (tournament_id) REFERENCES tournament(id);
ALTER TABLE tb_tournament_participant ADD CONSTRAINT fk_tb_tournament_participant_participant_id FOREIGN KEY (participant_id) REFERENCES profile(user_id);
