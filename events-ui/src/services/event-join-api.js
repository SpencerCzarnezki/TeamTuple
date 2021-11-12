const url = process.env.REACT_APP_API_URL;

export async function findAllUserEvents() {
    const response = await fetch(`${url}/api/event/user`);
    if (response.status === 200) {
        return response.json();
    }
    throw new Error("Could not fetch user events");
}

export async function findEventIdsByUserId(userId) {
    const response = await fetch(`${url}/api/event/user/find/user/${userId}`);
    if (response.status === 200) {
        return response.json();
    }
    throw new Error("Could not fetch eventIds");
}


function makeFetchInit(method, event) {
    return {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(event)
    };
}
export async function addAUserToEvent(userEvent) {
    const first = makeFetchInit("POST", userEvent);
    const response = await fetch(`${url}/api/event/user/add`, first);
    if (response.status === 201) {
        return response.json();
    }
    throw new Error("Could not add user to event");
}

export async function leaveEvent(eventId, userId) {
    const init = { method: "DELETE" };
    const response = await fetch(`${url}/api/event/user/${eventId}/${userId}`, init);
    if (response.status === 204) {
        return response.json();
    }
    throw new Error("Could not delete user from event");

}


