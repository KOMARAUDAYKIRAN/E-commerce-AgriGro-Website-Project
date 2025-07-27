import axios from 'axios';
import { mockProducts, mockUsers, mockOrders } from './mockData';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 second timeout
});

// Demo mode flag - set to true to use mock data when backend is not available
const DEMO_MODE = true;

// Helper function to simulate API delay
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

// Add request interceptor for debugging
api.interceptors.request.use(
  (config) => {
    console.log(`🔄 API Request: ${config.method?.toUpperCase()} ${config.url}`);
    return config;
  },
  (error) => {
    console.error('❌ Request Error:', error);
    return Promise.reject(error);
  }
);

// Add response interceptor for debugging
api.interceptors.response.use(
  (response) => {
    console.log(`✅ API Response: ${response.status} ${response.config.url}`);
    return response;
  },
  (error) => {
    console.error('❌ Response Error:', error.message);
    if (error.response) {
      console.error('Status:', error.response.status);
      console.error('Data:', error.response.data);
    } else if (error.request) {
      console.error('No response received. Check if backend server is running on http://localhost:8080');
    }
    return Promise.reject(error);
  }
);

// Products API
export const productAPI = {
  getAllProducts: async () => {
    if (DEMO_MODE) {
      console.log('📦 Using mock product data');
      await delay(500);
      return { data: mockProducts };
    }
    return api.get('/products');
  },
  getProductById: async (id) => {
    if (DEMO_MODE) {
      await delay(300);
      const product = mockProducts.find(p => p.id === parseInt(id));
      return { data: product };
    }
    return api.get(`/products/${id}`);
  },
  createProduct: async (product) => {
    if (DEMO_MODE) {
      await delay(500);
      const newProduct = { ...product, id: Date.now() };
      mockProducts.push(newProduct);
      console.log('✅ Product created in demo mode:', newProduct);
      return { data: newProduct };
    }
    return api.post('/products', product);
  },
  updateProduct: async (id, product) => {
    if (DEMO_MODE) {
      await delay(500);
      const index = mockProducts.findIndex(p => p.id === parseInt(id));
      if (index !== -1) {
        mockProducts[index] = { ...product, id: parseInt(id) };
        console.log('✅ Product updated in demo mode:', mockProducts[index]);
        return { data: mockProducts[index] };
      }
      throw new Error('Product not found');
    }
    return api.put(`/products/${id}`, product);
  },
  deleteProduct: async (id) => {
    if (DEMO_MODE) {
      await delay(500);
      const index = mockProducts.findIndex(p => p.id === parseInt(id));
      if (index !== -1) {
        mockProducts.splice(index, 1);
        console.log('✅ Product deleted in demo mode, ID:', id);
        return { data: { success: true } };
      }
      throw new Error('Product not found');
    }
    return api.delete(`/products/${id}`);
  },
};

// Users API
export const userAPI = {
  getAllUsers: async () => {
    if (DEMO_MODE) {
      console.log('👥 Using mock user data');
      await delay(500);
      return { data: mockUsers };
    }
    return api.get('/users');
  },
  getUserById: async (id) => {
    if (DEMO_MODE) {
      await delay(300);
      const user = mockUsers.find(u => u.id === parseInt(id));
      return { data: user };
    }
    return api.get(`/users/${id}`);
  },
  createUser: async (user) => {
    if (DEMO_MODE) {
      await delay(500);
      const newUser = { ...user, id: Date.now() };
      mockUsers.push(newUser);
      console.log('✅ User created in demo mode:', newUser);
      return { data: newUser };
    }
    return api.post('/users', user);
  },
  updateUser: async (id, user) => {
    if (DEMO_MODE) {
      await delay(500);
      const index = mockUsers.findIndex(u => u.id === parseInt(id));
      if (index !== -1) {
        mockUsers[index] = { ...user, id: parseInt(id) };
        console.log('✅ User updated in demo mode:', mockUsers[index]);
        return { data: mockUsers[index] };
      }
      throw new Error('User not found');
    }
    return api.put(`/users/${id}`, user);
  },
  deleteUser: async (id) => {
    if (DEMO_MODE) {
      await delay(500);
      const index = mockUsers.findIndex(u => u.id === parseInt(id));
      if (index !== -1) {
        mockUsers.splice(index, 1);
        console.log('✅ User deleted in demo mode, ID:', id);
        return { data: { success: true } };
      }
      throw new Error('User not found');
    }
    return api.delete(`/users/${id}`);
  },
};

// Orders API
export const orderAPI = {
  getAllOrders: async () => {
    if (DEMO_MODE) {
      console.log('📋 Using mock order data');
      await delay(500);
      return { data: mockOrders };
    }
    return api.get('/orders');
  },
  getOrderById: async (id) => {
    if (DEMO_MODE) {
      await delay(300);
      const order = mockOrders.find(o => o.id === parseInt(id));
      return { data: order };
    }
    return api.get(`/orders/${id}`);
  },
  placeOrder: async (userId, productIds) => {
    if (DEMO_MODE) {
      await delay(500);
      const newOrder = {
        id: Date.now(),
        orderDate: new Date().toISOString().split('T')[0],
        totalAmount: productIds.length * 300,
        status: 'pending',
        userId,
        products: productIds
      };
      mockOrders.push(newOrder);
      console.log('✅ Order placed in demo mode:', newOrder);
      return { data: newOrder };
    }
    return api.post(`/orders/place?userId=${userId}`, productIds);
  },
  updateOrderStatus: async (id, status) => {
    if (DEMO_MODE) {
      await delay(500);
      const index = mockOrders.findIndex(o => o.id === parseInt(id));
      if (index !== -1) {
        mockOrders[index].status = status;
        console.log('✅ Order status updated in demo mode:', mockOrders[index]);
        return { data: mockOrders[index] };
      }
      throw new Error('Order not found');
    }
    return api.put(`/orders/${id}/status`, { status });
  },
};

export default api;
