import { DocumentCard, DocumentCardDetails, DocumentCardLocation, DocumentCardStatus, DocumentCardTitle } from "@fluentui/react";
import { Link, useNavigate } from "react-router-dom";


function EventCard({event}, {location}){
        // const navigate = useNavigate();
    function onClick(){
        // navigate("/");
    }
    
        
    return(
       <DocumentCard onClick={onClick}>
           {/* <DocumentCardTitle>{event.title}</DocumentCardTitle>
           <DocumentCardDetails>{event.description}</DocumentCardDetails> */}
           <DocumentCardTitle title="Title"></DocumentCardTitle>
           <input type="datetime-local"></input>
       </DocumentCard>

    

        
    );
}
export default EventCard;