# The-Vault

Author: Thinh B. Vo


Version: 1.1


Date published: 03/26/2021


Hosted at: https://github.com/votb92/The-Vault 


Source codes can be found in TheVault directory or TheVault.zip


# DISCLAIMER:
 *This program is free software. It comes without any warranty, to the extent permitted by applicable law. You can redistribute it and/or modify it. The author will not be responsible for any damage cause by using this software.*

Requirements:
- installed JAVA
- installed mySQL server and running (recommend MAMP)
- An USB to hold a key

step 1: Connect to mySQL server:
-acquire port, username, password

step 2: Create a database and name it "thevault"

step 3: Paste the following code into SQL tab and click go:

------------------------------start of code-----------------------------don't copy this line-------------------------------

 
CREATE TABLE `thevault`.`accounts` ( `account_id` INT(4) NOT NULL AUTO_INCREMENT ,
				`domain` VARCHAR(255) NOT NULL , `username` VARCHAR(255) NOT NULL , 
				`password` VARCHAR(255) NOT NULL , `email` VARCHAR(255) NULL , 
				`misc` TEXT NULL , PRIMARY KEY (`account_id`)) ENGINE = InnoDB;
				
ALTER TABLE `thevault`.`accounts` ADD UNIQUE KEY (domain, username);

-------------------------------end of code-------------------------------don't copy this line-------------------------------

step 4: run launch.bat

step 5: insert your usb before enter 1 to create a new key

step 6: enter the letter of your usb drive, your mySQL host and port, mySQL username, and mySQL password or hit enter if your configuration looks like this:

localhost:3306
username: root
pasword: root 

step 7: a key will be created in your usb-drive, this key will contain an encrypting key to read your password, along with your mySQL configuration.
DO NOT LOSE YOUR KEY, YOUR KEY WAS RANDOMLY GENERATED, I CAN'T HELP YOU RETRIEVE YOUR PASSWORDS WITHOUT THE KEY. You can create a copy of your key by copy the FOLDER: "vault" 
that was created in your usb-drive. DO NOT MOVE THE KEY!

step 8: you will only be logged in with your usb (that holds the key) inserted, please eject your usb and store it somewhere safe when it is not used. 

step 9 (optional): BACK UP YOUR DATABASE MONTHLY! 

# Update Log:

v1.1
- added icon
- added executable file
- added change key at option 6




