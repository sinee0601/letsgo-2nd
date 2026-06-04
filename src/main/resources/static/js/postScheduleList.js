let likeButtons = document.querySelectorAll(".like-btn");
let idEvent = function() {
    let clickedBtn;
    clickedBtn = this;
    let postId = this.getAttribute("data-postId");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            if (response.result === true) {
                let dbCount = parseInt(response.count);
                let likeCount = clickedBtn.parentElement.querySelector(".like-Count");
                if (likeCount) {
                    let currentCount = parseInt(likeCount.innerText) || 0;
                    if (dbCount > currentCount) {
                        likeCount.innerText = dbCount;
                    }
                }
            } else {
                alert("좋아요 처리에 실패했습니다.");
            }
        }
    };
    xhr.open("GET", "controller?cmd=postScheduleLike&postId=" + postId, true);
    xhr.send(null);
};
for (let i = 0; i < likeButtons.length; i++) {
    likeButtons[i].onclick = idEvent;
}