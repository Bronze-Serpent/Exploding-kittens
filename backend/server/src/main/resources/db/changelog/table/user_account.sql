--liquibase formatted sql

--changeset kovalenko:user_account-1
create table if not exists user_account
(
    id            bigserial primary key,
    login         varchar(255) unique not null,
    password_hash varchar(512)        not null,

    created_date  timestamp,
    modified_date timestamp
);