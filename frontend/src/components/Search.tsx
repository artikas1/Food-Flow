import React from "react";
import {TextField} from "@mui/material";

function Search(){

    return (
        <TextField
            id="outlined-size-small"
            label="Search"
            variant="outlined"
            size="small"
            sx={{
                width: "100%",
                justifyContent: 'top',
                '& .MuiOutlinedInput-root': {
                    backgroundColor: 'white',
                }
            }}
        />
    );
}

export default Search;