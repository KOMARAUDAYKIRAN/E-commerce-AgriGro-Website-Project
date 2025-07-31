import React, { useState, useEffect } from 'react';
import { productAPI, userAPI, orderAPI } from '../../services/api';
import './Dashboard.css';

const Dashboard = () => {
  const [stats, setStats] = useState({
    totalProducts: 0,
    totalUsers: 0,
    totalOrders: 0,
    recentOrders: []
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      setLoading(true);
      const [productsResponse, usersResponse, ordersResponse] = await Promise.all([
        productAPI.getAllProducts(),
        userAPI.getAllUsers(),
        orderAPI.getAllOrders()
      ]);

      const products = productsResponse.data;
      const users = usersResponse.data;
      const orders = ordersResponse.data;

      setStats({
        totalProducts: products.length,
        totalUsers: users.length,
        totalOrders: orders.length,
        recentOrders: orders.slice(-5).reverse() // Get last 5 orders
      });
    } catch (err) {
      console.error('Error fetching dashboard data:', err);
      setError('Failed to load dashboard data');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading dashboard...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="dashboard">
      <div className="page-header">
        <h1 className="page-title">Dashboard</h1>
        <p className="page-subtitle">Overview of your AgriBazaar platform</p>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-number">{stats.totalProducts}</div>
          <div className="stat-label">Total Products</div>
        </div>
        <div className="stat-card">
          <div className="stat-number">{stats.totalUsers}</div>
          <div className="stat-label">Total Users</div>
        </div>
        <div className="stat-card">
          <div className="stat-number">{stats.totalOrders}</div>
          <div className="stat-label">Total Orders</div>
        </div>
        <div className="stat-card">
          <div className="stat-number">₹{stats.totalOrders * 150}</div>
          <div className="stat-label">Revenue (Est.)</div>
        </div>
      </div>

      <div className="dashboard-content">
        <div className="recent-orders">
          <div className="card">
            <div className="card-header">
              <h2 className="card-title">Recent Orders</h2>
            </div>
            {stats.recentOrders.length > 0 ? (
              <div className="table-container">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Order ID</th>
                      <th>Date</th>
                      <th>Amount</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {stats.recentOrders.map((order, index) => (
                      <tr key={index}>
                        <td>#{index + 1}</td>
                        <td>{new Date().toLocaleDateString()}</td>
                        <td>₹{order.totalAmount || 0}</td>
                        <td>
                          <span className="status-badge status-pending">Pending</span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <div className="empty-state">
                <h3>No recent orders</h3>
                <p>Orders will appear here once customers start placing them.</p>
              </div>
            )}
          </div>
        </div>

        <div className="quick-actions">
          <div className="card">
            <div className="card-header">
              <h2 className="card-title">Quick Actions</h2>
            </div>
            <div className="action-grid">
              <button className="action-btn" onClick={() => window.location.href = '/products'}>
                <span className="action-icon">🌾</span>
                <span>Manage Products</span>
              </button>
              <button className="action-btn" onClick={() => window.location.href = '/orders'}>
                <span className="action-icon">📦</span>
                <span>View Orders</span>
              </button>
              <button className="action-btn" onClick={() => window.location.href = '/users'}>
                <span className="action-icon">👥</span>
                <span>Manage Users</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
