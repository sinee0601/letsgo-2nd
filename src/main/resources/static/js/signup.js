document.getElementById("signupForm").onsubmit = function (event) {
    event.preventDefault();

    var name = document.getElementById("nameInput").value;
    var userId = document.getElementById("idInput").value;
    var email = document.getElementById("emailInput").value;
    var password = document.getElementById("pwInput").value;
    var passwordConfirm = document.getElementById("pwConfirmInput").value;
    var message = document.getElementById("signupMessage");

    message.style.display = "none";
    message.innerText = "";
    message.style.color = "red";

    if (name.trim() == "" || userId.trim() == "" || email.trim() == "" || password.trim() == "" || passwordConfirm.trim() == "") {
        message.style.display = "block";
        message.innerText = "필수 항목을 모두 입력해주세요.";
        return;
    }

    if (password != passwordConfirm) {
        message.style.display = "block";
        message.innerText = "비밀번호 확인이 일치하지 않습니다.";
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState != 4) {
            return;
        }
        if (xhr.status != 200) {
            message.style.display = "block";
            message.innerText = "회원가입을 다시해주세요.";
            return;
        }

        var data;
        try {
            data = JSON.parse(xhr.responseText);
        } catch (e) {
            message.style.display = "block";
            message.innerText = "회원가입 오류.";
            return;
        }
        message.style.display = "block";
        message.innerText = data.message;

        if (data.result == "success") {
            message.style.color = "black";
            setTimeout(function () {
                location.href = data.url;
            }, 700);
        } else {
            message.style.color = "red";
        }
    };

    xhr.open("POST", "/user/api/signUpAjax", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("name=" + encodeURIComponent(name)
        + "&userID=" + encodeURIComponent(userId)
        + "&email=" + encodeURIComponent(email)
        + "&password=" + encodeURIComponent(password)
        + "&passwordConfirm=" + encodeURIComponent(passwordConfirm));
};
