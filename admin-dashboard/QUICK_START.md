# 🌾 AgriBazaar Admin Dashboard - Quick Start

## 🚀 Super Quick Setup (Recommended)

1. **Run the automated setup:**
   ```bash
   setup-complete.bat
   ```

2. **Start the dashboard:**
   ```bash
   start-dashboard.bat
   ```

3. **Open in browser:**
   ```
   http://localhost:3000
   ```

## 📋 What You Get

- **Dashboard Overview**: Statistics and recent orders
- **Products Management**: Add, edit, delete products
- **Orders Management**: Track and update order status
- **Users Management**: Handle user accounts and roles

## ❗ If You See "Failed to load data"

This means the backend server is not running. Follow these steps:

### Option 1: Automated Fix
```bash
setup-complete.bat
```

### Option 2: Manual Fix
1. **Start backend server:**
   ```bash
   cd agriBazaar-backend
   .\mvnw.cmd spring-boot:run
   ```

2. **Wait for this message:**
   ```
   Started AgriBazaarBackendApplication
   ```

3. **Start frontend:**
   ```bash
   cd admin-dashboard
   npm start
   ```

## 🔧 Requirements

- ✅ Java 11+
- ✅ Node.js 14+
- ✅ MySQL running on port 3306

## 📚 Need More Help?

Check the detailed guides:
- `TROUBLESHOOTING_GUIDE.md` - Complete troubleshooting steps
- `ADMIN_DASHBOARD_GUIDE.md` - Full feature documentation

## 🎯 Quick Test

To verify everything is working:
```bash
cd admin-dashboard
node test-api.js
```

You should see:
```
✅ Products: 200 - X items
✅ Users: 200 - X items
✅ Orders: 200 - X items
```

## 🎉 Success!

When working correctly, you'll see:
- Statistics on the dashboard
- Products, orders, and users loading
- No error messages in browser console

Happy managing! 🚀
