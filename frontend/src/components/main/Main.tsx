import React, { useState } from 'react';
import Header from '../Header.tsx';
import Drawer from '../Drawer.tsx';
import FoodCardListing from '../FoodCardListing.tsx';
import useFetchAllFoods from '../../hooks/useFetchAllFoods.tsx';

export const Main = () => {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const { data, error, loading } = useFetchAllFoods(page, pageSize);

    const handleOnMenuIconClick = () => {
        setIsDrawerOpen(true);
    };

    const handleDrawerClose = () => {
        setIsDrawerOpen(false);
    };

    return (
        <div className="flex flex-col w-full h-screen">
            <Header onMenuIconClick={handleOnMenuIconClick} />
            <Drawer open={isDrawerOpen} onClose={handleDrawerClose} />
            <div className="flex-grow flex justify-center items-center flex-wrap gap-4">
                {loading && <p>Loading...</p>}
                {error && <p>Error: {error}</p>}
                {data && data.content.map((food) => (
                    <FoodCardListing
                        key={food.id}
                        title={food.title}
                        city={food.city}
                        image={food.image}
                        rating="4.2/5 (14 reviews)"
                    />
                ))}
            </div>
        </div>
    );
};