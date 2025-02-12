import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';

interface FilterProps {
    value: string[];  // Change to string[]
    onChange: (value: string[]) => void;  // Change to string[]
    categories: string[];
}

export function Filter({ value, onChange, categories }: FilterProps) {
    const handleChange = (event: SelectChangeEvent<string[]>) => {
        onChange(event.target.value as unknown as string[]);
    };

    return (
        <FormControl sx={{ m: 1, minWidth: 120, marginTop: "0", marginRight: "0" }} size="small">
            <InputLabel id="demo-select-small-label">Filter by</InputLabel>
            <Select
                labelId="demo-select-small-label"
                id="demo-select-small"
                value={value}  // Now accepts string[]
                label="Filter"
                onChange={handleChange}
                multiple  // Allow multiple selections
                sx={{
                    backgroundColor: "white"
                }}
            >
                <MenuItem value="">
                    <em>None</em>
                </MenuItem>
                {categories && categories.length > 0 ? (
                    categories.map((category: string) => (
                        <MenuItem key={category} value={category}>
                            {category}
                        </MenuItem>
                    ))
                ) : (
                    <MenuItem value="">
                        <em>No categories available</em>
                    </MenuItem>
                )}
            </Select>
        </FormControl>
    );
}

