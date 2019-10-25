--liquibase formatted sql

--changeset tkindy:create_rooms_table
CREATE TABLE rooms (
  id varchar(30) PRIMARY KEY,
  flip boolean DEFAULT NULL
);

--changeset tkindy:add_room_name
ALTER TABLE rooms
    ADD COLUMN name varchar(64)
;

--changeset tkindy:add_max_flips
ALTER TABLE rooms
    ADD COLUMN singleFlip BOOLEAN NOT NULL DEFAULT FALSE
;
