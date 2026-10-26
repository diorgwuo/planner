--liquibase formatted sql

--changeset yuldashov:2
create table task(
    id serial PRIMARY KEY,
    title VARCHAR NOT NULL,
    description text NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR NOT NULL,
    completed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--rollback DROP TABLE tasks;