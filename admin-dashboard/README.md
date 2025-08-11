# AgriBazaar Admin Dashboard

A modern React-based admin dashboard for managing the AgriBazaar e-commerce platform.

## Features

### 🏠 Dashboard
- Overview statistics (Total Products, Users, Orders, Revenue)
- Recent orders display
- Quick action buttons for easy navigation

### 🌾 Products Management
- View all products in a structured table
- Add new products with name, description, price, and stock
- Edit existing product information
- Delete products with confirmation
- Search products by name or description
- Stock level indicators (Good/Low/Out of Stock)

### 📦 Orders Management
- View all customer orders
- Track order status (Pending, Shipped, Delivered)
- Update order status directly from the interface
- Search orders by ID or date
- Filter orders by status

### 👥 Users Management
- View all registered users
- Add new users with different roles (Buyer, Farmer, Admin)
- Edit user information
- Block/Unblock user accounts
- Delete users with confirmation
- Search users by name or email
- Filter users by role

## Tech Stack

- **React 18** - Frontend framework
- **React Router DOM** - Client-side routing
- **Axios** - HTTP client for API calls
- **CSS3** - Styling with custom CSS (no external frameworks for better performance)
- **Responsive Design** - Mobile-first approach

## Getting Started

### Prerequisites

- Node.js (v14 or higher)
- npm or yarn
- AgriBazaar backend server running on `http://localhost:8080`

### Installation

1. Navigate to the admin-dashboard directory:
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

4. Open your browser and navigate to `http://localhost:3000`

### Build for Production

```bash
npm run build
```

## API Integration

The dashboard integrates with the AgriBazaar backend API endpoints:

- **Products**: `/api/products`
- **Users**: `/api/users`
- **Orders**: `/api/orders`

The API base URL is configured in `src/services/api.js` and defaults to `http://localhost:8080/api`.

## Project Structure

```
src/
├── components/
│   └── Layout/
│       ├── Layout.js          # Main layout with sidebar navigation
│       └── Layout.css         # Layout styling
├── pages/
│   ├── Dashboard/
│   │   ├── Dashboard.js       # Dashboard overview page
│   │   └── Dashboard.css      # Dashboard styling
│   ├── Products/
│   │   ├── Products.js        # Products management page
│   │   ├── ProductModal.js    # Add/Edit product modal
│   │   └── Products.css       # Products styling
│   ├── Orders/
│   │   ├── Orders.js          # Orders management page
│   │   └── Orders.css         # Orders styling
│   └── Users/
│       ├── Users.js           # Users management page
│       ├── UserModal.js       # Add/Edit user modal
│       └── Users.css          # Users styling
├── services/
│   └── api.js                 # API service functions
├── App.js                     # Main app component
├── App.css                    # Global app styling
├── index.js                   # App entry point
└── index.css                  # Global CSS styles
```

## Features in Detail

### Responsive Design
- Mobile-first approach with collapsible sidebar
- Touch-friendly interface elements
- Adaptive layouts for different screen sizes

### User Experience
- Loading states for all API operations
- Error handling with user-friendly messages
- Confirmation dialogs for destructive actions
- Search and filter functionality across all sections

### Data Management
- Real-time data updates
- Optimistic UI updates for better performance
- Form validation with error messages
- Modal dialogs for adding/editing items

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is part of the AgriBazaar e-commerce platform and follows the same MIT license.
