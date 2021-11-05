function ErrorPage(){
    const history = useHistory();

    setTimeout(toHome, 4600);

    function toHome(){
        history.push("/");
    }
    

    return(
        <div>
    <div className="d-flex justify-content-center">
    <img src="https://media3.giphy.com/media/R7m04yMaGWVeE/200.webp?cid=ecf05e47gr1ezeiuu4mbfdl30e3yyjgind082de2yc38vl2d&rid=200.webp&ct=g" className="img-fluid " alt="filler" style={{width: 600}}></img>
    </div>
    </div>
        
    );
}
export default ErrorPage;