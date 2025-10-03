@echo off
cls
echo ==========================================
echo     Payroll System Setup Script
echo ==========================================
echo.

REM Check Java installation
echo 1. Checking Java installation...
java -version >nul 2>&1
if %errorlevel% == 0 (
    echo    ✓ Java found
) else (
    echo    ✗ Java not found. Please install Java 8 or higher
    echo      Download from: https://adoptopenjdk.net/
    pause
    exit /b 1
)

REM Create directories
echo 2. Creating directory structure...
if not exist "build\classes" mkdir build\classes
if not exist "dist\lib" mkdir dist\lib
if not exist "logs" mkdir logs
if not exist "lib" mkdir lib
echo    ✓ Directories created

REM Check configuration
echo 3. Checking configuration...
if exist "config\database.properties" (
    echo    ✓ Configuration file found
) else (
    echo    ✗ Configuration file not found!
    echo      Please ensure config\database.properties exists
    pause
    exit /b 1
)

REM Compile Java files
echo 4. Compiling Java source files...
set CLASSPATH=.;lib\*
javac -cp "%CLASSPATH%" -d build\classes src\*.java
if %errorlevel% == 0 (
    echo    ✓ Compilation successful
) else (
    echo    ✗ Compilation failed
    pause
    exit /b 1
)

REM Create JAR file
echo 5. Creating executable JAR...
cd build\classes
jar -cfm ..\..\dist\PayrollSystem.jar ..\..\manifest.mf *.class ..\..\src\icon
cd ..\..

if exist "dist\PayrollSystem.jar" (
    echo    ✓ JAR file created: dist\PayrollSystem.jar
) else (
    echo    ⚠ JAR creation may have failed
)

echo.
echo ==========================================
echo     Setup Complete!
echo ==========================================
echo.
echo To run the application:
echo   java -cp ".;lib\*;dist\PayrollSystem.jar" Splash
echo.
echo Default login credentials:
echo   Username: admin
echo   Password: admin123
echo.
echo Make sure to:
echo   1. Configure your database connection in config\database.properties
echo   2. Run the database setup scripts in the database\ directory
echo   3. Install MySQL and start the service
echo   4. Download MySQL Connector J and place it in the lib\ directory
echo.
pause