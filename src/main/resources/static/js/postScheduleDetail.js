const sortableList = document.querySelector("#sortableList");
const contentRight = document.querySelector("#contentRight");
const addBtn = document.querySelector("#addBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const reportBtn = document.querySelector("#reportBtn");
const likeBtn = document.querySelector("#likeBtn");
const likeCountEL = document.querySelector("#likeCount");
const sidebarLinks = document.querySelectorAll(".sidebar-link");
const postId = location.pathname.split("/").filter(Boolean)[2];
const PANELS = ["route", "budget", "todo"];

likeBtn.addEventListener("click", () => {
        fetchPlueLike(postId)
});

deleteBtn?.addEventListener("click", () => {
    deletePostSchedule(postId)
});

reportBtn?.addEventListener("click", () => {
    reportPostSchedule(postId);
});

addBtn.addEventListener("click", () => {
    addPostScheduleToMySchedule(postId)
});

function readRouteOrder() {
    return [...document.querySelectorAll("#sortableList li")].map((li, index) => ({
        visitItemId: li.dataset.visitId,
        visitOrder: index + 1
    }));
}


function readSavedBudget() {
    const data = document.querySelector("#budgetData");
    try {
        return JSON.parse(data?.textContent?.trim() || "{}");
    } catch (e) {
        return {};
    }
}

function fetchPlueLike(postId) {
    fetch(`/postschedule/api/${postId}/plusLike`, {
        method: "PUT",
        })
        .then(res => res.json())
        .then(count => likeCountEL.textContent = count)
        .catch(err => console.error("좋아요 증가 실패", err));
}


sidebarLinks.forEach(btn => {
    btn.addEventListener("click", () => showPanel(btn.dataset.filter));
});


function deletePostSchedule(postId){
    if (!confirm("해당 게시물을 삭제하시겠습니까?")) return;
    fetch(`/postschedule/api/${postId}`, {
        method: "DELETE",
    })
        .then(res => alert(res.ok ? "게시물이 삭제되었습니다" : "게시물 삭제에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
    location.replace("/postschedule/list");
}

function addPostScheduleToMySchedule(postId){
    if (!confirm("게시물을 일정에 추가하시겠습니까?")) return;
    fetch(`/postschedule/api/${postId}/copy`, {
        method: "PUT",
    })
        .then(res => alert(res.ok ? "일정이 추가되었습니다" : "일정 추가에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function reportPostSchedule(postId) {
    const reason = prompt("신고 사유를 입력해주세요.");
    if (!reason || !reason.trim()) return;

    fetch(`/postschedule/api/${postId}/report`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ reason: reason.trim() })
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("게시물 신고 실패");
            }
            alert("신고가 접수되었습니다.");
        })
        .catch(err => {
            console.error("fetch 오류:", err);
            alert("신고 처리 중 오류가 발생했습니다.");
        });
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

    if (location.hash !== `#${name}`) history.replaceState(null, "", `#${name}`);
}

function readRoutesFromPage() {
    return [...document.querySelectorAll("#sortableList li")].map(li => ({
        visitId: li.dataset.visitId,
        visitOrder: li.dataset.visitOrder,
        title: li.dataset.title
    }));
}

function renderBudget() {
    const routes = readRoutesFromPage();
    const budgetList = document.querySelector("#budgetList");
    if (!budgetList) return;

    const budgetDetailText = document.querySelector("#budgetDetailText");
    const savedBudget = readSavedBudget();
    const budgetItems = savedBudget.items || {};
    const budgetValues = Object.values(budgetItems);
    if (budgetDetailText) {
        budgetDetailText.textContent = Object.keys(budgetItems).length > 0 ? "" : "등록된 예산 정보가 없습니다.";
    }

    budgetList.innerHTML = routes.map((route, index) => {
        const savedItem = budgetItems[route.visitId] || budgetValues[index] || {};
        const amount = savedItem.amount ?? "";

        return `
        <li class="budget-row" data-visit-id="${route.visitId}">
            <span class="budget-place">${route.visitOrder}. ${route.title}</span>
            <input type="number" class="budget-amount" min="0" step="1000" placeholder="0" value="${amount}"  readonly/>
            <span class="budget-won">원</span>
        </li>
    `;
    }).join("");

    bindBudgetTotal();
}

function renderTodo() {
    const todoEl = document.querySelector("#todoDetail");
    if (!todoEl) return;

    if (!todoEl.value.trim()) {
        todoEl.placeholder = "등록된 To-Do가 없습니다.";
    }
}

function bindBudgetTotal() {
    const budgetTotalEl = document.querySelector("#budgetTotal");

    const updateTotal = () => {
        let total = 0;
        document.querySelectorAll(".budget-amount").forEach(input => {
            total += Number(input.value) || 0;
        });
        budgetTotalEl.textContent = total.toLocaleString();
    };

    document.querySelector("#budgetList")
        ?.addEventListener("input", updateTotal);

    updateTotal();
}

function loadRouteMap() {
    return fetch(`/postschedule/api/${postId}/map`).then(res => res.json());
}

function renderRoute() {
    const mapEl = document.querySelector("#routeMap");
    if (!mapEl) return;

    loadRouteMap()
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


showPanel((location.hash || "#route").slice(1));


