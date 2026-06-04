let currentFilter = "all";

document.querySelectorAll(".nav-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        currentFilter = btn.dataset.filter;
        fetchSchedules();
    });
});

let searchPlaces = document.querySelector("#searchPlaces");
if (searchPlaces)
    searchPlaces.addEventListener("click", fetchSchedules);

function fetchSchedules() {
    const userId = "user01";
    const searchTitle = document.querySelector("[name='searchTitle']")?.value ?? "";
    const sortOrder = document.querySelector("[name='sortOrder']")?.value ?? "date";
    const isShared = currentFilter === "shared";

    fetch(`/myschedule/api/list?userId=${userId}&searchTitle=${searchTitle}&sortOrder=${sortOrder}&isShared=${isShared}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(result => {
            console.log("응답 status:", result.status);
            return result.json();
        })
        .then(data => {
            console.log("받은 데이터:", data);
            renderMySchedules(data);
        })
        .catch(err => console.error("fetch 오류:", err));
}


function renderMySchedules(myScheduleList) {
    const container = document.querySelector("#scheduleListContainer");
    if (!container) return;

    container.innerHTML = myScheduleList.map(item => {
        const isShared = item.isShared === "1" ? " 👥" : "";
        return `
            <figure class="figure">
                <div>${item.myScheduleTitle}${isShared}</div>
                <a href="#" class="box-placeholder">
                    <img src="${item.firstImage}" alt="이미지" class="box-placeholder">
                </a>
                <figcaption class="figure-caption">${item.placeTitle}</figcaption>
                <div>${item.startAt} 📍${item.addr1}</div>
            </figure>
        `;
    }).join("");
}
