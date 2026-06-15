(function () {
	var pagination = document.querySelector("#pagination");
	var contextPath = document.body.getAttribute("data-context-path") || "";
	var apiBase = window.location.origin + contextPath.replace(/\/$/, "");
	var currentPage = 1;

	function renderPlaces(list, totalElements) {
		var container = document.getElementById("restaurantPlaceContainer");
		if (!container) {
			return;
		}
		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}

		var countHeader = document.querySelector(".content-left h3") || document.querySelector(".content-container h3");
		if (countHeader) {
			var total = (typeof totalElements === "number") ? totalElements : list.length;
			countHeader.textContent = "총 " + total + "개의 항목";
		}

		var frag = document.createDocumentFragment();
		for (var i = 0; i < list.length; i++) {
			var p = list[i];
			var fig = document.createElement("figure");
			fig.className = "figure";
			if (p.firstImage && String(p.firstImage).length > 0) {
				var img = document.createElement("img");
				img.src = p.firstImage;
				img.alt = p.title || "";
				img.className = "box-placeholder";
				fig.appendChild(img);
			} else {
				var a = document.createElement("a");
				a.href = "#";
				a.className = "box-placeholder";
				a.textContent = "이미지 없음";
				fig.appendChild(a);
			}

			var cap = document.createElement("figcaption");
			cap.className = "figure-caption";
			cap.textContent = p.title || "";
			fig.appendChild(cap);

			var addrSpan = document.createElement("span");
			addrSpan.textContent = p.addr1 || "";
			fig.appendChild(addrSpan);

			var likeBtn = document.createElement("button");
			likeBtn.type = "button";
			likeBtn.className = "like-btn";
			likeBtn.setAttribute("data-place-id", p.placeId);
			likeBtn.setAttribute("data-place-type", p.placeType || "RESTAURANT");
			likeBtn.setAttribute("data-like-url", apiBase + "/placeLikeAjax");

			var h1 = document.createElement("h1");
			h1.textContent = "❤️";
			likeBtn.appendChild(h1);
			fig.appendChild(likeBtn);

			var likeDiv = document.createElement("div");
			likeDiv.className = "like-count";
			likeDiv.id = "likeCount-" + p.placeId;
			likeDiv.textContent = "좋아요: " + p.likeCount;
			fig.appendChild(likeDiv);

			var cartBtn = document.createElement("button");
			cartBtn.type = "button";
			cartBtn.className = "add-to-cart-btn";
			cartBtn.setAttribute("data-place-id", p.placeId);
			cartBtn.setAttribute("data-place-title", p.title || "");
			cartBtn.setAttribute("data-place-type", p.placeType || "RESTAURANT");
			cartBtn.textContent = "담기";
			fig.appendChild(cartBtn);
			frag.appendChild(fig);
		}
		container.appendChild(frag);
	}

	function requestSearch(page) {
		currentPage = page || 1;

		var categoryInput = document.getElementById("categoryInput");
		var category = categoryInput ? categoryInput.value : "";

		var keywordInput = document.querySelector("input[name='keyword']");
		var keyword = keywordInput ? keywordInput.value : "";

		var sortSel = document.getElementById("sortOrderSelect");
		var sortOrder = sortSel ? sortSel.value : "name";

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function () {
			if (xhr.readyState !== 4) {
				return;
			}
			if (xhr.status !== 200) {
				alert("목록 요청 실패 (HTTP " + xhr.status + ")");
				return;
			}
			var data;
			try {
				data = JSON.parse(xhr.responseText);
			} catch (e) {
				alert("JSON이 아닙니다.");
				return;
			}
			if (data && data.error === true) {
				alert(data.message || "목록을 불러오지 못했습니다.");
				return;
			}
			var list = data && Array.isArray(data.content) ? data.content : [];
			renderPlaces(list, data.totalElements);
			renderPagination(data.page, data.totalPages);
		};

		var url = apiBase + "/list/restaurant"
			+ "?sortOrder=" + encodeURIComponent(sortOrder)
			+ "&category=" + encodeURIComponent(category)
			+ "&keyword=" + encodeURIComponent(keyword)
			+ "&page=" + currentPage;

		xhr.open("GET", url, true);
		xhr.send(null);
	}

	function renderPagination(page, totalPages) {
		if (!pagination) return;

		if (!totalPages || totalPages <= 1) {
			pagination.innerHTML = "";
			return;
		}

		var html = "";
		html += '<button type="button" class="page-btn" data-page="' + (page - 1) + '"' + (page <= 1 ? " disabled" : "") + '>이전</button>';
		for (var i = 1; i <= totalPages; i++) {
			html += '<button type="button" class="page-btn' + (i === page ? " active" : "") + '" data-page="' + i + '">' + i + '</button>';
		}
		html += '<button type="button" class="page-btn" data-page="' + (page + 1) + '"' + (page >= totalPages ? " disabled" : "") + '>다음</button>';
		pagination.innerHTML = html;

		var btns = pagination.querySelectorAll(".page-btn");
		for (var j = 0; j < btns.length; j++) {
			btns[j].addEventListener("click", function () {
				var target = Number(this.getAttribute("data-page"));
				if (target >= 1 && target <= totalPages) {
					requestSearch(target);
				}
			});
		}
	}

	window.filterCategory = function(value) {
		var categoryInput = document.getElementById("categoryInput");
		if (categoryInput) {
			categoryInput.value = value;
		}
		requestSearch(1);
	};

	var sortSel = document.getElementById("sortOrderSelect");
	if (sortSel) {
		sortSel.addEventListener("change", function () {
			requestSearch(1);
		});
	}

	var form = document.getElementById("searchForm");
	if (form) {
		form.addEventListener("submit", function (event) {
			event.preventDefault();
			requestSearch(1);
		});
	}

	requestSearch(1);
})();
