document.getElementById("getIdForm").onsubmit = function (event) {
    event.preventDefault();

    const name = document.getElementById("nameInput").value;
    const email = document.getElementById("emailInput").value;
    const message = document.getElementById("getIdMessage");
    const found = document.getElementById("foundIdMessage");

    message.style.display = "none";
    found.style.display = "none";
    message.innerText = "";
    found.innerText = "";

    if (name.trim() == "" || email.trim() == "") {
        message.style.display = "block";
        message.innerText = "이름과 이메일을 입력해주세요.";
        return;
    }

    fetch("/user/api/getIdAjax", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "name=" + encodeURIComponent(name)
            + "&email=" + encodeURIComponent(email)
    }).then(response => {
        if (!response.ok) {
            return response.json().then(data => { throw new Error(data.message); });
        }
        return response.json();
    }).then(data => {
        found.style.display = "block";
        found.innerText = "회원님의 아이디는 \"" + data.userId + "\" 입니다.";
    }).catch(error => {
        message.style.display = "block";
        message.innerText = error.message;
    });
};