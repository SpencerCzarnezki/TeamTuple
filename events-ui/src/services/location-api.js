
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


export async function addLocation(location){
    const first = makeFetchInit("POST", location);
    const response = await fetch(`http://localhost:8080/api/location`, first);
    if(response.status === 201){
        return response.json();
    }
    throw new Error("Could not add location");
}