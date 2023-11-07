--liquibase formatted sql

--changeset kovalenko:example-1
create table if not exists example(
    id bigserial primary key
)