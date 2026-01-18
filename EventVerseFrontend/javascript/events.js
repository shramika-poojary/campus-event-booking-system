document.addEventListener("DOMContentLoaded", () => {
    loadCategories();

})
function loadCategories() {
    fetch(`${BASE_URL}/api/category/get/categories`)
        .then(res => res.json())
        .then(data => {
            console.log(data);

            const categoryDiv = document.getElementById("categoryButtons");

            data.forEach(category => {
                const btn = document.createElement("button");
                btn.className = "btn btn-outline-primary m-3";
                btn.innerText = category.categoryName;
                btn.onclick=()=>loadEvents(category.categoryId);
            
                categoryDiv.appendChild(btn);
            });
        }).catch(err => console.error(err));


    const categoryEvent = document.getElementById("categoryEvents")
    function loadEvents(categoryId) {
         categoryEvent.innerHTML="";
        fetch(`${BASE_URL}/api/event/geteventbycategory/${categoryId}`)
            .then(res => res.json())
            .then(events => {
                console.log(events)
                if (events.length === 0) {
                categoryEvent.innerHTML = "<p>No events available</p>";
                return;
                 }

                events.forEach(event => {
                     categoryEvent.innerHTML += `
                    
                        <div class="card m-2 shadow p-3 mb-5 bg-body-tertiary rounded" style="width: 18rem;">
                            <img src="http://localhost:8080${event.posterImg}" class="card-img-top" alt="..." style="width: 100%; height: 180px; object-fit: cover >
                            <div class="card-body">
                                <h5 class="card-title">${event.title}</h5>
                                <p class="card-text">${event.description}</p>
                                <p class="card-text">Date: ${event.date}</p>
                                <button class="btn btn-primary mt-auto" onclick="viewDetails(${event.eventId})">View Details</button>
                            </div>
                        </div>`
                        ;
                })
            })
    }
}

function viewDetails(eventId){
     window.location.href = `event_details.html?eventId=${eventId}`;
}