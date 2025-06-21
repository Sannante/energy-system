-- usage_table (passt zu UsageEntry.java)
CREATE TABLE IF NOT EXISTS usage_table (
hour TIMESTAMP PRIMARY KEY,
grid_used DOUBLE PRECISION,
community_produced DOUBLE PRECISION,
community_used DOUBLE PRECISION
);

-- percentage_table (passt zu PercentageEntity.java)
CREATE TABLE IF NOT EXISTS percentage_table (
id SERIAL PRIMARY KEY,
hour TIMESTAMP,
community_depleted DOUBLE PRECISION,
grid_portion DOUBLE PRECISION
);
