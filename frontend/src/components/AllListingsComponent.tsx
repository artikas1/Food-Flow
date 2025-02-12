import React from "react";
import Search from "./Search.tsx";
import Filter from "./Filter.tsx";
import FoodCardListings from './FoodCardListings.tsx';
import {Box} from "@mui/material";

function AllListingsComponent() {
    return (
        <Box sx={{ marginTop: '5rem'}}>
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

            <FoodCardListings />
            <FoodCardListings />
            <FoodCardListings />
            <FoodCardListings />
        </Box>
    );
}
export default AllListingsComponent;
