const axios = require('axios');

const API_BASE_URL = 'http://localhost:8080/api';

const testAPI = async () => {
  console.log('🔍 Testing API endpoints...\n');
  
  const endpoints = [
    { name: 'Products', url: '/products' },
    { name: 'Users', url: '/users' },
    { name: 'Orders', url: '/orders' }
  ];

  for (const endpoint of endpoints) {
    try {
      console.log(`Testing ${endpoint.name}...`);
      const response = await axios.get(`${API_BASE_URL}${endpoint.url}`);
      console.log(`✅ ${endpoint.name}: ${response.status} - ${response.data.length} items`);
      if (response.data.length > 0) {
        console.log(`   Sample data:`, JSON.stringify(response.data[0], null, 2));
      }
    } catch (error) {
      console.log(`❌ ${endpoint.name}: ${error.message}`);
      if (error.response) {
        console.log(`   Status: ${error.response.status}`);
        console.log(`   Data:`, error.response.data);
      } else if (error.request) {
        console.log('   No response received from server');
      }
    }
    console.log('');
  }
};

testAPI().catch(console.error);
