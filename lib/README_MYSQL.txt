MySQL Connector/J Required
==========================

This directory should contain the MySQL JDBC driver (MySQL Connector/J).

Download Instructions:
1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Download the latest version (8.0.33 or newer recommended)
3. Extract the JAR file (mysql-connector-java-8.0.33.jar or similar)
4. Place it in this lib/ directory

Alternative: Run the setup script (setup.sh or setup.bat) which will attempt to download it automatically.

The JAR filename should match the Class-Path entry in manifest.mf
Current expected filename: mysql-connector-java-8.0.33.jar

Without this driver, the application will not be able to connect to the MySQL database.