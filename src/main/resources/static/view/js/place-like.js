document.addEventListener("DOMContentLoaded", function () {
	// Use event delegation on document.body to handle dynamically added like buttons too
	document.body.addEventListener("click", function (event) {
		var likeBtn = event.target.closest(".like-btn");
		if (!likeBtn) {
			return;
		}
		
		event.preventDefault();
		
		var placeId = likeBtn.getAttribute("data-place-id");
		var placeType = likeBtn.getAttribute("data-place-type");
		var likeUrl = likeBtn.getAttribute("data-like-url") || "/placeLikeAjax";
		
		if (!placeId) {
			return;
		}
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
				if (xhr.status === 200) {
					var parsed;
					try {
						parsed = JSON.parse(xhr.responseText);
					} catch (e) {
						console.error("Failed to parse response", e);
						return;
					}
					
					if (parsed.result === "success") {
						var countEl = document.getElementById("likeCount-" + placeId);
						if (countEl) {
							countEl.innerText = "좋아요: " + parsed.likeCount;
						}
					} else {
						alert("로그인이 필요합니다.");
					}
				} else {
					alert("좋아요 요청 실패 (HTTP " + xhr.status + ").");
				}
			}
		};
		
		xhr.open("GET", likeUrl + "?placeId=" + encodeURIComponent(placeId) + "&placeType=" + encodeURIComponent(placeType), true);
		xhr.send(null);
	});
});
