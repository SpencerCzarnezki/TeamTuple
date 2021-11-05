import { Label, SearchBox } from "@fluentui/react";

function Search(){

    return(
        <div>
            <div>Search</div>
        <Label>Search by Keyword</Label>
        <SearchBox placeholder="Search By Keyword"></SearchBox>
        <label>Search By Organizer</label>
        <SearchBox placeholder="Search By Organizer"></SearchBox>
        <label>Search By Location</label>
        <SearchBox placeholder="Search By Location"></SearchBox>

    </div>
    );

}
export default Search;