document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.querySelector(".customer-login-form");
    loginForm.addEventListener("submit", async function(event) {
        event.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const payload = {
            email: email,
            password: password
        };
        try {
            const response = await fetch("/login/customer", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });
            if (!response.ok) {
                throw new Error('Login failed');
            }
            window.location.href = "/customer/home";
        } catch (error) {
            alert("Error during login.");
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.querySelector(".company-login-form");
    loginForm.addEventListener("submit", async function(event) {
        event.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const payload = {
            email: email,
            password: password
        };
        try {
            const response = await fetch("/login/company", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });
            if (!response.ok) {
                throw new Error('Login failed');
            }
            window.location.href = "/company/home";
        } catch (error) {
            alert("Error during login.");
        }
    });
});

document.getElementById("logout").addEventListener("click", async function(event) {
    event.preventDefault();
    const response = await fetch("/logout", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (response.ok) {
        window.location.href = "/";
    } else {
        alert("Logout failed.");
    }
});
