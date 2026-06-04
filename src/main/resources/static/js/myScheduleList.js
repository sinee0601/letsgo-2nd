let searchPlaces = document.querySelector("#searchPlaces");

if (searchPlaces)
    searchPlaces.addEventListener("click", () => {
        // const userId = document.querySelector("#loginOK").value;
        const userId = "user01";
        const searchRequest = document.querySelector("[name='searchTitle']");
        fetch(`/myschedule/api/list?userId=${userId}&searchTitle=${searchRequest ? searchRequest.value : ""}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
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
    });


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
