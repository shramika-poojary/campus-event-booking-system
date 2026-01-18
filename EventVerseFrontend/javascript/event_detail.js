const params = new URLSearchParams(window.location.search);
const eventId = params.get("eventId");
//to display event details
document.addEventListener("DOMContentLoaded", () => {
    loadEvent(eventId);
console.log(eventId)
});

function loadEvent(eventId){
    fetch(`${BASE_URL}/api/event/getevent/${eventId}`)
    .then(res=>res.json())
    .then(event=>{
        console.log(event);
        
        const now = new Date();
        const eventDateTime = new Date(`${event.date}T${event.time}`);

        let disableBooking = false;
        let buttonText = "Book Your Ticket";

        const isSoldOut = event.availableSeats<=0;
        const isCompleted= eventDateTime<now;
        if(isSoldOut){
            disableBooking = true;
            buttonText = "Sold Out";
        }else if(isCompleted){
            disableBooking = true;
            buttonText = "Event Completed";
        }
        const eventDetails=document.getElementById("eventInfo");
            eventDetails.innerHTML = "";
            eventDetails.innerHTML+=`
            <div class="row shadow p-3 mb-5 bg-body-tertiary rounded">
            <div class="col-8 border h-5"> <img src="${event.posterImg.startsWith('http') || event.posterImg.startsWith('/') 
        ? 'http://localhost:8080' + event.posterImg 
        : 'http://localhost:8080/images/' + event.posterImg} "style="width: 100%; height: 300px;"></div>
            <div class="col-4 border">
            <h2 class="mt-4">${event.title}</h2>
            <p>Venue:${event.venue}</p>
            <p>Available Seats:${event.availableSeats}
            <p>Date:${event.date}</p>
            <p>Time:${event.time}</p>
            <p>Price:${event.price}</p>
            <br>
            
            <button type="button" class="btn btn-info mb-5" ${disableBooking? "disabled":""} onClick="goToPayment(${event.eventId})">${buttonText}</button>
            </div>
            </div>
            <div class="container-fluid border border mt-4 p-4 shadow p-3 mb-5 bg-body-tertiary rounded">
            <h2>About the event </h2>
            <p>${event.description}</p>
            <br>
            <h2>Rules</h2>
            <p>${event.rules}</p>
            </div>

            `     
    }).catch(err => {
            alert(err.message);
        });
}

function goToPayment(eventId){
     window.location.href=`payment.html?eventId=${eventId}`;
}

