CREATE TABLE IF NOT EXISTS tb_user_role (
    role_id bigserial,
    user_id bigserial
);

ALTER TABLE tb_user_role ADD CONSTRAINT fk_tb_user_role_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id);
ALTER TABLE tb_user_role ADD CONSTRAINT fk_tb_user_role_role_id FOREIGN KEY (role_id) REFERENCES tb_role(id);