-- --------------------------------------------------------
-- Table BATCH_CURSOR
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS BATCH_CURSOR(  ID VARCHAR NOT NULL,PATH VARCHAR NOT NULL,CREATION_DATE TIMESTAMP NOT NULL,LAST_EXECUTION_DATE TIMESTAMP,PRIMARY KEY (ID), UNIQUE KEY path_UNIQUE (PATH));