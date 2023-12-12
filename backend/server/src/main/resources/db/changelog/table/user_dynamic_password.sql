--liquibase formatted sql

--changeset kovalenko:user_dynamic_password-1
create table if not exists user_dynamic_password
(
    id              bigserial primary key,
    enter_durations int4[]                              not null,
    user_account_id bigint references user_account (id) not null,

    created_date    timestamp,
    modified_date   timestamp
);

create index if not exists user_dynamic_password_user_account_idx on user_dynamic_password using btree (user_account_id);