import React, { useState } from "react";
import FoodCardListing from "./FoodCardListing.tsx";
import { useFoodSearch } from "../../hooks/useFoodSearch.tsx";
import { useFetchData } from "../../hooks/useFetchData.tsx";
import { useFetchAverageRating } from "../../hooks/useFetchAverageRating.tsx"; // Import the new hook
import { Loader } from "../loader/Loader.tsx";
import { Pagination, Box, TextField, Button } from "@mui/material";
import { Filter } from "./Filter.tsx";
import { API_ENDPOINTS } from "../../apiConfig.ts";

export const Main = () => {
    const { data, loading, error, setPage, setSearch, setCategories } = useFoodSearch();
    const { data: categories, loading: categoriesLoading } = useFetchData(API_ENDPOINTS.CATEGORIES);
    const { ratings, loading: ratingsLoading } = useFetchAverageRating(data?.foods); // Use the new hook
    const [searchInput, setSearchInput] = useState("");
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchInput(event.target.value);
    };

    const handleSearchSubmit = () => {
        setSearch(searchInput);
    };

    const handleCategoryChange = (selected: string[]) => {
        setSelectedCategories(selected);
        setCategories(selected);
    };

    const handleResetFilters = () => {
        setSearch("");
        setSearchInput("");
        setSelectedCategories([]);
        setCategories([]);
        setPage(1);
    };

    if (loading || categoriesLoading || ratingsLoading) {
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
                <Filter value={selectedCategories} onChange={handleCategoryChange} categories={categories || []} />
                <Button
                    variant="outlined"
                    color="primary"
                    onClick={handleResetFilters}
                    size="small"
                >
                    Reset Filters
                </Button>
            </Box>

            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", gap: 4, mt: 4 }}>
                {data?.foods?.length ? (
                    data.foods.map((food) => (
                        <FoodCardListing
                            key={food.id}
                            title={food.title}
                            city={food.city}
                            image={food.image}
                            rating={`${ratings[food.id] || "Loading..."} - User Score`}
                        />
                    ))
                ) : (
                    <p>No foods available</p>
                )}

                {data && data.totalPages > 1 && (
                    <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                        <Pagination
                            count={data.totalPages}
                            page={data.currentPage}
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