document.addEventListener('DOMContentLoaded', () => {
    const productGrid = document.getElementById('product-grid');
    const searchInput = document.getElementById('search-input');
    const apiUrl = 'http://localhost:8080/api/products';

    let allProducts = []; // To store the master list of products

    // --- Function to render products on the page ---
    const renderProducts = (productsToDisplay) => {
        productGrid.innerHTML = ''; // Clear the grid first

        if (productsToDisplay.length === 0) {
            productGrid.innerHTML = '<p>No products match your search.</p>';
            return;
        }

        productsToDisplay.forEach(product => {
            const card = document.createElement('div');
            card.className = 'product-card';
            const imageUrl = product.imageURL || `https://via.placeholder.com/300x220.png?text=${encodeURIComponent(product.name)}`;

            card.innerHTML = `
                <img src="${imageUrl}" alt="${product.name}" class="product-image">
                <div class="product-info">
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-description">${product.description}</p>
                    <p class="product-price">₹${product.price.toFixed(2)}</p>
                </div>
            `;
            productGrid.appendChild(card);
        });
    };

    // --- Event listener for the search input ---
    searchInput.addEventListener('input', () => {
        const searchTerm = searchInput.value.toLowerCase();
        const filteredProducts = allProducts.filter(product => 
            product.name.toLowerCase().includes(searchTerm)
        );
        renderProducts(filteredProducts);
    });

    // --- Fetch initial data ---
    productGrid.innerHTML = '<p class="loading-message">Loading products...</p>';
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Network response was not ok: ${response.statusText}`);
            }
            return response.json();
        })
        .then(products => {
            allProducts = products; // Store the fetched products
            renderProducts(allProducts); // Render all products initially
        })
        .catch(error => {
            console.error('Error fetching products:', error);
            productGrid.innerHTML = `<p class="error-message">Failed to load products. Please ensure the backend is running and accessible.</p>`;
        });
});