--liquibase formatted sql


--changeset barabanov:room
CREATE TABLE room
(
    id              BIGSERIAL                 PRIMARY KEY
);


--changeset barabanov:user_in_room
CREATE TABLE user_in_room
(
    id              BIGSERIAL                 PRIMARY KEY,
    room_id         BIGINT                    REFERENCES room (id)  ON DELETE CASCADE,
    user_id         BIGINT                    REFERENCES user_account (id) ON DELETE CASCADE
);