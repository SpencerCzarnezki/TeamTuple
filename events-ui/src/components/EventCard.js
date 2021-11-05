import { DocumentCard, DocumentCardDetails, DocumentCardLocation, DocumentCardTitle } from "@fluentui/react";
import { Link } from "react-router-dom";


function EventCard({event}, {location}){
        
    function onClick(){
        // history.push("/event/:eventId");
    }
    
    

    return(
       <DocumentCard onClick={onClick}>
           <DocumentCardTitle>{event.title}</DocumentCardTitle>
           <DocumentCardDetails>{event.description}</DocumentCardDetails>
       </DocumentCard>

    

        
    );
}
export default EventCard;