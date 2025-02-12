import React from "react";
import { TextField } from "@mui/material";

function Search({ value, onChange }: { value: string; onChange: (e: React.ChangeEvent<HTMLInputElement>) => void }) {
    return (
        <TextField
            id="outlined-size-small"
            label="Search"
            variant="outlined"
            size="small"
            value={value}
            onChange={onChange}
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
