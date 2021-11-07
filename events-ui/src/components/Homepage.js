import { MDBCard, MDBCardBody, MDBCardImage, MDBCardOverlay, MDBCardText, MDBCardTitle, MDBCarousel, MDBCarouselCaption, MDBCarouselElement, MDBCarouselInner, MDBCarouselItem, MDBCol, MDBRow } from "mdb-react-ui-kit";

function Homepage(){
    return(
        <div className="w-auto"> 
        
        <MDBCarousel showIndicators showControls  fade className="">
            <MDBCarouselInner className="">
                <MDBCarouselItem className="active ">
                <MDBCarouselElement src="https://images.pexels.com/photos/853168/pexels-photo-853168.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"></MDBCarouselElement>
                <MDBCarouselCaption>Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>
                <MDBCarouselItem className="">
                <MDBCarouselElement src="https://images.pexels.com/photos/6203563/pexels-photo-6203563.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"></MDBCarouselElement>
                <MDBCarouselCaption>Find Local Events</MDBCarouselCaption>
                </MDBCarouselItem>

            </MDBCarouselInner>


        </MDBCarousel>
    
        
                </div>

    );
}
export default Homepage;
