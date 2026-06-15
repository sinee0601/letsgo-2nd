// admin-posts.js

document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (e) {
        const toggleBtn = e.target.closest(".btn-toggle-post");
        if (toggleBtn) {
            const postId = toggleBtn.getAttribute("data-id");
            const currentActive = toggleBtn.getAttribute("data-active") === "true";
            togglePostVisibility(postId, !currentActive);
        }

        const deleteBtn = e.target.closest(".btn-delete-post");
        if (deleteBtn) {
            const postId = deleteBtn.getAttribute("data-id");
            deletePost(postId);
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
});

function togglePostVisibility(postId, nextStatus) {
    const actionText = nextStatus ? '노출' : '숨김';
    if (confirm(`해당 게시글을 ${actionText} 상태로 변경하시겠습니까?`)) {
        fetch(`/admin/api/posts/${postId}/toggle?isActive=${nextStatus}`, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            }
        });
    }
}

function deletePost(postId) {
    if (confirm("정말 이 게시글을 삭제하시겠습니까? (삭제 시 복구할 수 없습니다)")) {
        fetch(`/admin/api/posts/${postId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            }
        });
    }
}
