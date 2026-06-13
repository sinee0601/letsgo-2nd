const navBtns = document.querySelectorAll(".nav-btn");
const searchBtn = document.querySelector("#searchPlaces");
const searchTitleInput = document.querySelector("[name='searchTitle']");
const sortOrderSelect = document.querySelector("[name='sortOrder']");
const container = document.querySelector("#scheduleListContainer");
const pagination = document.querySelector("#pagination");

let currentFilter = "all";
let currentPage = 1;

window.addEventListener("pageshow", () => {
    fetchSchedules(1);
});


navBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        navBtns.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        currentFilter = btn.dataset.filter;
        fetchSchedules(1);
    });
});

searchBtn.addEventListener("click", () => fetchSchedules(1));

sortOrderSelect?.addEventListener("change", () => fetchSchedules(1));

function fetchSchedules(page = 1) {
    currentPage = page;
    const searchTitle = searchTitleInput?.value ?? "";
    const sortType = sortOrderSelect?.value ?? "date";
    const isShared = currentFilter === "shared";

    fetch(`/myschedule/api/list?searchTitle=${encodeURIComponent(searchTitle)}&sortType=${sortType}&isShared=${isShared}&page=${page}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" }
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(t => { throw new Error(`HTTP ${res.status}: ${t}`); });
            }
            return res.json();
        })
        .then(data => {
            renderMySchedules(data.content);
            renderPagination(data.page, data.totalPages);
        })
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

function renderPagination(page, totalPages) {
    if (!pagination) return;

    if (!totalPages || totalPages <= 1) {
        pagination.innerHTML = "";
        return;
    }

    let html = "";
    html += `<button type="button" class="page-btn" data-page="${page - 1}" ${page <= 1 ? "disabled" : ""}>이전</button>`;
    for (let i = 1; i <= totalPages; i++) {
        html += `<button type="button" class="page-btn${i === page ? " active" : ""}" data-page="${i}">${i}</button>`;
    }
    html += `<button type="button" class="page-btn" data-page="${page + 1}" ${page >= totalPages ? "disabled" : ""}>다음</button>`;
    pagination.innerHTML = html;

    pagination.querySelectorAll(".page-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const target = Number(btn.dataset.page);
            if (target >= 1 && target <= totalPages) {
                fetchSchedules(target);
            }
        });
    });
}
