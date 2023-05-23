# encryption-decryption
Demo project for java encryption and decryption

### Steps for Encryption

### Option 1
Using postman: (POST) https://localhost:8080/encrypt and attach source file as 
form-data with the parameter name `file`. Save the response as file and DO NOT copy the content to create the file.
File might corrupt and may not work properly during decryption.

### Option 2
Download this JAR: encryption-decryption-1.0.jar to your favorite location and run the following cmd: 
`java -jar encryption-decryption-1.0.jar encrypt <source-file>`
This will generate an encrypted file in same location.

### Steps for Decryption

### Option 1
Using postman: (POST) https://localhost:8080/decrypt and attach source file as 
form-data with the parameter name `file`. Verify the response. 

### Option 2
Download this JAR: encryption-decryption-1.0.jar to your favorite location and run the following cmd: `java -jar encryption-decryption-1.0.jar decrypt config.sql` & verify.
