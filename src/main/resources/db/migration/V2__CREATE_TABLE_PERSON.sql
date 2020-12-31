CREATE TABLE PERSON(
	ID BIGINT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	SOCIALRASON VARCHAR(150),
	FIRSTNAME VARCHAR(50),
	LASTNAME VARCHAR(50),
	NICKNAME VARCHAR(15),
	AGE INT(3),
	DOCUMENT VARCHAR(20),
	PERSON_TYPE VARCHAR(20),
	PERSON_USER_KEY VARCHAR(150),
	CREATED_DATE DATE,
	UPDATED_DATE DATE,
	ADDRESS_ID INT(10),
    CONSTRAINT FK_ADDRESS_PERSON FOREIGN KEY (ADDRESS_ID)
    REFERENCES ADDRESS(ID)
);