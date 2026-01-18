function loadFooter() {
    fetch("components/footer.html")
        .then(res => res.text())
        .then(html => {
            document.querySelector("footer").innerHTML = html;
        })
        .catch(err => console.log("Footer load failed", err));
}

document.addEventListener("DOMContentLoaded", () => {
    loadFooter();
});