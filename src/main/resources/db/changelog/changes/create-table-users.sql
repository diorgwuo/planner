--liquibase formatted sql

--changeset yuldashov:1
create table users(
    id serial PRIMARY KEY ,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);
--rollback drop table users;