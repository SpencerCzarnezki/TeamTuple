import { MDBRow } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { findAllEvents, findByCategory, findByEventKeyWord} from "../services/api";
import EventCard from "./EventCard";



function SearchEvents() {
    const [events, setEvents] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [category, setCategory] = useState('');


    useEffect(() => {

        if (keyword.length === 0 && category.length === 0) {
            findAllEvents().then(events => setEvents(events));


        }
        else if (keyword.length > 0) {

            findByEventKeyWord(keyword).then(events => setEvents(events))
                .catch(err => emptyResult);
        }

        if(category.length > 0) {
            findByCategory(category).then(events => setEvents(events))
            .catch(err => emptyResult);
        }

     

    }, [keyword, category]);

    const emptyResult = (
        <div>No Results</div>
    );


    function onSubmit(evt) {
        evt.preventDefault();

    }
    function onChange(ev) {

        setKeyword(ev.target.value);
    }

    function onChangeCategory(evt) {
        setCategory(evt.target.value);
    }


    return (
        <form onSubmit={onSubmit}>
            <div>Search</div>


            <div className="input-group m-3" style={{ width: 500 }}>
                <div>

                    <input type="search" className="form-control" value={keyword} onChange={onChange} id="keysearch" placeholder="Search Keyword" ></input>
                </div>
            </div>

            <div className="input-group m-3" style={{ width: 500 }}>
                <div>

                    <input type="search" className="form-control" value={category} onChange={onChangeCategory} id="categorysearch" placeholder="Search Category" ></input>
                </div>
            </div>

            <MDBRow className="">
                {events.map(e => <EventCard event={e} key={e.eventId} />)}
            </MDBRow>

        </form>
    );

}
export default SearchEvents;