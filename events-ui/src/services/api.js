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
