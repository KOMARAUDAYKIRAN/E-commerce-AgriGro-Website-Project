let cart= JSON.parse(localStorage.getItem("cart"))||[];

function loadCart(){
    let cart=JSON.parse(localStorage.getItem("cart"))||[];
    let cartItems=document.getElementById("cart-items");
    if(!cartItems) return;
    let totalAmount=0;
    cartItems.innerHTML ="";

    cart.forEach((item,index) => {
        let itemTotal=item.price*item.quantity;
        totalAmount+=itemTotal;

        cartItems.innerHTML += `
            <tr>
                <td><img src="${item.imageUrl}" width="50"></td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>
                    <button class="changequantity" onClick="changeQuantity(${index},-1)">-</button>
                    ${item.quantity}
                    <button class="changequantity" onClick="changeQuantity(${index},1)">+</button>
                </td>
                <td>${itemTotal}</td>
                <td><button class="remove" onClick="removeFromCart(${index})">X</button></td>
            </tr>
        `;
    });
    document.getElementById("total-amount").innerText=totalAmount;
}

function addToCart(id,name,price,imageUrl){
    price=parseFloat(price);
    let itemIndex=cart.findIndex((item)=> item.id==id);

    if(itemIndex!==-1){
        cart[itemIndex].quantity+=1;
    }else{
        cart.push({
            id:id,
            name:name,
            price:price,
            imageUrl:imageUrl,
            quantity:1
        })
    }
    localStorage.setItem("cart",JSON.stringify(cart));
    updateCounter();
}

function updateCounter(){
    const badge = document.querySelector(".cart-badge");
    if (badge) {
        badge.innerText = cart.length;
    }
}

function changeQuantity(index,quantity){
    let cart=JSON.parse(localStorage.getItem("cart"))||[];
    cart[index].quantity+=quantity;
    if(cart[index].quantity <=0) cart.splice( index,1);
    localStorage.setItem("cart",JSON.stringify(cart));
    loadCart();
    updateCounter();
}

function removeFromCart(index){
    let cart=JSON.parse(localStorage.getItem("cart"))||[];
    cart.splice( index,1);
    localStorage.setItem("cart",JSON.stringify(cart));
    loadCart();
    updateCounter();
}

document.addEventListener("DOMContentLoaded",()=>{
    loadCart();
    updateCounter();
})