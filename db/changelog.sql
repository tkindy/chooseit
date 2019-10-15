--liquibase formatted sql

--changeset tkindy:create_rooms_table
CREATE TABLE rooms (
  id varchar(30) PRIMARY KEY,
  flip boolean DEFAULT NULL
);
