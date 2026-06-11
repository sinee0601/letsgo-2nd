// admin-places.js

const categoryTree = {
    TOURIST: {
        code: "TR", name: "관광지",
        sub: {
            "TR01": {
                name: "관광지",
                detail: {
                    "TR010100": "자연관광지",
                    "TR010200": "역사관광지",
                    "TR010300": "휴양관광지",
                    "TR010400": "체험관광지"
                }
            }
        }
    },
    RESTAURANT: {
        code: "FD", name: "음식",
        sub: {
            "FD01": {
                name: "한식",
                detail: {
                    "FD010100": "한식"
                }
            },
            "FD02": {
                name: "기타식문화",
                detail: {
                    "FD020100": "중식",
                    "FD020200": "일식",
                    "FD020300": "양식"
                }
            }
        }
    },
    ACCOMMODATION: {
        code: "AC", name: "숙박",
        sub: {
            "AC01": {
                name: "호텔",
                detail: {
                    "AC010100": "호텔"
                }
            },
            "AC02": {
                name: "콘도/레지던스",
                detail: {
                    "NO_DATA_CONDO": "콘도",
                    "NO_DATA_RESIDENCE": "레지던스"
                }
            },
            "AC03": {
                name: "펜션/민박",
                detail: {
                    "NO_DATA_PENSION": "펜션",
                    "AC030200": "한옥스테이",
                    "NO_DATA_FARM": "농어촌",
                    "NO_DATA_MINBAK": "민박",
                    "NO_DATA_HOMESTAY": "홈스테이"
                }
            },
            "AC04": {
                name: "모텔",
                detail: {
                    "NO_DATA_MOTEL": "모텔"
                }
            },
            "AC05": {
                name: "캠핑",
                detail: {
                    "NO_DATA_CAMP_GENERAL": "일반야영장",
                    "NO_DATA_CAMP_AUTO": "오토캠핑장",
                    "NO_DATA_CAMP_CARAVAN": "카라반",
                    "NO_DATA_CAMP_GLAMPING": "글램핑장"
                }
            }
        }
    },
    LEISURE: {
        code: "LS", name: "레저스포츠",
        sub: {
            "LS01": {
                name: "육상레저스포츠",
                detail: {
                    "NO_DATA_INLINE": "인라인",
                    "LS010200": "자전거 (둘레길)",
                    "NO_DATA_CART": "카트",
                    "NO_DATA_GOLF": "골프",
                    "NO_DATA_HORSE_RACE": "경마",
                    "NO_DATA_HORSE_RIDE": "승마",
                    "NO_DATA_SKI": "스키/스노보드",
                    "LS010900": "스케이트",
                    "NO_DATA_SLED": "썰매장",
                    "LS011200": "사격장",
                    "LS011900": "산책/둘레길"
                }
            },
            "LS02": {
                name: "수상레저스포츠",
                detail: {
                    "LS020100": "윈드서핑/수상스키",
                    "NO_DATA_KAYAK": "카약/카누",
                    "LS020300": "요트",
                    "NO_DATA_SCUBA": "스노쿨링/스킨스쿠버다이빙",
                    "NO_DATA_FISHING": "낚시",
                    "LS020700": "수영",
                    "NO_DATA_JET_SKI": "수상오토바이",
                    "NO_DATA_WATER_BIKE": "수상자전거",
                    "NO_DATA_WATER_SLED": "워터슬레드",
                    "NO_DATA_PARASAIL": "패러세일",
                    "NO_DATA_WATER_ETC": "기타수상레저스포츠"
                }
            },
            "LS03": {
                name: "항공레저스포츠",
                detail: {
                    "NO_DATA_SKYDIVE": "스카이다이빙",
                    "NO_DATA_ULTRALIGHT": "초경량비행",
                    "NO_DATA_PARAGLIDE": "헹글라이딩/패러글라이딩",
                    "NO_DATA_BALLOON": "열기구",
                    "NO_DATA_DRONE": "무인비행장치(드론)",
                    "NO_DATA_AIR_ETC": "기타항공레저스포츠"
                }
            },
            "VE12": {
                name: "복합레저스포츠",
                detail: {
                    "VE120200": "복합레저스포츠"
                }
            },
            "VE10": {
                name: "체육/수련시설",
                detail: {
                    "VE100100": "종합체육시설",
                    "VE100200": "수련시설"
                }
            }
        }
    }
};

document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".btn-edit-place").forEach(button => {
        button.addEventListener("click", function () {
            openEditPlaceModal(this);
        });
    });


    document.querySelectorAll(".btn-toggle-place").forEach(button => {
        button.addEventListener("click", function () {
            const placeId = this.getAttribute("data-id");
            const currentActive = this.getAttribute("data-active") === "true";
            togglePlaceVisibility(placeId, !currentActive);
        });
    });


    document.querySelectorAll(".btn-delete-place").forEach(button => {
        button.addEventListener("click", function () {
            const placeId = this.getAttribute("data-id");
            deletePlace(placeId);
        });
    });
});

function openEditPlaceModal(button) {
    const id = button.getAttribute('data-id');
    const title = button.getAttribute('data-title');
    const type = button.getAttribute('data-type');
    const addr1 = button.getAttribute('data-addr1');
    const addr2 = button.getAttribute('data-addr2');
    const l1 = button.getAttribute('data-l1');
    const l2 = button.getAttribute('data-l2');
    const l3 = button.getAttribute('data-l3');

    document.getElementById('edit_placeId').value = id;
    document.getElementById('edit_title').value = title;
    document.getElementById('edit_placeType').value = type;
    document.getElementById('edit_addr1').value = addr1;
    document.getElementById('edit_addr2').value = addr2;

    setupCategories(type, l1, l2, l3);

    const editModal = new bootstrap.Modal(document.getElementById('editPlaceModal'));
    editModal.show();
}

function setupCategories(type, l1, l2, l3) {
    const l1Select = document.getElementById('edit_lclssystm1');
    const l2Select = document.getElementById('edit_lclssystm2');
    const l3Select = document.getElementById('edit_lclssystm3');

    l1Select.innerHTML = '<option value="">대분류 선택</option>';
    if (categoryTree[type]) {
        const opt = document.createElement("option");
        opt.value = categoryTree[type].code;
        opt.textContent = categoryTree[type].name;
        l1Select.appendChild(opt);
        l1Select.value = l1 || categoryTree[type].code;
    }

    l2Select.innerHTML = '<option value="">중분류 선택</option>';
    if (categoryTree[type]) {
        const subs = categoryTree[type].sub;
        for (const [code, item] of Object.entries(subs)) {
            const opt = document.createElement("option");
            opt.value = code;
            opt.textContent = item.name;
            l2Select.appendChild(opt);
        }
        l2Select.value = l2 || "";
    }

    l3Select.innerHTML = '<option value="">소분류 선택</option>';
    if (categoryTree[type] && categoryTree[type].sub[l2Select.value]) {
        const details = categoryTree[type].sub[l2Select.value].detail;
        for (const [code, name] of Object.entries(details)) {
            const opt = document.createElement("option");
            opt.value = code;
            opt.textContent = name;
            l3Select.appendChild(opt);
        }
        l3Select.value = l3 || "";
    }

    document.getElementById('edit_placeType').onchange = function() {
        setupCategories(this.value, "", "", "");
    };
    l2Select.onchange = function() {
        setupCategories(document.getElementById('edit_placeType').value, l1Select.value, this.value, "");
    };
}

function togglePlaceVisibility(placeId, nextStatus) {
    const statusText = nextStatus ? '공개' : '비공개';
    if (confirm(`해당 장소를 ${statusText} 상태로 변경하시겠습니까?`)) {
        fetch(`/admin/api/places/${placeId}/toggle?isActive=${nextStatus}`, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                alert(`장소가 성공적으로 ${statusText} 상태로 변경되었습니다.`);
                location.reload();
            } else {
                alert("상태 변경 중 오류가 발생했습니다.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("네트워크 통신 오류가 발생했습니다.");
        });
    }
}

function deletePlace(placeId) {
    if (confirm("정말 이 장소를 삭제하시겠습니까?")) {
        fetch(`/admin/api/places/${placeId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert("장소가 삭제되었습니다.");
                location.reload();
            } else {
                alert("장소 삭제 중 오류가 발생했습니다.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("네트워크 통신 오류가 발생했습니다.");
        });
    }
}
