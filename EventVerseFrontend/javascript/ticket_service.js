const ticketparams = new URLSearchParams(window.location.search);
const bookingId = ticketparams.get("bookingId");

if (!bookingId) {
    console.error("Booking ID missing");
    
}


fetch(`${BASE_URL}/api/user/get/ticketbybooking/${bookingId}`, {
    credentials: "include"
})
.then(res => {
if (!res.ok){
    throw new Error("Ticket not found");
    }
    return res.json()
})
.then(ticketData => {
    console.log(ticketData);
    const ticketDiv = document.getElementById("ticket");

    ticketDiv.innerHTML = `
        <div class="card shadow-lg border-0 mx-auto" style="max-width: 600px;">
            <div class="card-header bg-success text-white text-center">
                <h4 class="mb-0">🎟️ Event Ticket</h4>
            </div>

            <div class="card-body">
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
                        <span class="badge bg-success">${ticketData.ticket.status}</span>
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
    `;
})
.catch(err => console.error(err));


