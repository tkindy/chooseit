CREATE USER chooseit_user;
ALTER USER chooseit_user WITH PASSWORD 'password';

CREATE TABLE rooms (
  id varchar(30) PRIMARY KEY,
  flip boolean DEFAULT NULL
);

GRANT ALL PRIVILEGES ON DATABASE chooseit TO chooseit_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO chooseit_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO chooseit_user;
