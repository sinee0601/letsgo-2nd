// admin-users.js

document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".btn-give-warning").forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-userid");
            openGiveWarningModal(userId);
        });
    });


    document.querySelectorAll(".btn-suspend").forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-userid");
            suspendUser(userId);
        });
    });


    document.querySelectorAll(".btn-unsuspend").forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-userid");
            unsuspendUser(userId);
        });
    });
});


function openGiveWarningModal(userID) {
    document.getElementById('warning_userId').value = userID;
    document.getElementById('warning_userDisplay').textContent = userID;
    const warningModal = new bootstrap.Modal(document.getElementById('warningModal'));
    warningModal.show();
}


function suspendUser(userID) {
    if (confirm(`정말 ${userID} 회원의 계정을 영구 정지하시겠습니까?`)) {
        fetch(`/admin/api/users/${userID}/suspend`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                alert("회원 계정이 정지되었습니다.");
                location.reload();
            } else {
                alert("정지 처리 중 문제가 발생했습니다.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("네트워크 에러가 발생했습니다.");
        });
    }
}


function unsuspendUser(userID) {
    if (confirm(`정말 ${userID} 회원의 계정 정지를 해제하시겠습니까?`)) {
        fetch(`/admin/api/users/${userID}/unsuspend`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                alert("회원 계정 정지가 해제되었습니다.");
                location.reload();
            } else {
                alert("정지 해제 처리 중 문제가 발생했습니다.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("네트워크 에러가 발생했습니다.");
        });
    }
}
