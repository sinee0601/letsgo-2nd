document.getElementById("updatePwForm").onsubmit = function (event) {
    event.preventDefault();

    const userId = document.getElementById("userIdInput").value;
    const email = document.getElementById("emailInput").value;
    const newPassword = document.getElementById("newPasswordInput").value;
    const newPasswordConfirm = document.getElementById("newPasswordConfirmInput").value;
    const message = document.getElementById("updatePwMessage");

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