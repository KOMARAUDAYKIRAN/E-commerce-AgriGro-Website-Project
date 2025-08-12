@echo off
title AgriBazaar - Thymeleaf Application
color 0A

echo.
echo ===============================================
echo    🌾 AgriBazaar - Thymeleaf Application 🌾
echo ===============================================
echo.
echo Starting the modernized AgriBazaar application...
echo.

echo [1/2] Checking prerequisites...
echo.

:: Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo    Please install Java 11+ and try again
    pause
    exit /b 1
) else (
    echo ✅ Java is installed
)

:: Check MySQL
netstat -an | findstr :3306 >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ MySQL is not running on port 3306
    echo    Please start MySQL service and try again
    pause
    exit /b 1
) else (
    echo ✅ MySQL is running
)

echo.
echo [2/2] Starting the Thymeleaf application...
echo.
echo 🚀 Application will be available at:
echo    📱 Main Website: http://localhost:8083/
echo    🛒 Products: http://localhost:8083/products
echo    🔐 Login: http://localhost:8083/login
echo    📝 Register: http://localhost:8083/register
echo.
echo 📊 Admin Dashboard (React): http://localhost:3000/
echo    (Start separately with: cd admin-dashboard && npm start)
echo.
echo Press Ctrl+C to stop the application
echo.

cd /d "%~dp0agriBazaar-backend"
call .\mvnw.cmd spring-boot:run

pause