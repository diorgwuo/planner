--liquibase formatted sql

--changeset yuldashov:1
create table users(
    id serial primary key,
    username varchar(50) unique not null,
    password varchar(255) not null,
    email varchar(255) unique not null
);
--rollback drop table users;