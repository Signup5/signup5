ALTER TABLE event ADD COLUMN time_of_event TIME;

UPDATE event SET time_of_event = '12:00:00';