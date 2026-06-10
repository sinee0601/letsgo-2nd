function readRouteOrder() {
    return [...document.querySelectorAll("#sortableList li")].map((li, index) => ({
        visitItemId: li.dataset.visitId,
        visitOrder: index + 1
    }));
}

function submitRoute(order) {
    MyScheduleApi.saveRoute(order)
        .then(ok => alert(ok ? "동선이 수정되었습니다." : "수정에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function deleteVisit(visitId) {
    if (!confirm("이 항목을 삭제하시겠습니까?")) return;

    MyScheduleApi.deleteVisit(visitId)
        .catch(err => console.error("fetch 오류:", err));
}

function renderRoute() {
    const mapEl = document.querySelector("#routeMap");
    if (!mapEl) return;

    MyScheduleApi.loadRouteMap()
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
