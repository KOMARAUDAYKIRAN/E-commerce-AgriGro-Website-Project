<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products - AgriBazaar</title>
</head>
<body>
<h1>Our Products</h1>
<div id="product-list">Loading...</div>

<script>
    fetch("http://localhost:8080/api/products")
        .then(res => res.json())
        .then(products => {
            const container = document.getElementById("product-list");
            if (products.length === 0) {
                container.innerHTML = "<p>No products available.</p>";
                return;
            }

            container.innerHTML = "";
            products.forEach(p => {
                const item = document.createElement("div");
                item.innerHTML = `
            <h3>${p.name}</h3>
            <p>${p.description}</p>
            <p><strong>₹${p.price}</strong> | Stock: ${p.stock}</p>
            <hr/>
          `;
                container.appendChild(item);
            });
        })
        .catch(err => {
            document.getElementById("product-list").innerHTML = "<p>Error loading products.</p>";
            console.error(err);
        });
</script>
</body>
</html>
