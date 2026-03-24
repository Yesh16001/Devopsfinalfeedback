document.getElementById("form").addEventListener("submit", function(e) {
    e.preventDefault();

    let isValid = true;

    const name = document.getElementById("name");
    const email = document.getElementById("email");
    const mobile = document.getElementById("mobile");
    const password = document.getElementById("password");
    const confirm = document.getElementById("confirm");
    const course = document.getElementById("course");
    const gender = document.querySelector('input[name="gender"]:checked');

    document.querySelectorAll(".error").forEach(e => e.innerText = "");


    if (name.value.trim() === "") {
        showError(name, "Name is required");
        isValid = false;
    }

   
    if (!email.value.includes("@")) {
        showError(email, "Enter valid email");
        isValid = false;
    }


    if (mobile.value.length !== 10) {
        showError(mobile, "Enter 10 digit number");
        isValid = false;
    }

    if (password.value.length < 6) {
        showError(password, "Minimum 6 characters");
        isValid = false;
    }

    if (password.value !== confirm.value) {
        showError(confirm, "Passwords do not match");
        isValid = false;
    }

    if (!gender) {
        document.querySelector(".gender + .error").innerText = "Select gender";
        isValid = false;
    }


    if (course.value === "") {
        showError(course, "Select course");
        isValid = false;
    }

    if (isValid) {
        alert("Form Submitted Successfully!");
    }
});


function showError(input, message) {
    input.nextElementSibling.innerText = message;
}