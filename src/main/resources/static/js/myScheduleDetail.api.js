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
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title })
        }).then(res => res.json());
    },

    saveStartAt(startAt) {
        return fetch(`/myschedule/api/${scheduleId}/startAt`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ startAt })
        }).then(res => res.json());
    },

    saveRoute(order) {
        const payload = order.map(data => ({
            visitItemId: data.visitItemId,
            visitOrder: data.visitOrder,
            distance: "0"
        }));
        return fetch(`/myschedule/api/${scheduleId}/visit/orders`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        }).then(res => res.json());
    },

    loadRouteMap() {
        return fetch(`/myschedule/api/${scheduleId}/mapSchedule`).then(res => res.json());
    },

    saveBudget(items) {
        const budgetDetail = JSON.stringify({ items });
        return fetch(`/myschedule/api/${scheduleId}/budget`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ budgetDetail })
        }).then(res => res.json());
    },

    saveTodo(todoDetail) {
        return fetch(`/myschedule/api/${scheduleId}/todo`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ todoDetail })
        }).then(res => res.json());
    },

    loadCompanions() {
        return fetch(`/myschedule/api/${scheduleId}/companion`).then(res => res.json());
    },

    addCompanion(sharedUserId) {
        return fetch(`/myschedule/api/${scheduleId}/companion`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ sharedUserId })
        }).then(res => res.json());
    },

    changePermission(sharedUserId, permission) {
        return fetch(`/myschedule/api/${scheduleId}/companion/permission`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ sharedUserId, permission })
        }).then(res => res.json());
    },

    shareToPost(isAnonymous) {
        return fetch(`/myschedule/api/${scheduleId}/share`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ isAnonymous })
        }).then(res => res.text());
    }
};
