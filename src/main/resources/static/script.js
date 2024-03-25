// JavaScript för att visa dropdown-rutan när användaren klickar på ikonen
document.getElementById("globe-icon").addEventListener("click", function() {
    var dropdownContent = document.getElementById("dropdown-content");
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
    } else {
        dropdownContent.style.display = "block";
    }
});