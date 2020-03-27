ALTER TABLE event ADD COLUMN isCanceled BOOLEAN DEFAULT false;

UPDATE event SET isCanceled = FALSE;