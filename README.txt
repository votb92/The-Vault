Author: Thinh B. Vo
This is my personal password manager system, use it cautiously, I am not responsible 

Requirements:
- JAVA
- mySQL server (recommend MAMP)
- An USB to hold a key

step : Connect to mySQL server:
-acquire port, username, password

step : Create a database and name it "thevault"

step : Paste the following code into SQL tab and click go:

********************start of code********************don't copy this line ******************************

 
CREATE TABLE `thevault`.`accounts` ( `account_id` INT(4) NOT NULL AUTO_INCREMENT ,
				`domain` VARCHAR(255) NOT NULL , `username` VARCHAR(255) NOT NULL , 
				`password` VARCHAR(255) NOT NULL , `email` VARCHAR(255) NULL , 
				`misc` TEXT NULL , PRIMARY KEY (`account_id`)) ENGINE = InnoDB;
				
ALTER TABLE `thevault`.`accounts` ADD UNIQUE KEY (domain, username);

********************end of code********************don't copy this line ******************************
