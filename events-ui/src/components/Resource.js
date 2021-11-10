function Resource({resources}){
    {console.log(resources)}
    if(resources.length !== 0){
         return(
            <div>
                {resources && resources.resource}
            </div>
         );
            
    }
    return (
        <div style={{style: "italics"}}>No Attendees</div>
    );
}
export default Resource;