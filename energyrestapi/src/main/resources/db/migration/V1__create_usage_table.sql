CREATE TABLE IF NOT EXISTS usage_table (
    hour TIMESTAMP PRIMARY KEY,
    grid_used DOUBLE PRECISION,
    community_produced DOUBLE PRECISION,
    community_used DOUBLE PRECISION
);


