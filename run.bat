@echo off
cls
echo ==========================================
echo   Payroll Management System v3.1
echo ==========================================
echo.

REM Check if Java is installed
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java not found. Please install Java 8 or higher.
    echo Download from: https://adoptopenjdk.net/
    pause
    exit /b 1
)
echo [OK] Java found

REM Create necessary directories
echo Creating directories...
if not exist "build\classes" mkdir build\classes
if not exist "data" mkdir data
if not exist "logs" mkdir logs
echo [OK] Directories created

REM Check if H2 database JAR exists
if not exist "lib\h2-2.1.214.jar" (
    echo [ERROR] H2 database JAR not found: lib\h2-2.1.214.jar
    echo Please ensure the H2 JAR file is in the lib directory.
    pause
    exit /b 1
)
echo [OK] H2 database JAR found

REM Compile if needed
if not exist "build\classes\Splash.class" (
    echo Compiling source code...
    javac -cp "lib/*" -d build/classes src/*.java
    if %errorlevel% neq 0 (
        echo [ERROR] Compilation failed
        echo Please check for syntax errors in the source code.
        pause
        exit /b 1
    )
    echo [OK] Compilation successful
) else (
    echo [OK] Classes already compiled
)

REM Initialize database if needed
if not exist "data\payrolldb.mv.db" (
    echo Initializing database...
    java -cp "lib/*;build/classes" DatabaseInitializer
    if %errorlevel% neq 0 (
        echo [ERROR] Failed to initialize database
        pause
        exit /b 1
    )
    echo [OK] Database initialized
) else (
    echo [OK] Database already exists
)

REM Ensure login credentials exist
echo Checking database integrity...
java -cp "lib/*;build/classes" DatabaseChecker >nul 2>&1
if %errorlevel% neq 0 (
    echo Database check failed, reinitializing...
    java -cp "lib/*;build/classes" DatabaseInitializer
    echo [OK] Database reinitialized
) else (
    echo [OK] Database integrity verified
)

REM Set classpath including H2 database
set CLASSPATH=.;lib/*;build/classes

REM Start the application
echo.
echo ==========================================
echo   Starting Application...
echo ==========================================
echo.
echo Default login credentials:
echo   Username: admin    Password: admin123
echo   Username: hr       Password: hr123
echo.
echo Press any key to continue...
pause >nul

java -cp "%CLASSPATH%" Splash

echo.
echo Application closed.
pause