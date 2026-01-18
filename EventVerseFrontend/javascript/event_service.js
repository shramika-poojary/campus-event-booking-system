
let uploadedImageUrl = ""; 
function addEventForm() {
    const mainContent = document.getElementById("mainContent");
     const searchResult=document.getElementById("searchResult");
     searchResult.innerHTML="";
    mainContent.innerHTML = `
        <div class="d-flex justify-content-center">
            <form class="card p-4 shadow-sm" id="addEventForm" style="width:900px">
                <h5 class="text-center mb-4">Add Event</h5>

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Event Name</label>
                        <input type="text" class="form-control" id="title">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Venue</label>
                        <input type="text" class="form-control" id="venue">
                    </div>

                    <div class="col-md-12">
                        <label class="form-label">Description</label>
                        <input type="text" class="form-control" id="description">
                    </div>

                    <div class="col-md-12">
                        <label class="form-label">Rules</label>
                        <input type="text" class="form-control" id="rules">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Date</label>
                        <input type="date" class="form-control" id="date">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Time</label>
                        <input type="time" class="form-control" id="time">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Price Per Ticket</label>
                        <input type="number" class="form-control" id="price">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Total Seats</label>
                        <input type="number" class="form-control" id="totalSeats">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Available Seats</label>
                        <input type="number" class="form-control" id="availableSeats">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Poster Image</label>
                        <input type="file" class="form-control" id="posterImg">
                    </div>
                    
                    <label>Category</label>
<select id="categoryId" class="form-select>
    <option value="">Select Category</option>
</select>

                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary px-5">
                        Add Event
                    </button>
                </div>
            </form>
        </div>
    `;

    document
        .getElementById("addEventForm")
        .addEventListener("submit", addEvent);

loadCategories();
uploadImage(); //
}





function addEvent(e) {
    e.preventDefault();
    if (!uploadedImageUrl) {
        alert("Please upload a poster image first");
        return;
    }
    const categoryId=document.getElementById("categoryId").value;
    const data = {
        title: document.getElementById("title").value,
        description: document.getElementById("description").value,
        rules: document.getElementById("rules").value,
        date: document.getElementById("date").value,
        time: document.getElementById("time").value,
        venue: document.getElementById("venue").value,
        price: document.getElementById("price").value,
        totalSeats: document.getElementById("totalSeats").value,
        availableSeats: document.getElementById("availableSeats").value,
        posterImg: uploadedImageUrl
    };
 if (!categoryId || !title || !description || !rules || !date || !time || !venue || !price || !totalSeats || !availableSeats) {
        alert("Please fill in all fields");
        
    }

    

    fetch(`${BASE_URL}/api/admin/add/event/${categoryId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Forbidden or Unauthorized");
            }
            return res.json();
        })
        .then(data => {
            alert("Event Added Successfully");
            console.log(data);

            
        })
        .catch(err => {
            console.error(err.message);
            alert("You are not authorized as ADMIN");
        });
}


function uploadImage() {


    const fileInput = document.getElementById("posterImg");

    fileInput.addEventListener("change", async () => {

        const file = fileInput.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append("file", file);

        try {
            const res = await fetch(`${BASE_URL}/api/admin/upload`, {
                method: "POST",
                body: formData,
                credentials: "include"
            });

            uploadedImageUrl = await res.text();
            alert("Image uploaded successfully");

        } catch (err) {
            console.error(err);
            alert("Image upload failed");
        }
    });
}
