function removeFromFavorite(productID) {
    fetch('/customer/favorites/remove/' + productID, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response=> {
        if (response.ok) {
            document.getElementById('button' + productID).style.display = 'none';
            document.getElementById('removed' + productID).style.display='block'
        } else if(response.status === 400) {
            alert("Product was not in the favorites.")
        } else {
            alert("Could not remove from favorites.");
        }
    })
}

function removeFromCart(productID) {
    fetch('/customer/cart/remove/' + productID, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response=> {
        if (response.ok) {
            location.reload();
        } else if (response.status === 400) {
            alert("Product was not in the cart.")
        } else {
            alert("Could not remove from cart.");
        }
    })
}

