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
const PANELS = ["route", "budget", "todo", "companion"];

function readTitle() {
    return document.getElementById("scheduleTitleInput").value.trim();
}

function deleteMySchedule() {
    if (!confirm("이 항목을 삭제하시겠습니까?")) return Promise.resolve();
    return MyScheduleApi.deleteSchedule()
        .catch(err => console.error("fetch 오류:", err));
}

function submitTitle(title) {
    MyScheduleApi.saveTitle(title)
        .then(ok => alert(ok ? "일정 제목이 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function submitStartAt(startAt) {
    if (!startAt) {
        alert("날짜를 선택해주세요.");
        return;
    }
    MyScheduleApi.saveStartAt(startAt)
        .then(ok => alert(ok ? "날짜가 수정되었습니다." : "수정에 실패했습니다."))
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

deleteBtn.addEventListener("click", () => {
    deleteMySchedule();
    location.replace("/myschedule/list");
});

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

if ("${updateTitleResult}" === "true") alert("일정 제목이 수정되었습니다.");
if ("${deleteVisitItemResult}" === "true") alert("항목이 삭제되었습니다.");
if ("${updateRouteResult}" === "true") alert("동선이 수정되었습니다.");

showPanel((location.hash || "#route").slice(1));
