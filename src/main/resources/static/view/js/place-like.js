document.addEventListener("DOMContentLoaded", function () {
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
		
		fetch(likeUrl, {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			},
			body: "placeId=" + encodeURIComponent(placeId) + "&placeType=" + encodeURIComponent(placeType)
		})
		.then(function (response) {
			return response.json();
		})
		.then(function (parsed) {
			if (parsed.result === "success") {
				var countEl = document.getElementById("likeCount-" + placeId);
				if (countEl) {
					countEl.innerText = "좋아요: " + parsed.likeCount;
				}
			} else {
				alert("로그인이 필요합니다.");
			}
		});
		
	});
});
