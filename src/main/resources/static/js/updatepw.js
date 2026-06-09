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

    if (userId.trim() == "" || email.trim() == "" || newPassword.trim() == "" || newPasswordConfirm.trim() == "") {
        message.style.display = "block";
        message.innerText = "필수 항목을 모두 입력해주세요.";
        return;
    }

    if (newPassword != newPasswordConfirm) {
        message.style.display = "block";
        message.innerText = "새 비밀번호 확인이 일치하지 않습니다.";
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState != 4) {
            return;
        }
        if (xhr.status != 200) {
            message.style.display = "block";
            message.innerText = "비밀번호 변경을 다시해주세요.";
            return;
        }

        var data;
        try {
            data = JSON.parse(xhr.responseText);
        } catch (e) {
            message.style.display = "block";
            message.innerText = "비밀번호 변경 오류.";
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

    xhr.open("POST", "/user/api/updatePwAjax", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("userID=" + encodeURIComponent(userId)
        + "&email=" + encodeURIComponent(email)
        + "&password=" + encodeURIComponent(newPassword)
        + "&passwordConfirm=" + encodeURIComponent(newPasswordConfirm));
};
