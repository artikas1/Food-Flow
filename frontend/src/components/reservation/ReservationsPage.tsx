import React, { useState } from "react";
import { useFetchUserReservations } from "../../hooks/useFetchUserReservations.tsx";
import { Typography, Box, Pagination, Chip } from "@mui/material";
import { Loader } from "../loader/Loader.tsx";
import FoodCardListing from "../main/FoodCardListing.tsx";

export default function ReservationsPage() {
    const [page, setPage] = useState(0);
    const pageSize = 5;

    const { data, error, loading } = useFetchUserReservations({ page, pageSize });

    const handlePageChange = (_event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    const formatDate = (dateString: string) => {
        return new Intl.DateTimeFormat("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric",
        }).format(new Date(dateString));
    };

    return (
        <Box className="p-6 max-w-4xl mx-auto">
            <Typography variant="h4" className="mb-6 text-center font-semibold">
                My Reservations
            </Typography>

            {loading && <Loader />}
            {error && <Typography color="error">Error: {error}</Typography>}

            {data?.content.length === 0 && !loading && (
                <Typography className="text-center text-gray-500">No reservations found.</Typography>
            )}

            <Box className="space-y-6">
                {data?.content.map((reservation) => (
                    <Box key={reservation.id} sx={{ display: "flex", alignItems: "center", gap: 2, p: 2, border: "1px solid #ddd", borderRadius: "8px" }}>
                        <FoodCardListing
                            title={reservation.food.title}
                            city={reservation.food.city}
                            image={reservation.food.image}
                            rating="4.2/5 (14 reviews)"
                        />
                        <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
                            <Typography variant="body2" sx={{ fontWeight: "bold", color: "#555" }}>
                                Reservation Date: <span style={{ color: "#3f51b5" }}>{formatDate(reservation.reservationDate)}</span>
                            </Typography>
                            <Typography variant="body2" sx={{ fontWeight: "bold", color: "#555" }}>
                                Expiration Date: <span style={{ color: "#d32f2f" }}>{formatDate(reservation.expirationDate)}</span>
                            </Typography>
                            <Chip
                                label={reservation.isCancelled ? "Cancelled" : "Active"}
                                color={reservation.isCancelled ? "error" : "success"}
                                sx={{ fontWeight: "bold", fontSize: "0.85rem" }}
                            />
                        </Box>
                    </Box>
                ))}
            </Box>

            {data && data.totalPages > 1 && (
                <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                    <Pagination
                        count={data.totalPages}
                        page={data.number + 1}
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
    );
}
