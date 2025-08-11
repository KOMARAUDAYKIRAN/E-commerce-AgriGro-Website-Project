@echo off
title AgriBazaar Admin Dashboard Setup
color 0A

echo.
echo ===============================================
echo    🌾 AgriBazaar Admin Dashboard Setup 🌾
echo ===============================================
echo.

echo [1/5] Checking prerequisites...
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

:: Check Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed or not in PATH
    echo    Please install Node.js 14+ and try again
    pause
    exit /b 1
) else (
    echo ✅ Node.js is installed
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
echo [2/5] Installing frontend dependencies...
cd /d "%~dp0admin-dashboard"
call npm install
if %errorlevel% neq 0 (
    echo ❌ Failed to install dependencies
    pause
    exit /b 1
)
echo ✅ Dependencies installed

echo.
echo [3/5] Starting backend server...
echo    This will take a moment...
cd /d "%~dp0agriBazaar-backend"
start "Backend Server" cmd /k "echo Starting backend server... && .\mvnw.cmd spring-boot:run"

echo.
echo [4/5] Waiting for backend to start...
timeout /t 30 /nobreak
echo.

echo [5/5] Testing backend connectivity...
cd /d "%~dp0admin-dashboard"
node test-api.js

echo.
echo ===============================================
echo               🎉 Setup Complete! 🎉
echo ===============================================
echo.
echo Next steps:
echo 1. Backend server is running in a separate window
echo 2. To start the admin dashboard, run:
echo.
echo    cd admin-dashboard
echo    npm start
echo.
echo 3. Then open: http://localhost:3000
echo.
echo If you see errors above, check the TROUBLESHOOTING_GUIDE.md
echo.
pause
