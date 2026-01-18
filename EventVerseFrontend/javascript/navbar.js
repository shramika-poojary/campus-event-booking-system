function loadNavbar() {
    fetch("components/nav.html")
        .then(res => res.text())
        .then(html => {
            document.getElementById("navbar").innerHTML = html;
        })
        .catch(err => console.log("Navbar load failed", err));
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
});
