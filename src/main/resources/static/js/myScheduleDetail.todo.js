function saveTodo(todoDetail) {
    MyScheduleApi.saveTodo(todoDetail)
        .then(ok => alert(ok ? "저장되었습니다." : "저장에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function renderTodo() {
    const todoEl = document.querySelector("#todoDetail");
    const saveTodoBtn = document.querySelector("#saveTodoBtn");
    if (!todoEl) return;

    saveTodoBtn?.addEventListener("click", () => saveTodo(todoEl.value));
}
