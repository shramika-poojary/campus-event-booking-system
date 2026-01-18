//Login
const loginForm = document.getElementById("loginForm");
if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        let emailMsg = document.getElementById("emailMsg");
        let pwdMsg = document.getElementById("pwdMsg");
        let roleMsg = document.getElementById("roleMsg");

        emailMsg.textContent = "";
        pwdMsg.textContent = "";
        roleMsg.textContent = "";

        if(email==""){
        emailMsg.textContent = "Email is required";
        }
        if(password==""){
        pwdMsg.textContent="Password is required"
        }
        if(role==""){
        roleMsg.textContent = "Please select a role.";
        }

        fetch(`${BASE_URL}/api/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify({ email, password, role })
        })
        .then(res => {
            if (!res.ok) {
                alert("Invalid Credentials")
            }
            return res.json();
        })
        .then(data => {
            alert("Login Successful");
            window.location.href =
                data.authorities[0].authority === "ROLE_ADMIN"
                    ? "admin_dashboard.html"
                    : "homepage.html";
        })
        .catch(err => alert(err.message));
    });
}

// REGISTER 
const registerForm = document.getElementById("RegisterForm");
if (registerForm) {
    registerForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const confirm_password = document.getElementById("confirm_password").value;
        const contact = document.getElementById("contact").value;
        const role = document.getElementById("role").value;
        const collegeName = document.getElementById("collegeName").value;
        const stream = document.getElementById("stream").value;
        const year = document.getElementById("year").value;

        if(email==""){
        errorMsg.textContent = "Email is required";
        }
        if(password==""){
        errorMsg.textContent="Password is required"
        }
        if(role==""){
        errorMsg.textContent = "Please select a role.";
        }
        if(collegeName==""){
        errorMsg.textContent = "Please select your college name.";
        }

        if (password !== confirm_password) {
            alert("Passwords do not match");
            return;
        }

        fetch(`${BASE_URL}/api/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                name, email, password, contact, role, collegeName, stream, year
            })
        })
        .then(res => {
            if (!res.ok) throw new Error("Registration failed");
            return res.json();
        })
        .then(data => {
            alert("Registration Successful");
            window.location.href = "login.html";
        })
        .catch(err => alert(err.message));
    });
}

//logout
function logoutUser() {
    fetch(`${BASE_URL}/api/auth/logout`, {
        method: 'POST',
        credentials: 'include' 
    })
    .then(response => {
        if(response.ok) {
            alert("Logged out successfully!");
            window.location.href = "/login.html";
        } else {
            alert("Logout failed!");
        }
    })
    .catch(error => console.error(error));
}
