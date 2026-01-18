const params = new URLSearchParams(window.location.search);
const eventId = params.get("eventId");
console.log(eventId);
document.addEventListener("DOMContentLoaded", () => {
    loadEventDetails(eventId);
console.log(eventId)
});

function loadEventDetails(eventId){
    fetch(`${BASE_URL}/api/event/getevent/${eventId}`)
    .then(res=>res.json())
    .then(event=>{
        console.log(event);
        const paymentDetails=document.getElementById("payment_info");
        paymentDetails.innerHTML=`
        <div class="card-body shadow p-3 g-body-tertiary rounded">
    <h5 class="card-title" id="eventName">Event Name:${event.title}</h5>
    <p class="card-text" id="venue">Venue: ${event.venue}</p>
     <p class="card-text" id="date">Date: ${event.date}</p>
      <p class="card-text"id="time">Time :${event.time}</p>
       <p class="card-text"id="price">Price: ${event.price}</p>
     
        <div class="alert alert-danger" role="alert">
            Note: One Registered user can book only one ticket
        </div>
    <a href="#" class="btn btn-primary" onClick=payNow(${event.eventId})>Pay now</a>`

    }).catch(err=>{
        console.log(err);
    });

}

function payNow(eventId){
console.log(eventId);
console.log("In Pay now method");

fetch(`${BASE_URL}/api/user/payment/create/${eventId}`,{
    method:"POST",
    credentials:"include"
})
.then(res=>{
        
        return res.json();
})
.then(payment=>{
    console.log("Payment created",payment)
    const paymentId=payment.paymentId
     setTimeout(() => {
            verifyPayment(paymentId, "SUCCESS");
        }, 1500);
}).catch(err => console.error(err));
}

function verifyPayment(paymentId,status){
    fetch(`${BASE_URL}/api/user/payment/verify`,{
    method:"POST",
    headers: {
            "Content-Type": "application/json"
        },
    credentials:"include",
 body: JSON.stringify({
            paymentId: paymentId,
            status: status
        })
    })
        .then(res => res.json())
    .then(data => {
        const bookingId = data.bookingId;
        if (data.status === "SUCCESS") {
            alert("Booking Confirmed");
            window.location.href =
                `/payment_success.html?bookingId=${bookingId}`;
        } else {
            alert("Payment Failed. Booking not confirmed.");
        }
})
}




