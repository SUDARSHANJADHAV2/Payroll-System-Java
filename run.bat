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

REM Create necessary directories
if not exist "build\classes" mkdir build\classes
if not exist "data" mkdir data

REM Compile if needed
if not exist "build\classes\Splash.class" (
    echo Compiling source code...
    javac -cp "lib/*" -d build/classes src/*.java
    if %errorlevel% neq 0 (
        echo Error: Compilation failed
        pause
        exit /b 1
    )
)

REM Initialize database if needed
if not exist "data\payrolldb.mv.db" (
    echo Initializing database...
    java -cp "lib/*;build/classes" DatabaseInitializer
    if %errorlevel% neq 0 (
        echo Error: Failed to initialize database
        pause
        exit /b 1
    )
)

REM Ensure login credentials exist
echo Checking database...
java -cp "lib/*;build/classes" DatabaseChecker >nul 2>&1
if %errorlevel% neq 0 (
    echo Database check failed, reinitializing...
    java -cp "lib/*;build/classes" DatabaseInitializer
)

REM Set classpath including H2 database
set CLASSPATH=.;lib/*;build/classes

REM Start the application
echo Login credentials: admin/admin123 or hr/hr123
echo.
java -cp "%CLASSPATH%" Splash

echo Application closed.
pause