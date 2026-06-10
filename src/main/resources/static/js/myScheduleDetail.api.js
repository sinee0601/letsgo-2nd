const scheduleId = location.pathname.split("/").filter(Boolean)[2];

const MyScheduleApi = {
    deleteSchedule() {
        return fetch(`/myschedule/api/${scheduleId}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        }).then(res => res.json());
    },

    deleteVisit(visitId) {
        return fetch(`/myschedule/api/${scheduleId}/visit/${visitId}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        }).then(res => res.json());
    },

    saveTitle(title) {
        return fetch(`/myschedule/api/${scheduleId}/title`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ title })
        }).then(res => res.json());
    },

    saveStartAt(startAt) {
        return fetch(`/myschedule/api/${scheduleId}/startAt`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ startAt })
        }).then(res => res.json());
    },

    saveRoute(order) {
        const body = new URLSearchParams();
        order.forEach(data => {
            body.append("visitItemIds", data.visitItemId);
            body.append("visitOrders", data.visitOrder);
            body.append("distances", "0");
        });
        return fetch(`/myschedule/api/${scheduleId}/visit/orders`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body
        }).then(res => res.json());
    },

    loadRouteMap() {
        return fetch(`/myschedule/api/${scheduleId}/mapSchedule`).then(res => res.json());
    },

    saveBudget(items) {
        const payload = JSON.stringify({ items });
        return fetch(`/myschedule/api/${scheduleId}/budget`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ budgetDetail: payload })
        }).then(res => res.json());
    },

    saveTodo(todoDetail) {
        return fetch(`/myschedule/api/${scheduleId}/todo`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ todoDetail })
        }).then(res => res.json());
    },

    loadCompanions() {
        return fetch(`/myschedule/api/${scheduleId}/companion`).then(res => res.json());
    },

    addCompanion(sharedUserId) {
        return fetch(`/myschedule/api/${scheduleId}/companion`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ sharedUserId })
        }).then(res => res.json());
    },

    changePermission(sharedUserId, permission) {
        return fetch(`/myschedule/api/${scheduleId}/companion/permission`, {
            method: "PUT",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ sharedUserId, permission })
        }).then(res => res.json());
    },

    shareToPost(isAnonymous) {
        return fetch(`/myschedule/api/${scheduleId}/share`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({ isAnonymous })
        }).then(res => res.text());
    }
};
