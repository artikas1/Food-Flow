import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem, Checkbox, ListItemText, SelectChangeEvent } from "@mui/material";

interface FoodDetail {
    id: string;
    label: string;
}

const foodDetails: FoodDetail[] = [
    { id: "spicy", label: "Spicy" },
    { id: "vegan", label: "Vegan" },
    { id: "gluten-free", label: "Gluten free" },
    { id: "halal", label: "Halal" },
];

interface FoodDetailsSelectProps {
    selectedDetails: string[];
    setSelectedDetails: (details: string[]) => void;
}

export default function FoodDetailsSelect({ selectedDetails, setSelectedDetails }: FoodDetailsSelectProps) {
    const handleChange = (event: SelectChangeEvent<string[]>) => {
        setSelectedDetails(event.target.value as string[]);
    };

    return (
        <FormControl fullWidth>
            <InputLabel id="food-details-label">Food details</InputLabel>
            <Select
                labelId="food-details-label"
                id="food-details"
                multiple
                value={selectedDetails}
                onChange={handleChange}
                renderValue={(selected) => `${(selected as string[]).length} selected`}
            >
                {foodDetails.map((detail) => (
                    <MenuItem key={detail.id} value={detail.id}>
                        <Checkbox checked={selectedDetails.indexOf(detail.id) > -1} />
                        <ListItemText primary={detail.label} />
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    );
}
