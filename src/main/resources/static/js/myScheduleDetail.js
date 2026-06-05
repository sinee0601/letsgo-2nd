const sortableList = document.querySelector("#sortableList");
const saveRouteBtn = document.getElementById("updateRouteBtn");
const saveTitleBtn = document.getElementById("saveTitleBtn");
const addVisitBtn  = document.getElementById("addVisitBtn");
const contentRight = document.querySelector("#contentRight");
const sidebarLinks = document.querySelectorAll(".sidebar-link");

const scheduleId = location.pathname.split("/").filter(Boolean)[2];
const PANELS = ["route", "budget", "todo"];

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
    const el = document.querySelector("#budgetData");
    try {
        return JSON.parse(el?.textContent?.trim() || "{}");
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
    location.href = "#";
});

sidebarLinks.forEach(btn => {
    btn.addEventListener("click", () => showPanel(btn.dataset.filter));
});

if (sortableList && typeof Sortable !== "undefined") {
    Sortable.create(sortableList, {
        animation: 150,
        filter: ".delete-visit-btn",
        preventOnFilter: false
    });
}

function deleteVisit(visitId) {
    if (!confirm("이 항목을 삭제하시겠습니까?")) return;

    fetch(`myschedule/api/visit/${visitId}`, {
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

    fetch(`/myschedule/detail/${scheduleId}/budget`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ budgetDetail: payload })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "예산이 저장되었습니다." : "저장에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function saveTodo(todoDetail) {
    fetch(`/myschedule/todo`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ scheduleId, todoDetail })
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

    if (name === "budget") renderBudget();
    if (name === "todo") renderTodo();

    if (location.hash !== `#${name}`) history.replaceState(null, "", `#${name}`);
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

function submitTitle(title) {
    fetch(`/myschedule/title`, {
        method: "PUT",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ title, myScheduleId: scheduleId, userId: "user01" })
    })
        .then(res => res.json())
        .then(ok => alert(ok ? "일정 제목이 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function submitRoute(order) {
    const body = new URLSearchParams();
    order.forEach(o => {
        body.append("visitItemIds", o.visitItemId);
        body.append("visitOrders", o.visitOrder);
        body.append("distances", "0");
    });

    fetch(`/myschedule/visitOrders`, {
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
