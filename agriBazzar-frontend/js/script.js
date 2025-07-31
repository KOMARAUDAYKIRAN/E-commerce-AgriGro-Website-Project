document.addEventListener('DOMContentLoaded', () => {
  console.log("AgriBazaar frontend loaded!");
});
async function checkAbandonedCarts(userId) {
    try {
        const response = await fetch(`/api/cart/abandoned/${userId}`);
        if (!response.ok) return;
        const carts = await response.json();
        if (carts.length > 0) {
            showCartReminder(carts);
        }
    } catch (err) {
        console.error('Error fetching abandoned carts:', err);
    }
}

function showCartReminder(carts) {
    const modal = document.createElement('div');
    modal.className = 'cart-reminder-modal';
    modal.innerHTML = `
        <div class="modal-content">
            <h2>You left items in your cart!</h2>
            <p>You have ${carts[0].items.length} items waiting.</p>
            <button id="goToCartBtn">Go to Cart</button>
            <button id="closeCartModal">Close</button>
        </div>
    `;
    document.body.appendChild(modal);

    document.getElementById('closeCartModal').addEventListener('click', () => modal.remove());
    document.getElementById('goToCartBtn').addEventListener('click', () => {
        window.location.href = '/cart.jsp'; // ✅ Change to your cart page
    });
}

// ✅ Call this on page load for logged-in user
window.addEventListener('DOMContentLoaded', () => {
    const userId = 1; // ✅ Replace with actual logged-in user ID (from session)
    checkAbandonedCarts(userId);
});
