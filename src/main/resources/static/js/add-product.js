function addToCart(productID) {
    fetch('/customer/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productID: productID })
    }).then(response => {
            if (response.ok) {
                alert(response.body);
            } else if (response.status === 409) {
                alert(response.body)
            } else {
                alert("An unexpected error occurred. Please try again later.")
            }
        })
}

function addToFavorites(productID) {


    fetch('/customer/favorites/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productID: productID })
    })
        .then(response => {
            if (response.ok) {
                alert(response.body);
            } else if (response.status === 400) {
                alert(response.text())
            } else {
                alert("An unexpected error occurred. Please try again later.")
            }
        });
}