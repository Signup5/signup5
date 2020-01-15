INSERT INTO users (email, name) VALUES ("kristian.grundstrom@live.se", "Kristian Grundström"), ("marcus8209@gmail.com", "Marcus Damsgaard Jensen");
INSERT INTO event (host_id, title, description, date, location) VALUES
    (SELECT id from users WHERE name = "Marcus Damsgaard Jensen", "Marcus Event", "Superdåligt event.", CURRENT_DATE , "Kungsängen");
INSERT INTO invitation (user_id, event_id) VALUES (SELECT id from users WHERE name = "Kristian Grundström", SELECT id FROM event WHERE title = "Marcus Event");