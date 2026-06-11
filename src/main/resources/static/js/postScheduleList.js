let searchButton = document.querySelector("#searchButton");
let sortOrder = document.querySelector("#sortOrder");
if (searchButton)
    searchButton.addEventListener("click", fetchSchedules);
if (sortOrder)
    sortOrder.addEventListener("change", fetchSchedules);

let currentFilter = "all";

const navBtns = document.querySelectorAll(".nav-btn");

navBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        navBtns.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");

        currentFilter = btn.dataset.filter;
        fetchSchedules();
    });
});

window.addEventListener("pageshow", () => {
    fetchSchedules();
});

function fetchSchedules(){
    const sortOrder = document.querySelector("#sortOrder").value;
    const keyword = document.querySelector("#keyword").value;

    const url = currentFilter === "user"
        ? `/postschedule/api/mylist?sortOrder=${sortOrder}&keyword=${keyword}`
        : `/postschedule/api/list?sortOrder=${sortOrder}&keyword=${keyword}`;
    fetch(url, {
        method: "get",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((result)=> {
        return result.json();
    }).then((data) => {
        renderPostSchedules(data);
    }).catch(error=>{
        console.error("실패 " + error);
    });
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
                        <button type="button" class="like-btn" data-postId="${postSchedule.postId}">
                           <h1>❤️</h1>
                        </button>
                        <span>좋아요 : <span class="like-Count">${postSchedule.likeCount}</span></span>
                    </div>
                    
                    <div>
                        <span>조회수 : ${postSchedule.viewCount} </span>
                    </div>
                    <div>
                	    <span>
                             📍${postSchedule.addr1.substring(0, 10)}
                        </span>
                    </div>
                    
                    <div>
                        <div>👤 ${postSchedule.isAnonymous == 1 ? '익명' : postSchedule.userName}</div>
                    </div>
                </figure>
        `).join("");
}
