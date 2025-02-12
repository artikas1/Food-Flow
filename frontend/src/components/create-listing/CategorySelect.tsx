"use client"

import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem } from "@mui/material"

const categories = [
    "Fruits",
    "Vegetables",
    "Dairy products",
    "Meat",
    "Seafood",
    "Grains and cereals",
    "Baked goods",
    "Snacks",
    "Beverages",
]

export default function CategorySelect() {
    const [category, setCategory] = React.useState("")

    return (
        <FormControl fullWidth>
            <InputLabel id="category-select-label">Category</InputLabel>
            <Select
                labelId="category-select-label"
                id="category-select"
                value={category}
                label="Category"
                onChange={(e) => setCategory(e.target.value)}
            >
                {categories.map((cat) => (
                    <MenuItem key={cat} value={cat.toLowerCase()}>
                        {cat}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    )
}

