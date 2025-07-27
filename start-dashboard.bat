@echo off
title AgriBazaar Admin Dashboard
color 0B

echo.
echo ===============================================
echo    🌾 Starting AgriBazaar Admin Dashboard 🌾
echo ===============================================
echo.

echo Checking if backend is running...
timeout /t 2 /nobreak >nul

curl -s http://localhost:8080/api/products >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ⚠️  WARNING: Backend server doesn't seem to be running!
    echo.
    echo Please make sure to start the backend server first:
    echo 1. Run: setup-complete.bat
    echo 2. Or manually start: agriBazaar-backend\mvnw.cmd spring-boot:run
    echo.
    echo Press any key to continue anyway, or Ctrl+C to cancel...
    pause >nul
)

echo.
echo Starting admin dashboard...
echo.
echo 📋 Dashboard will open at: http://localhost:3000
echo 🔧 Backend should be running at: http://localhost:8080
echo.

cd /d "%~dp0admin-dashboard"
npm start
