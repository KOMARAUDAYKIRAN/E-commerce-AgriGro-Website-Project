// User ID is now retrieved dynamically from localStorage
// const userId = 1;

async function fetchCartByUserId(userId) {
    const response = await fetch(`http://localhost:8083/api/cart/${userId}`);
    const cart = await response.json();
    return cart;
}

async function loadCart() {
    // Get user ID from localStorage
    const userId = localStorage.getItem("userId");
    
    // If no user ID is found, we can't load the cart
    if (!userId) {
        console.error("User not logged in. Cannot load cart.");
        return;
    }
    
    const cartItemsElement = document.getElementById("cart-items");
    if (!cartItemsElement) return;

    try {
        const cart = await fetchCartByUserId(userId);

        let totalAmount = 0;
        cartItemsElement.innerHTML = "";

        const cartItems = cart.items || [];

        cartItems.forEach(item => {
            const product = item.product;
            const quantity = item.quantity;
            const itemTotal = product.price * quantity;
            totalAmount += itemTotal;

            cartItemsElement.innerHTML += `
                <tr>
                    <td><img src="${product.imageUrl}" alt="${product.name}" width="100" height="100"></td>
                    <td>${product.name}</td>
                    <td>₹${product.price}</td>
                    <td>
                        <button class="changequantity" onClick="changeQuantity(${item.id},-1)">-</button>
                        ${item.quantity}
                        <button class="changequantity" onClick="changeQuantity(${item.id},1)">+</button>
                    </td>
                    <td>₹${itemTotal}</td>
                    <td><button class="remove" onclick="removeFromCart(${item.id})">X</button></td>
                </tr>
            `;
        });

        document.getElementById("total-amount").innerText = `${totalAmount}`;
        console.log(cartItems.length);
        count = cartItems.length;
        updateCartBadge();
    } catch (error) {
        console.error("Failed to load cart", error);
    }
}

async function changeQuantity(itemId, delta) {
    // Get user ID from localStorage
    const userId = localStorage.getItem("userId");
    
    // If no user ID is found, we can't update the cart
    if (!userId) {
        console.error("User not logged in. Cannot update cart.");
        return;
    }
    
    const cart = await fetchCartByUserId(userId);
    const cartItems = cart.items || [];
    const alreadyInCart = cartItems.find(item => item.id === itemId);
    const newQuantity = alreadyInCart.quantity + delta;

    if(newQuantity<1){
        await removeFromCart(itemId);
        return;
    }

    const updatedItem = {
        id: alreadyInCart.id,
        quantity: alreadyInCart.quantity + delta,
        product: alreadyInCart.product,
        cart: { id: cart.id }
    }
    await fetch(`http://localhost:8083/api/cart-items/update/${alreadyInCart.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedItem)
    });

    loadCart();
    updateCartBadge();

}

async function addToCart(productId) {
    // Get user ID from localStorage
    const userId = localStorage.getItem("userId");
    
    // If no user ID is found, we can't add to cart
    if (!userId) {
        console.error("User not logged in. Cannot add to cart.");
        return;
    }
    
    const cart = await fetchCartByUserId(userId);
    const cartItems = cart.items || [];
    const alreadyInCart = cartItems.find(item => item.product.id === productId);

    if (alreadyInCart) {
        const updatedItem = {
            id: alreadyInCart.id,
            quantity: alreadyInCart.quantity + 1,
            product: alreadyInCart.product,
            cart: { id: cart.id }
        }
        await fetch(`http://localhost:8083/api/cart-items/update/${alreadyInCart.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedItem)
        });
    } else {
        try {
            await fetch(`http://localhost:8083/api/cart/add/${userId}?productId=${productId}&quantity=1`, {
                method: "POST"
            });

        } catch (error) {
            console.error("Failed to add to cart", error);
        }
    }
    loadCart();
    updateCartBadge();

}

async function removeFromCart(itemId) {
    try {
        await fetch(`http://localhost:8083/api/cart/remove/${itemId}`, {
            method: "DELETE"
        });
        loadCart();
    } catch (error) {
        console.error("Failed to remove from cart", error);
    }
}

async function updateCartBadge() {
    // Get user ID from localStorage
    const userId = localStorage.getItem("userId");
    
    // If no user ID is found, we can't update the cart badge
    if (!userId) {
        console.error("User not logged in. Cannot update cart badge.");
        return;
    }
    
    const badge = document.querySelector(".cart-badge");
    const cart = await fetchCartByUserId(userId);
    const cartItems = cart.items || [];

    if (badge) badge.innerText = cartItems.length;
}




document.addEventListener("DOMContentLoaded", () => {
    // Check if user is logged in before loading cart
    const userId = localStorage.getItem("userId");
    if (!userId) {
        console.error("User not logged in. Please log in to view your cart.");
        // Optionally, redirect to login page
        // window.location.href = "login.html";
        return;
    }
    
    loadCart();
    updateCartBadge();

});
