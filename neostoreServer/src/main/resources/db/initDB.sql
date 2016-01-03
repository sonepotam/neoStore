DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS pets;
drop TABLE if EXISTS breeds;
DROP SEQUENCE IF EXISTS neostore_seq CASCADE ;

create sequence neostore_seq start with   1;

create table users(
  id         INTEGER PRIMARY KEY DEFAULT nextval('neostore_seq'),
  username   VARCHAR NOT NULL,
  password   varchar not null,
  lastmodified TIMESTAMP,
  attempts     INTEGER
);

CREATE UNIQUE INDEX us_unique_name ON users(username);

create table breeds(
id integer primary key default nextval( 'neostore_seq'),
name varchar
);

create UNIQUE INDEX br_unique_name on breeds(name);


create table pets(
  id         INTEGER PRIMARY KEY DEFAULT nextval('neostore_seq'),
  name       VARCHAR ,
  breed_id   integer,
  birthdate  DATE,
  sex        VARCHAR,
  user_id   INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (breed_id) REFERENCES breeds(id)
);

create UNIQUE INDEX pt_unique_name on pets( name, user_id);