--liquibase formatted sql

--changeset yuldashov:2
create table tasks(
    id serial primary key,
    title varchar not null,
    description text not null,
    user_id int not null,
    status varchar not null,
    completed_at timestamp,
    foreign key (user_id) references users(id) on delete cascade
);

--rollback DROP TABLE tasks;