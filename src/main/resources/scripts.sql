CREATE DATABASE PARSER;

CREATE TABLE BLOCKED_IP (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  IP VARCHAR(256) NOT NULL,
  COMMENT VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID)
);


CREATE TABLE IP_REQUEST (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  DATE DATETIME NOT NULL,
  IP VARCHAR(256) NOT NULL,
  STATUS INT NOT NULL,
  REQUEST VARCHAR(256) NOT NULL,
  USER_AGENT VARCHAR(256) NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE (DATE)
);

SELECT IP, count(*) FROM IP_REQUEST where date >= '2017-01-01 00:00:12' and date <= '2017-01-01 00:56:29' group by IP having count(*) >= 100

SELECT * FROM IP_REQUEST WHERE IP = '192.168.169.194'