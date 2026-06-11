// admin-posts.js

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".btn-toggle-post").forEach(button => {
        button.addEventListener("click", function () {
            const postId = this.getAttribute("data-id");
            const currentActive = this.getAttribute("data-active") === "true";
            togglePostVisibility(postId, !currentActive);
        });
    });

    document.querySelectorAll(".btn-delete-post").forEach(button => {
        button.addEventListener("click", function () {
            const postId = this.getAttribute("data-id");
            deletePost(postId);
        });
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
                alert(`게시글이 ${actionText} 상태로 변경되었습니다.`);
                location.reload();
            } else {
                alert("상태 변경 중 오류가 발생했습니다.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("네트워크 통신 오류가 발생했습니다.");
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
                alert("게시글이 삭제되었습니다.");
                location.reload();
            } else {
                alert("게시글 삭제 중 오류가 발생했습니다.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("네트워크 통신 오류가 발생했습니다.");
        });
    }
}
