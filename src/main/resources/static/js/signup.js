document.getElementById("signupForm").onsubmit = function (event) {
    event.preventDefault();

    const name = document.getElementById("nameInput").value;
    const userId = document.getElementById("idInput").value;
    const email = document.getElementById("emailInput").value;
    const password = document.getElementById("pwInput").value;
    const passwordConfirm = document.getElementById("pwConfirmInput").value;
    const message = document.getElementById("signupMessage");

    message.style.display = "none";
    message.innerText = "";
    message.style.color = "red";

    if (name.trim() == "" || userId.trim() == "" || email.trim() == ""
        || password.trim() == "" || passwordConfirm.trim() == "") {
        message.style.display = "block";
        message.innerText = "필수 항목을 모두 입력해주세요.";
        return;
    }
    if (password != passwordConfirm) {
        message.style.display = "block";
        message.innerText = "비밀번호 확인이 일치하지 않습니다.";
        return;
    }

    fetch("/user/api/signUpAjax", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "name=" + encodeURIComponent(name)
            + "&userID=" + encodeURIComponent(userId)
            + "&email=" + encodeURIComponent(email)
            + "&password=" + encodeURIComponent(password)
            + "&passwordConfirm=" + encodeURIComponent(passwordConfirm)
    }).then(response => {
        if (!response.ok) {
            return response.json().then(data => { throw new Error(data.message); });
        }
        return response.json();
    }).then(data => {
        message.style.display = "block";
        message.innerText = data.message;
        if (data.result === "success") {
            message.style.color = "black";
            setTimeout(() => { location.href = data.url; }, 700);
        }
    }).catch(error => {
        message.style.display = "block";
        message.innerText = error.message;
    });
};