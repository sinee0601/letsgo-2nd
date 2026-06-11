// admin-reports.js

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".btn-process-report").forEach(button => {
        button.addEventListener("click", function () {
            const reportId = this.getAttribute("data-id");
            const action = this.getAttribute("data-action");
            processReport(reportId, action);
        });
    });
});

function processReport(reportId, action) {
    const actionText = action === 'REJECT' ? '반려' : '게시글 삭제';
    if (confirm(`정말 이 신고를 ${actionText} 처리하시겠습니까?`)) {
        fetch(`/admin/api/reports/${reportId}/process?action=${action}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                alert("처리되었습니다.");
                location.reload();
            } else {
                alert("처리 중 문제가 발생했습니다.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("네트워크 에러가 발생했습니다.");
        });
    }
}
