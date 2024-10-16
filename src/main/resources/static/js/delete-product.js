function deleteProduct(productID) {

    fetch('/company/products/' + productID + '/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response=> {
        if (response.ok) {
            location.reload();
        } else if (response.status === 409) {
            alert(response.body);
        }
    })
}