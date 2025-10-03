@echo off
cls
echo Starting Payroll Management System...
echo ======================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java not found. Please install Java 8 or higher.
    pause
    exit /b 1
)

REM Check if JAR file exists
if not exist "dist\PayrollSystem.jar" (
    echo Error: PayrollSystem.jar not found.
    echo Please run setup.bat first to compile the application.
    pause
    exit /b 1
)

REM Set classpath including MySQL connector
set CLASSPATH=.;lib\*;dist\PayrollSystem.jar

REM Start the application
java -cp "%CLASSPATH%" Splash

echo Application closed.
pause