import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem, SelectChangeEvent } from "@mui/material";

interface FoodDetail {
    id: string;
    label: string;
}

const foodDetails: FoodDetail[] = [
    { id: "SPICY", label: "Spicy" },
    { id: "VEGAN", label: "Vegan" },
    { id: "GLUTEN-FREE", label: "Gluten free" },
    { id: "HALAL", label: "Halal" },
];

interface FoodDetailsSelectProps {
    selectedDetail: string;
    setSelectedDetail: (detail: string) => void;
}

export default function FoodDetailsSelect({ selectedDetail, setSelectedDetail }: FoodDetailsSelectProps) {
    const handleChange = (event: SelectChangeEvent<string>) => {
        setSelectedDetail(event.target.value);
    };

    return (
        <FormControl fullWidth>
            <InputLabel id="food-detail-label">Food detail</InputLabel>
            <Select
                labelId="food-detail-label"
                id="food-detail"
                value={selectedDetail}
                onChange={handleChange}
                label="Food detail"
            >
                {foodDetails.map((detail) => (
                    <MenuItem key={detail.id} value={detail.id}>
                        {detail.label.toUpperCase()}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    );
}