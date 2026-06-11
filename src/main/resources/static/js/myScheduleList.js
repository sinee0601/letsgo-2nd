const navBtns = document.querySelectorAll(".nav-btn");
const searchBtn = document.querySelector("#searchPlaces");
const searchTitleInput = document.querySelector("[name='searchTitle']");
const sortOrderSelect = document.querySelector("[name='sortOrder']");
const container = document.querySelector("#scheduleListContainer");

let currentFilter = "all";

window.addEventListener("pageshow", () => {
    fetchSchedules();
});


navBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        navBtns.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        currentFilter = btn.dataset.filter;
        fetchSchedules();
    });
});

searchBtn.addEventListener("click", fetchSchedules);

sortOrderSelect?.addEventListener("change", fetchSchedules);

function fetchSchedules() {
    const searchTitle = searchTitleInput?.value ?? "";
    const sortOrder = sortOrderSelect?.value ?? "date";
    const isShared = currentFilter === "shared";

    fetch(`/myschedule/api/list?searchTitle=${searchTitle}&sortOrder=${sortOrder}&isShared=${isShared}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(res => res.json())
        .then(data => renderMySchedules(data))
        .catch(err => console.error("fetch 오류:", err));
}

function renderMySchedules(myScheduleList) {
    if (!container) return;

    container.innerHTML = myScheduleList.map(item => {
        const sharedBadge = item.isShared === "1" ? " 👥" : "";
        return `
            <figure class="figure">
                <div>${item.myScheduleTitle}${sharedBadge}</div>
                <a href="/myschedule/detail/${item.myScheduleId}" class="box-placeholder">
                    <img src="${item.firstImage}" alt="이미지" class="box-placeholder">
                </a>
                <figcaption class="figure-caption">${item.placeTitle}</figcaption>
                <div>${item.startAt}<br>📍${item.addr1}</div>
            </figure>
        `;
    }).join("");
}
