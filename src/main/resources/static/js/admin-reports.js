// admin-reports.js

document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (e) {
        const processBtn = e.target.closest(".btn-process-report");
        if (processBtn) {
            const reportId = processBtn.getAttribute("data-id");
            const action = processBtn.getAttribute("data-action");
            processReport(reportId, action);
        }
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
                location.reload();
            }
        });
    }
}
