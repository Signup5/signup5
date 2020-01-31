INSERT INTO person (email, first_name)
    VALUES  ('nataliia.zaluska@gmail.com', 'Nataliya Zaluska'), ('marcus8209@gmail.com', 'Marcus Damsgaard Jensen');

INSERT INTO event (host_id, title, description, date_of_event, location)
    VALUES  ((SELECT id from person WHERE first_name = 'Marcus Damsgaard Jensen'), 'Marcus Event', 'Party party.', CURRENT_DATE, 'Kungsängen');

INSERT INTO invitation (person_id, event_id)
    VALUES ((SELECT id from person WHERE first_name = 'Nataliya Zaluska'), (SELECT id FROM event WHERE title = 'Marcus Event'));