import * as React from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Chip from "@mui/material/Chip";
import Box from "@mui/material/Box";

interface FilterProps {
    value: string[];
    onChange: (value: string[]) => void;
    categories: string[];
}

export function Filter({ value, onChange, categories }: FilterProps) {
    const handleChange = (event: SelectChangeEvent<string[]>) => {
        const selectedValues = event.target.value as unknown as string[];
        onChange(selectedValues);
    };

    return (
        <FormControl sx={{ m: 1, minWidth: 150, marginTop: 0, marginRight: 0 }} size="small">
            <InputLabel id="category-filter-label">Filter by</InputLabel>
            <Select
                labelId="category-filter-label"
                id="category-filter"
                multiple
                value={value}
                onChange={handleChange}
                renderValue={(selected) => (
                    <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                        {selected.map((category) => (
                            <Chip key={category} label={category} />
                        ))}
                    </Box>
                )}
                sx={{
                    backgroundColor: "white",
                }}
            >
                {categories.length > 0 ? (
                    categories.map((category) => (
                        <MenuItem key={category} value={category}>
                            {category}
                        </MenuItem>
                    ))
                ) : (
                    <MenuItem disabled>
                        <em>No categories available</em>
                    </MenuItem>
                )}
            </Select>
        </FormControl>
    );
}
