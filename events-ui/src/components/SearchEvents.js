import { MDBContainer, MDBInput, MDBRow } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { findAllEvents, findByCategory, findByEventKeyWord } from "../services/api";
import EventCard from "./EventCard";



function SearchEvents() {
    const [events, setEvents] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [category, setCategory] = useState('');



    useEffect(() => {
        const emptyResult = (
            <div>No Results</div>
        );
        if (keyword.length === 0 && category.length === 0) {
            findAllEvents().then(events => {
                const holder = events.filter(e => e.status === true);

                setEvents(holder);
            });


        }
        else if (keyword.length > 0) {

            findByEventKeyWord(keyword).then(events => {
                const holder = events.filter(e => e.status === true);
                setEvents(holder);
            })
                .catch(err => emptyResult);
        }

        if (category.length > 0) {
            findByCategory(category).then(events => {
                const holder = events.filter(e => e.status === true);



                setEvents(holder);


            })
                .catch(err => emptyResult);
        }



    }, [keyword, category]);




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
        <MDBContainer fluid>
            <form onSubmit={onSubmit} color="">
                <div className="input-group m-3 text-white">Search</div>


                <div className="input-group m-3" style={{ width: 500 }}>
                    <div>

                        <MDBInput type="search" label="Search Keyword" contrast className="form-control text-white" value={keyword} onChange={onChange} id="keysearch" ></MDBInput>
                    </div>
                </div>

                <div className="input-group m-3" style={{ width: 500 }}>
                    <div>

                        <MDBInput type="search" label="Search Category" className="form-control" value={category} onChange={onChangeCategory} id="categorysearch" contrast></MDBInput>
                    </div>
                </div>

                <MDBRow className="">
                    {events.map(e => <EventCard event={e} key={e.eventId} />)}
                </MDBRow>

            </form>
        </MDBContainer>
    );

}
export default SearchEvents;