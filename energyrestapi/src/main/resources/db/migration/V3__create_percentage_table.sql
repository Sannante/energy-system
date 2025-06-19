CREATE TABLE IF NOT EXISTS percentage_table (
    hour TIMESTAMP PRIMARY KEY,
    community_depleted DOUBLE PRECISION,
    grid_portion DOUBLE PRECISION
);
