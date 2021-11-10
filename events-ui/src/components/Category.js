import { MDBCard, MDBCardBody, MDBCardImage, MDBCardOverlay, MDBCardText, MDBCardTitle, MDBCol, MDBRow, MDBTypography } from "mdb-react-ui-kit";
import { useState } from "react";
import { useNavigate } from "react-router";

function Category() {
    const [category, setCategory] = useState([]);
    const navigate = useNavigate();
    function onClick() {
        console.log(category);
        navigate("/");
    }


    return (
        <div>
            <div>Search By Category</div>
            <MDBRow className="row-cols-1 row-cols-md-4 g-5 m-2">
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://images.unsplash.com/photo-1492037766660-2a56f9eb3fcb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dGhlJTIwd29yZCUyMGFydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80"
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4" >
                                ART EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4" value={category}>
                                SPORT EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4" value={category}>
                                MUSIC EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" onClick={onClick}>
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4">
                                ENTERTAINMENT EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4">
                                LEISURE EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className="display-1"><MDBTypography variant="h4">
                                BUSINESS EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
                <MDBCol className="col">
                    <MDBCard className="h-100" alignment="center" >
                        <MDBCardImage src="https://media.istockphoto.com/photos/team-of-young-football-players-stacking-hands-before-match-picture-id1278976828?b=1&k=20&m=1278976828&s=170667a&w=0&h=1-GftAi5BTANyoB-5pqe-bpA42EfgPh_oJwxf1O5Chw="
                            position="top" />
                        <MDBCardBody>
                            <MDBCardTitle className><MDBTypography variant="h4">
                                MISCELLANEOUS EVENTS
                            </MDBTypography>
                            </MDBCardTitle>
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            </MDBRow>
        </div>);
}
export default Category;