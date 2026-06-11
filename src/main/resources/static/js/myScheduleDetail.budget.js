let budgetState = null;

function readBudgetItems() {
    const items = {};
    document.querySelectorAll(".budget-row").forEach(row => {
        const amount = Number(row.querySelector(".budget-amount").value) || 0;
        if (amount > 0) items[row.dataset.visitId] = { amount };
    });
    return items;
}

function readSavedBudget() {
    const data = document.querySelector("#budgetData");
    try {
        return JSON.parse(data?.textContent?.trim() || "{}");
    } catch (e) {
        return {};
    }
}

function calcBudgetTotal() {
    let total = 0;
    document.querySelectorAll(".budget-amount").forEach(i => total += Number(i.value) || 0);
    return total;
}

function saveBudget() {
    MyScheduleApi.saveBudget(readBudgetItems())
        .then(ok => alert(ok ? "예산이 저장되었습니다." : "저장에 실패했습니다."))
        .catch(err => console.error("fetch 오류:", err));
}

function renderBudget() {
    const budgetList    = document.querySelector("#budgetList");
    const budgetTotalEl = document.querySelector("#budgetTotal");
    const saveBudgetBtn = document.querySelector("#saveBudgetBtn");
    if (!budgetList) return;

    const showTotal = () => budgetTotalEl.textContent = calcBudgetTotal().toLocaleString();

    const items = budgetState !== null ? budgetState : (readSavedBudget().items || {});
    document.querySelectorAll(".budget-row").forEach(row => {
        const v = items[row.dataset.visitId];
        if (v) row.querySelector(".budget-amount").value = v.amount ?? "";
    });
    showTotal();

    budgetList.addEventListener("input", () => {
        budgetState = readBudgetItems(); // 편집값을 상태에 보존
        showTotal();
    });
    saveBudgetBtn?.addEventListener("click", saveBudget);
}
