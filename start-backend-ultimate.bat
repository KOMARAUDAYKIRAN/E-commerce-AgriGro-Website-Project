@echo off
setlocal enabledelayedexpansion
echo.
echo ================================
echo  AgriBazaar Backend Server
echo ================================
echo.

REM Force CMD mode to avoid PowerShell issues
if /I "%COMSPEC%"=="%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" (
    echo Detected PowerShell. Switching to CMD...
    %SystemRoot%\System32\cmd.exe /c "%~f0" %*
    exit /b
)

REM Get current directory
set "PROJECT_DIR=%~dp0"
set "BACKEND_DIR=%PROJECT_DIR%agriBazaar-backend"

echo Project Directory: %PROJECT_DIR%
echo Backend Directory: %BACKEND_DIR%
echo.

REM Navigate to backend directory
cd /d "%BACKEND_DIR%"

REM Verify we're in the right place
if not exist "pom.xml" (
    echo ERROR: pom.xml not found in %CD%
    echo Please verify the agriBazaar-backend folder exists
    pause
    exit /b 1
)

echo Found pom.xml in: %CD%
echo.

REM Check Java installation
echo Checking Java installation...
java -version 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 11 or higher
    pause
    exit /b 1
)
echo Java is available.
echo.

REM Method 1: Try to compile first
echo Step 1: Compiling with Maven wrapper...
if exist "mvnw.cmd" (
    echo Running: mvnw.cmd clean compile
    mvnw.cmd clean compile
    if !ERRORLEVEL! EQU 0 (
        echo Compilation successful. Starting application...
        mvnw.cmd spring-boot:run
        if !ERRORLEVEL! EQU 0 goto :success
    )
)

echo.
echo Step 2: Looking for existing JAR files...
if exist "target\*.jar" (
    for %%f in ("target\*.jar") do (
        echo Found JAR: %%f
        echo Starting application...
        java -jar "%%f"
        if !ERRORLEVEL! EQU 0 goto :success
    )
)

echo.
echo Step 3: Manual compilation approach...
echo Setting up classpath and compiling manually...

REM Create target directories
if not exist "target\classes" mkdir "target\classes"

REM Try to find Maven dependencies
set "MAVEN_REPO=%USERPROFILE%\.m2\repository"
if exist "%MAVEN_REPO%" (
    echo Found Maven repository at %MAVEN_REPO%
) else (
    echo Maven repository not found, downloading dependencies...
    if exist "mvnw.cmd" (
        mvnw.cmd dependency:resolve
    )
)

echo.
echo ================================
echo All automated methods failed.
echo.
echo MANUAL STEPS TO START BACKEND:
echo.
echo 1. Open IntelliJ IDEA or Eclipse
echo 2. Import this project: %BACKEND_DIR%
echo 3. Run: AgriBazaarBackendApplication.java
echo.
echo OR use VS Code:
echo 1. Install Java Extension Pack
echo 2. Open backend folder in VS Code
echo 3. Press F5 to run
echo.
echo OR install Maven globally:
echo 1. Download Maven from https://maven.apache.org
echo 2. Add to PATH
echo 3. Run: mvn spring-boot:run
echo ================================
goto :end

:success
echo.
echo ================================
echo   Backend Started Successfully!
echo   Server running on: http://localhost:8080
echo   API Base URL: http://localhost:8080/api
echo ================================
echo.
echo The backend is now ready for your React admin dashboard!
echo Keep this window open to maintain the server.

:end
echo.
pause
