const sortableList = document.querySelector("#sortableList");
const saveRouteBtn = document.getElementById("updateRouteBtn");
const saveTitleBtn = document.getElementById("saveTitleBtn");
const addVisitBtn  = document.getElementById("addVisitBtn");
const contentRight = document.querySelector("#contentRight");
const sidebarLinks = document.querySelectorAll(".sidebar-link");
const deleteBtn = document.querySelector("#deleteBtn");
const dateEditBtn = document.querySelector("#dateEditBtn");
const dateEditForm = document.querySelector("#dateEditForm");
const dateConfirmBtn = document.querySelector("#dateConfirmBtn");
const dateCancelBtn = document.querySelector("#dateCancelBtn");
const startAtInput = document.querySelector("#startAtInput");
const companionBtn = document.querySelector("#companionBtn");
const scheduleId = location.pathname.split("/").filter(Boolean)[2];
const PANELS = ["route", "budget", "todo", "companion"];

deleteBtn.addEventListener("click", () => {
    deleteMySchedule(scheduleId)
    location.replace("/myschedule/list");
});

function readTitle() {
    return document.getElementById("scheduleTitleInput").value.trim();
}

function readRouteOrder() {
    return [...document.querySelectorAll("#sortableList li")].map((li, index) => ({
        visitItemId: li.dataset.visitId,
        visitOrder: index + 1
    }));
}

function readBudgetItems() {
    const items = {};
    document.querySelectorAll(".budget-row").forEach(row => {
        const amount = Number(row.querySelector(".budget-amount").value) || 0;
        if (amount > 0) items[row.dataset.visitId] = { amount };
    });
    return items;
}

function readSavedBudget() {
    const data = document.querySelector("#budgetData");
    try {
        return JSON.parse(data?.textContent?.trim() || "{}");
    } catch (e) {
        return {};
    }
}

sortableList.addEventListener("click", (e) => {
    const btn = e.target.closest(".delete-visit-btn");
    if (btn) deleteVisit(btn.dataset.visitId);
});

saveTitleBtn.addEventListener("click", () => {
    const title = readTitle();
    if (!title) {
        alert("제목을 입력해주세요.");
        return;
    }
    submitTitle(title);
});

saveRouteBtn.addEventListener("click", () => submitRoute(readRouteOrder()));

addVisitBtn.addEventListener("click", () => {
    location.href = `/myschedule/detail/${scheduleId}/addVisit`;
});

sidebarLinks.forEach(btn => {
    btn.addEventListener("click", () => showPanel(btn.dataset.filter));
});

dateEditBtn.addEventListener("click", () =>
    dateEditForm.style.display = dateEditForm.style.display === 'none' ? 'block' : 'none');

dateConfirmBtn.addEventListener("click", () => submitStartAt(startAtInput.value));

dateCancelBtn.addEventListener("click", () => {
    dateEditForm.style.display = 'none';
});

companionBtn.addEventListener("click", () => showPanel("companion"));

if (sortableList && typeof Sortable !== "undefined") {
    Sortable.create(sortableList, {
        animation: 150,
        filter: ".delete-visit-btn",
        preventOnFilter: false
    });
}

function deleteVisit(visitId) {
    if (!confirm("이 항목을 삭제하시겠습니까?")) return;

    fetch(`/myschedule/api/${scheduleId}/visit/${visitId}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" }
    })
        .then(res => res.json())
        .catch(err => console.error("fetch 오류:", err));
}

function deleteMySchedule(myScheduleId){
    if (!confirm("이 항목을 삭제하시겠습니까?")) return;
    fetch(`/myschedule/api/${myScheduleId}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" }
    })
        .then(res => res.json())
        .catch(err => console.error("fetch 오류:", err));
}

function calcBudgetTotal() {
    let total = 0;
    document.querySelectorAll(".budget-amount").forEach(i => total += Number(i.value) || 0);
    return total;
}

function saveBudget() {
    const payload = JSON.stringify({ items: readBudgetItems() });

    fetch(`/myschedule/api/${scheduleId}/budget`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ budgetDetail: payload })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "예산이 저장되었습니다." : "저장에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function saveTodo(todoDetail) {
    fetch(`/myschedule/api/${scheduleId}/todo`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ todoDetail })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "저장되었습니다." : "저장에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function showPanel(name) {
    if (!PANELS.includes(name)) name = "route";
    const tpl = document.querySelector(`#tpl-${name}`);
    if (!tpl || !contentRight) return;

    contentRight.replaceChildren(tpl.content.cloneNode(true));
    sidebarLinks.forEach(b => b.classList.toggle("active", b.dataset.filter === name));

    if (name === "route") renderRoute();
    if (name === "budget") renderBudget();
    if (name === "todo") renderTodo();
    if (name === "companion") renderCompanion();

    if (location.hash !== `#${name}`) history.replaceState(null, "", `#${name}`);
}

function renderRoute() {
    const mapEl = document.querySelector("#routeMap");
    if (!mapEl) return;

    fetch(`/myschedule/api/${scheduleId}/mapSchedule`)
        .then(res => res.json())
        .then(drawRouteMap)
        .catch(err => console.error("fetch 오류:", err));
}

function drawRouteMap(routes) {
    const mapEl = document.querySelector("#routeMap");
    if (!mapEl || typeof naver === "undefined") return;

    const points = routes
        .filter(r => r.mapX && r.mapY)
        .map(r => ({
            order: r.visitOrder,
            title: r.title,
            position: new naver.maps.LatLng(Number(r.mapY), Number(r.mapX))
        }));

    if (points.length === 0) {
        mapEl.classList.add("map-empty");
        mapEl.textContent = "추가된 장소가 없습니다.";
        return;
    }

    mapEl.classList.remove("map-empty");
    mapEl.textContent = "";

    const map = new naver.maps.Map(mapEl, {
        center: points[0].position,
        zoom: 12
    });

    const bounds = new naver.maps.LatLngBounds(points[0].position, points[0].position);

    points.forEach(p => {
        new naver.maps.Marker({
            map,
            position: p.position,
            title: p.title,
            icon: {
                content:
                    `<div style="background:#ff7a00;color:#fff;border-radius:50%;` +
                    `width:28px;height:28px;line-height:28px;text-align:center;` +
                    `font-weight:bold;border:2px solid #fff;box-shadow:0 1px 4px rgba(0,0,0,.4)">` +
                    `${p.order}</div>`,
                anchor: new naver.maps.Point(14, 14)
            }
        });
        bounds.extend(p.position);
    });

    if (points.length > 1) map.fitBounds(bounds);
}

function renderBudget() {
    const budgetList    = document.querySelector("#budgetList");
    const budgetTotalEl = document.querySelector("#budgetTotal");
    const saveBudgetBtn = document.querySelector("#saveBudgetBtn");
    if (!budgetList) return;

    const showTotal = () => budgetTotalEl.textContent = calcBudgetTotal().toLocaleString();

    const items = readSavedBudget().items || {};
    document.querySelectorAll(".budget-row").forEach(row => {
        const v = items[row.dataset.visitId];
        if (v) row.querySelector(".budget-amount").value = v.amount ?? "";
    });
    showTotal();

    budgetList.addEventListener("input", showTotal);
    saveBudgetBtn?.addEventListener("click", saveBudget);
}

function renderTodo() {
    const todoEl = document.querySelector("#todoDetail");
    const saveTodoBtn = document.querySelector("#saveTodoBtn");
    if (!todoEl) return;

    saveTodoBtn?.addEventListener("click", () => saveTodo(todoEl.value));
}

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
    fetch(`/myschedule/api/${scheduleId}/companion`)
        .then(res => res.json())
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
    fetch(`/myschedule/api/${scheduleId}/companion`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ sharedUserId })
    })
        .then(res => res.json())
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
    fetch(`/myschedule/api/${scheduleId}/companion/permission`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ sharedUserId, permission })
    })
        .then(res => res.json())
        .then(ok => { if (!ok) alert("권한 변경에 실패했습니다."); })
        .catch(err => console.error("fetch 오류:", err));
}

function shareToPost(isAnonymous) {
    fetch(`/myschedule/api/${scheduleId}/share`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ isAnonymous })
    })
        .then(res => res.text())
        .then(postId => alert(postId ? `게시판에 공유되었습니다.` : "공유에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function submitTitle(title) {
    fetch(`/myschedule/api/${scheduleId}/title`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ title })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "일정 제목이 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function submitStartAt(startAt) {
    if (!startAt) {
        alert("날짜를 선택해주세요.");
        return;
    }
    fetch(`/myschedule/api/${scheduleId}/startAt`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ startAt })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "날짜가 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function submitRoute(order) {
    const body = new URLSearchParams();
    order.forEach(data => {
        body.append("visitItemIds", data.visitItemId);
        body.append("visitOrders", data.visitOrder);
        body.append("distances", "0");
    });

    fetch(`/myschedule/api/${scheduleId}/visit/orders`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "동선이 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

if ("${updateTitleResult}" === "true") alert("일정 제목이 수정되었습니다.");
if ("${deleteVisitItemResult}" === "true") alert("항목이 삭제되었습니다.");
if ("${updateRouteResult}" === "true") alert("동선이 수정되었습니다.");

showPanel((location.hash || "#route").slice(1));
