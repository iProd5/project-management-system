SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;


SET default_tablespace = '';

SET default_with_oids = false;


---
--- drop table
---

DROP TABLE IF EXISTS Projects;


CREATE TABLE Projects(
    project_id serial primary key,
    project_name text not null,
    Project_description text not null

);


INSERT INTO Projects(project_name, project_description) values('Heroku', 'TCMs equired');

INSERT INTO Projects(project_name, project_description) values('logging service', 'logging pub/sub service');

INSERT INTO Projects(project_name, project_description) values('email service', 'email pub/sub service');

INSERT INTO Projects(project_name, project_description) values('project', 'TCMs Management App');

