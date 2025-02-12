import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem, CircularProgress, FormHelperText } from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";
import { API_ENDPOINTS } from "../../apiConfig.ts";
import useFetchData from "../../hooks/useFetchData.tsx";

interface CategorySelectProps {
    selectedCategory: string | null;
    setSelectedCategory: React.Dispatch<React.SetStateAction<string | null>>;
}

export default function CategorySelect({ selectedCategory, setSelectedCategory }: CategorySelectProps) {
    const { data: categories, error, loading } = useFetchData(API_ENDPOINTS.CATEGORIES);

    const handleChange = (e: SelectChangeEvent<string>) => {
        setSelectedCategory(e.target.value);
    };

    if (loading) {
        return (
            <FormControl fullWidth>
                <InputLabel>Category</InputLabel>
                <Select value="" onChange={handleChange} label="Category">
                    <MenuItem value="">
                        <CircularProgress size={24} />
                    </MenuItem>
                </Select>
            </FormControl>
        );
    }

    if (error) {
        return (
            <FormControl fullWidth>
                <InputLabel>Category</InputLabel>
                <Select value="" onChange={handleChange} label="Category">
                    <MenuItem value="">
                        <FormHelperText error>Failed to load categories</FormHelperText>
                    </MenuItem>
                </Select>
            </FormControl>
        );
    }

    return (
        <FormControl fullWidth>
            <InputLabel id="category-select-label">Category</InputLabel>
            <Select
                labelId="category-select-label"
                id="category-select"
                value={selectedCategory || ""}
                label="Category"
                onChange={handleChange}
            >
                {categories && categories.length > 0 ? (
                    categories.map((cat: string) => (
                        <MenuItem key={cat} value={cat.toUpperCase()}>
                            {cat.toUpperCase()}
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