document.getElementById('form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const inStock = document.getElementById('in-stock').value;
    const price = document.getElementById('price').value;
    const category = document.getElementById("category").value;
    const imageFile = document.getElementById('image').files[0];

    const formData = new FormData();
    formData.append('name', name);
    formData.append('description', description);
    formData.append('inStock', inStock);
    formData.append('price', price);
    formData.append('category', category);
    formData.append('image', imageFile);

    const response = await fetch('/company/products/add', {
        method: 'POST',
        body: formData,
    });
    if (response.ok) {
        alert('Product uploaded successfully!');
        window.location.href = '/company/home';
    } else {
        alert('Failed to upload product.');
    }
});