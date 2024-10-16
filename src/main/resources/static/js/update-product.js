document.getElementById('form').addEventListener('submit', async function(event) {

    const productID = document.getElementById('productID').value;
    const values = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: document.getElementById('price').value,
        inStock: document.getElementById('in-stock').value,
    };
    const response = fetch('/company/products/' + productID, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    });

    if (response.ok) {
        window.location.href = '/company/home';
    } else {
        alert('Error updating resource');
    }
});

