ALTER TABLE event ADD COLUMN duration INTEGER;

UPDATE event SET duration = 60;