(function () {
	var contextPath = document.body.getAttribute("data-context-path") || "";
	var apiBase = window.location.origin + contextPath.replace(/\/$/, "");

	function renderPlaces(list) {
		var container = document.getElementById("restaurantPlaceContainer");
		if (!container) {
			return;
		}
		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}

		var countHeader = document.querySelector(".content-left h3") || document.querySelector(".content-container h3");
		if (countHeader) {
			countHeader.textContent = "총 " + list.length + "개의 항목";
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

	function requestSearch() {
		var categoryInput = document.getElementById("categoryInput");
		var category = categoryInput ? categoryInput.value : "";
		
		var keywordInput = document.querySelector("input[name='keyword']");
		var keyword = keywordInput ? keywordInput.value : "";

		var sortSel = document.getElementById("sortOrderSelect");
		var sortOrder = sortSel ? sortSel.value : "name";

		var url = apiBase + "/restaurantListAjax"
			+ "?sortOrder=" + encodeURIComponent(sortOrder)
			+ "&category=" + encodeURIComponent(category)
			+ "&keyword=" + encodeURIComponent(keyword);

		fetch(url)
			.then(function (response) {
				return response.json();
			})
			.then(function (data) {
				renderPlaces(data);
			});
	}

	window.filterCategory = function(value) {
		var categoryInput = document.getElementById("categoryInput");
		if (categoryInput) {
			categoryInput.value = value;
		}
		requestSearch();
	};

	var sortSel = document.getElementById("sortOrderSelect");
	if (sortSel) {
		sortSel.addEventListener("change", function () {
			requestSearch();
		});
	}

	var form = document.getElementById("searchForm");
	if (form) {
		form.addEventListener("submit", function (event) {
			event.preventDefault();
			requestSearch();
		});
	}
})();
