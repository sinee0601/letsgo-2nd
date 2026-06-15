// admin-users.js

document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (e) {
        const warningBtn = e.target.closest(".btn-give-warning");
        if (warningBtn) {
            const userId = warningBtn.getAttribute("data-userid");
            openGiveWarningModal(userId);
        }

        const suspendBtn = e.target.closest(".btn-suspend");
        if (suspendBtn) {
            const userId = suspendBtn.getAttribute("data-userid");
            suspendUser(userId);
        }

        const unsuspendBtn = e.target.closest(".btn-unsuspend");
        if (unsuspendBtn) {
            const userId = unsuspendBtn.getAttribute("data-userid");
            unsuspendUser(userId);
        }
    });

    document.addEventListener("submit", function (e) {
        const searchForm = e.target.closest("form[method='GET']");
        if (searchForm) {
            e.preventDefault();
            const url = new URL(searchForm.action, window.location.origin);
            const keyword = searchForm.querySelector("input[name='keyword']").value;
            url.searchParams.set("keyword", keyword);

            fetch(url)
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");
                const newTableContainer = doc.querySelector(".admin-table-container");
                if (newTableContainer) {
                    document.querySelector(".admin-table-container").innerHTML = newTableContainer.innerHTML;
                }
                history.pushState(null, "", url);
            });
        }
    });

    const warningForm = document.getElementById("warningForm");
    if (warningForm) {
        warningForm.addEventListener("submit", function (e) {
            e.preventDefault();
            const formData = new FormData(warningForm);
            fetch(warningForm.action, {
                method: "POST",
                body: new URLSearchParams(formData)
            })
            .then(function (response) {
                if (response.ok) {
                    location.reload();
                }
            });
        });
    }
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
                location.reload();
            }
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
                location.reload();
            }
        });
    }
}
