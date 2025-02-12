import React, { useState } from "react";
import FoodCardListing from "./FoodCardListing.tsx";
import { useFoodSearch } from "../../hooks/useFoodSearch.tsx";
import { Loader } from "../loader/Loader.tsx";
import { Pagination, Box, TextField, Chip } from "@mui/material";

export const Main = () => {
    const { data, loading, error, setPage, setSearch, setCategories } = useFoodSearch();
    const [searchInput, setSearchInput] = useState("");

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchInput(event.target.value);
    };

    const handleSearchSubmit = () => {
        setSearch(searchInput);
    };

    if (loading) {
        return (
            <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                <Loader />
            </Box>
        );
    }

    if (error) {
        return (
            <Box sx={{ textAlign: "center", mt: 4 }}>
                <p>Error: {error}</p>
            </Box>
        );
    }

    return (
        <Box sx={{ mt: 4, px: 3 }}>
            <Box sx={{ display: "flex", justifyContent: "center", gap: 2, mb: 2 }}>
                <TextField
                    label="Search"
                    variant="outlined"
                    value={searchInput}
                    onChange={handleSearchChange}
                    onKeyPress={(e) => e.key === "Enter" && handleSearchSubmit()}
                />
            </Box>

            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", gap: 4, mt: 4 }}>
                {data?.foods?.length ? (
                    data.foods.map((food) => (
                        <FoodCardListing
                            key={food.id}
                            title={food.title}
                            city={food.city}
                            image={food.image}
                            rating="4.2/5 (14 reviews)"
                        />
                    ))
                ) : (
                    <p>No foods available</p>
                )}

                {data && data.totalPages > 1 && (
                    <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                        <Pagination
                            count={data.totalPages}
                            onChange={handlePageChange}
                            sx={{
                                "& .MuiPaginationItem-root": {
                                    color: "#AEC761",
                                    "&.Mui-selected": {
                                        backgroundColor: "#AEC761",
                                        color: "white",
                                    },
                                },
                            }}
                        />
                    </Box>
                )}
            </Box>
        </Box>
    );
};

export default Main;
