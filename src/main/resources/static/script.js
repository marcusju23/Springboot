// shows dropdown menu when user clicks the icon
document.getElementById("globe-icon").addEventListener("click", function() {
    let dropdownContent = document.getElementById("dropdown-content");
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
    } else {
        dropdownContent.style.display = "block";
    }
});