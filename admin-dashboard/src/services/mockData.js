// Mock data for demo mode when backend is not available
export const mockProducts = [
  {
    id: 1,
    name: "Organic Wheat Seeds",
    description: "High quality organic wheat seeds for sustainable farming",
    price: 250,
    stock: 100
  },
  {
    id: 2,
    name: "Bio Fertilizer",
    description: "Natural fertilizer to enhance soil fertility",
    price: 450,
    stock: 50
  },
  {
    id: 3,
    name: "Premium Rice Seeds",
    description: "Disease resistant rice seeds with high yield",
    price: 300,
    stock: 75
  },
  {
    id: 4,
    name: "Corn Seeds",
    description: "Hybrid corn seeds suitable for all seasons",
    price: 200,
    stock: 120
  },
  {
    id: 5,
    name: "Organic Pesticide",
    description: "Environment-friendly pesticide for crop protection",
    price: 350,
    stock: 30
  }
];

export const mockUsers = [
  {
    id: 1,
    name: "Farmer John",
    email: "farmer.john@example.com",
    role: "FARMER",
    status: "active"
  },
  {
    id: 2,
    name: "Alice Johnson",
    email: "alice@example.com",
    role: "BUYER",
    status: "active"
  },
  {
    id: 3,
    name: "Bob Smith",
    email: "bob@example.com",
    role: "BUYER",
    status: "blocked"
  },
  {
    id: 4,
    name: "Farmer Raju",
    email: "raju@example.com",
    role: "FARMER",
    status: "active"
  },
  {
    id: 5,
    name: "Admin User",
    email: "admin@agribazaar.com",
    role: "ADMIN",
    status: "active"
  }
];

export const mockOrders = [
  {
    id: 1,
    orderDate: "2025-07-25",
    totalAmount: 750,
    status: "pending",
    userId: 2,
    products: [1, 2]
  },
  {
    id: 2,
    orderDate: "2025-07-24",
    totalAmount: 500,
    status: "shipped",
    userId: 4,
    products: [3]
  },
  {
    id: 3,
    orderDate: "2025-07-23",
    totalAmount: 950,
    status: "delivered",
    userId: 1,
    products: [2, 4, 5]
  },
  {
    id: 4,
    orderDate: "2025-07-22",
    totalAmount: 300,
    status: "pending",
    userId: 2,
    products: [3]
  },
  {
    id: 5,
    orderDate: "2025-07-21",
    totalAmount: 200,
    status: "shipped",
    userId: 4,
    products: [4]
  }
];
