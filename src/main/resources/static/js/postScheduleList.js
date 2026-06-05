function renderPostSchedules(postScheduleList) {
    const container = document.querySelector("#scheduleListContainer");
    if (!container) return;
        visitorView.innerHTML = postScheduleList.map(postSchedule => {
            return `
                <figure class="figure" >
                    <div >
                        ${postSchedule.title}
                    </div>

                    <a href="postschedule/api/list=${postSchedule.postId}" className="box-placeholder">
                        <img src="${postSchedule.firstImage}" alt="일정 이미지" className="box-placeholder"/></a>

                    <figcaption class="figure-caption">${postSchedule.placeTitle}</figcaption>

                    <div>
                        <button type="button" class="like-btn" data-postId="${postSchedule.postId}">
                            <h1>❤️</h1>
                        </button>
                        <span class="like-Count" >좋아요 : ${postSchedule.likeCount} </span>
                        <span>&ensp;</span>

                    </div>
                    <div>
                        <span>조회수 ${postSchedule.viewCount} </span>
                    </div>

                    <div>
                	<span>
                        📍.substring(postSchedule.addr1,0,10)"
                    </span>
                    </div>
                    <div>👤 ${postSchedule.isAnonymous == 1 ? '익명' : postSchedule.userName}</div>
                </figure>
                `;
        }).catch(error =>{
            console.error("get visitors " + error);
        });
    }

