
console.log("inside my ticket");
//to get user tickets
fetch(`${BASE_URL}/api/user/tickets`, {
    credentials: "include"
})
    .then(res => {
        if (res.status === 401) {
            alert("Please login to continue");
            window.location.href = "/login.html";
            Promise.reject("Unauthorized");
        }
        return res.json();
    })
    .then(tickets => {

        const ticketDiv = document.getElementById("ticket");

        if (!tickets || tickets.length === 0) {
            ticketDiv.innerHTML = "<p class='text-center'>No tickets booked yet.</p>";
            return;
        }

        ticketDiv.innerHTML = "";

        tickets.forEach(ticketData => {
            console.log(ticketData);
            ticketDiv.innerHTML += `
        <div class="card shadow-lg border-0 mx-auto mb-4" style="max-width: 900px;">
            <div class="card-header bg-success text-white text-center">
                <h4 class="mb-0">🎟️ Event Ticket</h4>
            </div>

            <div class="card-body ">
                <h5 class="card-title text-center mb-3">
                    ${ticketData.event.title}
                </h5>

                <p class="text-center text-muted mb-4">
                    Ticket Code: <strong>${ticketData.ticket.ticketCode}</strong>
                </p>

                <div class="text-center mb-4">
                    <img 
                            src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(ticketData.ticket.ticketCode)}"
                            alt="QR Code"
                    />
                <p class="small text-muted mt-1">Scan at entry</p>
                </div>
                <div class="row mb-2">
                    <div class="col-6"><strong>📅 Date:</strong></div>
                    <div class="col-6">${ticketData.event.date}</div>
                </div>

                <div class="row mb-2">
                    <div class="col-6"><strong>⏰ Time:</strong></div>
                    <div class="col-6">${ticketData.event.time}</div>
                </div>

                <div class="row mb-2">
                    <div class="col-6"><strong>📍 Location:</strong></div>
                    <div class="col-6">${ticketData.event.venue}</div>
                </div>

                <div class="row mb-2">
                    <div class="col-6"><strong>Booking ID:</strong></div>
                    <div class="col-6">${ticketData.booking.bookingId}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-6"><strong>Status:</strong></div>
                    <div class="col-6">
                        <span class="badge bg-success">
                            ${ticketData.ticket.status}
                        </span>
                    </div>
                </div>

                <div class="text-center">
                    <button class="btn btn-outline-primary" onclick="window.print()">
                        Print Ticket
                    </button>
                </div>
            </div>

            <div class="card-footer text-center text-muted">
                Please carry this ticket to the event
            </div>
        </div>
        <hr>
        `;
        });
    })
    .catch(err => {
        console.log(err);
    });


