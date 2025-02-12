import React, { useEffect, useState } from 'react';
import FoodCardListing from './FoodCardListing.tsx';
import useFetchAllFoods from '../../hooks/useFetchAllFoods.tsx';
import { Loader } from '../loader/Loader.tsx';
import { Pagination, Box } from '@mui/material';

export const Main = () => {
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const { data, error, loading } = useFetchAllFoods(page - 1, pageSize);  // Adjust page indexing

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);  // Update the page number
    };

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
                {data && data.content.map((food) => (
                    <FoodCardListing
                        key={food.id}
                        title={food.title}
                        city={food.city}
                        image={food.image}
                        rating="4.2/5 (14 reviews)"
                    />
                ))}

                {/* Pagination */}
                {data && data.totalPages > 1 && (
                    <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                        <Pagination
                            count={data.totalPages}
                            page={page}
                            onChange={handlePageChange}
                            sx={{
                                '& .MuiPaginationItem-root': {
                                    color: '#AEC761',
                                    '&.Mui-selected': {
                                        backgroundColor: '#AEC761',
                                        color: 'white'
                                    }
                                }
                            }}
                        />
                    </Box>
                )}
            </Box>
        </Box>
    );
};

export default Main;
