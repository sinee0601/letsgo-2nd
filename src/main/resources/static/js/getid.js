document.getElementById("getIdForm").onsubmit = function (event) {
    event.preventDefault();

    var name = document.getElementById("nameInput").value;
    var email = document.getElementById("emailInput").value;
    var message = document.getElementById("getIdMessage");
    var found = document.getElementById("foundIdMessage");

    message.style.display = "none";
    found.style.display = "none";
    message.innerText = "";
    found.innerText = "";

    if (name.trim() == "" || email.trim() == "") {
        message.style.display = "block";
        message.innerText = "이름과 이메일을 입력해주세요.";
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState != 4) {
            return;
        }
        if (xhr.status != 200) {
            message.style.display = "block";
            message.innerText = "아이디가 없습니다.";
            return;
        }

        var data;
        try {
            data = JSON.parse(xhr.responseText);
        } catch (e) {
            message.style.display = "block";
            message.innerText = "아이디 찾기 오류.";
            return;
        }
        if (data.result == "success") {
            found.style.display = "block";
            found.innerText = "회원님의 아이디는 \"" + data.userId + "\" 입니다.";
        } else {
            message.style.display = "block";
            message.innerText = data.message;
        }
    };

    xhr.open("POST", "/user/api/getIdAjax", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("name=" + encodeURIComponent(name)
        + "&email=" + encodeURIComponent(email));
};
