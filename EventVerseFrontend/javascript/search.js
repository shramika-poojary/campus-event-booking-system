function addSearch() {
    const mainContent = document.getElementById("mainContent");

    mainContent.innerHTML = `
    <div class="d-flex align-items-center">
     <form class="d-flex w-100" id="searchForm">
    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" style="height:45px;font-size: 1.2rem; "id="query" required>
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
  </form>
  </div>
    `

    document
        .getElementById("searchForm")
        .addEventListener("submit", submitSearch);
}


//search category
function submitSearch(e) {
    e.preventDefault();


    const content =
        document.getElementById("query").value;
        console.log(content);
    fetch(`${BASE_URL}/api/category/get/categorybyname/${content}`)
        .then(res => res.json())
        .then(data => {console.log(data);
            
            const searchResult=document.getElementById("searchResult");
            searchResult.innerHTML=`
            <div class="border">
            <p>
            <h2>Category Id:${data.categoryId}</h2>
            <h2>Category Name:${data.categoryName}</h2>
            </p>
                
            </div>

            `
        })
        .catch(err => {
            console.error(err.message)
            searchResult.innerHTML="<p>No Such category</p>"
        });
}

function addSearchEvent() {
    const mainContent = document.getElementById("mainContent");
    const searchResult=document.getElementById("searchResult");
searchResult.innerHTML="";
    mainContent.innerHTML = `
    <div class="d-flex align-items-center">
     <form class="d-flex w-100" id="searchEventForm">
    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" style="height:45px;font-size: 1.2rem; "id="query" required>
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
  </form>
  </div>
    `

    document
        .getElementById("searchEventForm")
        .addEventListener("submit", submitEventSearch);
}


//search event
function submitEventSearch(e) {
    e.preventDefault();

    
    const content =
        document.getElementById("query").value;
        console.log(content);
    fetch(`${BASE_URL}/api/event/geteventbyname/${content}`)
        .then(res => res.json())
        .then(data => {console.log(data);
            const searchResult=document.getElementById("searchResult");
    
            searchResult.innerHTML=`
            <div class="card shadow p-3 bg-body-tertiary rounded" style="width: 40rem;">
  
  <div class="card-body">
    <h5 class="card-title">Event Name:${data.title}</h5>
    <p class="card-text">Description: ${data.description}</p>
    <p class="card-text">Rules: ${data.rules}</p>
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">Date: ${data.date}</li>
    <li class="list-group-item">Time: ${data.time}</li>
    <li class="list-group-item">Venue: ${data.venue}</li>
     <li class="list-group-item">Available Seats: ${data.availableSeats}</li>
      <li class="list-group-item">Price per ticket: ${data.price}</li>
  </ul>
  
</div>
            `
        })
        .catch(err => {
            console.error(err.message)
            searchResult.innerHTML="<p>No Such Event</p>"
        });
}

