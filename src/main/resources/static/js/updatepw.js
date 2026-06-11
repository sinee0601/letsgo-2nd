document.getElementById("updatePwForm").onsubmit = function (event) {
    event.preventDefault();

    var userId = document.getElementById("userIdInput").value;
    var email = document.getElementById("emailInput").value;
    var newPassword = document.getElementById("newPasswordInput").value;
    var newPasswordConfirm = document.getElementById("newPasswordConfirmInput").value;
    var message = document.getElementById("updatePwMessage");

    message.style.display = "none";
    message.innerText = "";
    message.style.color = "red";

    if (userId.trim() == "" || email.trim() == ""
        || newPassword.trim() == "" || newPasswordConfirm.trim() == "") {
        message.style.display = "block";
        message.innerText = "필수 항목을 모두 입력해주세요.";
        return;
    }
    if (newPassword != newPasswordConfirm) {
        message.style.display = "block";
        message.innerText = "새 비밀번호 확인이 일치하지 않습니다.";
        return;
    }

    fetch("/user/api/updatePwAjax", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "userID=" + encodeURIComponent(userId)
            + "&email=" + encodeURIComponent(email)
            + "&password=" + encodeURIComponent(newPassword)
            + "&passwordConfirm=" + encodeURIComponent(newPasswordConfirm)
    }).then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
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