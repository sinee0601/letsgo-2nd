const sortableList = document.querySelector("#sortableList");
const contentRight = document.querySelector("#contentRight");
const deleteBtn = document.querySelector("#deleteBtn");
const dateEditBtn = document.querySelector("#dateEditBtn");
const dateEditForm = document.querySelector("#dateEditForm");
const postId = location.pathname.split("/").filter(Boolean)[2];
const PANELS = ["route", "budget", "todo"];

deleteBtn.addEventListener("click", () => {
    deletePostSchedule(postId)
    location.replace("/postschedule/list");
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

function deletePostSchedule(postId){
    if (!confirm("이 항목을 삭제하시겠습니까?")) return;
    fetch(`/postschedule/api/${postId}`, {
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



function showPanel(name) {
    if (!PANELS.includes(name)) name = "route";
    const tpl = document.querySelector(`#tpl-${name}`);
    if (!tpl || !contentRight) return;

    contentRight.replaceChildren(tpl.content.cloneNode(true));
    sidebarLinks.forEach(b => b.classList.toggle("active", b.dataset.filter === name));

    if (name === "budget") renderBudget();
    if (name === "todo") renderTodo();
    if (name === "companion") renderCompanion();

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


showPanel((location.hash || "#route").slice(1));


