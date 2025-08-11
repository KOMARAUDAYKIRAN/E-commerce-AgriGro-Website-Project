@echo off
echo Setting up AgriBazaar Admin Dashboard...
echo.

echo Installing Node.js dependencies...
cd admin-dashboard
call npm install

if %errorlevel% equ 0 (
    echo.
    echo ✅ Installation completed successfully!
    echo.
    echo To start the admin dashboard:
    echo 1. Make sure the backend server is running on http://localhost:8080
    echo 2. Run: npm start
    echo 3. Open http://localhost:3000 in your browser
    echo.
    echo Enjoy your new admin dashboard! 🎉
) else (
    echo.
    echo ❌ Installation failed. Please check the error messages above.
)

pause
