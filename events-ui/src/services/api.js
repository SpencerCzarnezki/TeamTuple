const url = "http://localhost:8080/api/event"

export async function findAllEvents(){
    const response = await fetch(url);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch events");
}


export async function findByEventKeyWord(keyword){

    const response = await fetch(`${url}/keyword/${keyword}`);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch Events by keyword");
}
export async function findByEventId(eventId){

    const response = await fetch(`${url}/${eventId}`);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch Event");

}
export async function findByCategory(category){
    const response = await fetch(`${url}/category/${category}`);
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch Events");
}
export async function findByLocationId(locationId){
    const response = await fetch(`http://localhost:8080/api/location/${locationId}`)
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch Location");
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

async function addEvent(event){
    const first = makeFetchInit("POST", event);
    const response = await fetch(url, first);
    if(response.status === 201){
        return response.json();
    }
    throw new Error("Could not add event");
}
async function updateEvent(event){
    const update = makeFetchInit("PUT", event);
    const response = await fetch(`${url}/${event.eventId}`, update);
    if (response.status !== 204){
        throw new Error("Could not update event"); 
    }
}
export async function saveEvent(event){
    console.log(event);
    return event.eventId > 0 ? updateEvent(event) : addEvent(event);
     
}
async function addEventUser(event){
    const first = makeFetchInit("POST", event);
    const response = await fetch(`${url}/user`, first);
    if(response.status === 201){
        return response.json();
    }
    throw new Error("Could not add event");
}   
export async function addResource(resource){
    const first = makeFetchInit("POST", resource);
    const response = await fetch(`http://localhost:8080/api/resources`, first);
    if(response.status === 201){
        return response.json();
    }
    throw new Error("Could not add resource");
}
export async function findResourcesByLocationId(id){
    const response = await fetch(`http://localhost:8080/api/resources/amenities/${id}`)
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch resources");
}






