
function addCategoryForm() {
    const mainContent = document.getElementById("mainContent");
const searchResult=document.getElementById("searchResult");
     searchResult.innerHTML="";
    mainContent.innerHTML = `
        <div>
            <form class="card p-4 shadow-sm" id="addCategoryForm" style="width:700px">
                <h5 class="text-center mb-3">Add Category</h5>

                <div class="form-group">
                    <label for="categoryName">Category Name</label>
                    <input type="text"
                           class="form-control"
                           id="categoryName"
                           required>
                </div>

                <button type="submit"
                        class="btn btn-primary mt-4">
                    Add
                </button>
            </form>
        </div>
    `;

    document
        .getElementById("addCategoryForm")
        .addEventListener("submit", addCategory);
}


function addCategory(e) {
    e.preventDefault();

    const categoryName =
        document.getElementById("categoryName").value;

    fetch(`${BASE_URL}/api/admin/add/category`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({
            categoryName: categoryName
        })
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Forbidden or Unauthorized");
            }
            return res.json();
        })
        .then(data => {
            alert("Category Added Successfully");
            console.log(data);
        })
        .catch(err => {
            console.error(err.message);
            alert("You are not authorized as ADMIN");
        });
}

function loadCategories() {
    fetch(`${BASE_URL}/api/category/get/categories`, {
    })
    .then(res => res.json())
    .then(data => {
        const categoryDropdown = document.getElementById("categoryId");
        categoryDropdown.innerHTML = `<option value="">Select Category</option>`;

        data.forEach(cat => {
            const option = document.createElement("option");
            option.value = cat.categoryId;   
            option.textContent = cat.categoryName;
            categoryDropdown.appendChild(option);
        });
    })
    .catch(err => console.error(err));
}

