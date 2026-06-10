function renderCompanion() {
    const addBtn = document.querySelector("#addCompanionBtn");
    const shareBtn = document.querySelector("#shareToPostBtn");
    const input = document.querySelector("#sharedUserIdInput");
    if (!addBtn) return;

    loadCompanions();

    addBtn.addEventListener("click", () => {
        const sharedUserId = input.value.trim();
        if (!sharedUserId) {
            alert("추가할 사용자 ID를 입력하세요.");
            return;
        }
        addCompanion(sharedUserId);
    });

    shareBtn?.addEventListener("click", () => {
        const isAnonymous = document.querySelector("#isAnonymousCheck").checked ? 1 : 0;
        shareToPost(isAnonymous);
    });
}

function loadCompanions() {
    MyScheduleApi.loadCompanions()
        .then(renderCompanionList)
        .catch(err => console.error("fetch 오류:", err));
}

function renderCompanionList(list) {
    const memberList = document.querySelector("#memberList");
    if (!memberList) return;
    memberList.innerHTML = "";

    list.forEach(member => {
        const data = document.createElement("div");
        data.className = "member-item";
        data.innerHTML = `
            <span class="member-icon">▣</span>
            <div class="member-info">
                <span class="member-name"></span>
                <span class="member-email"></span>
            </div>
            <div class="member-select">
                <select class="permission-select">
                    <option value="W">편집 허용</option>
                    <option value="R">읽기 허용</option>
                </select>
            </div>`;
        data.querySelector(".member-name").textContent = member.name;
        data.querySelector(".member-email").textContent = member.email;

        const select = data.querySelector(".permission-select");
        select.value = member.permission;
        select.addEventListener("change", () => changePermission(member.userId, select.value));

        memberList.appendChild(data);
    });
}

function addCompanion(sharedUserId) {
    MyScheduleApi.addCompanion(sharedUserId)
        .then(ok => {
            if (!ok) {
                alert("동반자 추가에 실패했습니다.");
                return;
            }
            document.querySelector("#sharedUserIdInput").value = "";
            loadCompanions();
        })
        .catch(err => console.error("fetch 오류:", err));
}

function changePermission(sharedUserId, permission) {
    MyScheduleApi.changePermission(sharedUserId, permission)
        .then(ok => { if (!ok) alert("권한 변경에 실패했습니다."); })
        .catch(err => console.error("fetch 오류:", err));
}

function shareToPost(isAnonymous) {
    MyScheduleApi.shareToPost(isAnonymous)
        .then(postId => alert(postId ? `게시판에 공유되었습니다.` : "공유에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}
