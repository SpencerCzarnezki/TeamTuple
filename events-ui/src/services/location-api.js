
function makeFetchInit(method, location){
    return {
        method: method,
        headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
        },
        body: JSON.stringify(location)
    };
}
export async function findAllLocations(){
    const response = await fetch("http://localhost:8080/api/location");
    if(response.status === 200){
        return response.json();
    }
    throw new Error("Could not fetch locations");
}

export async function findByLocationId(locationId) {
    const response = await fetch(`http://localhost:8080/api/location/${locationId}`);
    if (response.status === 200) {
        return response.json();
    }
    throw new Error("Could not fetch location");
}

export async function saveLocation(location) {
    console.log(location);
    return location.id > 0 ? updateLocation(location) : addLocation(location);

}


export async function addLocation(location){
    const first = makeFetchInit("POST", location);
    const response = await fetch(`http://localhost:8080/api/location`, first);
    if(response.status === 201){
        return response.json();
    } else if(response.status === 400){
        const errors = await response.json();
        console.log(errors);
    }
    throw new Error("Could not add location");
}

export async function updateLocation(location) {
    const update = makeFetchInit("PUT", location);
    const response = await fetch(`http://localhost:8080/api/location/${location.id}`, update);
    if (response.status !== 204) {
        throw new Error("Could not update location");
    }
}