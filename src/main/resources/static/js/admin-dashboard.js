
function openPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            const addr = data.roadAddress || data.address;
            document.getElementById('addr1').value = addr;

            const statusDiv = document.getElementById('geocoderStatus');
            const demoAddressEl = document.getElementById('demoAddress');
            const kakaoResultEl = document.getElementById('kakaoResult');

            if (statusDiv) statusDiv.style.display = 'block';
            if (demoAddressEl) demoAddressEl.textContent = addr;

            if (kakaoResultEl) {
                kakaoResultEl.textContent = '조회 중...';
                kakaoResultEl.style.color = 'orange';
            }

            if (typeof kakao !== 'undefined' && kakao.maps && kakao.maps.load) {
                kakao.maps.load(function () {
                    const geocoder = new kakao.maps.services.Geocoder();
                    const kakaoStartTime = performance.now();
                    geocoder.addressSearch(addr, function (result, status) {
                        const kakaoDuration = (performance.now() - kakaoStartTime).toFixed(1);
                        console.log("카카오 Geocoder 호출 결과:", JSON.stringify({ status: status, result: result }, null, 2));
                        if (status === kakao.maps.services.Status.OK) {
                            const x = result[0].x;
                            const y = result[0].y;
                            document.getElementById('mapx').value = x;
                            document.getElementById('mapy').value = y;
                            if (kakaoResultEl) {
                                kakaoResultEl.textContent = `성공 (X: ${Number(x).toFixed(4)}, Y: ${Number(y).toFixed(4)}) [${kakaoDuration}ms]`;
                                kakaoResultEl.style.color = 'green';
                            }
                        } else {
                            if (kakaoResultEl) {
                                kakaoResultEl.textContent = `실패 [${kakaoDuration}ms]`;
                                kakaoResultEl.style.color = 'red';
                            }
                            document.getElementById('mapx').value = '126.9784';
                            document.getElementById('mapy').value = '37.5665';
                        }
                    });
                });
            } else {
                if (kakaoResultEl) {
                    kakaoResultEl.textContent = '카카오 API 미로드';
                    kakaoResultEl.style.color = 'red';
                }
                document.getElementById('mapx').value = '126.9784';
                document.getElementById('mapy').value = '37.5665';
            }
        }
    }).open();
}


function deletePlace(placeId) {
    if (confirm("정말 이 장소를 삭제하시겠습니까?")) {
        fetch(`/admin/api/places/${placeId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            }
        });
    }
}





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
    STAY: {
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
    const regForm = document.getElementById("placeRegForm");
    if (regForm) {
        regForm.addEventListener("submit", function (e) {
            e.preventDefault();
            const formData = new FormData(regForm);
            fetch(regForm.action, {
                method: "POST",
                body: new URLSearchParams(formData)
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                }
            });
        });
    }

    const placeTypeSelect = document.getElementById("placeType");
    const lclssystm1Select = document.getElementById("lclssystm1");
    const lclssystm2Select = document.getElementById("lclssystm2");
    const lclssystm3Select = document.getElementById("lclssystm3");
    if (!placeTypeSelect) return;

    updateLclssystm1();

    placeTypeSelect.addEventListener("change", updateLclssystm1);

    lclssystm1Select.addEventListener("change", updateLclssystm2);

    lclssystm2Select.addEventListener("change", updateLclssystm3);
    function updateLclssystm1() {
        const selectedType = placeTypeSelect.value;
        lclssystm1Select.innerHTML = '<option value="">대분류 선택</option>';
        lclssystm2Select.innerHTML = '<option value="">중분류 선택</option>';
        lclssystm3Select.innerHTML = '<option value="">소분류 선택</option>';
        if (categoryTree[selectedType]) {
            const mainCategory = categoryTree[selectedType];
            const opt = document.createElement("option");
            opt.value = mainCategory.code;
            opt.textContent = mainCategory.name;
            lclssystm1Select.appendChild(opt);
            // 자동 선택 처리
            lclssystm1Select.value = mainCategory.code;
            updateLclssystm2();
        }
    }
    function updateLclssystm2() {
        const selectedType = placeTypeSelect.value;
        lclssystm2Select.innerHTML = '<option value="">중분류 선택</option>';
        lclssystm3Select.innerHTML = '<option value="">소분류 선택</option>';
        if (categoryTree[selectedType]) {
            const subCategories = categoryTree[selectedType].sub;
            for (const [code, item] of Object.entries(subCategories)) {
                const opt = document.createElement("option");
                opt.value = code;
                opt.textContent = item.name;
                lclssystm2Select.appendChild(opt);
            }
        }
    }
    function updateLclssystm3() {
        const selectedType = placeTypeSelect.value;
        const selectedSub = lclssystm2Select.value;
        lclssystm3Select.innerHTML = '<option value="">소분류 선택</option>';
        if (categoryTree[selectedType] && categoryTree[selectedType].sub[selectedSub]) {
            const details = categoryTree[selectedType].sub[selectedSub].detail;
            for (const [code, name] of Object.entries(details)) {
                const opt = document.createElement("option");
                opt.value = code;
                opt.textContent = name;
                lclssystm3Select.appendChild(opt);
            }
        }
    }
});



