CREATE SCHEMA IF NOT EXISTS populationMS;
CREATE EXTENSION IF NOT EXISTS postgis;

DROP TABLE IF EXISTS counties;
CREATE TABLE counties(
    id SERIAL PRIMARY KEY,
    county_name VARCHAR(100) NOT NULL UNIQUE,
    county_code INTEGER NOT NULL UNIQUE,
    geometry GEOMETRY(POLYGON, 4326) NOT NULL,
    area_sq_km DECIMAL(10, 2)
);

CREATE INDEX idx_counties_geometry ON counties USING GIST (geometry);

COMMENT ON TABLE counties IS 'Kenya counties with boundaries';
COMMENT ON COLUMN counties.county_code IS 'County code (1-47)';
COMMENT ON COLUMN counties.geometry IS 'County boundary polygon';


DROP TABLE IF EXISTS population_data;
CREATE TABLE population_data(
    id SERIAL PRIMARY KEY,
    county_id INTEGER NOT NULL REFERENCES counties(id) ON DELETE CASCADE,
    year INTEGER NOT NULL CHECK (year >= 1900 AND year<=2100),
    population BIGINT NOT NULL CHECK (population>=0 ),
    UNIQUE(county_id, year)
);

CREATE INDEX idx_population_county ON population_data(county_id);
CREATE INDEX idx_population_year ON population_data(year);

COMMENT ON TABLE population_data IS 'Population data by county and year';
