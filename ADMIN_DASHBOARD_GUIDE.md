# AgriBazaar Admin Dashboard - Setup Guide

## 🎯 Overview

This admin dashboard provides a complete management interface for the AgriBazaar e-commerce platform. It allows administrators to manage products, track orders, and handle user accounts through an intuitive React-based interface.

## 🚀 Quick Start

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn
- AgriBazaar backend server running

### Option 1: Automated Setup (Windows)
1. Run the setup script:
   ```bash
   setup-admin-dashboard.bat
   ```

### Option 2: Manual Setup
1. Navigate to the admin dashboard directory:
   ```bash
   cd admin-dashboard
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Open your browser and go to `http://localhost:3000`

## 📋 Features Implemented

### Dashboard Overview
- **Statistics Cards**: Display total products, users, orders, and estimated revenue
- **Recent Orders**: Show the latest 5 orders with quick status overview
- **Quick Actions**: Direct navigation buttons to main sections

### Products Management
- ✅ **View Products**: Complete product listing with search functionality
- ✅ **Add Products**: Create new products with name, description, price, and stock
- ✅ **Edit Products**: Update existing product information
- ✅ **Delete Products**: Remove products with confirmation dialog
- ✅ **Search & Filter**: Find products by name or description
- ✅ **Stock Indicators**: Visual indicators for stock levels (Good/Low/Out)

### Orders Management
- ✅ **View Orders**: Complete order listing with search and filter
- ✅ **Status Management**: Update order status (Pending/Shipped/Delivered)
- ✅ **Search Orders**: Find orders by ID or date
- ✅ **Filter by Status**: View orders by their current status
- ✅ **Order Details**: Display order date, amount, and status

### Users Management
- ✅ **View Users**: Complete user listing with role and status
- ✅ **Add Users**: Create new user accounts with different roles
- ✅ **Edit Users**: Update user information
- ✅ **Block/Unblock**: Manage user account status
- ✅ **Delete Users**: Remove user accounts with confirmation
- ✅ **Role Management**: Handle Buyer, Farmer, and Admin roles
- ✅ **Search & Filter**: Find users by name, email, or role

## 🎨 Design Features

### Responsive Layout
- **Mobile-First Design**: Optimized for all screen sizes
- **Collapsible Sidebar**: Space-efficient navigation on mobile devices
- **Touch-Friendly**: Designed for both desktop and mobile interaction

### User Experience
- **Loading States**: Visual feedback during API operations
- **Error Handling**: User-friendly error messages
- **Confirmation Dialogs**: Prevent accidental deletions
- **Search & Filter**: Easy data discovery across all sections
- **Modal Dialogs**: Clean interface for adding/editing items

### Visual Design
- **Modern Interface**: Clean and professional appearance
- **Status Badges**: Color-coded indicators for different states
- **Consistent Styling**: Uniform design language throughout
- **Accessibility**: Proper contrast and navigation support

## 🔧 Technical Implementation

### Frontend Technologies
- **React 18**: Modern React with hooks and functional components
- **React Router**: Client-side routing for single-page application
- **Axios**: HTTP client for API communication
- **Custom CSS**: Tailored styling without external dependencies

### API Integration
- **RESTful APIs**: Full integration with backend endpoints
- **Error Handling**: Robust error management and user feedback
- **Optimistic Updates**: Immediate UI updates for better performance
- **Data Validation**: Form validation with error messaging

### Project Structure
```
admin-dashboard/
├── public/
│   ├── index.html
│   └── manifest.json
└── src/
    ├── components/
    │   └── Layout/
    ├── pages/
    │   ├── Dashboard/
    │   ├── Products/
    │   ├── Orders/
    │   └── Users/
    ├── services/
    │   └── api.js
    └── App.js
```

## 🔗 Backend Integration

### Required API Endpoints
The dashboard integrates with these backend endpoints:

#### Products
- `GET /api/products` - Fetch all products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

#### Users
- `GET /api/users` - Fetch all users
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Orders
- `GET /api/orders` - Fetch all orders
- `POST /api/orders/place` - Create new order

### Entity Updates Made
Updated backend entities to include missing getter methods:
- Added `getId()` method to User, Product, and Order entities
- Added getter methods for Order entity fields

## 🎯 Usage Instructions

### For Administrators

1. **Dashboard**: Start here to see platform overview and statistics
2. **Products**: Manage your product catalog
   - Add new agricultural products
   - Update prices and stock levels
   - Remove discontinued items
3. **Orders**: Track customer orders
   - Monitor order status
   - Update shipping information
   - Manage order fulfillment
4. **Users**: Handle user accounts
   - Review user registrations
   - Manage user roles and permissions
   - Handle account issues

### Navigation
- Use the sidebar menu to switch between sections
- Search bars are available in all data sections
- Filter options help narrow down results
- Action buttons provide quick access to common tasks

## 🔄 Development Workflow

### Making Changes
1. Edit files in the `src/` directory
2. Changes will be reflected immediately in development mode
3. Test functionality with the backend API
4. Build for production when ready

### Building for Production
```bash
npm run build
```

### Testing
- Test all CRUD operations
- Verify responsive design on different screen sizes
- Check error handling scenarios
- Validate form submissions

## 🤝 Contributing to GSSoC'25

This admin dashboard was built as part of GSSoC'25 (GirlScript Summer of Code). Contributions are welcome!

### How to Contribute
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Areas for Enhancement
- Add data export functionality
- Implement advanced filtering options
- Add analytics and reporting features
- Include notification system
- Add image upload for products

## 📞 Support

If you encounter any issues:
1. Check the console for error messages
2. Ensure the backend server is running
3. Verify API endpoints are accessible
4. Check network connectivity

## 🎉 Conclusion

This admin dashboard provides a comprehensive solution for managing the AgriBazaar platform. It combines modern React development practices with practical business requirements to deliver a professional administrative interface.

The dashboard is ready for production use and provides all the essential features needed to manage an agricultural e-commerce platform effectively.
