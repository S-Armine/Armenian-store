function CheckPassword(password) 
{ 
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,18}$/;
    if(regex.test(password.value)) 
    {
        return true;
    }
    else
    { 
        alert('Password does not meet the requirements. Password should have at least one upper case, one lower case letter, one number. Password should have from 8 to 18 length. Please try again.');
        password.value = '';
        password.focus();
        return false;
    }
}

function addFormEventListener() {
    const form = document.getElementById('signup');
    if (form) {
        form.addEventListener('submit', function(event) {
            if (!CheckPassword(document.getElementById('password'))) {
                event.preventDefault();
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', addFormEventListener);
