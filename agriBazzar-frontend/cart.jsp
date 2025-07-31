<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .cart-container { max-width: 800px; margin: 50px auto; }
        .cart-item { display: flex; justify-content: space-between; padding: 10px; border-bottom: 1px solid #ccc; }
        .cart-item p { margin: 0; }
        .cart-total { text-align: right; font-weight: bold; margin-top: 20px; }
        .btn-remove { background: #dc3545; color: white; border: none; padding: 5px 10px; cursor: pointer; }
        .btn-checkout { background: #28a745; color: white; border: none; padding: 10px 20px; cursor: pointer; margin-top: 20px; float: right; }
    </style>
</head>
<body>
    <div class="cart-container">
        <h1>Your Shopping Cart</h1>
        <div id="cartItems"></div>
        <div class="cart-total" id="cartTotal">Total: ₹0</div>
        <button class="btn-checkout" onclick="checkout()">Proceed to Checkout</button>
    </div>

    <script>
        const userId = 1; // ✅ Replace with logged-in user ID from session

        async function fetchCart() {
            const response = await fetch(`/api/cart/${userId}`);
            if (!response.ok) {
                document.getElementById('cartItems').innerHTML = "<p>Your cart is empty.</p>";
                return;
            }
            const cart = await response.json();
            renderCart(cart);
        }

        function renderCart(cart) {
            const container = document.getElementById('cartItems');
            container.innerHTML = '';
            let total = 0;

            if (!cart.items || cart.items.length === 0) {
                container.innerHTML = "<p>Your cart is empty.</p>";
                return;
            }

            cart.items.forEach(item => {
                const price = item.product.price * item.quantity;
                total += price;

                const div = document.createElement('div');
                div.className = 'cart-item';
                div.innerHTML = `
                    <p>${item.product.name} (x${item.quantity}) - ₹${price}</p>
                    <button class="btn-remove" onclick="removeItem(${item.id})">Remove</button>
                `;
                container.appendChild(div);
            });

            document.getElementById('cartTotal').innerText = "Total: ₹" + total;
        }

        async function removeItem(itemId) {
            await fetch(`/api/cart/remove/${itemId}`, { method: 'DELETE' });
            fetchCart();
        }

        function checkout() {
            alert("Proceeding to checkout...");
            // ✅ Integrate payment flow here
        }

        window.onload = fetchCart;
    </script>
</body>
</html>
