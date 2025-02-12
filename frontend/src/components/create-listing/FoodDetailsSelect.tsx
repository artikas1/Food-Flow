import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem, SelectChangeEvent, CircularProgress, Box } from "@mui/material";
import { useFetchFoodDetails } from "../../hooks/useFetchFoodDetails.tsx";
import {API_ENDPOINTS} from "../../apiConfig.ts"; // Import your updated hook

interface FoodDetailsSelectProps {
    selectedDetail: string;
    setSelectedDetail: (detail: string) => void;
}

export default function FoodDetailsSelect({ selectedDetail, setSelectedDetail }: FoodDetailsSelectProps) {
    const { data: foodDetails, error, loading } = useFetchFoodDetails(API_ENDPOINTS.DETAILS); // Fetch food details from backend

    const handleChange = (event: SelectChangeEvent<string>) => {
        setSelectedDetail(event.target.value);
    };

    if (loading) {
        return (
            <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100%" }}>
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return (
            <Box sx={{ textAlign: "center" }}>
                <p>Error loading food details: {error}</p>
            </Box>
        );
    }

    return (
        <FormControl fullWidth>
            <InputLabel id="food-detail-label">Food Detail</InputLabel>
            <Select
                labelId="food-detail-label"
                id="food-detail"
                value={selectedDetail}
                onChange={handleChange}
                label="Food Detail"
            >
                {foodDetails?.map((detail) => (
                    <MenuItem key={detail} value={detail}>
                        {detail.toUpperCase()}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    );
}
