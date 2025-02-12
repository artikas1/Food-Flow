import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

export default function Filter() {
    const [age, setAge] = React.useState('');

    const handleChange = (event: SelectChangeEvent) => {
        setAge(event.target.value);
    };

    return (
        <FormControl sx={{ m: 1, minWidth: 120, marginTop: "0", marginRight: "0" }} size="small">
            <InputLabel id="demo-select-small-label">Filter by</InputLabel>
            <Select
                labelId="demo-select-small-label"
                id="demo-select-small"
                value={age}
                label="Age"
                onChange={handleChange}
                sx={{
                    backgroundColor: "white"
                }}
            >
                <MenuItem value="">
                    <em>None</em>
                </MenuItem>
                <MenuItem value="Fru">Fru</MenuItem>
                <MenuItem value="Veg">Veg</MenuItem>
                <MenuItem value="Dai">Dai</MenuItem>
            </Select>
        </FormControl>
    );
}

