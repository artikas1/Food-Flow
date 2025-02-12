import React, {useState} from 'react';
import { Loader } from '../loader/Loader.tsx';
import { Box } from '@mui/material';
import FoodCardListing from '../main/FoodCardListing.tsx';
import useFetchData from "../../hooks/useFetchData.tsx";

const ReservationsPage = () => {
    const { data, error, loading } = useFetchData('/reservations/user');

    console.log('Data:', data);
    console.log('Error:', error);

    if (loading) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                <Loader />
            </Box>
        );
    }

    if (error) {
        return (
            <Box sx={{ textAlign: 'center', mt: 4 }}>
                <p>Error: {error}</p>
            </Box>
        );
    }

    return (
        <Box sx={{ mt: 4, px: 3 }}>
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 4 }}>
                {data && data.content.map((food: any) => (
                    <FoodCardListing
                        key={food.id}
                        title={food.title}
                        city={food.city}
                        image={food.image}
                        rating="Reserved"
                    />
                ))}
            </Box>
        </Box>
    );
};

export default ReservationsPage;
