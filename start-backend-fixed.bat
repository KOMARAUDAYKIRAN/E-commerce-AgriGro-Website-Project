@echo off
setlocal enabledelayedexpansion
echo Starting AgriBazaar Backend Server...
echo.

REM Get the short path name to avoid issues with single quotes
for %%I in ("%~dp0agriBazaar-backend") do set "BACKEND_DIR=%%~sI"

REM Navigate to the backend directory
cd /d "%BACKEND_DIR%"

REM Check if we're in the right directory
if not exist "pom.xml" (
    echo Error: pom.xml not found. Please check the path.
    echo Current directory: %CD%
    pause
    exit /b 1
)

echo Found pom.xml in: %CD%
echo Starting Spring Boot application...
echo This may take a few moments...
echo.

REM Try different approaches to start the application
echo Attempting to start with Maven wrapper...

REM First, try with cmd to avoid PowerShell issues
cmd /c "mvnw.cmd spring-boot:run"
if %ERRORLEVEL% EQU 0 goto :success

echo.
echo Maven wrapper failed. Trying direct Maven approach...

REM Try with maven directly if available
mvn spring-boot:run
if %ERRORLEVEL% EQU 0 goto :success

echo.
echo Direct Maven failed. Trying alternative approach...

REM If that fails, try with explicit Java execution
if exist "target\classes" (
    echo Found compiled classes, starting with java -jar...
    for %%f in (target\*.jar) do (
        echo Starting %%f
        java -jar "%%f"
        goto :success
    )
)

echo.
echo No JAR found. Trying to compile and run with Maven...
cmd /c "mvnw.cmd clean compile spring-boot:run"
if %ERRORLEVEL% EQU 0 goto :success

echo.
echo Trying direct Java execution...
set CLASSPATH=target\classes;%CLASSPATH%
for /f "delims=" %%i in ('dir /b /s target\lib\*.jar 2^>nul') do set CLASSPATH=%%i;!CLASSPATH!
java -cp "%CLASSPATH%" com.agriBazaar.backend.AgriBazaarBackendApplication
if %ERRORLEVEL% EQU 0 goto :success

echo.
echo All startup methods failed. Please check:
echo 1. Java is installed (java -version)
echo 2. MySQL is running on port 3306
echo 3. Network connectivity
echo.
echo You can also try opening this project in IntelliJ IDEA
echo and running AgriBazaarBackendApplication.java directly.
goto :end

:success
echo.
echo Backend started successfully!

:end
pause
