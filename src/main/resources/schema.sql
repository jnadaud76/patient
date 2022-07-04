CREATE TABLE IF NOT EXISTS PATIENT(
ID INT NOT NULL AUTO_INCREMENT,
FIRSTNAME VARCHAR(100) NOT NULL,
LASTNAME VARCHAR(100) NOT NULL,
DATE_OF_BIRTH DATE NOT NULL,
GENDER VARCHAR(1) NOT NULL,
ADDRESS VARCHAR(300) NOT NULL,
PHONE_NUMBER VARCHAR(20) NOT NULL,
PRIMARY KEY(ID)
)ENGINE=InnoDB;