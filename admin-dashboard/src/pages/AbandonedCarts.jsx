import React, { useEffect, useState } from 'react';

const AbandonedCarts = () => {
  const [carts, setCarts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAbandonedCarts = async () => {
      try {
        const res = await fetch('/api/cart/abandoned/all'); // ✅ Backend endpoint for all abandoned carts
        if (res.ok) {
          const data = await res.json();
          setCarts(data);
        }
      } catch (err) {
        console.error('Error fetching abandoned carts:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchAbandonedCarts();
  }, []);

  if (loading) return <p>Loading abandoned carts...</p>;

  return (
    <div style={styles.container}>
      <h1>Abandoned Carts</h1>
      {carts.length === 0 ? (
        <p>No abandoned carts found.</p>
      ) : (
        <table style={styles.table}>
          <thead>
            <tr>
              <th>User</th>
              <th>Items</th>
              <th>Last Updated</th>
            </tr>
          </thead>
          <tbody>
            {carts.map((cart) => (
              <tr key={cart.id}>
                <td>{cart.user.name}</td>
                <td>
                  {cart.items.map((item) => (
                    <div key={item.id}>
                      {item.product.name} (x{item.quantity})
                    </div>
                  ))}
                </td>
                <td>{new Date(cart.updatedAt).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

const styles = {
  container: { padding: '20px' },
  table: {
    width: '100%',
    borderCollapse: 'collapse',
    marginTop: '20px',
    background: '#fff'
  },
  th: {
    border: '1px solid #ddd',
    padding: '10px',
    background: '#f4f4f4'
  },
  td: {
    border: '1px solid #ddd',
    padding: '10px'
  }
};

export default AbandonedCarts;
