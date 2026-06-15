const searchButton = document.querySelector("#searchButton");
const sortOrder = document.querySelector("#sortOrder");
let currentFilter = "all";
let currentPage = 1;

document.querySelector("#scheduleListContainer")?.addEventListener("click", (event) => {
    const button = event.target.closest(".like-btn");
    if (!button) return;

    const postId = button.dataset.postId;
    const likeCountEl = button.closest("figure").querySelector(".like-count");

    fetchPlusLike(postId, likeCountEl);
});

if (searchButton) {
    searchButton.addEventListener("click", () => fetchSchedules(1));
}

if (sortOrder) {
    sortOrder.addEventListener("change", () => fetchSchedules(1));
}

const navBtns = document.querySelectorAll(".nav-btn");

navBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        navBtns.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");

        currentFilter = btn.dataset.filter;
        fetchSchedules(1);
    });
});

window.addEventListener("pageshow", () => {
    fetchSchedules(currentPage);
});

function fetchSchedules(page = 1) {
    currentPage = page;

    const selectedSortOrder = document.querySelector("#sortOrder")?.value || "latest";
    const keyword = document.querySelector("#keyword")?.value || "";
    const params = new URLSearchParams({
        sortOrder: selectedSortOrder,
        keyword,
        page: currentPage,
    });

    const url = currentFilter === "user"
        ? `/postschedule/api/mylist?${params}`
        : `/postschedule/api/list?${params}`;

    fetch(url, {
        method: "get",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((result) => {
        return result.json();
    }).then(data => {
        renderPostSchedules(data.content);
        renderPagination(data.page, data.totalPages);
    }).catch(error => {
        console.error("실패 " + error);
    });
}

function fetchPlusLike(postId, likeCountEl ) {
    fetch(`/postschedule/api/${postId}/plusLike`, {
        method: "PUT",
    })
        .then(res => res.json())
        .then(count => likeCountEl.textContent = count)
        .catch(err => console.error("fetch 오류:", err));
}

function renderPostSchedules(postScheduleList) {
    const container = document.querySelector("#scheduleListContainer");
    if (!container) return;

    container.innerHTML = postScheduleList.map(postSchedule => `
                <figure class="figure" >
                    <div>
                        ${postSchedule.title}
                    </div>
                    
                    <a href="/postschedule/detail/${postSchedule.postId}" class="box-placeholder">
                        <img src="${postSchedule.firstImage}" alt="일정 이미지" class="box-placeholder"/>
                    </a>

                    <figcaption class="figure-caption">
                        ${postSchedule.placeTitle}
                    </figcaption>

                    <div>
                        <button type="button" class="like-btn" data-post-id="${postSchedule.postId}">
                           <h1>❤️</h1>
                        </button>
                        <span>좋아요 : <span class="like-count">${postSchedule.likeCount}</span></span>
                    </div>
                    
                    <div>
                        <span>조회수 : ${postSchedule.viewCount} </span>
                    </div>
                    <div>
                	    <span>
                             📍${(postSchedule.addr1 || "").substring(0, 10)}
                        </span>
                    </div>
                    
                    <div>
                        <div>👤 ${postSchedule.isAnonymous == 1 ? "익명" : postSchedule.userName}</div>
                    </div>
                </figure>
        `).join("");
}

function renderPagination(page, totalPages) {
    const pagination = document.querySelector("#pagination");
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
}

document.querySelector("#pagination")?.addEventListener("click", (event) => {
    const button = event.target.closest("button[data-page]");
    if (!button) return;
    fetchSchedules(Number(button.dataset.page));
});
