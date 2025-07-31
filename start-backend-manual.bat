@echo off
echo ========================================
echo  AgriBazaar Backend Server Launcher
echo ========================================
echo.

REM Set the project directory
set "PROJECT_DIR=%~dp0agriBazaar-backend"
cd /d "%PROJECT_DIR%"

echo Current directory: %CD%
echo.

REM Check prerequisites
echo [1/4] Checking prerequisites...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    goto :error
)
echo ✅ Java is available

netstat -an | findstr :3306 >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ MySQL is not running on port 3306
    echo Please start MySQL service
    goto :error
)
echo ✅ MySQL is running

REM Check if port 8080 is free
netstat -an | findstr :8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo ⚠️  Port 8080 is already in use
    echo Please stop any application using port 8080
    goto :error
)
echo ✅ Port 8080 is available

echo.
echo [2/4] Compiling the application...

REM Try to compile using javac directly
if not exist "target\classes" mkdir "target\classes"

REM Use Windows findstr and for loop to compile Java files
echo Compiling Java source files...
for /r src\main\java %%f in (*.java) do (
    echo Compiling %%~nxf...
    javac -cp "target\classes;src\main\resources" -d "target\classes" "%%f" 2>nul
)

if exist "target\classes\com\agriBazaar\backend\AgriBazaarBackendApplication.class" (
    echo ✅ Compilation successful
) else (
    echo ❌ Compilation failed. Trying Maven wrapper one more time...
    echo.
    
    REM Final attempt with explicit call to mvnw in CMD mode
    echo [3/4] Attempting Maven compilation...
    cmd /c "mvnw.cmd compile" 2>nul
    
    if not exist "target\classes\com\agriBazaar\backend\AgriBazaarBackendApplication.class" (
        echo ❌ All compilation attempts failed
        echo.
        echo Suggestions:
        echo 1. Open project in IntelliJ IDEA and run AgriBazaarBackendApplication.java
        echo 2. Install Maven globally and use: mvn spring-boot:run
        echo 3. Move project to path without special characters
        goto :error
    )
)

echo.
echo [4/4] Starting Spring Boot application...
echo.
echo 🚀 Starting AgriBazaar Backend Server...
echo    This may take 30-60 seconds...
echo    Server will be available at: http://localhost:8080
echo.

REM Set classpath and run the application
set "CLASSPATH=target\classes;src\main\resources"

REM Add Maven dependencies to classpath (basic ones)
if exist "target\lib" (
    for %%j in (target\lib\*.jar) do set "CLASSPATH=!CLASSPATH!;%%j"
)

java -cp "%CLASSPATH%" com.agriBazaar.backend.AgriBazaarBackendApplication

goto :end

:error
echo.
echo ❌ Setup failed. Please check the issues above.
echo.
pause
exit /b 1

:end
echo.
echo Backend server stopped.
pause
