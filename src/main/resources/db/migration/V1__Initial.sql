CREATE TYPE attendance AS ENUM ('attending', 'not attending', 'maybe');

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE event
(
    id          SERIAL PRIMARY KEY,
    host_id     INT NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    datetime    DATE,
    location    VARCHAR(255)
);

CREATE TABLE invitation
(
    id         SERIAL,
    user_id    INT NOT NULL REFERENCES users (id),
    event_id   INT NOT NULL REFERENCES event (id),
    attendance attendance
);