CREATE TYPE attendance AS ENUM ('ATTENDING', 'NOT_ATTENDING', 'MAYBE', 'NO_RESPONSE');

CREATE TABLE person
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);
CREATE TABLE event
(
    id            SERIAL PRIMARY KEY,
    host_id       INT NOT NULL,
    title         VARCHAR(255),
    description   VARCHAR(255),
    date_of_event DATE,
    location      VARCHAR(255)
);
CREATE TABLE invitation
(
    id         SERIAL PRIMARY KEY,
    guest_id   INT        NOT NULL REFERENCES person (id),
    event_id   INT        NOT NULL REFERENCES event (id),
    attendance attendance NOT NULL DEFAULT 'NO_RESPONSE'
);