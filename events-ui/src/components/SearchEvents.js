import { Label, SearchBox } from "@fluentui/react";
import { MDBRow } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { findAll, findAllEvents, findByCategory, findByEventKeyWord, findByKeyWord } from "../services/api";
import EventCard from "./EventCard";



function SearchEvents(){
    const navigate = useNavigate();
    const [events, setEvents] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [search, setSearch] = useState('');
    

    useEffect(() => {

        if(keyword.length > 0){
            
        findByEventKeyWord(keyword).then(events => setEvents(events))
        .catch(err  => emptyResult);
    }


    }, [keyword]);

    const emptyResult = (
        <div>No Results</div>
    );

   
    function onSubmit(evt){
        evt.preventDefault();

    }
    function onChange(ev){

        setKeyword(ev.target.value);
    }
    

    return(
        <form onSubmit={onSubmit}>
            <div>Search</div>
    
        
        <div className="input-group m-3" style={{width : 500}}>
            <div>
               
        <input type="search" className="form-control" value={keyword} onChange={onChange} id="keysearch" placeholder="Search Keyword" aria-autocomplete="false"></input>
        </div>
        </div>
      
       
        <MDBRow className="">
            {events.map(e => <EventCard event={e} key={e.eventId} /> )}
        </MDBRow>

    </form>
    );

}
export default SearchEvents;