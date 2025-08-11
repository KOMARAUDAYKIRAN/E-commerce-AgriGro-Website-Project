import React, { useState, useEffect } from 'react';
import { userAPI } from '../../services/api';
import UserModal from './UserModal';
import './Users.css';

const Users = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [roleFilter, setRoleFilter] = useState('all');

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await userAPI.getAllUsers();
      // Add mock status to users since the backend doesn't have status field
      const usersWithStatus = response.data.map((user, index) => ({
        ...user,
        status: index % 4 === 0 ? 'blocked' : 'active'
      }));
      setUsers(usersWithStatus);
    } catch (err) {
      console.error('Error fetching users:', err);
      setError('Failed to load users');
    } finally {
      setLoading(false);
    }
  };

  const handleAddUser = () => {
    setEditingUser(null);
    setShowModal(true);
  };

  const handleEditUser = (user) => {
    setEditingUser(user);
    setShowModal(true);
  };

  const handleDeleteUser = async (id) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await userAPI.deleteUser(id);
        setUsers(users.filter(user => user.id !== id));
      } catch (err) {
        console.error('Error deleting user:', err);
        alert('Failed to delete user');
      }
    }
  };

  const handleToggleStatus = async (userId) => {
    try {
      const user = users.find(u => u.id === userId);
      const newStatus = user.status === 'active' ? 'blocked' : 'active';
      
      setUsers(users.map(u => 
        u.id === userId ? { ...u, status: newStatus } : u
      ));
      
      // Note: Backend doesn't have status field, this is for UI demonstration
    } catch (err) {
      console.error('Error updating user status:', err);
      fetchUsers(); // Revert changes
      alert('Failed to update user status');
    }
  };

  const handleSaveUser = async (userData) => {
    try {
      if (editingUser) {
        const response = await userAPI.updateUser(editingUser.id, userData);
        setUsers(users.map(user => 
          user.id === editingUser.id ? { ...response.data, status: user.status } : user
        ));
      } else {
        const response = await userAPI.createUser(userData);
        setUsers([...users, { ...response.data, status: 'active' }]);
      }
      setShowModal(false);
    } catch (err) {
      console.error('Error saving user:', err);
      throw new Error('Failed to save user');
    }
  };

  const filteredUsers = users.filter(user => {
    const matchesSearch = user.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         user.email?.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesRole = roleFilter === 'all' || user.role?.toLowerCase() === roleFilter.toLowerCase();
    return matchesSearch && matchesRole;
  });

  if (loading) {
    return <div className="loading">Loading users...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="users-page">
      <div className="page-header">
        <h1 className="page-title">Users</h1>
        <p className="page-subtitle">Manage registered users and their accounts</p>
      </div>

      <div className="actions-bar">
        <input
          type="text"
          placeholder="Search users..."
          className="search-input"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select
          className="form-select"
          value={roleFilter}
          onChange={(e) => setRoleFilter(e.target.value)}
        >
          <option value="all">All Roles</option>
          <option value="buyer">Buyer</option>
          <option value="farmer">Farmer</option>
          <option value="admin">Admin</option>
        </select>
        <button className="btn btn-primary" onClick={handleAddUser}>
          Add User
        </button>
      </div>

      {filteredUsers.length > 0 ? (
        <div className="table-container">
          <table className="table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredUsers.map((user) => (
                <tr key={user.id}>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>
                    <span className="role-badge">
                      {user.role}
                    </span>
                  </td>
                  <td>
                    <span className={`status-badge ${user.status === 'active' ? 'status-active' : 'status-blocked'}`}>
                      {user.status}
                    </span>
                  </td>
                  <td>
                    <div className="action-buttons">
                      <button
                        className="btn btn-secondary btn-small"
                        onClick={() => handleEditUser(user)}
                      >
                        Edit
                      </button>
                      <button
                        className={`btn btn-small ${user.status === 'active' ? 'btn-warning' : 'btn-success'}`}
                        onClick={() => handleToggleStatus(user.id)}
                      >
                        {user.status === 'active' ? 'Block' : 'Unblock'}
                      </button>
                      <button
                        className="btn btn-danger btn-small"
                        onClick={() => handleDeleteUser(user.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <div className="empty-state">
          <h3>No users found</h3>
          <p>
            {searchTerm || roleFilter !== 'all'
              ? 'No users match your search criteria.'
              : 'Users will appear here once they register on the platform.'
            }
          </p>
        </div>
      )}

      {showModal && (
        <UserModal
          user={editingUser}
          onSave={handleSaveUser}
          onClose={() => setShowModal(false)}
        />
      )}
    </div>
  );
};

export default Users;
