import { MDBCarousel, MDBCarouselCaption, MDBCarouselElement, MDBCarouselInner, MDBCarouselItem, MDBCol} from "mdb-react-ui-kit";
function Homepage(){
    return(
        <div className="md-4"> 
        <MDBCol className="md-4">
        <MDBCarousel showIndicators  showControls  fade light interval={10000}>
            <MDBCarouselInner>
                <MDBCarouselItem className="active">
                <MDBCarouselElement src="https://cdn.pixabay.com/photo/2014/03/26/20/55/trekking-299000_1280.jpg" className="img-fluid"></MDBCarouselElement>
                <MDBCarouselCaption>Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>
                <MDBCarouselItem className="">
                <MDBCarouselElement src="https://images.pexels.com/photos/6203563/pexels-photo-6203563.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" className="img-fluid"></MDBCarouselElement>
                <MDBCarouselCaption>Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>
                <MDBCarouselItem>
                    <MDBCarouselElement src="https://images.pexels.com/photos/69773/uss-nimitz-basketball-silhouettes-sea-69773.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" className="img-fluid"></MDBCarouselElement>
                    <MDBCarouselCaption >Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>
                <MDBCarouselItem>
                    <MDBCarouselElement src="https://images.pexels.com/photos/1549196/pexels-photo-1549196.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" className="img-fluid"></MDBCarouselElement>
                    <MDBCarouselCaption >Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>

            </MDBCarouselInner>
            </MDBCarousel>
            </MDBCol>
        
                </div>

    );
}
export default Homepage;
