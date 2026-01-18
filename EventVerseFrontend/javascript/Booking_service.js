function goToPayment(eventId) {

    fetch(`${BASE_URL}/api/check-login`, {
        method: "GET",
        credentials: "include"
    })
    .then(response => {
        if (response.status === 200) {
            // user logged in
            window.location.href = `payment.html?eventId=${eventId}`;
        } else {
            // user not logged in
            alert("Please login first");
            window.location.href = "login.html";
        }
    })
    .catch(error => {
        alert("Something went wrong");
        console.log(error);
    });
}
