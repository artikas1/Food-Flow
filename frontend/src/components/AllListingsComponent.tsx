import React from "react";
import Search from "./Search.tsx";
import Filter from "./Filter.tsx";
import FoodCardListings from './FoodCardListings.tsx';
import {Box} from "@mui/material";

function AllListingsComponent() {
    return (
        <Box sx={{ marginTop: '5rem'}}>
            {/* Flexbox container to align Search & Filter horizontally */}
            <Box
                sx={{
                display: 'flex',
                maxWidth: '50%',
                margin: 'auto',
                marginBottom: '1rem',
                }}
            >
                <Search />
                <Filter />
            </Box>

            {/* FoodCardListings below */}
            <FoodCardListings />
            <FoodCardListings />
            <FoodCardListings />
            <FoodCardListings />
        </Box>
    );
}
export default AllListingsComponent;
