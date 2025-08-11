# 🔧 AgriBazaar Admin Dashboard - Troubleshooting Guide

## ❌ Common Issue: "Failed to load data"

This usually happens when the backend server is not running or there are connectivity issues.

## 🚀 Step-by-Step Solution

### Step 1: Start the Backend Server

**Option A: Using the batch file (Recommended)**
```bash
# Run this from the project root directory
start-backend.bat
```

**Option B: Manual start**
```bash
cd agriBazaar-backend
./mvnw.cmd spring-boot:run
```

**Option C: If you have Maven installed**
```bash
cd agriBazaar-backend
mvn spring-boot:run
```

### Step 2: Verify Backend is Running

1. **Check if server started successfully:**
   - Look for "Started AgriBazaarBackendApplication" in the console
   - Server should be running on port 8080

2. **Test API endpoints manually:**
   ```bash
   # Test products endpoint
   curl http://localhost:8080/api/products
   
   # Test users endpoint
   curl http://localhost:8080/api/users
   
   # Test orders endpoint
   curl http://localhost:8080/api/orders
   ```

3. **Check database connection:**
   - Ensure MySQL is running on port 3306
   - Database 'agribazaar' should be created automatically
   - Check console for any database errors

### Step 3: Start the Admin Dashboard

```bash
cd admin-dashboard
npm install
npm start
```

### Step 4: Verify Everything Works

1. Open browser to `http://localhost:3000`
2. Check browser console (F12) for any errors
3. Navigate to different sections (Products, Orders, Users)

## 🐛 Debugging Steps

### Check Backend Logs
Look for these messages in the backend console:
```
✅ GOOD: "Started AgriBazaarBackendApplication"
✅ GOOD: "Tomcat started on port(s): 8080"
❌ BAD: "Failed to configure a DataSource"
❌ BAD: "Connection refused"
```

### Check Frontend Console
Open browser console (F12) and look for:
```
✅ GOOD: "🔄 API Request: GET /products"
✅ GOOD: "✅ API Response: 200 /products"
❌ BAD: "❌ Response Error: Network Error"
❌ BAD: "No response received. Check if backend server is running"
```

## 🔧 Common Solutions

### Problem: Backend won't start
**Solution:**
1. Check if Java is installed: `java -version`
2. Check if MySQL is running: `netstat -an | findstr :3306`
3. Verify database credentials in `application.properties`

### Problem: Frontend can't connect to backend
**Solution:**
1. Verify backend is running on port 8080
2. Check CORS configuration
3. Ensure firewall isn't blocking connections

### Problem: Database errors
**Solution:**
1. Start MySQL service
2. Create database manually: 
   ```sql
   CREATE DATABASE agribazaar;
   ```
3. Check MySQL credentials in `application.properties`

### Problem: Port already in use
**Solution:**
1. Kill process using port 8080:
   ```bash
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   ```

## 📝 Configuration Files to Check

### Backend Configuration
File: `agriBazaar-backend/src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agribazaar?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

### Frontend Configuration
File: `admin-dashboard/package.json`
```json
{
  "proxy": "http://localhost:8080"
}
```

## 🆘 Still Having Issues?

1. **Check System Requirements:**
   - Java 11+ installed
   - Node.js 14+ installed
   - MySQL 8+ running

2. **Clear Caches:**
   ```bash
   # Backend
   cd agriBazaar-backend
   ./mvnw.cmd clean
   
   # Frontend
   cd admin-dashboard
   rm -rf node_modules
   npm install
   ```

3. **Restart Everything:**
   - Stop backend server (Ctrl+C)
   - Stop MySQL and restart
   - Start backend server
   - Start frontend

## 📞 Quick Test Script

Run this to test if everything is working:

```bash
# Test backend connectivity
cd admin-dashboard
node test-api.js
```

Expected output:
```
🔍 Testing API endpoints...
✅ Products: 200 - X items
✅ Users: 200 - X items  
✅ Orders: 200 - X items
```

## 🎯 Success Indicators

When everything is working correctly, you should see:

1. **Backend Console:**
   ```
   Started AgriBazaarBackendApplication in X.X seconds
   ```

2. **Frontend Console:**
   ```
   ✅ API Response: 200 /products
   ✅ API Response: 200 /users
   ✅ API Response: 200 /orders
   ```

3. **Browser:**
   - Dashboard loads with statistics
   - Products, Orders, and Users sections show data
   - No error messages in console

## 🎉 You're All Set!

Once you see data loading in all sections, your admin dashboard is fully functional!

Remember: Always start the backend server BEFORE the frontend application.
