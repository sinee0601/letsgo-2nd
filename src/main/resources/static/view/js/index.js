(function () {
	var contextPath = document.body.getAttribute("data-context-path") || "/";
	var apiBase = window.location.origin + contextPath.replace(/\/$/, "");

	function renderPlaces(list) {
		var container = document.getElementById("leisurePlaceContainer");
		if (!container) {
			return;
		}
		while (container.firstChild) {
			container.removeChild(container.firstChild);
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
				a.textContent = "이미지";
				fig.appendChild(a);
			}
			var cap = document.createElement("figcaption");
			cap.className = "figure-caption";
			cap.textContent = p.title || "";
			fig.appendChild(cap);
			fig.appendChild(document.createTextNode(p.addr1 || ""));

			var likeBtn = document.createElement("button");
			likeBtn.type = "button";
			likeBtn.className = "like-btn";
			likeBtn.setAttribute("data-place-id", p.placeId);
			likeBtn.setAttribute("data-place-type", p.placeType || "LEISURE");
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
			cartBtn.setAttribute("data-place-type", p.placeType || "LEISURE");
			cartBtn.textContent = "담기";
			fig.appendChild(cartBtn);
			frag.appendChild(fig);
		}
		container.appendChild(frag);
	}

	function requestSort(sortOrder) {
		var url = apiBase + "/leisureListAjax?sortOrder=" + encodeURIComponent(sortOrder);

		fetch(url)
			.then(function (response) {
				if (!response.ok) {
					throw new Error("HTTP " + response.status);
				}
				return response.json();
			})
			.then(function (data) {
				if (data && data.error === true) {
					alert(data.message || "목록을 불러오지 못했습니다.");
					return;
				}
				if (!Array.isArray(data)) {
					alert("목록 형식 오류");
					return;
				}
				renderPlaces(data);
			})
			.catch(function (error) {
				if (error instanceof SyntaxError) {
					alert("JSON이 아닙니다.");
				} else {
					alert("목록 정렬 요청 실패 (" + error.message + ")");
				}
			});
	}

	var sortSel = document.getElementById("sortOrderSelect");
	if (sortSel) {
		sortSel.addEventListener("change", function () {
			requestSort(sortSel.value);
		});
	}
})();
