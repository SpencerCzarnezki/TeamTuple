const url = "http://localhost:8080/api/event/user"

export async function findAllUserEvents(){
    const response = await fetch(url);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch user events");
}

export async function findEventIdsByUserId(userId){
    const response = await fetch(`${url}/find/user/${userId}`);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch eventIds");
}


function makeFetchInit(method, event){
    return {
        method: method,
        headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
        },
        body: JSON.stringify(event)
    };
}
export async function addAUserToEvent(userEvent){
    const first = makeFetchInit("POST", userEvent);
    console.log("Event-Join APi",userEvent);
    const response = await fetch(`${url}/add`, first);
    if(response.status === 201){
        return response.json();
    }
    throw new Error("Could not add user to event");
}

export async function leaveEvent(eventId, userId){
    const init = { method: "DELETE"};
    const response = await fetch(`${url}/${eventId}/${userId}`, init);
    if(response.status === 204){
        return response.json();
    }  
    throw new Error("Could not delete user from event");

}


