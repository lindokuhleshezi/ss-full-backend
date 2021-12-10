sudo -i -u postgres
psql $database
CREATE EXTENSION pgcrypto;
