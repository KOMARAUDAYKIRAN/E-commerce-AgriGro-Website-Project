@echo off
echo Starting AgriBazaar Admin Dashboard in Demo Mode...
echo Demo mode uses mock data and doesn't require backend server
echo.

cd /d "%~dp0"
set NODE_OPTIONS=--max-old-space-size=4096
npm start

pause
