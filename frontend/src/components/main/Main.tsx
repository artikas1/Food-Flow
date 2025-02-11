import React, { useState } from "react";
import Header from "../Header.tsx";
import Drawer from "../Drawer.tsx";
import FoodCardListing from "../FoodCardListing.tsx";

export const Main = () => {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

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
            <div className="flex-grow flex justify-center items-center">
                <FoodCardListing />
            </div>
        </div>

    );
};