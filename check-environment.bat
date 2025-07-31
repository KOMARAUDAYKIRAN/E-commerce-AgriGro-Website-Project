@echo off
echo.
echo ============================================
echo          Quick Backend Health Check
echo ============================================
echo.

REM Check Java
echo 1. Checking Java installation...
java -version 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java not found. Please install Java JDK 11+
) else (
    echo ✅ Java is installed
)

echo.
echo 2. Checking MySQL service...
sc query mysql 2>nul | find "RUNNING" >nul
if %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL service is running
) else (
    echo ❌ MySQL service not running
    echo Starting MySQL service...
    net start mysql 2>nul
    if %ERRORLEVEL% EQU 0 (
        echo ✅ MySQL service started
    ) else (
        echo ❌ Could not start MySQL. Please start it manually
        echo    - Check MySQL is installed
        echo    - Start MySQL from Services or XAMPP
    )
)

echo.
echo 3. Testing MySQL connection...
echo quit | mysql -u root -p 2>nul
if %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL connection successful
) else (
    echo ⚠️  MySQL connection test failed (this might be normal if password is required)
)

echo.
echo 4. Checking backend project structure...
cd /d "%~dp0agriBazaar-backend" 2>nul
if exist "pom.xml" (
    echo ✅ Found pom.xml
    if exist "src\main\java\com\agriBazaar\backend\AgriBazaarBackendApplication.java" (
        echo ✅ Found main application class
        echo.
        echo ============================================
        echo Ready to start backend! 
        echo Run: start-backend-ultimate.bat
        echo ============================================
    ) else (
        echo ❌ Main application class not found
    )
) else (
    echo ❌ pom.xml not found in agriBazaar-backend directory
)

echo.
pause
